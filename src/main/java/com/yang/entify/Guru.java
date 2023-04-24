package com.yang.entify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Guru implements Serializable {

    @Id
    private  String id;
    private  String name;
    private  String fahao;
    private  String avatar;

}
