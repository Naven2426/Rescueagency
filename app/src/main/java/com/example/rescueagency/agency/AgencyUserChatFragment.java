package com.example.rescueagency.agency;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.net.UriCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.ChatLeftSideBinding;
import com.example.rescueagency.databinding.ChatRightSideBinding;
import com.example.rescueagency.databinding.FragmentAgencyUserChatBinding;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;


public class AgencyUserChatFragment extends Fragment {

    FragmentAgencyUserChatBinding binding;
    private Socket socket;
    private final RecyclerAdapter adapter = new RecyclerAdapter();
    String roomId;
    String userName,number;
    SharedPreferences sf;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyUserChatBinding.inflate(inflater, container, false);
        sf = getActivity().getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        userName = sf.getString(Constant.SF_USERNAME,"");
        String id = sf.getString(Constant.SF_USERID,"");
        number = sf.getString(Constant.SF_PHONE,"");
        Toast.makeText(requireContext(),"number "+ number+" id "+id, Toast.LENGTH_SHORT).show();
        assert getArguments() != null;
        roomId = getArguments().getString("roomId");
        MainActivity mainActivity= (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        try {
            socket = IO.socket("https://e2ff-103-249-82-242.ngrok-free.app");
            socket.emit("joinRoom",getJsonRoomObject(userName,roomId));
            socket.on("message",new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject data = (JSONObject) args [0];https://6321-2409-4072-80e-c767-9da-c714-d0cf-d9a.ngrok-free.app
                            try {
                                String userName = data.getString("user");
                                String message = data.getString("text");
                                String number= data.getString("number");
                                ChatModel chat= new ChatModel(userName,message,number);
                                adapter.setData(chat,requireContext());
//                            binding.idAgencyChatRecyclerView.scrollToPosition(chatList.size() - 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            socket.connect();
            if(socket.connected()) Toast.makeText(requireContext(), "Connected", Toast.LENGTH_SHORT).show();
            else Toast.makeText(requireContext(), "Not Connected", Toast.LENGTH_SHORT).show();
            click();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return binding.getRoot();
    }
    private void socketConnection(){

    }

    private JSONObject getJsonRoomObject(String userName,String roomId) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("username",userName );
            jsonObject.put("room",roomId);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void initSocket() throws  URISyntaxException {
    }
    private void click(){
        binding.idAgencyUserChatBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
        binding.idAgencyChatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.idAgencyChatEditText.getText().toString();
                if (!message.isEmpty()) {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("username", userName);
                        data.put("message", message);
                        data.put("number", number);
                        data.put("room", roomId);
                        socket.emit("sendMessage", data);
                        ChatModel chat= new ChatModel(userName,message,number);
                        adapter.setData(chat,requireContext());
//                    chatList.add(new ChatModel(userName, message, 0, "SEND"));
//                    adapter.setData(new ChatModel(userName, message, 0, "SEND"));
//                        binding.recyclerView.scrollToPosition(chatList.size() - 1);
                        binding.idAgencyChatEditText.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public static class ChatModel {
        private final String userName;
        private final String message;
        private final String number;
        public ChatModel(String userName, String message, String number) {
            this.userName = userName;
            this.message = message;
            this.number=number;
        }
        public String getNumber() {
            return number;
        }
        public String getUserName() {
            return userName;
        }
        public String getMessage() {
            return message;
        }
    }
    static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
//        ActivityMyChatBinding binding;
        private static final int LEFT_VIEW = 0;
        private static final int RIGHT_VIEW = 1;
        SharedPreferences sf;
        private final List<ChatModel> listOfChat = new ArrayList<>();
        String number;
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == LEFT_VIEW) {
                ChatLeftSideBinding binding = ChatLeftSideBinding.inflate(inflater, parent, false);
                return new LeftViewHolder(binding);
            } else {
                ChatRightSideBinding binding = ChatRightSideBinding.inflate(inflater, parent, false);
                return new RightViewHolder(binding);
            }
        }

        @Override
        public int getItemCount() {
            return listOfChat.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ChatModel chat = listOfChat.get(position);

            if (chat.getNumber().equals(number)) {
                ((RightViewHolder) holder).bind(chat);
            } else {
                ((LeftViewHolder) holder).bind(chat);
            }
        }

        @Override
        public int getItemViewType(int position) {
            ChatModel chat = listOfChat.get(position);
            return chat.getNumber().equals(number) ? RIGHT_VIEW : LEFT_VIEW;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setData(ChatModel list, Context context) {
//            listOfChat.clear();
            sf = context.getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
            number = sf.getString(Constant.SF_PHONE,"");
            listOfChat.add(list);
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        static class LeftViewHolder extends ViewHolder {
            private final ChatLeftSideBinding binding;

            public LeftViewHolder(ChatLeftSideBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(ChatModel chat) {
//                binding.itemUsername.setText(chat.getUserName());
                binding.itemMessage.setText(chat.getMessage());
            }
        }

        static class RightViewHolder extends ViewHolder {
            private final ChatRightSideBinding binding;

            public RightViewHolder(ChatRightSideBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(ChatModel chat) {
                binding.itemMessage.setText(chat.getMessage());
            }
        }
    }


}