package com.givon.baseproject.xinlu.act;

import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseFragmentActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.fragment.FraHome;
import com.givon.baseproject.xinlu.fragment.FraPublish;
import com.givon.baseproject.xinlu.fragment.FraUserCenter;
import com.givon.baseproject.xinlu.util.FragmentFactory;

public class MainActivity extends BaseFragmentActivity {

	private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private Fragment fromFragment;
	private Fragment toFragment;
	private Fragment mContent;
	private Map<String, Fragment> mFragmentMap = new HashMap<String, Fragment>();
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        fragmentManager = this.getSupportFragmentManager();;
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);
//                transaction.replace(R.id.content, fragment);
//                transaction.commit();
            	switch (checkedId) {
                case R.id.rb_1:
                    switchContentClass(FraHome.class);
                    break;
                case R.id.rb_2:
                    switchContentClass(FraPublish.class);
                    break;
                case R.id.rb_3:
                    switchContentClass(FraUserCenter.class);
                    break;
                
            }
            	
            }
        });
        switchContentClass(FraHome.class);
    }
    
    
    public void switchContentClass(Class classz) {
		if (!mFragmentMap.containsKey(classz.getSimpleName())) {
			System.out.println(classz.getName());
			toFragment = Fragment.instantiate(MainActivity.this, classz.getName());
			if (classz.getSimpleName().equals(
					FraHome.class.getSimpleName())) {
				mFragmentMap.put(classz.getSimpleName(), toFragment);
			}
		} else {
			toFragment = mFragmentMap.get(classz.getSimpleName());
		}
		fromFragment = mContent;
		switchContent(fromFragment, toFragment);
	}
    
    public void switchContent(Fragment from, Fragment to) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();

			transaction
					.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if (!to.isAdded()) { // 先判断是否被add过
				if (from == null) {
					transaction.add(R.id.main_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
					return;
				}
				transaction.hide(from).add(R.id.main_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

}
