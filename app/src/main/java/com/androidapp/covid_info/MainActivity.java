package com.androidapp.covid_info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.covid_info.BroadcastReceiver.NetworkChangeReceiver;
import com.androidapp.covid_info.Utils.Helper;
import com.androidapp.covid_info.coustumUI.GradientTextView;
import com.androidapp.covid_info.fragments.HomeFragment;
import com.androidapp.covid_info.fragments.NotificationFragment;
import com.androidapp.covid_info.fragments.SmsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        HomeFragment homeFragmentt = new HomeFragment();
        Helper.addWithoutBackStack(MainActivity.this,homeFragmentt,HomeFragment.TAG);
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            HomeFragment homeFragment = new HomeFragment();
                            Helper.addFragment(MainActivity.this,homeFragment,HomeFragment.TAG);
                            return true;
                        case R.id.navigation_sms:
                            Fragment fragment = Helper.getCurrentFragment(MainActivity.this);
                            if(fragment != null  ){
                                if(fragment instanceof SmsFragment){

                                }else {
                                    Showpopupconfirmation();
                                }
                            }

                            return true;
                    }
                    return false;
                }
            };
    private void Showpopupconfirmation() {

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.textfield_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        GradientTextView confirm = (GradientTextView) dialog.findViewById(R.id.confirm);
        GradientTextView cancel = (GradientTextView) dialog.findViewById(R.id.cancel);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.check_box);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBox.isChecked()){
                    SmsFragment smsFragment = new SmsFragment();
                    Helper.addWithoutBackStack(MainActivity.this,smsFragment,SmsFragment.TAG);
                    dialog.cancel();
                }
            }
        });

    }
}