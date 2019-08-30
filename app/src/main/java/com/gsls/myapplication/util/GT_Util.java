package com.gsls.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gsls.gt.GT;
import com.gsls.myapplication.annotation.Collection;
import com.gsls.myapplication.annotation.GT_Activity;
import com.gsls.myapplication.annotation.GT_Object;
import com.gsls.myapplication.annotation.GT_Res;
import com.gsls.myapplication.annotation.GT_View;
import com.gsls.myapplication.annotation.OnClickEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GT_Util {

    //Fragment 注入
    public static void initAll(Object object, View view){

        initView(object,view);                 //为加载 组件 初始化
        initClick(object,view);                //为加载 组件单击 初始化

        initObject(object);                    //为加载 Object 成员变量初始化
        initList(object);                      //为加载 List 成员变量初始化
        initMap(object);                       //为加载 Map 成员变量初始化
        initSet(object);                       //为加载 Set 成员变量初始化

        initAnimation(object,view);            //为加载 Animation 资源初始化
        initDimen(object,view);                //为加载 Dimen 资源初始化
        initDrawable(object,view);             //为加载 Style 资源初始化
        initColor(object,view);                //为加载 Color 资源初始化
        initString(object,view);               //为加载 String 资源初始化
        initIntArray(object,view);             //为加载 IntArray 资源初始化
        initStringArray(object,view);          //为加载 StringArray 资源初始化
        initLayout(object,view);               //为加载 Layout 资源初始化

    }

    /**
     * Fragment 中的使用
     */

    /** Fragment 注入控件*/
    private static void initView(Object object,View view){
        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields = clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_View initView = field.getAnnotation(GT_View.class);
            if(initView != null){
                int viewId = initView.value();
                try {
                    View viewById = view.findViewById(viewId);
                    field.setAccessible(true);
                    field.set(object,viewById);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入点击事件 */
    private static void initClick(Object object,View view){
        Class<? extends Object> clazz = object.getClass();
        Method[] methods= clazz.getMethods();//获取所有声明为公有的方法
        for (Method method:methods){//遍历所有公有方法
            Annotation[] annotations = method.getAnnotations();//获取该公有方法的所有注解
            for (Annotation annotation:annotations){//遍历所有注解
                Class<? extends Annotation> annotationType = annotation.annotationType();//获取具体的注解类
                OnClickEvent onClickEvent = annotationType.getAnnotation(OnClickEvent.class);//取出注解的onClickEvent注解
                if(onClickEvent!=null){//如果不为空
                    try {
                        Method valueMethod=annotationType.getDeclaredMethod("value");//获取注解InjectOnClick的value方法
                        int[] viewIds= (int[]) valueMethod.invoke(annotation, (Object[]) null);//获取控件值
                        Class<?> listenerType = onClickEvent.listenerType();//获取接口类型
                        String listenerSetter = onClickEvent.listenerSetter();//获取set方法
                        String methodName = onClickEvent.methodName();//获取接口需要实现的方法
                        MyInvocationHandler handler = new MyInvocationHandler(object);//自己实现的代码，负责调用
                        handler.setMethodMap(methodName,method);//设置方法及设置方法
                        Object object2 = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class<?>[]{listenerType},handler);//创建动态代理对象类
                        for (int viewId:viewIds){//遍历要设置监听的控件
                            View view2 = view.findViewById(viewId);//获取该控件
                            Method m = view2.getClass().getMethod(listenerSetter, listenerType);//获取方法
                            m.invoke(view2,object2);//调用方法
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /** 注入 Animation 资源字符串 **/
    private static void initAnimation(Object object, View view) {
        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Animation initView = field.getAnnotation(GT_Res.GT_Animation.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    Context context = view.getContext();
                    if(context != null){
                        Animation animation = AnimationUtils.loadAnimation(context, viewRes);
                        field.setAccessible(true);
                        field.set(object,animation);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 Dimen 资源字符串 **/
    private static void initDimen(Object object, View view) {
        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Dimen initView = field.getAnnotation(GT_Res.GT_Dimen.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    float dimension = view.getResources().getDimension(viewRes);
                    field.setAccessible(true);
                    field.set(object,dimension);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 Drawable 资源字符串 **/
    private static void initDrawable(Object object, View view) {
        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Drawable initView = field.getAnnotation(GT_Res.GT_Drawable.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    Drawable drawable = view.getResources().getDrawable(viewRes);
                    field.setAccessible(true);
                    field.set(object,drawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 Color 资源字符串 **/
    private static void initColor(Object object, View view) {
        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Color initView = field.getAnnotation(GT_Res.GT_Color.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    int color = view.getResources().getColor(viewRes);
                    field.setAccessible(true);
                    field.set(object,color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 String 资源字符串 **/
    private static void initString(Object object, View view) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_String initView = field.getAnnotation(GT_Res.GT_String.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    String string = view.getResources().getString(viewRes);
                    field.setAccessible(true);
                    field.set(object,string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 Int 资源字符串数组 **/
    private static void initIntArray(Object object, View view) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_IntArray initView = field.getAnnotation(GT_Res.GT_IntArray.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    int[] intArray = view.getResources().getIntArray(viewRes);
                    field.setAccessible(true);
                    field.set(object,intArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 String 资源字符串数组 **/
    private static void initStringArray(Object object, View view) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_StringArray initView = field.getAnnotation(GT_Res.GT_StringArray.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    String[] stringArray = view.getResources().getStringArray(viewRes);
                    field.setAccessible(true);
                    field.set(object,stringArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 解析 Layout 资源文件成 View **/
    private static void initLayout(Object object, View view) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Layout initView = field.getAnnotation(GT_Res.GT_Layout.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    Context context = view.getContext();
                    if(context != null){
                        View viewLayout = LayoutInflater.from(context).inflate(viewRes, null);
                        field.setAccessible(true);
                        field.set(object,viewLayout);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 List 资源字符串 **/
    private static void initList(Object object) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            Collection.List initView = field.getAnnotation(Collection.List.class);
            if(initView!=null){
                Class[] classes = initView.value();
                List<Object> objectList = new ArrayList<>();//创建一个 ListView
                for(Class cla : classes){

                    String classPage = cla.toString();
                    String[] s = classPage.split(" ");
                    classPage = s[1];

                    //实例化一个对象
                    Object object2 = null;
                    try {
                        object2 = Class.forName(classPage).newInstance();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    objectList.add(object2);//添加每一个经过反射得到的 对象
                }
                try {
                    field.setAccessible(true);
                    field.set(object,objectList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 Map 资源字符串 **/
    private static void initMap(Object object) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            Collection.Map initView = field.getAnnotation(Collection.Map.class);
            if(initView!=null){
                Class[] classes = initView.value();
                Map<Object,Object> objectMap = new HashMap<>();//创建一个 Map
                for(Class cla : classes){

                    String classPage = cla.toString();
                    String[] s = classPage.split(" ");
                    classPage = s[1];

                    //实例化一个对象
                    Object object2 = null;
                    try {
                        object2 = Class.forName(classPage).newInstance();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    objectMap.put(cla,object2);//保存每个创建出来的 对象 key 为 每个对象的 class
                }
                try {
                    field.setAccessible(true);
                    field.set(object,objectMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 Set 资源字符串 **/
    private static void initSet(Object object) {

        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            Collection.Set initView = field.getAnnotation(Collection.Set.class);
            if(initView!=null){
                Class[] classes = initView.value();
                Set<Object> objectSet = new HashSet<>();//创建一个 Set
                for(Class cla : classes){

                    String classPage = cla.toString();
                    String[] s = classPage.split(" ");
                    classPage = s[1];

                    //实例化一个对象
                    Object object2 = null;
                    try {
                        object2 = Class.forName(classPage).newInstance();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    objectSet.add(object2);//保存每个创建出来的 对象
                }
                try {
                    field.setAccessible(true);
                    field.set(object,objectSet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }





    /**
     * Activity 中的使用
     */

    /**
     * activity 注入
     * @param activity
     */
    public static void initAll(Activity activity){

        initActivity(activity);             //为加载 Activity 布局初始化
        initView(activity);                 //为加载 组件 初始化
        initClick(activity);                //为加载 组件单击 初始化

        initObject(activity);               //为加载 Object 成员变量初始化

        initAnimation(activity);            //为加载 Animation 资源初始化
        initDimen(activity);                //为加载 Dimen 资源初始化
        initDrawable(activity);             //为加载 Style 资源初始化
        initColor(activity);                //为加载 Color 资源初始化
        initString(activity);               //为加载 String 资源初始化

        initIntArray(activity);             //为加载 IntArray 资源初始化
        initStringArray(activity);          //为加载 StringArray 资源初始化

        initLayout(activity);               //为加载 Layout 资源初始化

    }


    /** 解析 Layout 资源文件成 View **/
    private static void initLayout(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Layout initView = field.getAnnotation(GT_Res.GT_Layout.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    View view = LayoutInflater.from(activity).inflate(viewRes, null);
                    field.setAccessible(true);
                    field.set(activity,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 Int 资源字符串数组 **/
    private static void initIntArray(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_IntArray initView = field.getAnnotation(GT_Res.GT_IntArray.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    int[] intArray = activity.getResources().getIntArray(viewRes);
                    field.setAccessible(true);
                    field.set(activity,intArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 String 资源字符串数组 **/
    private static void initStringArray(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_StringArray initView = field.getAnnotation(GT_Res.GT_StringArray.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    String[] stringArray = activity.getResources().getStringArray(viewRes);
                    field.setAccessible(true);
                    field.set(activity,stringArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** 注入 Animation 资源字符串 **/
    private static void initAnimation(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Animation initView = field.getAnnotation(GT_Res.GT_Animation.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    Animation animation = AnimationUtils.loadAnimation(activity, viewRes);
                    field.setAccessible(true);
                    field.set(activity,animation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 Dimen 资源字符串 **/
    private static void initDimen(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Dimen initView = field.getAnnotation(GT_Res.GT_Dimen.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    float dimension = activity.getResources().getDimension(viewRes);
                    field.setAccessible(true);
                    field.set(activity,dimension);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 Drawable 资源字符串 **/
    private static void initDrawable(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Drawable initView = field.getAnnotation(GT_Res.GT_Drawable.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    Drawable drawable = activity.getResources().getDrawable(viewRes);
                    field.setAccessible(true);
                    field.set(activity,drawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 Color 资源字符串 **/
    private static void initColor(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_Color initView = field.getAnnotation(GT_Res.GT_Color.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    int color = activity.getResources().getColor(viewRes);
                    field.setAccessible(true);
                    field.set(activity,color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 注入 String 资源字符串 **/
    private static void initString(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Res.GT_String initView = field.getAnnotation(GT_Res.GT_String.class);
            if(initView!=null){
                int viewRes = initView.value();
                try {
                    String string = activity.getResources().getString(viewRes);
                    field.setAccessible(true);
                    field.set(activity,string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 参数版
     * @param object
     */
    private static void initObject(Object object){
        Class<? extends Object> clazz = object.getClass();//获取该类信息
        Field[] fields = clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_Object initView = field.getAnnotation(GT_Object.class);

            if(initView != null){

                //获取 完整的类路径
                String classPage = field.toString();
                String[] s = classPage.split(" ");
                classPage = s[1];

                //实例化一个对象
                Object object2 = null;
                try {
                    object2 = Class.forName(classPage).newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                //获取参数的值类型
                String type = initView.type();
                String[] types = initView.types();


                //创建保存 参数类型的容器
                List<Object> valueList = new ArrayList<>();
                if(type.length() != 0){
                    valueType(type,valueList,initView,0);//将当前的单个数据赋值到 listView 中
                }else if(types.length != 0){
                    for(int i = 0; i < types.length; i++){
                        valueType(types[i],valueList,initView,i);//将当前的多个数据赋值到 listView 中
                    }
                }
                String function = initView.function();
                String[] functions = initView.functions();

                /**
                 * 获取当前方法所有方法
                 */
                if(function.length() != 0 && valueList.size() != 0){
                    functionValue(field,object2,function,valueList,0);//对相应的方法进行赋值
                }else if(functions.length != 0 && valueList.size() != 0){
                    for(int i = 0; i < functions.length; i++){
                        functionValue(field,object2,functions[i],valueList,i);//对相应的方法进行赋值
                    }
                }


                //给注解下面的 成员变量注入值
                try {
                    field.setAccessible(true);
                    field.set(object,object2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void functionValue(Field field, Object object, String functionName, List<Object> valueList, int index){

        if(functionName.length() != 0 && valueList.size() != 0){

            Class<?> aClass = object.getClass();
            Method[] methods = aClass.getMethods();

            //获取当前类中所有方法
            for(int i = methods.length-1; i >= 0 ; i--){
                String name = methods[i].getName();
                if(name.equals(functionName)){
                    try {
                        Method method = getAllValueTypeMethod(valueList.get(index),functionName,aClass);
                        method.setAccessible(true);
                        field.setAccessible(true);
                        method.invoke(object, valueList.get(index));
                    } catch (Exception e) {
                        GT.log_i("赋值报错");
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * 自获取当前传入数据的类型
     * @param data
     * @param functionName
     * @param aClass
     * @return
     */
    private static Method getAllValueTypeMethod(Object data, String functionName, Class<?> aClass){

        Method method = null;

        Class<?> aClass1 = data.getClass();
        switch (aClass1.toString()){
            case "class java.lang.Byte":
                try {
                    method =  aClass.getMethod(functionName, byte.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Short":
                try {
                    method =  aClass.getMethod(functionName, short.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Integer":
                try {
                    method =  aClass.getMethod(functionName, int.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Long":
                try {
                    method =  aClass.getMethod(functionName, long.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Float":
                try {
                    method =  aClass.getMethod(functionName, float.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Double":
                try {
                    method =  aClass.getMethod(functionName, double.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Boolean":
                try {
                    method =  aClass.getMethod(functionName, boolean.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.Character":
                try {
                    method =  aClass.getMethod(functionName, char.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
            case "class java.lang.String":
                try {
                    method =  aClass.getMethod(functionName, String.class);
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                }
                break;
        }
        return method;
    }


    /**
     * 给 listView 赋值
     * @param type
     * @param list
     * @param values
     */
    private static void valueType(String type, List<Object> list, GT_Object values, int index){

        switch (type){

            /** 单个参数的赋值 **/
            case "byte":
                list.add(values.valueByte());
                break;
            case "short":
                list.add(values.valueShort());
                break;
            case "int":
                list.add(values.valueInt());
                break;
            case "long":
                list.add(values.valueLong());
                break;
            case "float":
                list.add(values.valueFloat());
                break;
            case "double":
                list.add(values.valueDouble());
                break;
            case "boolean":
                list.add(values.valueBoolean());
                break;
            case "char":
                list.add(values.valueChar());
                break;
            case "String":
                list.add(values.valueString());
                break;

            /** 多个参数的赋值 **/
            case "bytes":
                byte[] bytes = values.valueBytes();
                for(byte value:bytes){
                    list.add(value);
                }
                break;
            case "shorts":
                short[] shorts = values.valueShorts();
                list.add(shorts[index]);
                break;
            case "ints":
                int[] ints = values.valueInts();
                list.add(ints[index]);
                break;
            case "longs":
                long[] longs = values.valueLongs();
                list.add(longs[index]);
                break;
            case "floats":
                float[] floats = values.valueFloats();
                list.add(floats[index]);
                break;
            case "doubles":
                double[] doubles = values.valueDoubles();
                list.add(doubles[index]);
                break;
            case "booleans":
                boolean[] booleans = values.valueBooleans();
                list.add(booleans[index]);
                break;
            case "chars":
                char[] chars = values.valueChars();
                list.add(chars[index]);
                break;
            case "Strings":
                String[] strings = values.valueStrings();
                list.add(strings[index]);
                break;
        }

    }


    /**
     * 注入 ContextView
     * @param activity
     */
    private static void initActivity(Activity activity){
        Class<? extends Activity> mClass = activity.getClass();//获取该类信息
        GT_Activity contentView = mClass.getAnnotation(GT_Activity.class);//获取该类 ContextView 的注解类
        //如果有注解
        if(contentView != null){
            int viewId = contentView.value();//获取注解类参数
            try {
                Method method = mClass.getMethod("setContentView",int.class);//获取该方法的信息
                method.setAccessible(true);//获取该方法的访问权限
                method.invoke(activity,viewId);//调用该方法的，并设置该方法参数
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *注入控件
     * @param activity
     */
    private static void initView(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();//获取该类信息
        Field[] fields=clazz.getDeclaredFields();//获致所有成员变更
        for (Field field:fields) {
            GT_View initView = field.getAnnotation(GT_View.class);
            if(initView!=null){
                int viewId=initView.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    method.setAccessible(true);
                    field.setAccessible(true);
                    Object object = method.invoke(activity, viewId);
                    field.set(activity,object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 注入点击事件
     * @param activity
     */
    private static void initClick(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods= clazz.getMethods();//获取所有声明为公有的方法
        for (Method method:methods){//遍历所有公有方法
            Annotation[] annotations = method.getAnnotations();//获取该公有方法的所有注解
            for (Annotation annotation:annotations){//遍历所有注解
                Class<? extends Annotation> annotationType = annotation.annotationType();//获取具体的注解类
                OnClickEvent onClickEvent = annotationType.getAnnotation(OnClickEvent.class);//取出注解的onClickEvent注解
                if(onClickEvent!=null){//如果不为空
                    try {
                        Method valueMethod=annotationType.getDeclaredMethod("value");//获取注解InjectOnClick的value方法
                        int[] viewIds= (int[]) valueMethod.invoke(annotation, (Object[]) null);//获取控件值
                        Class<?> listenerType = onClickEvent.listenerType();//获取接口类型
                        String listenerSetter = onClickEvent.listenerSetter();//获取set方法
                        String methodName = onClickEvent.methodName();//获取接口需要实现的方法
                        MyInvocationHandler handler = new MyInvocationHandler(activity);//自己实现的代码，负责调用
                        handler.setMethodMap(methodName,method);//设置方法及设置方法
                        Object object= Proxy.newProxyInstance(listenerType.getClassLoader(),new Class<?>[]{listenerType},handler);//创建动态代理对象类
                        for (int viewId:viewIds){//遍历要设置监听的控件
                            View view=activity.findViewById(viewId);//获取该控件
                            Method m=view.getClass().getMethod(listenerSetter, listenerType);//获取方法
                            m.invoke(view,object);//调用方法
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //注解帮助类
    static class MyInvocationHandler implements InvocationHandler {

        private Object object;
        private Map<String, Method> methodMap = new HashMap<>(1);
        public MyInvocationHandler(Object object) {
            this.object = object;
        }
        public void setMethodMap(String name, Method method) {
            this.methodMap.put(name, method);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (object != null) {
                String name = method.getName();
                method= this.methodMap.get(name);
                if (method != null) {
                    return method.invoke(object, args);
                }
            }
            return null;
        }

    }

}
