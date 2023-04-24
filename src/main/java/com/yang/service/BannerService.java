package com.yang.service;

import com.yang.entify.Banner;

import java.util.Map;

public interface BannerService {
    // 显示所有信息图
   Map showAllBanners(Integer page,Integer rows);

   Map add(Banner banner);
   Map update(Banner banner);
   Map delete(Banner banner);
}
