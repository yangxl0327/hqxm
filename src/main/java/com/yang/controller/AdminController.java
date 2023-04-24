package com.yang.controller;

import com.yang.entify.Admin;
import com.yang.service.AdminService;
import com.yang.util.SecurityCode;
import com.yang.util.SecurityImage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private TaskController taskController;

    @RequestMapping("login")
    @ResponseBody
    public  String  login(Admin admin, String code, HttpServletRequest request){
        //验证码
       String securityCode = (String) request.getSession().getAttribute("securityCode");
        // 封装令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(admin.getName(), admin.getPassword());
        //通过 util类获取 subject主体
        Subject subject = SecurityUtils.getSubject();
        //登录
        if(!code.equals(securityCode)){
             return  "验证码错误！";
        }else{
            try{
                subject.login(usernamePasswordToken);
                return null;
            }catch (UnknownAccountException u){
                return "用户名错误！";
            }catch (IncorrectCredentialsException e){
                return "密码错误！";
            }
        }
    }
    @RequestMapping("yzm")
    public  void yzm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生产验证码的随机数
        String securityCode = SecurityCode.getSecurityCode();
        request.getSession().setAttribute("securityCode",securityCode);
        //绘制生成验证码的图片
        BufferedImage createImage = SecurityImage.createImage(securityCode);
        //响应到客户端
        ServletOutputStream outputStream = response.getOutputStream();
        /* 第一个参数：指定验证码的图片对象
         * 第二个参数：图片得到的格式
         * 第三个参数:指定输出流
         *
         * */
        ImageIO.write(createImage,"png",outputStream);
        outputStream.close();
    }





}
