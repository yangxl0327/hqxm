package com.yang;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class DemoDataListener extends AnalysisEventListener<DemoDate> {
    List list = new ArrayList<>();
    //每行数据读取完毕后会调用invoke方法
    @Override
    public void invoke(DemoDate data, AnalysisContext context) {
   System.out.println(data);
   list.add(data);
    }
    //全部读取完成之后 调用此方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    System.out.println("over");
    }
}
