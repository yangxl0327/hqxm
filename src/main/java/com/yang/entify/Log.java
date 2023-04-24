package com.yang.entify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Log implements Serializable {

    @Id
    private  String id;
    private  String admin;
    private  String action;
    private Date time;
    private  String status;
}
