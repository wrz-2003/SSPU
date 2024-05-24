package com.example.QQ;

import com.example.QQ.domain.Friends;
import com.example.QQ.domain.User;
import com.example.QQ.utils.JDBCUtils;
import com.example.QQ.utils.UserUtil;

import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 *
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> users=new ArrayList();//测试findAll方法
        users= UserUtil.findAllUser();

        for(User e:users)
            System.out.println(e);
    }

    //测试添加用户方法
    @Test
    public void testAddUser() throws SQLException {
        User user=new User("txl3","123");
        int res= JDBCUtils.addUser(user);
        if(res==1)
            System.out.println("添加成功");
        else if(res==0)
            System.out.println("添加失败");
    }

    @Test
    public void test2(){
        Friends f=new Friends("haha");
        System.out.println(f.toString());
    }
}