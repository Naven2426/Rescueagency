package com.example.rescueagency.admin.spinner;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rescueagency.Constant;
import com.example.rescueagency.R;

import org.w3c.dom.Text;

import java.util.List;

/********************************************
 *     Created by DailyCoding on 15-May-21.  *
 ********************************************/

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private List<Fruit> fruitList;

    public FruitAdapter(Context context, List<Fruit> fruitList) {
        this.context = context;
        this.fruitList = fruitList;
    }

    @Override
    public int getCount() {
        return fruitList != null ? fruitList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder")
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_fruit, viewGroup, false);
//        RelativeLayout relativeLayout=rootView.findViewById(R.id.idRelativeLayout);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(fruitList.get(i).getName());

//        image.setImageResource(fruitList.get(i).getImage());
        Glide.with(context).load(fruitList.get(i).getImage()).placeholder(R.mipmap.error)
                .error(R.mipmap.error).into(image);

        return rootView;
    }
}
