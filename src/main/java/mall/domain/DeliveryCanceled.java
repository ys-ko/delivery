package mall.domain;

import java.util.Date;
import lombok.Data;
import mall.domain.*;
import mall.infra.AbstractEvent;

@Data
public class DeliveryCanceled extends AbstractEvent {

    private Long id;
    private String orderId;
    private Integer qty;
    private String productId;
    private String status;
    private String customerId;

    public DeliveryCanceled(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryCanceled() {
        super();
    }
    // keep

}
