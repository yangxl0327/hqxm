package com.yang.service;

import com.yang.entify.Chapter;

import java.util.List;

public interface ChapterService {
    //查询
    List<Chapter> findAll();
    //带分页的查询所有
    List<Chapter> findAllf(Integer page,Integer rows,String id);
    //添加
    void addOne(Chapter chapter);
    //删除
    void removeOne(String id);
    //更新
    void updateOne(Chapter chapter);
}
