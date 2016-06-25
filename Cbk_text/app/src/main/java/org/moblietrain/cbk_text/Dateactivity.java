package org.moblietrain.cbk_text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.moblietrain.cbk_text.Contentfragment.ContentFragment;

/**
 * Created by Administrator on 2016/6/23.
 */

public class Dateactivity extends Activity {
    private static final String TAG = Dateactivity.class.getName();
    private TextView text_biaoti, text_miaoshu, text_shijian, text_neirong;
    private Bundle bundle=null;
    private ImageView image_back,imge_fen,image_shoucan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_layout);
        image_back= (ImageView) findViewById(R.id.img_one);
        imge_fen= (ImageView) findViewById(R.id.img_two);
        image_shoucan= (ImageView) findViewById(R.id.img_three);

        findViewById(R.id.img_two);
        findViewById(R.id.img_three);
        text_biaoti = (TextView) findViewById(R.id.text_biaoti);
        text_miaoshu = (TextView) findViewById(R.id.text_maoshu);
        text_neirong = (TextView) findViewById(R.id.text_neirong);
        bundle=getIntent().getExtras();
        Log.i(TAG,"-------------onCreate");
        initView();

    }

    private void initView() {
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String keywords=bundle.getString("keywords");
        String time=bundle.getString("time");
        long id=bundle.getLong("id");
        Log.i(TAG,"==============initView"+title);
        text_biaoti.setText(title);
        text_miaoshu.setText(keywords);
        text_neirong.setText(description);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dateactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
