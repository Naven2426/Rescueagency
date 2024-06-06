package com.example.rescueagency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.rescueagency.LoginActivityFragments.StartingFragment;
import com.example.rescueagency.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_login);
        SharedPreferences sf=getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
        String user_id=sf.getString(Constant.SF_USERID,null);
        if(user_id!=null)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        else{
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                    R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.loginFrameLayout,new StartingFragment()).commit();
        }

    }
}