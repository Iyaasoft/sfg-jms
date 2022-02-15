package guru.springframework.sfgjms.msg.listener;

import guru.springframework.sfgjms.HelloworldMessage;
import guru.springframework.sfgjms.config.JmsConfig;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Message;
import java.util.UUID;

@AllArgsConstructor
@Component
public class HelloworldListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.HELLO_Q)
    public void messageListener(@Payload HelloworldMessage helloworldMsg, @Headers MessageHeaders headers, Message message) {
        System.out.println("Message received hello q!!");
        System.out.println(helloworldMsg);
    }


    @JmsListener(destination = JmsConfig.SEND_RCV_Q)
    public void messageSendRcvListener(@Payload HelloworldMessage helloworldMsg, @Headers MessageHeaders headers, Message message) throws Exception {
        System.out.println("Message received send_rcv_q!! " + helloworldMsg);
        HelloworldMessage msg = HelloworldMessage.builder()
                .ID(UUID.randomUUID())
                .message("Hello alien world")
                .build();

        //  spring message object
        //      jmsTemplate.convertAndSend((Destination)message.getHeaders().get("jms_replyTo"), msg );

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), msg );

    }
}
