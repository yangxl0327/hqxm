package com.yang.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yang.entify.Picture;
import com.yang.service.PictureService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("pic")
public class PictureController {
   @Autowired
    private PictureService pictureService;

   //查询所有
    @RequestMapping("findAll")
    @ResponseBody
    public List<Picture> findAll(){
        return pictureService.findAll();
    }
    //allmethod
    @ResponseBody
    @RequestMapping("allmethod")
    public Map allmethod(Picture picture,String pid,String[] id, String oper, MultipartFile path, HttpServletRequest request){
        HashMap hashMap = new HashMap<>();
        if(oper.equals("del")){
            pictureService.deleteOne(picture.getId());
            pictureService.deleteAll(Arrays.asList(id));
            hashMap.put("picId",pid);
            hashMap.put("status",200);
            return  hashMap;
        }else if(oper.equals("add")){
            String sid = UUID.randomUUID().toString().replace("-","");
            picture.setId(sid);
            picture.setCreate_time(new Date());
            pictureService.addOne(picture);
            hashMap.put("picId",sid);
            hashMap.put("status",200);
            return  hashMap;
        }else{
            Picture one = pictureService.findOne(picture.getId());
            if(picture.getPath().equals("")||picture.getPath().equals(null)){
                picture.setPath(one.getPath());
            }
            pictureService.modifyOne(picture);
            hashMap.put("status",200);
            hashMap.put("picId",picture.getId());
            return  hashMap;
        }

    }
    @RequestMapping("findAll1")
    @ResponseBody
    public Map<String,Object> findAll1(String searchField,String searchString,String searchOper,Integer page,Integer rows,Boolean _search){
        //不做搜索处理
        Map<String, Object> hashMap = new HashMap<>();
        List<Picture> lists =null;
        Long totalCounts = null;
        if(_search){
            //根据搜索的条件查询集合
            lists = pictureService.selectSearch(searchField,searchString,searchOper,page,rows);
            //根据搜索的条件查询条数
            totalCounts = pictureService.findTotalSearch(searchField,searchString,searchOper);
        }else{
            //进行分页查询
            lists = pictureService.findAllByPage(page, rows);
            //获取数据的总条数
            Integer size = pictureService.findAll().size();

            totalCounts = size.longValue();

        }
       Long totalPage = totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        hashMap.put("rows",lists);
        hashMap.put("total",totalPage);
        hashMap.put("page",page);
        hashMap.put("records",totalCounts);
        return hashMap;
    }
    @RequestMapping("upload1")
    @ResponseBody
    public void upload1(MultipartFile path,String pid,HttpServletRequest request) throws IOException {
        Boolean flag = true;
        if(path.getOriginalFilename().equals("")||path.getOriginalFilename().equals("null")){
            flag=false;
        }
        if(flag){
            //获取真实路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload");
            //修改文件名   前缀  后缀
           String  newFileNamePrefix=  new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+UUID.randomUUID().toString().replace("-","");
           //后缀
           String  newFileNameSuffix= "."+FilenameUtils.getExtension(path.getOriginalFilename());
           //新的文件名
           String newFileName = newFileNamePrefix+newFileNameSuffix;
           //创建日期目录
            String dataDayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dataDay = new File(realPath, dataDayString);
            if(!dataDay.exists()){
                dataDay.mkdirs();
            }
            path.transferTo(new File(dataDay,newFileName));
            //      网络路径：http://Ip:端口/项目名/文件存放位置
            String http = request.getScheme();

            String localHost = InetAddress.getLocalHost().toString();

            int serverPort = request.getServerPort();

            String contextPath = request.getContextPath();

            //网络路径
            String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/upload/"+dataDayString+"/"+newFileName;
            Picture picture = new Picture();
            picture.setPath(uri);
            picture.setId(pid);
            pictureService.modifyOne(picture);
        }
    }
       /* Boolean flag=true;
        if(path.getOriginalFilename().equals("")||path.getOriginalFilename().equals(null)){
            flag=false;
        }
        if(flag){
            String realPath = request.getSession().getServletContext().getRealPath("/upload");
            //修改文件名
            String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                    UUID.randomUUID().toString().replace("-","");
            String newFileNameSuffix="."+ FilenameUtils.getExtension(path.getOriginalFilename());
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
            path.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
            String http = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                    "/upload/"+dataDayString+"/"+newFileName;
            //System.out.println(uri);
            Picture picture = new Picture();
            picture.setPath(uri);
            picture.setId(pid);
            pictureService.modifyOne(picture);
        }*/


    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile path,String pid,HttpServletRequest request) throws IOException {
       /* //ystem.out.println("dadas");
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名
        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                UUID.randomUUID().toString().replace("-","");
        String newFileNameSuffix="."+ FilenameUtils.getExtension(path.getOriginalFilename());
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
        path.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localhost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                "/upload/"+dataDayString+"/"+newFileName;
        //System.out.println(uri);
        Picture picture = new Picture();
        picture.setPath(uri);
        picture.setId(pid);
        pictureService.modifyOne(picture);

*/
       //获取真实路径
       String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名 前缀
        String newFileNamePrefix = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        //后缀
        String newFileNameSuffix ="."+FilenameUtils.getExtension(path.getOriginalFilename());
        //新的文件名
        String newFileName = newFileNamePrefix+newFileNameSuffix;
        //创建日期目录
        String dataDayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dataDay = new File(realPath, dataDayString);
        if(!dataDay.exists()){
            dataDay.mkdirs();
        }
        path.transferTo(new File(dataDay,newFileName));
        //      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/upload/"+dataDayString+"/"+newFileName;
        Picture picture = new Picture();
        picture.setPath(uri);
        picture.setId(pid);
        pictureService.modifyOne(picture);
    }
    @RequestMapping("export")
    @ResponseBody
    public  void  export(HttpServletResponse response) throws IOException {
        List<Picture> all = pictureService.findAll();
        ArrayList<Picture> pictures = new ArrayList<>();
        for (Picture picture : all) {
            Picture picture1 = new Picture();
            picture1.setId(picture.getId());
            picture1.setPath(picture.getPath());
            picture1.setName(picture.getName());
            picture1.setCreate_time(picture.getCreate_time());
            picture1.setHref(picture.getHref());
            picture1.setIntroduction(picture.getIntroduction());
            picture1.setStatus(picture.getStatus());
            pictures.add(picture1);
        }
        //创建输出流
        OutputStream outputStream = response.getOutputStream();
        //创建表格
        ExcelWriter build = EasyExcel.write(outputStream, Picture.class).build();
        WriteSheet sheet = EasyExcel.writerSheet("轮播图信息").build();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("轮播图信息.xls","UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");//设置类型
        build.write(pictures,sheet);
        build.finish();

    }

}
