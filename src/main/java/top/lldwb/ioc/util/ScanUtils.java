package top.lldwb.ioc.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.List;

public class ScanUtils {
    /**
     * 扫描指定的包，并返回相关的Class对象
     *
     * @param packages
     * @return
     */
    public static List<Class<?>> scan(String... packages) {
        try (ScanResult scan = new ClassGraph().enableAllInfo().acceptPackages(packages).scan()) {
            return scan.getAllClasses().loadClasses();
        }
    }

    /**
     * 扫描指定的包，并返回相关的实现类对象
     *
     * @param clazz    接口
     * @param packages 包
     * @return
     */
    public static List<Class<?>> scan(Class<?> clazz, String... packages) {
        List<Class<?>> list = new ArrayList<>();
        ScanUtils.scan(packages).forEach(aClass -> {
            if (clazz.isAssignableFrom(aClass)) {
                list.add(aClass);
            }
        });
        return list;
    }
}

