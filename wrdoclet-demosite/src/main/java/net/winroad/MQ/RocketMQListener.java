package net.winroad.MQ;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;  
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;  
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;  
import com.alibaba.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;  
  

public class RocketMQListener  implements MessageListenerConcurrently {  
  
    @Override  
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {  
        for (MessageExt message : msgs) {  
            String msg;
			try {
				msg = new String(message.getBody(),"UTF-8");
	            System.out.println("msg data from rocketMQ:" + msg);  
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
  
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;  
    }  
}  
