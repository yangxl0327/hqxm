package com.yang;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEasyExcel {
  String fileName ="D:\\IDEA-WORK\\Excel\\"+new Date().getTime()+"DemoData.xlsx";
  @Test
  public  void testEasyExcel(){ //写入
       EasyExcel.write(fileName, DemoDate.class).sheet().doWrite(data());
  }

  private List<DemoDate> data(){
      DemoDate demoDate1 = new DemoDate("xiaolong", new Date(), 18.0, "");
      DemoDate demoDate2 = new DemoDate("xiaolong2", new Date(), 19.0, "");
      DemoDate demoDate3 = new DemoDate("xiaolong3", new Date(), 17.0, "");
      DemoDate demoDate4 = new DemoDate("xiaolong4", new Date(), 18.0, "");
      List<DemoDate> data = Arrays.asList(demoDate1, demoDate2, demoDate3, demoDate4);
      return  data;

  }
  @Test
    public  void testExcelRead(){
      //easyexcel read方法 需要三个参数   文件  数据  listener(自己写一个方法) .sheet().doRead()
      EasyExcel.read("D:\\IDEA-WORK\\Excel\\DemoData.xlsx",DemoDate.class,new DemoDataListener()).sheet().doRead();
  }
  //忽略 某一属性 生成表格
  @Test
  public  void testIgnore(){
      Set set = new HashSet<>();
      set.add("date");
      //ex  排除 某些属性集合
      EasyExcel.write(fileName,DemoDate.class).excludeColumnFiledNames(set).sheet().doWrite(data());
  }

}
