package com.wywhdgg.dzb.wywhdggnetty.netty.pipeline;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 * 责任链模式
 */
public class PipelineDemo {
    public HandlerChainContext head = new HandlerChainContext(new AbstractHandler() {
        @Override
        public void doHandler(HandlerChainContext handlerChainContext, Object arg) {
            handlerChainContext.runNext(arg);
        }
    });

    public void requestProcess(Object arg) {
        this.head.handler(arg);
    }

    public void addLast(AbstractHandler handler) {
        HandlerChainContext context = head;
        synchronized (this) {
            while (context.next != null) {
                context = context.next;
            }
            context.next = new HandlerChainContext(handler);
        }
    }

    public static void main(String[] args) {
        PipelineDemo pipelineDemo = new PipelineDemo();
        pipelineDemo.addLast(new AHandler());
        pipelineDemo.addLast(new BHandler());
        pipelineDemo.addLast(new CHandler());
        pipelineDemo.requestProcess("开始执行任务 pipeline --->");
    }
}
