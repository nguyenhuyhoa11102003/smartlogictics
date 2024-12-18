package com.tdtu.logistics_orders_service.entity;

import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import com.tdtu.logistics_orders_service.enumrator.ReceivingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id; // UUID của đơn hàng

    @Enumerated(EnumType.STRING)
    OrderStatus status; // Trạng thái đơn hàng

    @Column(name = "shipment_code", unique = true, length = 13)
    String shipmentCode; // Mã vận đơn

    @Column(name = "order_code")
    String orderCode; // Mã đơn hàng bán

    @Column(name = "note")
    String note; // Ghi chú

    @Column(name = "more_require")
    String moreRequire; // Yêu cầu bổ sung

    @Column(name = "recipient_id")
    String recipientId; // Liên kết đến người nhận

    @Column(name = "sender_id")
    String senderId; // Liên kết đến người gửi

    @Column(name = "branch_code")
    String branchCode; // Mã chi nhánh warehouse

    @Enumerated(EnumType.STRING)
    @Column(name = "service_code")
    DeliveryServiceType serviceCode; // Mã dịch vụ giao hàng

    @Enumerated(EnumType.STRING)
    @Column(name = "receiving_method")
    ReceivingMethod receivingMethod; // Phương thức nhận hàng

    @Column(name = "vehicle")
    String vehicle; // Loại phương tiện vận chuyển

    @Column(name = "is_broken")
    boolean isBroken; // Hàng hóa có bị hư hỏng không

    @Column(name = "delivery_time")
    String deliveryTime; // Thời gian giao hàng dự kiến

    @Column(name = "delivery_require")
    String deliveryRequire; // Yêu cầu giao hàng

    @Column(name = "delivery_instruction")
    String deliveryInstruction; // Hướng dẫn giao hàng

    @Column(name = "weight")
    String weight; // Trọng lượng đơn vị hàng (gram)

    @Column(name = "width")
    String width; // Chiều rộng của hàng hóa

    @Column(name = "length")
    String length; // Chiều dài của hàng hóa

    @Column(name = "height")
    String height; // Chiều cao của hàng hóa
}
