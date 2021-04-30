package com.security.demo;

import com.security.demo.pojo.SysUser;
import com.security.demo.repository.SysUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    SysUserRepository sysUserRepository;
    @Test
    void contextLoads() {

        System.out.println(sysUserRepository.findByName("long"));
    }

}
