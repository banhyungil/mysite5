package kr.co.itcen.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import kr.co.itcen.config.web.MessageConfig;
import kr.co.itcen.config.web.MvcConfig;
import kr.co.itcen.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan({"kr.co.itcen.mysite.controller"})
@Import({MvcConfig.class, SecurityConfig.class, MessageConfig.class})
public class WebConfig {

}
