package com.wywhdgg.dzb.wywhdggnetty.netty.pipeline;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class HandlerChainContext {
     HandlerChainContext next;
     AbstractHandler handler;

    public HandlerChainContext(AbstractHandler handler) {
        this.handler = handler;
    }

    public void handler(Object arg) {
        this.handler.doHandler(this, arg);
    }

    public void runNext(Object arg) {
        if (this.next != null) {
            this.next.handler(arg);
        }
    }
}
