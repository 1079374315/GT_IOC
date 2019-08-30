package com.gsls.myapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GT_Object {

    interface TYPE {
        String BYTE = "byte";
        String SHORT = "short";
        String INT = "int";
        String LONG = "long";
        String FLOAT = "float";
        String DOUBLE = "double";
        String BOOLEAN = "boolean";
        String CHAR = "char";
        String STRING = "String";

        String BYTES = "bytes";
        String SHORTS = "shorts";
        String INTS = "ints";
        String LONGS = "longs";
        String FLOATS = "floats";
        String DOUBLES = "doubles";
        String BOOLEANS = "booleans";
        String CHARS = "chars";
        String STRINGS = "Strings";
    }

    /** 单参数的传递 **/
    byte        valueByte()     default 0;
    short       valueShort()    default 0;
    int         valueInt()      default 0;
    long        valueLong()     default 0L;
    float       valueFloat()    default 0.0f;
    double      valueDouble()   default 0.0d;
    boolean     valueBoolean()  default false;
    char        valueChar()     default 0;
    String      valueString()   default "";

    /** 多参数的传递 **/
    byte[]      valueBytes()     default {};
    short[]     valueShorts()    default {};
    int[]       valueInts()      default {};
    long[]      valueLongs()     default {};
    float[]     valueFloats()    default {};
    double[]    valueDoubles()   default {};
    boolean[]   valueBooleans()  default {};
    char[]      valueChars()     default {};
    String[]    valueStrings()   default {};

    /** 修改参数的类型 **/
    String      type()           default "";
    String[]    types()          default {};

    /** 要赋值的方法 **/
    String      function()       default "";
    String[]    functions()      default {};

}