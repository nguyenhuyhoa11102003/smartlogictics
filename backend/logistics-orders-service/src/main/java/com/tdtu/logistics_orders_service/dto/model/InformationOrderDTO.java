package com.tdtu.logistics_orders_service.dto.model;

import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ReceivingMethod;
import lombok.Data;

import java.util.List;

/**
 * DTO for representing detailed information about an order.
 */
@Data
public class InformationOrderDTO {

    // Thông tin người gửi
    private String senderId; // Tên người gửi

//    private String senderPhone; // Số điện thoại người gửi
//    private String senderMail; // Email của người gửi
//    private String senderAddress; // Địa chỉ người gửi
//    private String senderProvinceCode; // Mã tỉnh/thành phố của người gửi
//    private String senderProvinceName; // Tên tỉnh/thành phố của người gửi
//    private String senderDistrictCode; // Mã quận/huyện của người gửi
//    private String senderDistrictName; // Tên quận/huyện của người gửi
//    private String senderCommuneCode; // Mã xã/phường của người gửi
//    private String senderCommuneName; // Tên xã/phường của người gửi

    // Thông tin người nhận
    private String receiverId; // Tên người nhận

//    private String receiverAddress; // Địa chỉ người nhận
//    private String receiverProvinceCode; // Mã tỉnh/thành phố của người nhận
//    private String receiverProvinceName; // Tên tỉnh/thành phố của người nhận
//    private String receiverDistrictCode; // Mã quận/huyện của người nhận
//    private String receiverDistrictName; // Tên quận/huyện của người nhận
//    private String receiverCommuneCode; // Mã xã/phường của người nhận
//    private String receiverCommuneName; // Tên xã/phường của người nhận
//    private String receiverPhone; // Số điện thoại người nhận
//    private String receiverEmail; // Email của người nhận (có thể null)

    // Dịch vụ và yêu cầu bổ sung
    private DeliveryServiceType serviceCode; // Mã dịch vụ giao hàng

    private List<AddonServiceDTO> addonService; // Danh sách dịch vụ bổ sung

    private List<AdditionRequestDTO> additionRequest; // Danh sách yêu cầu bổ sung

    // Thông tin chi nhánh
    private String branchCode ; // Mã chi nhánh wareHouseId
//    private String orgCodeCollect; // Mã tổ chức thu gom (có thể null)
//    private Integer orgCodeAccept; // Mã tổ chức nhận hàng


    // Thông tin vận chuyển
    private String vehicle; // Loại phương tiện vận chuyển (VD: BO - Bộ, BI - Bưu điện)

    private ReceivingMethod receivingMethod;

    private boolean isBroken; // Hàng hóa có bị hư hỏng không? (0 - Không, 1 - Có)

    private String deliveryTime; // Thời gian giao hàng dự kiến (VD: N - Ngày thường)

    private String deliveryRequire; // Yêu cầu giao hàng (VD: 1 - Bắt buộc giao tận tay)

    private String deliveryInstruction; // Hướng dẫn giao hàng

    // Thông tin khác: chua phat trien duoc goods-service => de tam data o day:
    private String saleOrderCode; // Mã đơn hàng bán goodId

    private String contentNote; // Ghi chú về nội dung hàng hóa
    private String weight; // Trọng lượng đơn vị hàng (gram)
    private String width; // Chiều rộng của hàng hóa (có thể null)
    private String length; // Chiều dài của hàng hóa (có thể null)
    private String height; // Chiều cao của hàng hóa (có thể null)

}
