package com.security.demo.repository;

import com.security.demo.pojo.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Integer> {
    SysUser findByName(String name);
}

