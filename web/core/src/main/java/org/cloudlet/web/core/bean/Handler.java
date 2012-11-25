package org.cloudlet.web.core.bean;


import org.cloudlet.web.core.service.ResourceService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Handler {
  Class<? extends ResourceService> value();
}