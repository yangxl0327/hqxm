package com.yang;

import com.yang.dao.UserDao;
import com.yang.entify.Admin;
import com.yang.entify.MapVO;
import com.yang.entify.Role;
import com.yang.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public  class HqxmApplicationTests {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminService adminService;
    @Test
    public  void  test01(){
       // List<User> users = userDao.selectSex("男");
       // users.forEach(user -> System.out.println(user));
        Integer i = userDao.selectCountBySexAndDay("男", 365);
        System.out.println("-------");
        System.out.println(i);
        System.out.println("-------");
        List<MapVO> mapVOS = userDao.selectByAddress();
        System.out.println(mapVOS);
    }
    @Test
    public void selectByname(){
        Admin admin = adminService.findOneByUsername("admin");
        System.out.println(admin);
        Role role = new Role();
        role.getRole_name();
    }

}
