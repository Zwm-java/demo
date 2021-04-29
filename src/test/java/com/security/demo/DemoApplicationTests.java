package com.security.demo;

import com.security.demo.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    SysUser sysUser;
    @Test
    void contextLoads() {
    }

}
