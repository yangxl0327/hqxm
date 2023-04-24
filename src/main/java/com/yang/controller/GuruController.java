package com.yang.controller;

import com.yang.entify.Guru;
import com.yang.service.GuruService;
import com.yang.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    //简单的查询所有
    @RequestMapping("findAll")
    @ResponseBody
    public List<Guru> findAll(){
        return guruService.findAll();
    }
    //表格里的 方法实现

    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(String oper, String pid, Guru guru, MultipartFile avatar, HttpServletRequest request){
        HashMap hashMap = new HashMap<>();
        if(oper.equals("del")){
            guruService.removeOne(guru.getId());
            hashMap.put("picId",guru.getId());
            hashMap.put("status",200);
            return hashMap;
        }else if(oper.equals("add")){
            String sid = UUID.randomUUID().toString().replace("-","");
            guru.setId(sid);
            guruService.addOne(guru);
            hashMap.put("picId",sid);
            hashMap.put("status",200);
            return  hashMap;
        }else {
            guruService.updateOne(guru);
            hashMap.put("picId",guru.getId());
            hashMap.put("status",200);
            return  hashMap;
        }
    }

    @ResponseBody
    @RequestMapping("upload")
    public  void  upload(MultipartFile avatar, String pid, HttpServletRequest request, HttpSession session) throws IOException {
        String dir = "/upload/articleImg";
        String httpUrl = HttpUtil.getHttpUrl(avatar, request, session, dir);
        Guru guru = new Guru();
        guru.setAvatar(httpUrl);
        guru.setId(pid);
        guruService.updateOne(guru);

    }
    @RequestMapping("upload1")
    @ResponseBody
    public  void  upload1(HttpSession session,HttpServletRequest request,MultipartFile avatar,String pid){
        Boolean flag=true;
        if(avatar.getOriginalFilename().equals("")||avatar.getOriginalFilename().equals(null)){
            flag=false;
        }
        if(flag) {

            String dir = "/upload/articleImg";
            String httpUrl = HttpUtil.getHttpUrl(avatar, request, session, dir);
            Guru guru = new Guru();
            guru.setId(pid);
            guru.setAvatar(httpUrl);
            guruService.updateOne(guru);
        }
    }

}
