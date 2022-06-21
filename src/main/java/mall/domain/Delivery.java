package mall.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import mall.DeliveryApplication;
import mall.domain.DeliveryCanceled;
import mall.domain.DeliveryStarted;

@Entity
@Table(name = "Delivery_table")
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;
    private Integer qty;
    private String productId;
    private String status;
    private String customerId;

    @PostPersist
    public void onPostPersist() {
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        deliveryCanceled.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    public static void startDelivery(Orderplaced orderplaced) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(String.valueOf(orderplaced.getId()));
        delivery.setQty(orderplaced.getQty());
        delivery.setProductId(orderplaced.getProductId());
        delivery.setStatus(DeliveryStarted.class.getSimpleName());
        delivery.setCustomerId(orderplaced.getCustomerId());
        repository().save(delivery);
    }

    public static void cancelDelivery(OrderCanceled orderCanceled) {
        repository().findByOrderId(String.valueOf(orderCanceled.getId())).ifPresent(delivery->{
            repository().delete(delivery);
         });
    }
}
