package com.wywhdgg.dzb.wywhdggnetty.netty.pipeline;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public abstract class AbstractHandler {

    public  abstract void doHandler(HandlerChainContext handlerChainContext,Object arg);

}
