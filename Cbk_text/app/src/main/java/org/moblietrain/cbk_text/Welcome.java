package org.moblietrain.cbk_text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telecom.Connection;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.moblietrain.cbk_text.content.Contentkeys;
import org.moblietrain.cbk_text.utils.Pref_Utils;


/**
 * Created by Administrator on 2016/6/20.
 */

public class Welcome extends Activity {
    private static final String LAG = "Welcome";
    private ViewPager viewPager;
    private LinearLayout layout;
    private Mypageradapter adapter;
    private int[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        layout = (LinearLayout) findViewById(R.id.welcome_ll);
        initisview();
        adapter = new Mypageradapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter);
    }

    private ImageView[] imageView = new ImageView[images.length];

    private void initisview() {
        Log.i(LAG,"===================initisview");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
        ViewGroup.LayoutParams lvp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        lp.leftMargin = 10;
        //初始化
        ImageView iv = null;
        View view=null;

        for (int i = 0; i < images.length; i++) {
            iv = new ImageView(this);
            //对图片的缩放,全屏
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(lvp);
            imageView[i] = iv;
            imageView[i].setImageResource(images[i]);
            Log.i(LAG,"===================for");
            if(i==images.length-1){
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Welcome.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            view=new View(this);
            if (i==0){
                view.setBackgroundResource(R.drawable.page_now);
            }else {
                view.setBackgroundResource(R.drawable.page);
            }
            view.setLayoutParams(lp);
            layout.addView(view);
        }

    }

    class Mypageradapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            return images.length;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageView[position]);
            return imageView[position];
        }

        //图片的点击
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            layout.getChildAt(index).setBackgroundResource(R.drawable.page);
            layout.getChildAt(position).setBackgroundResource(R.drawable.page_now);
            index=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Pref_Utils.putBoolean(this, Contentkeys.First_content,false);
    }
}
