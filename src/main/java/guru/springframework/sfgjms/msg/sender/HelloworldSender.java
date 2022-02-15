package guru.springframework.sfgjms.msg.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.HelloworldMessage;
import guru.springframework.sfgjms.config.JmsConfig;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@AllArgsConstructor
@Component
public class HelloworldSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;

    @Scheduled(fixedRate = 2000)
    public void sendHello() {
      //  System.out.println("I'm sending a message ");
        HelloworldMessage msg = HelloworldMessage.builder()
                                    .ID(UUID.randomUUID())
                                    .message("Hello earth world")
                                    .build();

        jmsTemplate.convertAndSend(JmsConfig.HELLO_Q,msg);
      //  System.out.println("sent a message ");
    }

    @Scheduled(fixedRate = 2000)
    public void sendReceiveHello() throws JMSException {
        System.out.println("I'm sending a message ");
        HelloworldMessage msg = HelloworldMessage.builder()
                .ID(UUID.randomUUID())
                .message("Hello earth world")
                .build();

            Message received = jmsTemplate.sendAndReceive(JmsConfig.SEND_RCV_Q, (MessageCreator) session -> {
            Message helloMsg = null ;
            try {
                helloMsg = session.createTextMessage(mapper.writeValueAsString(msg));
                helloMsg.setStringProperty("_type", msg.getClass().getName());
                System.out.println("Sending message SEND_RCV_Q");
                return helloMsg;
            } catch (Exception e) {
                throw new JMSException("Explosion k-boom");
            }

        }
        );
        System.out.println("++++++  "+ received.getBody(String.class));
    }
}
