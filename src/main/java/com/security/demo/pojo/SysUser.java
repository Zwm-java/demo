package com.security.demo.pojo;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "sys_user")
@Entity
@Data
public class SysUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;
    @Column(nullable = false, unique = true)
    private String name;
    private String password;
    private String username;
    private Boolean enabled = Boolean.TRUE;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    //sys_role定义： 定义多对多映射 权限permission
    private List<SysRole> roles;

    //根据自定义逻辑返回用户权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> roles = this.getRoles();
        roles.forEach(sysRole -> authorities.add(new
                SimpleGrantedAuthority(sysRole.getRole())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}