package org.moblietrain.cbk_text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.moblietrain.cbk_text.content.Contentkeys;
import org.moblietrain.cbk_text.utils.Pref_Utils;

/**
 * Created by Administrator on 2016/6/20.
 */

public class Larding extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.larding);

        initView();
    }
    private void initView() {
        //线程休眠
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(Larding.this,Welcome.class);
               if(!isFirstactivity()){
                    intent.setClass(Larding.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
    }

    public boolean isFirstactivity(){
        return Pref_Utils.getBoolean(this, Contentkeys.First_content,true);
    }
}
