import React, { useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";

interface Address {
  senderName: string;
  senderPhone: string;
  senderMail: string;
  senderAddress: string;
  senderProvinceCode: string;
  senderProvinceName: string;
  senderDistrictCode: string;
  senderDistrictName: string;
  senderCommuneCode: string;
  senderCommuneName: string;
  senderPostalCode: string;
}

export default function AddressForm({ accessToken }: { accessToken: string }) {
  const [formData, setFormData] = useState<Address>({
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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post("https://your-api.com/api/addresses", formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      });

      console.log("Địa chỉ đã lưu:", response.data);
    } catch (error) {
      console.error("Lỗi khi lưu địa chỉ:", error);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-3">Nhập địa chỉ</h2>
      <form onSubmit={handleSubmit} className="row g-3">
        <div className="col-md-6">
          <label className="form-label">Họ và tên</label>
          <input type="text" name="senderName" className="form-control" value={formData.senderName} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Số điện thoại</label>
          <input type="text" name="senderPhone" className="form-control" value={formData.senderPhone} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Email</label>
          <input type="email" name="senderMail" className="form-control" value={formData.senderMail} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Địa chỉ</label>
          <input type="text" name="senderAddress" className="form-control" value={formData.senderAddress} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Mã tỉnh</label>
          <input type="text" name="senderProvinceCode" className="form-control" value={formData.senderProvinceCode} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Tên tỉnh</label>
          <input type="text" name="senderProvinceName" className="form-control" value={formData.senderProvinceName} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Mã quận</label>
          <input type="text" name="senderDistrictCode" className="form-control" value={formData.senderDistrictCode} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Tên quận</label>
          <input type="text" name="senderDistrictName" className="form-control" value={formData.senderDistrictName} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Mã phường</label>
          <input type="text" name="senderCommuneCode" className="form-control" value={formData.senderCommuneCode} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Tên phường</label>
          <input type="text" name="senderCommuneName" className="form-control" value={formData.senderCommuneName} onChange={handleChange} required />
        </div>

        <div className="col-md-6">
          <label className="form-label">Mã bưu chính</label>
          <input type="text" name="senderPostalCode" className="form-control" value={formData.senderPostalCode} onChange={handleChange} required />
        </div>

        <div className="col-12">
          <button type="submit" className="btn btn-primary w-100">Lưu địa chỉ</button>
        </div>
      </form>
    </div>
  );
}
