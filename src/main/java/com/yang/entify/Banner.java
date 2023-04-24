package com.yang.entify;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banner implements Serializable {
    @Id
    private  String  id;
    private  String  title;
    private  String  url;
    private  String  href;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    private  Date    create_date;
    @Column(name = "`desc`")
    private  String desc;

    private  String status;
}
