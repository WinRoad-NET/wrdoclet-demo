package net.winroad.Models;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuthority {

    LoginAuthType authType();

}

