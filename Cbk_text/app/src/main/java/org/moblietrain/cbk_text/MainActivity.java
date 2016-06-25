package org.moblietrain.cbk_text;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.moblietrain.cbk_text.Contentfragment.ContentFragment;
import org.moblietrain.cbk_text.bean.TabCbk;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity{
    private static final String TAG =MainActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tab_text;
    private List<Fragment> list;
    private SlidingMenu menu;

    private TabCbk[] tabs=new TabCbk[]{

            new TabCbk(6,"社会热点"),
            new TabCbk(1,"企业要闻"),
            new TabCbk(2,"医疗新闻"),
            new TabCbk(3,"生活贴士"),
            new TabCbk(4,"药品新闻"),
            new TabCbk(7,"疾病快讯"),
            new TabCbk(5,"食品新闻"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.home_view);
        tab_text= (TabLayout) findViewById(R.id.home_tab);
        Mycontent fragmet_ad=new Mycontent(getSupportFragmentManager());
        viewPager.setAdapter(fragmet_ad);
        tab_text.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_text.setupWithViewPager(viewPager);
        Log.i(TAG,"----------------------onCreate");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    class Mycontent extends FragmentPagerAdapter{

        public Mycontent(FragmentManager fm) {
            super(fm);

        }
        @Override
        public Fragment getItem(int position) {
            Log.i(TAG,"-------------------getItem"+position);
            ContentFragment fragment=new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",tabs[position].Class_id);
            fragment.setArguments(bundle);
            return fragment;

        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position].name;
        }
    }


}
