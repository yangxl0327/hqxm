package com.yang.controller;

import com.yang.entify.Article;
import com.yang.service.ArticleService;
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
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page,Integer rows){
        HashMap hashMap = new HashMap<>();
        List<Article> all = articleService.findAll();
        List<Article> allf = articleService.findAllf(page, rows);
        Integer size = all.size();
        Integer total = size % rows == 0 ? size / rows : size / rows + 1;
        hashMap.put("rows",allf);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("records",size);
        return  hashMap;
    }

    @RequestMapping("update")
    @ResponseBody
    public  void update(MultipartFile articleImg, HttpServletRequest request, Article article, HttpSession session) throws IOException {
     if(articleImg.getOriginalFilename().equals("")||articleImg.getOriginalFilename().equals(null)){
         articleService.modifyOne(article);

     }else{
         String dir = "/upload/articleImg";
         String httpUrl = HttpUtil.getHttpUrl(articleImg, request, session, dir);
         article.setCover(httpUrl);
         article.setCreate_time(new Date());
         articleService.modifyOne(article);
     }
    }

    @RequestMapping("allmethod")
    @ResponseBody
    public  Map allmethod(String pid,Article article,String oper,HttpServletRequest request,MultipartFile path){
        HashMap hashMap = new HashMap<>();
        if(oper.equals("del")){
            articleService.removeOne(article.getId());
            hashMap.put("picId",article.getId());
            hashMap.put("status",200);
            return  hashMap;
        }else if(oper.equals("add")){
            String sid = UUID.randomUUID().toString().replace("-", "");
            article.setId(sid);
            article.setCreate_time(new Date());
            articleService.addOne(article);
            hashMap.put("picId",sid);
            hashMap.put("status",200);
            return  hashMap;
        }else {
            articleService.modifyOne(article);
            hashMap.put("picId",article.getId());
            hashMap.put("status",200);
            return  hashMap;
        }

    }

    /**
     *完成文件上传操作
     * 上传默认文件为 imgFile
     * */
    @RequestMapping("uploadImg")
    @ResponseBody
    public  Map uploadImg(MultipartFile imgFile,HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap<>();
        String dir = "/upload/articleImg/";

        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error",0);
            hashMap.put("url",httpUrl);
        } catch (Exception e) {
            hashMap.put("error",1);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImgs")
    public  Map showAllImgs(HttpServletRequest request,HttpSession session){
        // 1. 获取文件夹绝对路径
        String dataDayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String realPath = session.getServletContext().getRealPath("/upload/articleImg/"+dataDayString);
        //准备返回的json数据
        HashMap hashMap = new HashMap();
        //存放
        ArrayList arrayList = new ArrayList();
        //获取文件
        File file = new File(realPath);
        //对文件进行遍历
        File[] files = file.listFiles();
        for (File file1 : files) {
            //文件属性封装
          HashMap fileMap =  new HashMap();
          fileMap.put("is_dir",false);
          fileMap.put("has_file",false);
          fileMap.put("filesize",file1.length());
          fileMap.put("is_photo",true);
          //获取文件后缀  得到文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype",extension);
            fileMap.put("filename",file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);

        }
        hashMap.put("file_list",arrayList);
        hashMap.put("total_count",arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/"+dataDayString+"/");
        return  hashMap;

    }

    @RequestMapping("upload")
    @ResponseBody
    public  void  upload(MultipartFile articleImg,Article article,HttpServletRequest request,HttpSession session) throws IOException {
        String dir = "/upload/articleImg";
        String httpUrl = HttpUtil.getHttpUrl(articleImg, request, session, dir);
        article.setId(UUID.randomUUID().toString());
        article.setCover(httpUrl);
        article.setCreate_time(new Date());
        articleService.addOne(article);

    }

}
