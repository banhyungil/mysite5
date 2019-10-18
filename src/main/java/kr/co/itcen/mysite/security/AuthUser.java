package kr.co.itcen.mysite.security;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface AuthUser {
}
