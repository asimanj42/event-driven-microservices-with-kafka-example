package az.company.orderservice.controller;

import az.company.basedomain.model.Order;
import az.company.basedomain.model.OrderEvent;
import az.company.orderservice.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class OrderEventController {

    private final OrderProducer orderProducer;

    @PostMapping("/orders")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        OrderEvent orderEvent = new OrderEvent();
        order.setOrderId(UUID.randomUUID());
        orderEvent.setOrder(order);
        orderEvent.setMessage("order is created successfully");
        orderEvent.setStatus("CREATED");
        orderProducer.sendMessage(orderEvent);
        return ResponseEntity.ok("Order placed successfully");
    }
}
