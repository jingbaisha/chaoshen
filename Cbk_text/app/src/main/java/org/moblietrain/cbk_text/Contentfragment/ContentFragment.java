package org.moblietrain.cbk_text.Contentfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.moblietrain.cbk_text.ContentAdapter.InfoListadapter;
import org.moblietrain.cbk_text.Dateactivity;
import org.moblietrain.cbk_text.R;
import org.moblietrain.cbk_text.bean.Cbkdate;
import org.moblietrain.httputils.qianfeng.HttpHelper;
import org.moblietrain.httputils.qianfeng.Request;
import org.moblietrain.httputils.qianfeng.StringRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/22.
 */

public class ContentFragment extends Fragment {

    private static final String TAG = ContentFragment.class.getSimpleName();

    private ListView listView;
    private PtrClassicFrameLayout mLv;
    private InfoListadapter adapter;
    private int class_Id;
    private List<Cbkdate> infos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.frag_content, null);
        initView(view);
        Log.i(TAG, "----------------------onCreateView");
        class_Id = getArguments().getInt("id");
        if (bundle != null) {
            Parcelable[] ps = bundle.getParcelableArray("cache");
            Cbkdate[] tc = (Cbkdate[]) bundle.getParcelableArray("cache");
            if (tc != null && tc.length != 0) {
                infos = Arrays.asList(tc);
                adapter = new InfoListadapter(infos);
                listView.setAdapter(adapter);
            } else {
                getDataFromNetwork();
            }
        } else {

            getDataFromNetwork();
        }
        return view;

    }


    //下拉刷新数据
    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.frag_content_lv);
        mLv = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mLv.setResistance(1.7f);
        mLv.setRatioOfHeaderHeightToRefresh(1.2f);
        mLv.setDurationToClose(200);
        mLv.setDurationToCloseHeader(1000);
        // default is false
        mLv.setPullToRefresh(true);
        // default is true
        mLv.setKeepHeaderWhenRefresh(true);

        mLv.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDataFromNetwork();
            }
        });
        inititem();
    }

    //跳转到详情页面
    private void inititem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (infos != null && infos.get(position) != null) {
                    //得到数组的数据
                    long item=infos.get(position).getId();
                    String title=infos.get(position).getTitle();
                    String description=infos.get(position).getDescription();
                    String keywords=infos.get(position).getKeywords();
                    String time=infos.get(position).getTime();
                    //
                    Intent intent = new Intent(getActivity(), Dateactivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("title",title);
                    bundle.putString("description",description);
                    bundle.putString("keywords",keywords);
                    bundle.putString("time",time);
                    bundle.putLong("id",item);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }

    public void getDataFromNetwork() {
        String url = "http://www.tngou.net/api/info/list?id=" + class_Id;
        StringRequest req = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {

            }

            @Override
            public void onResonse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i(TAG, "==============response" + response);
                    Log.i(TAG, "==============JSONObject" + jsonObject.length());
                    List<Cbkdate> listinfo = parseJson2List(jsonObject);
                    Log.i(TAG, "==============listinfo" + listinfo);
                    if (listinfo != null) {
                        infos.clear();
                        infos.addAll(listinfo);
                        if (adapter == null) {
                            adapter = new InfoListadapter(infos);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(adapter);
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLv.refreshComplete();
                    }
                });

            }
        });
        Log.i(TAG,"========wwwwww=="+url);
        HttpHelper.addRequest(req);
    }

    private List<Cbkdate> parseJson2List(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "----------------------parseJson2List1方法：");

        if (jsonObject == null) return null;
        JSONArray arry = jsonObject.getJSONArray("tngou");
        Log.i(TAG, "----------------------parseJson2List2方法：");
        if (arry == null || arry.length() == 0) return null;
        List<Cbkdate> list = new ArrayList<>();
        int lenth = arry.length();
        JSONObject obj = null;
        Cbkdate cbkdate = null;
        Log.i(TAG, "----------------------parseJson2List3方法：");
        for (int i = 0; i < lenth; i++) {
            obj = arry.getJSONObject(i);
            cbkdate = new Cbkdate();

            cbkdate.setDescription(obj.optString("description"));
            cbkdate.setRcount(obj.optInt("rcount"));
            cbkdate.setTitle(obj.optString("title"));
            cbkdate.setKeywords(obj.optString("keywords"));
            long time = obj.optLong("time");
            String format = new SimpleDateFormat("yyyyMMdd:hhmmss").format(time);
            cbkdate.setTime(format);
            cbkdate.setImg(obj.getString("img"));
            cbkdate.setId(obj.optInt("Id"));
            Log.i(TAG, "----------------------list的长度：" + list.size());
            list.add(cbkdate);
        }

        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (infos == null || infos.size() == 0) return;
        Cbkdate[] date = new Cbkdate[infos.size()];
        infos.toArray(date);
        outState.putParcelableArray("cache", date);
    }
}
