package org.moblietrain.cbk_text.ContentAdapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.moblietrain.cbk_text.R;
import org.moblietrain.cbk_text.bean.Cbkdate;
import org.moblietrain.httputils.qianfeng.BitmapRequest;
import org.moblietrain.httputils.qianfeng.HttpHelper;
import org.moblietrain.httputils.qianfeng.Request;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */

public class InfoListadapter extends BaseAdapter {
    private static final String TAG = InfoListadapter.class.getSimpleName();

    private List<Cbkdate> infolist;

    public InfoListadapter(List<Cbkdate> infolist) {
        this.infolist = infolist;
    }

    @Override
    public int getCount() {
        return infolist==null?0:infolist.size();
    }

    @Override
    public Cbkdate getItem(int position) {
        return infolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.content_lv_item, null);
            vh.iv_icon = (ImageView) convertView.findViewById(R.id.item_iv);
            vh.tv_desc = (TextView) convertView.findViewById(R.id.item_tv_desc);
            vh.tv_rcount = (TextView) convertView.findViewById(R.id.item_tv_rc);
            vh.tv_time = (TextView) convertView.findViewById(R.id.item_tv_time);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        Cbkdate info = getItem(position);
        vh.tv_desc.setText(info.getDescription());
        vh.tv_rcount.setText("" + info.getRcount());
        vh.tv_time.setText(info.getTime());
        vh.iv_icon.setImageResource(R.mipmap.ic_launcher);
        loadimage(vh.iv_icon, "http://tnfs.tngou.net/image" + info.getImg() + "_100x100");
        return convertView;
    }

    class ViewHolder {
        TextView tv_desc, tv_time, tv_rcount;
        ImageView iv_icon;
    }

    //图片的下载
    private void loadimage(final ImageView iv, final String url) {
        iv.setTag(url);
        BitmapRequest bitmapRequest = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onEror(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResonse(final Bitmap response) {
                if (iv != null && response != null && ((String) iv.getTag()).equals(url)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(response);
                        }
                    });
                }
            }
        });

        HttpHelper.addRequest(bitmapRequest);
    }
}