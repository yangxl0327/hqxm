package com.yang.entify;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@HeadRowHeight(50)
@ContentRowHeight(100)
@ColumnWidth(100/8)
public class Picture implements Serializable {
    @Id
    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("name")
    private String name;
    @ExcelProperty("path")
    private String path;
    @ExcelProperty("introduction")
    private String introduction;
    @ExcelProperty("href")
    private String href;
    @ExcelProperty("status")
    private String status;
    @ExcelProperty("create-time")
    @JSONField(format = "yyyy-MM-dd")
    private Date create_time;
}
