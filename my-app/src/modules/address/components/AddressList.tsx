import {  useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Modal, Button, Table } from "react-bootstrap";
import { useAuth } from "@/context/app.context";
import axios from "axios";
import AddressForm from "@/modules/order/components/AddressForm";
import { Address } from "@/modules/address/models/AddressModel";
import { useRouter } from "next/router";

type AddressListProps = {
    senderName: "",
    senderPhone: "",
    senderMail: "",
    senderAddress: "",
    senderProvinceCode: "",
    senderProvinceName: "",
    senderDistrictCode: "",
    senderDistrictName: "",
    senderCommuneCode: "",
    senderCommuneName: "",
    senderPostalCode: "",
}
export default function AddressList() {
  const { accessToken }  = useAuth();
  const [addresses, setAddresses] = useState<AddressListProps[]>([]);

  const [show, setShow] = useState(false);
  const [newAddress, setNewAddress] = useState<Partial<Address>>({});
  const [editIndex, setEditIndex] = useState<number | null>(null);
  const [formData, setFormData] = useState({
    senderName: "",
    senderPhone: "",
    senderMail: "",
    senderAddress: "",
    senderProvinceCode: "",
    senderProvinceName: "",
    senderDistrictCode: "",
    senderDistrictName: "",
    senderCommuneCode: "",
    senderCommuneName: "",
    senderPostalCode: "",
  }); 

  const router = useRouter();
  let { page,  size } = router.query;



  const handleClose = () => {
    setShow(false);
    setNewAddress({});
    setEditIndex(null);
  };

  const handleShow = () => setShow(true);

  const  handleSave = async() => {
    const payload ={
      "senderName": formData.senderName,
      "senderPhone": formData.senderPhone,
      "senderMail": formData.senderMail,
      "senderAddress": newAddress.addressDetail,
      "senderProvinceCode": newAddress.stateOrProvinceId,
      "senderProvinceName": newAddress.stateOrProvinceName,
      "senderDistrictCode": newAddress.districtId,
      "senderDistrictName": newAddress.districtName,
      "senderCommuneCode": newAddress.wardId,
      "senderCommuneName": newAddress.wardName,
      "senderPostalCode": formData.senderPostalCode,
    }
    console.info(`payload:${JSON.stringify(payload)} , accessToken:${accessToken}`);
    const response = await axios.post("http://localhost:8082/users/sender/create", JSON.stringify(payload), {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });

    if (response.status === 200) {
      alert("Thêm địa chỉ thành công!");
    } else {
      alert("Có lỗi xảy ra khi thêm địa chỉ!");
    }
    handleClose();
  };

  const handleDelete = (index: number) => {
    setAddresses(addresses.filter((_, i) => i !== index));
  };

  const handleEdit = (index: number) => {
    // setEditIndex(index);
    // setNewAddress(addresses[index]);
    // handleShow();
  };


  const onAddressChange = (newAddress: Partial<Address>) => {
    setNewAddress(newAddress);
  }


  const fetchAddresses = async () => {
    page = (page || 0).toString();;
    size = (size || 10).toString();
    const response = await axios.get(`http://localhost:8082/users/api/sender?page=${page}&size=${size}`, {
    });
    console.info(`response:${JSON.stringify(response.data)}`);
    if (response.status === 200) {
      const senders = response.data._embedded.sender;
      console.info(`senders:${JSON.stringify(senders)}`);

      const updatedAddresses = [];
      for (const sender of senders) {
        console.log('fullname:', sender.fullName);
        console.log('phone:', sender.phoneNumber);
        console.log('email:', sender.email);
        console.log('address:', sender._links.address.href);
        const addressResponse = await axios.get(sender._links.address.href);
        if (addressResponse.status === 200) {
          console.log('address details:', addressResponse.data);
          updatedAddresses.push({
            senderName: sender.fullName,
            senderPhone: sender.phoneNumber,
            senderMail: sender.email,
            senderAddress: addressResponse.data.street,
            senderProvinceCode: addressResponse.data.provinceCode,
            senderProvinceName: addressResponse.data.province,
            senderDistrictCode: addressResponse.data.districtCode,
            senderDistrictName: addressResponse.data.district,
            senderCommuneCode: addressResponse.data.wardCode,
            senderCommuneName: addressResponse.data.ward,
            senderPostalCode: addressResponse.data.postalCode,
          });
        } else {
          console.error('Failed to fetch address:', addressResponse.status);
        }};
        console.info(`updatedAddresses:${JSON.stringify(updatedAddresses)}`);
        setAddresses(updatedAddresses);
    }
  }
  useEffect(() => {
    fetchAddresses();
  }, []);

  return (
    <div className="container mt-4">
      <h2>Danh Sách Địa Chỉ</h2>
      <Button variant="primary" onClick={handleShow}>
        + Thêm Địa Chỉ
      </Button>

      <Table striped bordered hover className="mt-3">
        <thead>
          <tr>
            <th>#</th>
            <th>Tỉnh</th>
            <th>Quan/huyen</th>
            <th>Xã/Phường</th>
            <th>Đường</th>
            <th>Mã Bưu Chính</th>
            <th>Hành Động</th>
          </tr>
        </thead>
        <tbody>
          {addresses.map((addr, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{addr.senderProvinceName}</td>
              <td>{addr.senderDistrictName}</td>
              <td>{addr.senderCommuneName}</td>
              <td>{addr.senderAddress}</td>
              <td>{addr.senderPostalCode}</td>
              <td>
                <Button variant="warning" className="me-2" onClick={() => handleEdit(index)}>
                  Chỉnh Sửa
                </Button>
                <Button variant="danger" onClick={() => handleDelete(index)}>
                  Xóa
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Modal Thêm/Chỉnh Sửa Địa Chỉ */}
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>{editIndex !== null ? "Chỉnh Sửa Địa Chỉ" : "Thêm Địa Chỉ Mới"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <div className="mb-3">
            <label htmlFor="senderName" className="form-label">Họ và tên</label>
            <input
              type="text"
              className="form-control"
              id="senderName"
              value={formData.senderName || ""}
              onChange={(e) => setFormData({ ...formData, senderName: e.target.value })}
              placeholder="Nhập họ và tên"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="senderPhone" className="form-label">Số điện thoại</label>
            <input
              type="text"
              className="form-control"
              id="senderPhone"
              value={formData.senderPhone || ""}
              onChange={(e) => setFormData({ ...formData, senderPhone: e.target.value })}
              placeholder="Nhập số điện thoại"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="senderMail" className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              id="senderMail"
              value={formData.senderMail || ""}
              onChange={(e) => setFormData({ ...formData, senderMail: e.target.value })}
              placeholder="Nhập email"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="postcode" className="form-label">Postcode</label>
            <input
              type="text"
              className="form-control"
              id="postcode"
              value={formData.senderPostalCode || ""}
              onChange={(e) => setFormData({ ...formData, senderPostalCode: e.target.value })}
              placeholder="Nhập ma buu kien"
            />
          </div>
          <AddressForm onAddressChange={onAddressChange}/>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Hủy
          </Button>
          <Button variant="primary" onClick={handleSave}>
            {editIndex !== null ? "Cập Nhật" : "Thêm"}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
