package com.example.QQ.ui.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QQ.MakeMomentPage;
import com.example.QQ.R;
import com.example.QQ.component.NewsAdapter;
import com.example.QQ.domain.Friends;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements View.OnClickListener {

    private NewsViewModel newsViewModel;
    private List<Friends> list;
    private static NewsAdapter adapter;
    private ImageView goMakeMoment;
    private List<Integer> profiles;
    private List<String> nickname;
    private List<List<Integer>> itemPictures;
    private List<Integer> itemPicture;
    private List<String> copyWriting;
    private List<Integer> times;
    private static boolean flag=false;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        AppBarLayout appBarLayout = root.findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolbarLayout collapsingToolbar = root.findViewById(R.id.collapsing_toolbar);
                int color = Color.argb(200,0,0,0);
                collapsingToolbar.setCollapsedTitleTextColor(color);
                ImageView imageView = root.findViewById(R.id.image1);
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // 折叠状态
                    collapsingToolbar.setTitle("动态");
                    imageView.setVisibility(View.GONE);
                } else { // 非折叠状态
                    collapsingToolbar.setTitle("");
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
        initFriends();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(list,profiles,nickname,itemPictures,copyWriting,times);
        recyclerView.setAdapter(adapter);


        goMakeMoment=root.findViewById(R.id.camera_id);
        goMakeMoment.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), MakeMomentPage.class);
        startActivity(intent);

    }



    private void initFriends() {
        list=new ArrayList<>();
        if(flag)
            list.add(new Friends("朱珠"));
        list.add(new Friends("武庸"));
        list.add(new Friends("商心慈"));
        list.add(new Friends("谢晗沫"));
        list.add(new Friends("白凝冰"));
        list.add(new Friends("凤九歌"));
        list.add(new Friends("古月方正"));
        list.add(new Friends("陶铸"));
        list.add(new Friends("柳贯一"));

        nickname=new ArrayList<>();
        if(flag)
            nickname.add("古月方源");
        nickname.add("武庸");
        nickname.add("商心慈");
        nickname.add("谢晗沫");
        nickname.add("白凝冰");
        nickname.add("凤九歌");
        nickname.add("古月方正");
        nickname.add("陶铸");
        nickname.add("柳贯一");

        profiles=new ArrayList<>();//发布动态好友的头像
        if(flag)
            profiles.add(R.drawable.p11);
        profiles.add(R.drawable.p12);
        profiles.add(R.drawable.p13);
        profiles.add(R.drawable.p17);
        profiles.add(R.drawable.p10);
        profiles.add(R.drawable.p7);
        profiles.add(R.drawable.p2);
        profiles.add(R.drawable.p4);
        profiles.add(R.drawable.p3);

        times=new ArrayList<>();//发布动态距离现在的时间
        if(flag)
            times.add(0);
        times.add(25);
        times.add(35);
        times.add(50);
        times.add(1);
        times.add(2);
        times.add(5);
        times.add(7);
        times.add(8);



        itemPictures=new ArrayList<>();
        itemPicture=new ArrayList<>();
        if(flag){
            itemPicture=new ArrayList<>();
            itemPicture.add(R.drawable.pyq0);
            itemPictures.add(itemPicture);
        }

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq1);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq2);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq3);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq4);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq5);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq6);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq7);
        itemPictures.add(itemPicture);

        itemPicture=new ArrayList<>();
        itemPicture.add(R.drawable.pyq8);
        itemPictures.add(itemPicture);


        copyWriting=new ArrayList<>();
        if(flag)
            copyWriting.add("我回来了");
        copyWriting.add("你我一见如故 不如让我送送你");
        copyWriting.add("此去三叉山危险 请珍重");
        copyWriting.add("你会为我而迟疑吗 可惜看不到了");
        copyWriting.add("万丈红尘缤纷彩 天涯云水路遥长");
        copyWriting.add("天地自由凤九歌");
        copyWriting.add("有要打球的吗？没有我等一会儿再来问问");
        copyWriting.add("交给你了 这些遗憾将会统统弥补");
        copyWriting.add("身随柳絮随风扬 无论云泥意贯一");

    }

    public static void update(){
        flag=true;
        adapter.notifyDataSetChanged();
    }

}




