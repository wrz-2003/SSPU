package com.example.QQ.ui.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.QQ.ChatGptPage;
import com.example.QQ.ChatPage;
import com.example.QQ.R;
import com.example.QQ.component.MessageSlideLayout;
import com.example.QQ.domain.Friends;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IndexFragment extends Fragment {

    private IndexViewModel indexViewModel;
    private ListView listView;
    private ArrayList<Friends> mDatas;
    private ArrayList<Integer> idList;
    private MyAdapter myAdapter;
    private Set<MessageSlideLayout> sets = new HashSet();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        indexViewModel =
                new ViewModelProvider(this).get(IndexViewModel.class);
        View root = inflater.inflate(R.layout.fragment_index, container, false);
        indexViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });


        listView=(ListView)root.findViewById(R.id.index_listView);
        initData();
        return root;
    }

    public void initData(){
        mDatas = new ArrayList<>();
        mDatas.add(new Friends("ChatGpt"));
        mDatas.add(new Friends("白凝冰"));
        mDatas.add(new Friends("柳贯一"));
        mDatas.add(new Friends("武庸"));
        mDatas.add(new Friends("商心慈"));
        mDatas.add(new Friends("谢晗沫"));
        mDatas.add(new Friends("凤九歌"));
        mDatas.add(new Friends("秦百盛"));

        myAdapter = new MyAdapter(getActivity().getApplicationContext(), mDatas);
        listView.setAdapter(myAdapter);

        idList=new ArrayList();
        idList.add(R.drawable.p19);
        idList.add(R.drawable.p12);
        idList.add(R.drawable.p8);
        idList.add(R.drawable.p7);
        idList.add(R.drawable.p4);
        idList.add(R.drawable.p3);
        idList.add(R.drawable.p5);
        idList.add(R.drawable.p9);

    }

    class MyAdapter extends BaseAdapter
    {
        private Context content;
        private ArrayList<Friends> datas;
        private MyAdapter(Context context, ArrayList<Friends> datas)
        {
            this.content = context;
            this.datas = datas;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(content).inflate(R.layout.message_item, null);
                viewHolder = new ViewHolder();
                viewHolder.contentView = (TextView) convertView.findViewById(R.id.content);
                viewHolder.menuView = (TextView) convertView.findViewById(R.id.message_menu);
                viewHolder.profile = (ImageView) convertView.findViewById(R.id.message_profile);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.profile.setImageResource(idList.get(position));
            viewHolder.contentView.setText(datas.get(position).getName());

            viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (datas.get(position).getName().equals("ChatGpt")) {
                        // 跳转到SpecialChatPage页面
                        intent = new Intent(getActivity(), ChatGptPage.class);
                        Toast.makeText(content, "Connect " + datas.get(position).getName(), Toast.LENGTH_SHORT).show();
                    } else {
                        // 跳转到ChatPage页面
                        intent = new Intent(getActivity(), ChatPage.class);
                        Toast.makeText(content, "Click " + datas.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }
                    intent.putExtra("nickname", datas.get(position).getName());
                    startActivity(intent);

                }
            });

            final Friends myContent = datas.get(position);
            final Integer p_id = idList.get(position);

            viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageSlideLayout slideLayout = (MessageSlideLayout) v.getParent();
                    slideLayout.closeMenu(); //解决删除item后下一个item变成open状态问题
                    datas.remove(myContent);
                    idList.remove(p_id);
                    notifyDataSetChanged();
                }
            });

            MessageSlideLayout slideLayout = (MessageSlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());

            return convertView;
        }


        public MessageSlideLayout slideLayout = null;
        class MyOnStateChangeListener implements MessageSlideLayout.OnStateChangeListener
        {
            /**
             * 滑动后每次手势抬起保证只有一个item是open状态，加入sets集合中
             **/
            @Override
            public void onOpen(MessageSlideLayout layout) {
                slideLayout = layout;
                if (sets.size() > 0) {
                    for (MessageSlideLayout s : sets) {
                        s.closeMenu();
                        sets.remove(s);
                    }
                }
                sets.add(layout);
            }

            @Override
            public void onMove(MessageSlideLayout layout) {
                if (slideLayout != null && slideLayout !=layout)
                {
                    slideLayout.closeMenu();
                }
            }

            @Override
            public void onClose(MessageSlideLayout layout) {
                if (sets.size() > 0) {
                    sets.remove(layout);
                }
                if(slideLayout ==layout){
                    slideLayout = null;
                }
            }
        }
    }

    static class ViewHolder
    {
        public TextView contentView;
        public TextView menuView;
        public ImageView profile;
    }

}