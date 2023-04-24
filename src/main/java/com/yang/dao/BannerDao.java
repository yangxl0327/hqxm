package com.yang.dao;


import com.yang.entify.Banner;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner> {

    void deleteByIdList(List<String> asList);

}
