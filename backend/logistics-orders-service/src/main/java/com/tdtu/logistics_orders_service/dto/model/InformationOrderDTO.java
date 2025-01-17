package com.tdtu.logistics_orders_service.dto.model;

import com.tdtu.common.orders_service.enums.AddOnService;
import com.tdtu.common.orders_service.enums.ShippingMethod;
import com.tdtu.logistics_orders_service.enumrator.ReceivingMethod;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for representing detailed information about an order.
 */
@Data
public class InformationOrderDTO {

    // Thông tin người gửi
    private String senderId; // Tên người gửi

    // Thông tin người nhận
    private String receiverPhone; // Số điện thoại người nhận

    private String recipientName; // Tên người nhận

    private String receiverAddress; // Địa chỉ người nhận

    private String receiverProvinceCode; // Mã tỉnh/thành phố của người nhận

    private String receiverProvinceName; // Tên tỉnh/thành phố của người nhận

    private String receiverDistrictCode; // Mã quận/huyện của người nhận

    private String receiverDistrictName; // Tên quận/huyện của người nhận

    private String receiverCommuneCode; // Mã xã/phường của người nhận

    private String receiverCommuneName; // Tên xã/phường của người nhận

    private String receiverEmail; // Email của người nhận (có thể null)

    @DecimalMin(value = "0.0", inclusive = true, message = "Tiền thu hộ không được nhỏ hơn 0.0")
    private BigDecimal codAmount; // Tiền thu hộ

    // Dịch vụ và yêu cầu bổ sung
    private ShippingMethod shippingMethod; // Mã dịch vụ giao hàng

    private List<AddOnService> addOnServices; // Mã dịch vụ bổ sung

    private String deliveryTime; // Thời gian giao hàng dự kiến (VD: N - Ngày thường)

    // Thông tin chi nhánh
    private String branchCode ; // Mã chi nhánh wareHouseId
//    private String orgCodeCollect; // Mã tổ chức thu gom (có thể null)
//    private Integer orgCodeAccept; // Mã tổ chức nhận hàng

    // Thông tin vận chuyển
    private String vehicle; // Loại phương tiện vận chuyển (VD: BO - Bộ, BI - Bưu điện)

    private ReceivingMethod receivingMethod;

    private boolean isBroken; // Hàng hóa có bị hư hỏng không? (0 - Không, 1 - Có)

    private String deliveryInstruction; // Hướng dẫn giao hàng

    private String contentNote; // Ghi chú về nội dung hàng hóa

    private String weight; // Trọng lượng đơn vị hàng (gram)

    private String width; // Chiều rộng của hàng hóa (có thể null)

    private String length; // Chiều dài của hàng hóa (có thể null)

    private String height; // Chiều cao của hàng hóa (có thể null)

    private String shipmentId;  // mã vận đơn

    // Thông tin khác: chua phat trien duoc goods-service => de tam data o day:
    private String saleOrderCode; // Mã đơn hàng bán goodId
}
