package com.givon.baseproject.xinlu.util;


import android.support.v4.app.Fragment;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.fragment.FraHome;
import com.givon.baseproject.xinlu.fragment.FraPublish;
import com.givon.baseproject.xinlu.fragment.FraUserCenter;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.rb_1:
//                fragment = new FraHome();
                break;
            case R.id.rb_2:
                fragment = new FraPublish();
                break;
            case R.id.rb_3:
                fragment = new FraUserCenter();
                break;
            
        }
        return fragment;
    }
}
