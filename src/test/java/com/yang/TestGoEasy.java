package com.yang;

import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGoEasy {

    @Test
    public  void  testGoEasy(){
        //新加坡rest-host：rest-singapore.goeasy.io
        GoEasy goEasy = new GoEasy("http://rest-hz.goeasy.io", "BC-af84aa957b884013b3dfa8e2b0217c39");
        goEasy.publish("hqxm", "Hello, GoEasy!2222");

    }

}
