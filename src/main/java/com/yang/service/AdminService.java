package com.yang.service;

import com.yang.entify.Admin;

import java.util.List;

public interface AdminService {
    //登录
    Admin findOneByName(String name);

    Admin findOneByUsername(String name);

    List<Admin> findAllAdmin(Integer page, Integer size);

    List<Admin> findAll1();
}
