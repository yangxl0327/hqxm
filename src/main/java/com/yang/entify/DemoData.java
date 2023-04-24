package com.yang.entify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DemoData {


    private String id;

    private String name;

    private String age;

    private Date bir;

    public DemoData(String id, String name, String age, String bir) {
    }
}
