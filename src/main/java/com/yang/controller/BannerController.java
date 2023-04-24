package com.yang.controller;

import com.yang.dao.BannerDao;
import com.yang.entify.Banner;
import com.yang.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
   @Autowired
   private BannerService bannerService;

   @Autowired
   private BannerDao bannerDao;

   @RequestMapping("showAllBanners")
   public Map showAllBanner(Integer page,Integer rows){
       Map map = bannerService.showAllBanners(page, rows);
       return  map;
   }
   @RequestMapping("edit")
    public  Map edit(String oper, Banner banner, String [] id){
       Map hashMap = new HashMap<>();
       if(oper.equals("add")){
           hashMap =  bannerService.add(banner);
       }
       if (oper.equals("edit")){
         bannerService.update(banner);
       }
       if (oper.equals("del")){
          bannerService.delete(banner);
       }
       return hashMap;
   }

   @RequestMapping("upload")
    public void  upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
   //获取路径
       String realPath = session.getServletContext().getRealPath("/upload/img");
       //判断路径文件夹是否存在
       File file = new File(realPath);
       if(!file.exists()){
           file.mkdirs();
       }
       //防止重名操作
       String originalFilename = url.getOriginalFilename();
      originalFilename = new Date().getTime()+"_"+originalFilename;
       try {
           url.transferTo(new File(realPath,originalFilename));
       } catch (IOException e) {
           e.printStackTrace();
       }
       //相对路径
       //网络路径  http://IP:端口/项目名/文件存放位置
       //获取请求头
       String http = request.getScheme();
       //获取地址
       String localHost = InetAddress.getLocalHost().toString();
       //获取端口号
       int serverPort = request.getServerPort();
       //获取项目名
       String contextPath = request.getContextPath();
       //网络路径拼接
       String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/upload/img/"+originalFilename;
       Banner banner = new Banner();
       banner.setId(bannerId);
       banner.setUrl(uri);
       bannerDao.updateByPrimaryKeySelective(banner);
       System.out.println(uri);
   }
}
