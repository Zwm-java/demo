package com.security.demo.repository;

import com.security.demo.pojo.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {
    SysRole findByRole(String name);
}
