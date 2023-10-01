package top.lldwb.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
    /**
     * 当有多个实现类的时候就需要指定需要注入的实现类
     *
     * @return
     */
    String value() default "";
}
