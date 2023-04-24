package com.yang;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoDate {
    @ExcelProperty("字符串标题")
    private  String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty(value = "数字标题",index = 3)
    private  Double doubleData;

    //忽略字段
    @ExcelIgnore
    private  String ignore;


}
