package com.yang.entify;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Article implements Serializable {

    @Id
    private  String  id;
    private  String  title;
    private  String  content;
    @JSONField(format = "yyyy-MM-dd")
    private Date create_time;
    private  String cover;

    @Column(name = "guruId")
    private  String guruId;

    private  String status;

}
