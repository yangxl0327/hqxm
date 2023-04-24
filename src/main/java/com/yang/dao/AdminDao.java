package com.yang.dao;

import com.yang.entify.Admin;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminDao extends Mapper<Admin> {
    //根据姓名查询
    Admin selectOneByName(String name);
    //查询所有
    List<Admin> selectAllAdmin(Integer page, Integer size);

    List<Admin> selectAll1();

}
