package org.cloudlet.web.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cloudlet.web.core.service.Service;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Handler {
	Class<? extends Service<?>> value();
}
