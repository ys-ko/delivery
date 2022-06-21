package mall.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import mall.config.kafka.KafkaProcessor;
import mall.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderplaced_StartDelivery(
        @Payload Orderplaced orderplaced
    ) {
        if (!orderplaced.validate()) return;
            Orderplaced event = orderplaced;
            System.out.println(
                "\n\n##### listener StartDelivery : " +
                orderplaced.toJson() +
                "\n\n"
        );

        Delivery.startDelivery(event);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_CancelDelivery(
        @Payload OrderCanceled orderCanceled
    ) {
        if (!orderCanceled.validate()) return;
        OrderCanceled event = orderCanceled;
        System.out.println(
            "\n\n##### listener CancelDelivery : " +
            orderCanceled.toJson() +
            "\n\n"
        );

        // Sample Logic //
        Delivery.cancelDelivery(event);
    }
    // keep

}
