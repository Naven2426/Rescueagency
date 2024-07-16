package com.example.rescueagency;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.agency.AgencyUserChatFragment;
import com.example.rescueagency.databinding.ActivityUserAgencyChatBinding;
import com.example.rescueagency.databinding.ChatLeftSideBinding;
import com.example.rescueagency.databinding.ChatRightSideBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class UserAgencyChatActivity extends AppCompatActivity {

    ActivityUserAgencyChatBinding binding;
    private Socket socket;
    private final RecyclerAdapter adapter = new RecyclerAdapter();
    String roomId;
    String userName,number;
    SharedPreferences sf;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = ActivityUserAgencyChatBinding.inflate(getLayoutInflater());
        sf = getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        userName = sf.getString(Constant.SF_NAME,"");
        String id = sf.getString(Constant.SF_USERID,"");
        number = sf.getString(Constant.SF_PHONE,"");
        roomId = getIntent().getStringExtra("roomId");

        binding.idChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.idChatRecyclerView.setAdapter(adapter);
        try {
            socket = IO.socket("https://7aca-2409-4072-92-ffad-25f9-cb9-f0d9-ba8d.ngrok-free.app");
            socket.emit("joinRoom",getJsonRoomObject(userName,roomId));
            socket.on("message",new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject data = (JSONObject) args [0];https://6321-2409-4072-80e-c767-9da-c714-d0cf-d9a.ngrok-free.app
                            try {
                                String userName = data.getString("user");
                                String message = data.getString("text");
                                String number= data.getString("number");
                                ChatModel chat= new ChatModel(userName,message,number);
//                                Toast.makeText(UserAgencyChatActivity.this, message, Toast.LENGTH_SHORT).show();
                                adapter.setData(chat,UserAgencyChatActivity.this);
//                            binding.idAgencyChatRecyclerView.scrollToPosition(chatList.size() - 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            socket.connect();
            if(socket.connected()) Toast.makeText(UserAgencyChatActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            else Toast.makeText(UserAgencyChatActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
            click();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        click();
        setContentView(binding.getRoot());
    }

    private JSONObject getJsonRoomObject(String userName,String roomId) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("username",userName);
            jsonObject.put("room",roomId);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void click() {
        binding.idChatBackButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.idChatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.idChatEditText.getText().toString();
                if (!message.isEmpty()) {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("username", userName);
                        data.put("message", message);
                        data.put("number", number);
                        data.put("room", roomId);
                        socket.emit("sendMessage", data);
                        binding.idChatEditText.setText("");
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
        public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == LEFT_VIEW) {
                ChatLeftSideBinding binding = ChatLeftSideBinding.inflate(inflater, parent, false);
                return new RecyclerAdapter.LeftViewHolder(binding);
            } else {
                ChatRightSideBinding binding = ChatRightSideBinding.inflate(inflater, parent, false);
                return new RecyclerAdapter.RightViewHolder(binding);
            }
        }

        @Override
        public int getItemCount() {
            return listOfChat.size();
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
            ChatModel chat = listOfChat.get(position);

            if (chat.getNumber().equals(number)) {
                ((RecyclerAdapter.RightViewHolder) holder).bind(chat);
            } else {
                ((RecyclerAdapter.LeftViewHolder) holder).bind(chat);
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

        static class LeftViewHolder extends RecyclerAdapter.ViewHolder {
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

        static class RightViewHolder extends RecyclerAdapter.ViewHolder {
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