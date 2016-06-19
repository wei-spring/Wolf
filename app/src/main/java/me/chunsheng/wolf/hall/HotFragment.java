package me.chunsheng.wolf.hall;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import me.chunsheng.wolf.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link .} interface
 * to handle interaction events.
 * Use the {@link HotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DOUYU_LIVE_URL = "http://capi.douyucdn.cn/api/v1/getColumnRoom/8?aid=android1&client_sys=android&limit=20&offset=0&time=1465535100&auth=f18ac21547ff3c2e900446dc6dc9938c";
    private static final String YINGKE_LIVE_HOT_URL = "http://service.ingkee.com/api/live/gettop?count=5&lc=3000000000004410&cv=IK2.8.10_Android&cc=TG36011&ua=MeizuPRO5&uid=103100727&sid=20FZyiUwU4z7hG2n7Katji2ugsx6x0ILwX5TVeJoTmo8yWAMUZ4S&devi=867905023696224&imsi=460078102967245&imei=867905023696224&icc=898600b1011580375092&conn=WIFI&vv=1.0.2-201601131421.android&aid=1c6184bc4f4d32f0&osversion=android_22&proto=3";
    private static final String YINGKE_LIVE_NEW_URL = "http://service.ingkee.com/api/live/homepage_new?lc=3000000000004410&cv=IK2.8.10_Android&cc=TG36011&ua=MeizuPRO5&uid=103100727&sid=20FZyiUwU4z7hG2n7Katji2ugsx6x0ILwX5TVeJoTmo8yWAMUZ4S&devi=867905023696224&imsi=460078102967245&imei=867905023696224&icc=898600b1011580375092&conn=WIFI&vv=1.0.2-201601131421.android&aid=1c6184bc4f4d32f0&osversion=android_22&proto=3&location=0&interest=0";
    private static final String HUYA_LIVE_URL = "http://capi.douyucdn.cn/api/v1/getColumnRoom/1?aid=android1&client_sys=android&limit=20&offset=0&time=1465545960&auth=d139e33a9ce652d6fdda435dbbbf35a3";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam2;

    android.support.v7.widget.RecyclerView hot_list;
    ImageView iv_hot_banner;

    public HotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotFragment newInstance(int param2) {
        HotFragment fragment = new HotFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @TargetApi(23)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        iv_hot_banner = (ImageView) view.findViewById(R.id.iv_hot_banner);
        hot_list = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.hot_list);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getHotList();
            }
        }).start();

        return view;
    }


    public void getHotList() {
        OkHttpClient client = new OkHttpClient();
        try {
            Request request;
            if (mParam2 == 2) {
                request = new Request.Builder()
                        .url(DOUYU_LIVE_URL)
                        .build();
                Response response = client.newCall(request).execute();
                final JSONArray dataArray = new JSONObject(response.body().string()).getJSONArray("data");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<HotLiveBean> hotLiveBeens = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                String room_srcData = jsonObject.getString("room_src");
                                String vertical_srcData = jsonObject.getString("vertical_src");
                                String room_namecData = jsonObject.getString("room_name");
                                String nicknamecData = jsonObject.getString("nickname");
                                String onlinecData = jsonObject.getString("online");
                                HotLiveBean hotLiveBean = new HotLiveBean(room_srcData, vertical_srcData, room_namecData, nicknamecData, onlinecData, "北京", null);
                                hotLiveBeens.add(hotLiveBean);

                                HotLiveAdapter hotLiveAdapter = new HotLiveAdapter(hotLiveBeens, getActivity());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                                hot_list.setLayoutManager(mLayoutManager);
                                hot_list.setItemAnimator(new DefaultItemAnimator());
                                hot_list.setAdapter(hotLiveAdapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (mParam2 == 0) {
                request = new Request.Builder()
                        .url(YINGKE_LIVE_HOT_URL)
                        .build();
                Response response = client.newCall(request).execute();
                final JSONArray dataArray = new JSONObject(response.body().string()).getJSONArray("lives");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<HotLiveBean> hotLiveBeens = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                String cityData = jsonObject.getString("city");
                                String creatorData = jsonObject.getString("creator");
                                String stream_addrData = jsonObject.getString("stream_addr");
                                String online_usersData = jsonObject.getString("online_users");

                                String descriptionData = new JSONObject(creatorData).getString("description");
                                String nicknamecData = new JSONObject(creatorData).getString("nick");
                                String portraitData = new JSONObject(creatorData).getString("portrait");

                                String room_srcData = "http://image.scale.a8.com/imageproxy2/dimgm/scaleImage?url=" +
                                        URLEncoder.encode("http://img.meelive.cn/" + portraitData) + "&w=540&h=540&s=80&c=0&o=0";
                                String room_srcData2 = "http://image.scale.a8.com/imageproxy2/dimgm/scaleImage?url=" +
                                        URLEncoder.encode("http://img.meelive.cn/" + portraitData) + "&w=100&h=100&s=80&c=0&o=0";

                                HotLiveBean hotLiveBean = new HotLiveBean(room_srcData2, room_srcData, descriptionData, nicknamecData, online_usersData, cityData, stream_addrData);
                                hotLiveBeens.add(hotLiveBean);

                                HotLiveAdapter hotLiveAdapter = new HotLiveAdapter(hotLiveBeens, getActivity());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                                hot_list.setLayoutManager(mLayoutManager);
                                hot_list.setItemAnimator(new DefaultItemAnimator());
                                hot_list.setAdapter(hotLiveAdapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (mParam2 == 1) {
                request = new Request.Builder()
                        .url(YINGKE_LIVE_NEW_URL)
                        .build();
                Response response = client.newCall(request).execute();
                final JSONArray dataArray = new JSONObject(response.body().string()).getJSONArray("lives");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<HotLiveBean> hotLiveBeens = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                String cityData = jsonObject.getString("city");
                                String creatorData = jsonObject.getString("creator");
                                String stream_addrData = jsonObject.getString("stream_addr");
                                String online_usersData = jsonObject.getString("online_users");

                                String descriptionData = jsonObject.getString("name");
                                String nicknamecData = new JSONObject(creatorData).getString("nick");
                                String portraitData = new JSONObject(creatorData).getString("portrait");

                                String room_srcData = "http://image.scale.a8.com/imageproxy2/dimgm/scaleImage?url=" +
                                        URLEncoder.encode("http://img.meelive.cn/" + portraitData) + "&w=540&h=540&s=80&c=0&o=0";
                                String room_srcData2 = "http://image.scale.a8.com/imageproxy2/dimgm/scaleImage?url=" +
                                        URLEncoder.encode("http://img.meelive.cn/" + portraitData) + "&w=100&h=100&s=80&c=0&o=0";

                                HotLiveBean hotLiveBean = new HotLiveBean(room_srcData2, room_srcData, descriptionData, nicknamecData, online_usersData, cityData, stream_addrData);
                                hotLiveBeens.add(hotLiveBean);

                                HotLiveAdapter hotLiveAdapter = new HotLiveAdapter(hotLiveBeens, getActivity());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                                hot_list.setLayoutManager(mLayoutManager);
                                hot_list.setItemAnimator(new DefaultItemAnimator());
                                hot_list.setAdapter(hotLiveAdapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                request = new Request.Builder()
                        .url(HUYA_LIVE_URL)
                        .build();

                Response response = client.newCall(request).execute();
                final JSONArray dataArray = new JSONObject(response.body().string()).getJSONArray("data");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<HotLiveBean> hotLiveBeens = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                String room_srcData = jsonObject.getString("room_src");
                                String vertical_srcData = jsonObject.getString("vertical_src");
                                String room_namecData = jsonObject.getString("room_name");
                                String nicknamecData = jsonObject.getString("nickname");
                                String onlinecData = jsonObject.getString("online");
                                HotLiveBean hotLiveBean = new HotLiveBean(room_srcData, vertical_srcData, room_namecData, nicknamecData, onlinecData, "北京", null);
                                hotLiveBeens.add(hotLiveBean);

                                HotLiveAdapter hotLiveAdapter = new HotLiveAdapter(hotLiveBeens, getActivity());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                                hot_list.setLayoutManager(mLayoutManager);
                                hot_list.setItemAnimator(new DefaultItemAnimator());
                                hot_list.setAdapter(hotLiveAdapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
