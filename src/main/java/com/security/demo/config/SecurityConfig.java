package com.security.demo.config;


import com.security.demo.handler.MyAuthenticationFailHandler;
import com.security.demo.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.annotation.Resource;
import javax.sql.DataSource;


@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SysSecurityService sysSecurityService;
    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Resource
    private MyAuthenticationFailHandler myAuthenticationFailHandler;
    @Autowired
    private DataSource dataSource;

    //http配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//认证权限
        http.authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll();
//rbac 访问admin/rbac时，使用自定义hasPermission验证是否允许访问
        http.authorizeRequests()
                .antMatchers("/admin/admin", "/admin/vip", "/admin/root")
                .access("@rbacService.hasPermission(request , authentication)");

//                .antMatchers("/admin/admin").hasRole("admin")
//                .antMatchers("/admin/vip").hasRole("vip")
//                .antMatchers("/admin/root").hasRole("root")
//                .anyRequest().authenticated();


        http.formLogin()
                .loginPage("/admin/login").permitAll()
                .usernameParameter("username").passwordParameter("pass")
                .defaultSuccessUrl("/admin/")
                .failureHandler(myAuthenticationFailHandler);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/admin/");
//csrf忽略的请求配置
// http.csrf().ignoringAntMatchers("");
//关闭csrf
        http.csrf().disable();
    }

    //认证配置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        MyPasswordEncoder encoder1 = new MyPasswordEncoder();
        auth.userDetailsService(sysSecurityService)
                .passwordEncoder(encoder);

//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("vip").password(new BCryptPasswordEncoder().encode("123456")).roles("vip", "admin")
//                .and()
//                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip", "root", "admin")
//                .and()
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("admin");


//        String userSQL = "SELECT NAME AS username,PASSWORD,state FROM sys_user WHERE NAME = ?";
//        String authoritySQL = "select c.username,a.authority from t_customer c,t_authority a," +
//                "t_customer_authority ca where ca.customer_id=c.id " +
//                "and ca.authority_id=a.id and c.username =?";
//        auth.jdbcAuthentication().passwordEncoder(encoder)
//                .dataSource(dataSource)
//                .usersByUsernameQuery(userSQL)
//                .authoritiesByUsernameQuery(authoritySQL);

    }

    // 密码编译器
    @Bean
    //SysSecurityService : 自定义认证过程
    PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();

    }

    //remember_me中，jdbc持久化操作
    @Autowired
    JdbcTokenRepositoryImpl tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//自动创建保存token持久化的表
        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
