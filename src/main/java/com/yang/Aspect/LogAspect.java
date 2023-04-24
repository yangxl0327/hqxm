package com.yang.Aspect;

import com.yang.Annotation.LogAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

   //切入点表达式  所有返回值
    @Around("@annotation(com.yang.Annotation.LogAnnotation)")
    public  Object addLog(ProceedingJoinPoint proceedingJoinPoint){
      /**
       *@Author Lints
       *@date 2023/4/1 21:03
       *@Description This is description of class
       *@Param [proceedingJoinPoint]
       *@return java.lang.Object
       *@Since version-1.0
       * 记录内容   谁  时间  事件  成功与否
      */
        //谁  设置一个session
        HttpSession session = request.getSession();
        session.setAttribute("admin","admin");
        String admin = (String) session.getAttribute("admin");
        //时间
        Date date = new Date();
        //获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //获取注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        System.out.println(value);

        //成功与否
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String  status = "success";
            System.out.println(admin+" "+date+" "+name+" "+status);
            return proceed;
        } catch (Throwable throwable) {
            String  status = "error";
            System.out.println(admin+" "+date+" "+name +""+status);
            throwable.printStackTrace();
            return  null;
        }

    }
}
