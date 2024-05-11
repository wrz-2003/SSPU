package com.example.QQ.utils;

import com.example.QQ.domain.Friends;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class FriendsUtil {
    public static boolean addFriends(Friends friends){//添加好友
        List<Friends> lists=new ArrayList();
        lists=findAllFriends();
        String name=friends.getName();
        for(Friends e:lists)
            if(e.getName().equals(name)){
                return false;
            }
        friends.save();
        return true;
    }

    public static List<Friends> findAllFriends(){//查询所有好友
        List<Friends> lists= DataSupport.findAll(Friends.class);
        return lists;
    }

    public static List<Friends> findMyFriends(){//查询自己的好友
        List<Friends> lists= DataSupport.findAll(Friends.class);
        List<Friends> res=new ArrayList<>();
        for(Friends e:lists){
            if(e.getIsFriend()==1)
                res.add(e);
        }
        return res;
    }

}
