package com.bargetor.nest.bpc.annotation;

import com.bargetor.nest.bpc.servlet.BPCDispatcherServlet;

import java.lang.annotation.*;

/**
 * Created by Bargetor on 16/3/20.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BPCService {
    String url() default BPCDispatcherServlet.BPC_DEFAULT_URL;
}
