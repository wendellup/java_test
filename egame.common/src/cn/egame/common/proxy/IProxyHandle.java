/**
 * 
 */
package cn.egame.common.proxy;

/**
 * Description  TODO
 * 
 * @ClassName IProxyHandle
 *
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author zhangjb
 * 
 * @Create Date  Mar 4, 2015
 * 
 * @Modified by none
 *
 * @Modified Date 
 */

public interface IProxyHandle {
    public boolean checkMethod(Object[] args, String method);
}
