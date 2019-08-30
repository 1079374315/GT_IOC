package com.gsls.myapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源注解
 */
public @interface GT_Res {

    /**
     * 字符串 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_String {
        int value();
    }

    /**
     * 颜色 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_Color {
        int value();
    }

    /**
     * 尺寸 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_Dimen {
        int value();
    }

    /**
     * 图片 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_Drawable {
        int value();
    }

    /**
     * 动画 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_Animation {
        int value();
    }

    /**
     * 字符串数组 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_StringArray {
        int value();
    }

    /**
     * 整数数组 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_IntArray {
        int value();
    }

    /**
     * 将 xml 文件解析成 View 注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GT_Layout {
        int value();
    }

}
