package com.yang.service;


import com.yang.dao.BannerDao;
import com.yang.entify.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerDao bannerDao;


    @Override
    public Map showAllBanners(Integer page, Integer rows) {
        // 分页数据  rows 数据  page 当前页  record 总条数 total 总页数
        HashMap hashMap = new HashMap<>();
        //分页
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        //总条数
        int records = bannerDao.selectCount( new Banner());
        //总页数
        int total =records%rows==0?records/rows:records/rows+1;
        hashMap.put("rows",banners);
        hashMap.put("page",page);
        hashMap.put("records",records);
        hashMap.put("total",total);
        return hashMap;

    }

    @Override
    public Map add(Banner banner) {
        HashMap hashMap = new HashMap<>();
        String s = UUID.randomUUID().toString();
        banner.setId(s);
        bannerDao.insert(banner);
        hashMap.put("bannerId",s);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map update(Banner banner) {
        HashMap hashMap = new HashMap<>();
        banner.setUrl(null);
        bannerDao.updateByPrimaryKeySelective(banner);
        hashMap.put("status",200);
        hashMap.put("msg","更新成功");
        return hashMap;
    }

    @Override
    public Map delete(Banner banner) {
        HashMap hashMap = new HashMap<>();
        bannerDao.delete(banner);
        hashMap.put("status",200);
        hashMap.put("msg","更新成功");
        return hashMap;
}
}
