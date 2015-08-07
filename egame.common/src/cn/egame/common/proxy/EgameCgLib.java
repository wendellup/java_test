package cn.egame.common.proxy;
/**
 * 
 */

import net.sf.cglib.proxy.Enhancer;

/**
 * Description TODO
 * 
 * @ClassName EgameCgLib
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author zhangjb
 * 
 * @Create Date Jan 15, 2015
 * 
 * @Modified by none
 * 
 * @Modified Date
 */

public class EgameCgLib {


    /**
     * 创建代理对象
     * 
     * @param target
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Object createProxyClass(Class clazz) {
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new EgameCgLibCallBack());
        // 通过字节码技术动态创建子类实例
        Object obj = enhancer.create();
        return obj;
    }
}
