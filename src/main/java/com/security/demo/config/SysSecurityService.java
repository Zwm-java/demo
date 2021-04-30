package com.security.demo.config;

import com.security.demo.pojo.SysUser;
import com.security.demo.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SysSecurityService implements UserDetailsService {
    @Autowired
    private SysUserRepository sysUserRepository;

    //自定义登录认证过程,根据用户名获取用户信息，如果用户被锁定，则抛出异常
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByName(username);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + sysUser.getUsername()));
        if (null == sysUser) throw new UsernameNotFoundException("用户名不存在");
        else if (!sysUser.getEnabled()) throw new LockedException("用户被锁定");
        return new User(sysUser.getUsername(), new BCryptPasswordEncoder().encode(sysUser.getPassword()), authorities);
    }

}
