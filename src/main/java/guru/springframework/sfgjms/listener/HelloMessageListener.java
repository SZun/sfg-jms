package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.models.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(
            @Payload HelloWorldMessage helloWorldMessage,
            @Headers MessageHeaders headers,
            Message message){
//        System.out.println("Message received");
//        System.out.println(helloWorldMessage);
//        throw new RuntimeException("foo");
    }

    @JmsListener(destination = JmsConfig.MY_SND_RCV_QUEUE)
    public void listenForHello(
            @Payload HelloWorldMessage helloWorldMessage,
            @Headers MessageHeaders headers,
            Message message) throws JMSException {

        HelloWorldMessage payloadMSG = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMSG);
//        throw new RuntimeException("foo");
    }

}
