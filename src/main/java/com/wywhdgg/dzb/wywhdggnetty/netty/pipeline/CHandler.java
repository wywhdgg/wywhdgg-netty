package com.wywhdgg.dzb.wywhdggnetty.netty.pipeline;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class CHandler extends AbstractHandler {
    @Override
    public void doHandler(HandlerChainContext handlerChainContext, Object arg) {
        System.out.println("handler C ,data:"+arg);
        arg = arg.toString() + "..handlerC的小尾巴.....";
        System.out.println("我是HandlerC的实例，我在处理：" + arg);
        handlerChainContext.runNext(arg);
    }
}
