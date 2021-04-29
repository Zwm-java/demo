package com.security.demo.repository;

import com.security.demo.pojo.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionRepository extends
        JpaRepository<SysPermission, Integer> {
}
