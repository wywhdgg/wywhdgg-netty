package com.wywhdgg.dzb.wywhdggnetty.netty.pipeline;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class AHandler extends AbstractHandler {
    @Override
    public void doHandler(HandlerChainContext handlerChainContext, Object arg) {
        System.out.println("handler A ,data:"+arg);
        arg = arg.toString() + "..handler1的小尾巴.....";
        System.out.println("我是Handler1的实例，我在处理：" + arg);
        handlerChainContext.runNext(arg);
    }
}
