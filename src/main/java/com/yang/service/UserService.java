package com.yang.service;

import com.yang.entify.MapVO;
import com.yang.entify.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    List<MapVO> findByAddress();

    Integer findCountBySexAndDay(
            @Param("sex") String sex,
            @Param("day") Integer day);
    //查询
    List<User> findAll();
    User findOne(String id);
    List<User> findAllf(Integer page, Integer size);
    // Integer findSex(String key);
    User findOneByPhone(String phone);
    // List<MapVO> selectByLoction();
    //添加
    void addOne(User user);
    //删除
    void removeOne(String id);
    //更新
    void updateOne(User user);


    //登录
    User login(String phone,String password);
    //注册
    void regist(String code);

}
