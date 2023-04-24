package com.yang.controller;

import com.yang.entify.MapVO;
import com.yang.entify.User;
import com.yang.service.UserService;
import com.yang.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
     @Autowired
     private UserService userService;

     @RequestMapping("showUsersCount")
     public Map showUsersCount(){
         HashMap hashMap = new HashMap<>();
         ArrayList<Object> man = new ArrayList<>();
         ArrayList<Object> women = new ArrayList<>();
         man.add(userService.findCountBySexAndDay("男",1));
         women.add(userService.findCountBySexAndDay("女",1));
         man.add(userService.findCountBySexAndDay("男",7));
         women.add(userService.findCountBySexAndDay("女",7));
         man.add(userService.findCountBySexAndDay("男",30));
         women.add(userService.findCountBySexAndDay("女",30));
         man.add(userService.findCountBySexAndDay("男",365));
         women.add(userService.findCountBySexAndDay("女",365));
         hashMap.put("man",man);
         hashMap.put("women",women);
         return  hashMap;
     }

     @RequestMapping("showUsersLocation")
    public List<MapVO> showUsersLocation(){
         List<MapVO> mapVOS = userService.findByAddress();
         return  mapVOS;
     }

     @RequestMapping("findAll")
     @ResponseBody
     public  Map findAll(Integer page ,Integer rows){
         HashMap<Object, Object> hashMap = new HashMap<>();
         List<User> all = userService.findAll();
         Integer size = all.size();
         Integer total = size % rows == 0 ? size / rows : size / rows + 1;
         List<User> allf = userService.findAllf(page, rows);
         hashMap.put("rows",allf);
         hashMap.put("page",page);
         hashMap.put("total",total);
         hashMap.put("record",size);
         return  hashMap;
     }
     @RequestMapping("allmethod")
     @ResponseBody
    public  Map allmethod(User user, String oper, HttpServletRequest request, String pid, String[] id, MultipartFile avatar){
         HashMap hashMap = new HashMap<>();
         if(oper.equals("del")){
             userService.removeOne(user.getId());
             hashMap.put("picId",user.getId());
             hashMap.put("status",200);
             return  hashMap;
         }else if(oper.equals("add")){
             String sid = UUID.randomUUID().toString().replace("-", "");
             user.setId(sid);
             user.setCreateTime(new Date());
             userService.addOne(user);
             hashMap.put("picId",sid);
             hashMap.put("status",200);
             return  hashMap;

         }else {
             User one = userService.findOne(user.getId());
             if(user.getAvatar().equals("")||user.getAvatar().equals(null)){
                 user.setAvatar(one.getAvatar());
             }
             userService.updateOne(user);
             hashMap.put("picId",user.getId());
             hashMap.put("status",200);
             return hashMap;
         }
     }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile avatar, String pid, HttpServletRequest request, HttpSession session) throws IOException {
     /*   //ystem.out.println("dadas");
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名
        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                UUID.randomUUID().toString().replace("-","");
        String newFileNameSuffix="."+ FilenameUtils.getExtension(avatar.getOriginalFilename());
        //新的文件名
        String newFileName=newFileNamePrefix+newFileNameSuffix;
        //创建日期目录
        String dataDayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String truePath="img/"+dataDayString;
        File dataDay = new File(realPath, dataDayString);
        if(!dataDay.exists()){
            dataDay.mkdirs();
        }
        //System.out.println(dataDayString);
        avatar.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localhost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                "/upload/"+dataDayString+"/"+newFileName;
        //System.out.println(uri);*/
        //利用工具类  获取网络路径
        String dir = "/upload/avatar";
        String httpUrl = HttpUtil.getHttpUrl(avatar, request, session, dir);
        User user = new User();
        user.setAvatar(httpUrl);
        user.setId(pid);
        userService.updateOne(user);

    }

    @RequestMapping("upload1")
    @ResponseBody
    public void upload1(MultipartFile avatar,String pid,HttpServletRequest request) throws IOException {
        Boolean flag=true;
        if(avatar.getOriginalFilename().equals("")||avatar.getOriginalFilename().equals(null)){
            flag=false;
        }
        if(flag){
            String realPath = request.getSession().getServletContext().getRealPath("/upload");
            //修改文件名
            String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                    UUID.randomUUID().toString().replace("-","");
            String newFileNameSuffix="."+ FilenameUtils.getExtension(avatar.getOriginalFilename());
            //新的文件名
            String newFileName=newFileNamePrefix+newFileNameSuffix;
            //创建日期目录
            String dataDayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String truePath="img/"+dataDayString;
            File dataDay = new File(realPath, dataDayString);
            if(!dataDay.exists()){
                dataDay.mkdirs();
            }
            //System.out.println(dataDayString);
            avatar.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
            String http = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                    "/upload/"+dataDayString+"/"+newFileName;
            //System.out.println(uri);
            User user = new User();
            user.setAvatar(uri);
            user.setId(pid);
            userService.updateOne(user);
        }
    }
}
