package com.tdtu.logistics_orders_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    String id;

    @Column(name = "shipment_code", unique = true, length = 13)
    String shipmentCode; // Mã vận đơn

    @Column(name = "note")
    String note; // Ghi chú

    @Column(name = "more_require")
    String moreRequire; // Mô tả

    @Column(name = "order_code")
    String orderCode; // Mã vận đơn

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    Recipient recipient; // Liên kết đến người nhận

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    Sender sender; // Liên kết đến người gửi

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "shipper_id", referencedColumnName = "id")
    Shipper shipper; // Liên kết đến shipper

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<OrderGoodDetail> orderGoodDetails; // Liên kết danh sách OrderGoodDetail

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    Payment payment; // Liên kết với Payment
}
