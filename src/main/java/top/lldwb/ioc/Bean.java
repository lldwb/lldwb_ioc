package top.lldwb.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    /**
     * 声明一个value属性，用来定义Bean的别名
     * 用做容器中的key
     * @return
     */
    String value();

    /**
     * 是否唯一
     * @return
     */
    boolean sole() default true;
}