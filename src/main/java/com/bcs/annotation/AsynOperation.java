package com.bcs.annotation;


import com.bcs.adapter.AbstractAsynAdapter;
import com.bcs.adapter.DefaultAsynAdapter;
import com.bcs.asynconst.AdapterDefine;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Inherited
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("ALL")
public @interface AsynOperation {

    /**
     * adapter
     */
    AdapterDefine adapter() default AdapterDefine.DEFAULT_ASYN;

}
