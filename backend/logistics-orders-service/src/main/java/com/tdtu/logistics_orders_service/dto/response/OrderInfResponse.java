package com.tdtu.logistics_orders_service.dto.response;

import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
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
@Data
public class OrderInfResponse {
	private String id; // ID
	private String orderHdrID; // ID tiêu đề đơn hàng
	private String originalID; // ID gốc của đơn hàng
	private String itemCode; // Mã vận đơn
	private String shipmentId; // Mã vận đơn
	private String originalItemCode; // Mã vận đơn gốc
	private OrderStatus status; // Trạng thái đơn hàng
	private String saleOrderCode; // Mã đơn bán hàng
	private String senderCode; // Mã người gửi
	private String senderContractNumber; // Mã hợp đồng của người gửi
	private String senderName; // Tên người gửi
	private String senderPhone; // Số điện thoại người gửi
	private String senderEmail; // Email người gửi
	private String senderAddress; // Địa chỉ người gửi
	private String senderProvinceCode; // Mã tỉnh/thành phố của người gửi
	private String senderProvinceName; // Tên tỉnh/thành phố của người gửi
	private String senderDistrictCode; // Mã quận/huyện của người gửi
	private String senderDistrictName; // Tên quận/huyện của người gửi
	private String senderCommuneCode; // Mã xã/phường của người gửi
	private String senderCommuneName; // Tên xã/phường của người gửi
	private String senderPostcode; // Mã bưu chính của người gửi
	private String receiverCode; // Mã người nhận
	private String receiverName; // Tên người nhận
	private String receiverContractNumber; // Mã hợp đồng của người nhận
	private String receiverAddress; // Địa chỉ người nhận
	private String receiverProvinceCode; // Mã tỉnh/thành phố của người nhận
	private String receiverProvinceName; // Tên tỉnh/thành phố của người nhận
	private String receiverDistrictCode; // Mã quận/huyện của người nhận
	private String receiverDistrictName; // Tên quận/huyện của người nhận
	private String receiverCommCode; // Mã xã/phường của người nhận
	private String receiverCommName; // Tên xã/phường của người nhận
	private String receiverPostcode; // Mã bưu chính của người nhận
	private String receiverPhone; // Số điện thoại người nhận
	private String receiverEmail; // Email người nhận
	private String serviceCode; // Mã dịch vụ
	private Integer totalFee; // Tổng phí
	private Integer mainFee; // Phí chính
	private Integer mainFeeBeforeTax; // Phí chính trước thuế
	private Integer mainTax; // Thuế chính
	private Integer vasFee; // Phí dịch vụ gia tăng
	private Integer codAmount; // Số tiền thu hộ
	private List<AddonServiceResponse> addonService; // Danh sách dịch vụ bổ sung
	private List<AdditionRequestResponse> additionRequest; // Danh sách yêu cầu bổ sung
	private Integer weight; // Trọng lượng
	private Integer length; // Chiều dài
	private Integer width; // Chiều rộng
	private Integer height; // Chiều cao
	private Integer dimWeight; // Trọng lượng thể tích
	private Integer priceWeight; // Trọng lượng tính giá
	private String deliveryInstruction; // Hướng dẫn giao hàng
	private Integer quantity; // Số lượng
	private String contentNote; // Ghi chú nội dung
	private List<Object> contentDetail; // Chi tiết nội dung
	private String sendType; // Loại gửi
	private Boolean isBroken; // Hàng hóa có bị hư hỏng không
	private String deliveryTime; // Thời gian giao hàng
	private String deliveryRequire; // Yêu cầu giao hàng
	private String vehicle; // Loại phương tiện
	private String awbNumber; // Số vận đơn hàng không
	private String voucher; // Mã giảm giá
	private String orgCodeCollect; // Mã tổ chức thu gom
	private String orgCodeAccept; // Mã tổ chức nhận hàng
	private String createdBy; // Người tạo
	private String createdDate; // Ngày tạo
	private String updatedBy; // Người cập nhật
	private String updatedDate; // Ngày cập nhật
	private String acceptedDate; // Ngày chấp nhận
	private String sortingCode; // Mã phân loại
	private String paymentStatus; // Trạng thái thanh toán
	private String caseTypeName; // Loại case
	private String caseStatus; // Trạng thái case
	private String source; // Nguồn
	private String inputType; // Loại đầu vào
	private String inputMethod; // Phương thức đầu vào
	private List<Object> documents; // Tài liệu
	private List<Object> packageInfo; // Thông tin gói hàng

}
