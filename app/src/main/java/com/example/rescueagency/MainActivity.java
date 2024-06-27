package com.example.rescueagency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rescueagency.admin.AdminAddAgencyFragment;
import com.example.rescueagency.admin.AdminAddCategoryFragment;
import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.agency.SOSRequestRVFragment.AgencyHomeFragment;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.agency.AgencyRequestHistoryFragment;
import com.example.rescueagency.check_status_fragment.RequestHistoryFragment;
import com.example.rescueagency.main_menu_fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    private SharedPreferences sf;
    private String userType;
    Fragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sf=getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
        userType=sf.getString(Constant.USER_TYPE,null);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);
        Fragment fragment;
        if(userType.equalsIgnoreCase(Constant.LOGIN_AS_ADMIN)){
            fragment=new AdminHomeFragment();
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.addAgency).setVisible(true);
            menu.findItem(R.id.userHistory).setVisible(false);
        }else if(userType.equalsIgnoreCase(Constant.LOGIN_AS_AGENCY)){
            fragment=new AgencyHomeFragment();
        }else{
             fragment=new HomeFragment();
         }
        transaction.replace(R.id.frameLayout,fragment).commit();
    }

    private void showBottomSheetDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.choose_category_dialog_layout, null);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        CardView idAddAgency = view.findViewById(R.id.idChooseCategoryCV2);
        CardView idAddCategory = view.findViewById(R.id.idChooseCategoryCV);

        idAddAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddAgencyFragment adminFragment=new AdminAddAgencyFragment();
                transaction.replace(R.id.frameLayout,adminFragment);
                transaction.addToBackStack("AdminAddAgencyFragment");
                transaction.commit();
                dialog.dismiss();
            }
        });

        idAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddCategoryFragment adminFragment=new AdminAddCategoryFragment();
                transaction.replace(R.id.frameLayout,adminFragment);
                transaction.addToBackStack("AdminAddCategoryFragment");
                transaction.commit();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int menu=item.getItemId();
        transaction= getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);
        FragmentManager fragmentManager=getSupportFragmentManager();
        int count=fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            fragmentManager.popBackStack();
        }
        if(menu==R.id.home){
            if(userType.equalsIgnoreCase(Constant.LOGIN_AS_ADMIN)){
                fragment=new AdminHomeFragment();
            }else if(userType.equalsIgnoreCase(Constant.LOGIN_AS_AGENCY)){
                fragment=new AgencyHomeFragment();
            }else{
                fragment=new HomeFragment();
            }
            transaction.replace(R.id.frameLayout,fragment).commit();
        }else if(menu==R.id.profile){
            if(userType.equalsIgnoreCase(Constant.LOGIN_AS_AGENCY)){

                    fragment=new AgencyProfileFragment();

            }else{

                fragment=new ProfileFragment();
            }
            transaction.replace(R.id.frameLayout,fragment).commit();
        }
        else if(menu==R.id.userHistory){
            if(userType.equalsIgnoreCase(Constant.LOGIN_AS_AGENCY)){

                    fragment=new AgencyRequestHistoryFragment();


            }else {
                fragment=new RequestHistoryFragment();
            }
            transaction.replace(R.id.frameLayout,fragment).commit();
        }
        else if(menu==R.id.addAgency){
            showBottomSheetDialog();
        }
        return true;
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }



}