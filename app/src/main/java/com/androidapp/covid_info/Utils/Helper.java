package com.androidapp.covid_info.Utils;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidapp.covid_info.R;

public class Helper {

    public static void addWithoutBackStack(Activity mActivity, Fragment fragment, String TAG) {
        if (mActivity != null) {
            FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

            Fragment curFrag = fragmentManager.findFragmentByTag(TAG);
            if (curFrag == null || !curFrag.isVisible()) {
                fragmentTransaction.replace(R.id.container, fragment, TAG);
                fragmentTransaction.commit();
            } else {
                // fragmentTransaction.replace(R.id.container, fragment, TAG);
                //fragmentTransaction.commit();
            }
        }
    }

    public static Fragment getCurrentFragment(Activity mActivity) {
        if (mActivity != null)
            return ((AppCompatActivity) mActivity).getSupportFragmentManager().findFragmentById(R.id.container);
        else return null;
    }
    public static void addFragment(Activity mActivity, Fragment fragment, String tag) {
        FragmentTransaction transaction = ((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment, tag);
            transaction.addToBackStack(null);
//            transaction.commit();
            transaction.commitAllowingStateLoss();
        } else {
            Fragment fragment1 = ((AppCompatActivity) mActivity).getSupportFragmentManager().findFragmentByTag(tag);
            transaction.replace(R.id.container, fragment1, tag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
