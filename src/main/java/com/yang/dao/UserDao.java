package com.yang.dao;

import com.yang.cache.MyBatisCache;
import com.yang.entify.MapVO;
import com.yang.entify.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@CacheNamespace(implementation = MyBatisCache.class)
public interface UserDao extends Mapper<User> {
     List<User> selectSex(String sex);

     Integer selectCountBySexAndDay(@Param("sex") String sex,
                                    @Param("day") Integer day);

     List<MapVO> selectByAddress();


}
