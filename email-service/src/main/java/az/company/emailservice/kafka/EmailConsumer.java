package az.company.emailservice.kafka;

import az.company.basedomain.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);
    private final JavaMailSender javaMailSender;


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.name}")
    public void consume(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event is consuming in Email Consumer: %s", orderEvent.toString()));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("trialforspring@gmail.com");
        message.setTo("asimanmammadli2023@gmail.com");
        message.setText(String.format("Your order event: %s", orderEvent.toString()));
        message.setSubject("Email service");
        javaMailSender.send(message);
        LOGGER.info("Email sent successfully");
    }

}