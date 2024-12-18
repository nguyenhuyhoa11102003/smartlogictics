import { useEffect, useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label, } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Checkbox } from '@/components/ui/checkbox';
import { Separator } from "@/components/ui/separator"
import { Product } from '@/modules/catalog/models/Product';

import { v4 as uuidv4 } from "uuid";

interface ProductFormProps {
    onSubmit: (product: Product) => void;
}

const labels = {
    highValue: "Giá trị cao",
    fragile: "Dễ vỡ",
    solidBlock: "Nguyên khối",
    oversized: "Quá cỡ",
    liquid: "Chất lỏng",
    magnetic: "Từ tính",
    coldGoods: "Hàng lạnh",
    gia_tri_cao: "Giá trị cao",
    hoa_don_giay_chung_nhan: "Hóa đơn giấy chứng nhận",
    ho_so_thau: "Hồ sơ thầu",
};



export default function ProductForm({ onSubmit }: ProductFormProps) {
    const [product, setProduct] = useState<Product>({
        productType: 'buu_kien',
        productName: '',
        weight: 0,
        quantity: 1,
        value: 0,
        totalWeight: 0,
        totalValue: 0,
        specialCharacteristics: {
            highValue: false,
            fragile: false,
            solidBlock: false,
            oversized: false,
            liquid: false,
            magnetic: false,
            coldGoods: false,
        },
        documentCharacteristics: {
            gia_tri_cao: false,
            hoa_don_giay_chung_nhan: false,
            ho_so_thau: false,
        },
        dimensions: { length: 0, width: 0, height: 0 },
        orderId: '',
    });

    useEffect(() => {
        setProduct((prev) => ({
            ...prev,
            orderId: uuidv4(), // Tạo mã UUID mới
        }));
    }, []);

    const [products, setProducts] = useState<Product[]>([]);

    const handleInputChange = <T extends keyof Product>(field: T, value: Product[T]) => {
        setProduct((prev) => ({
            ...prev,
            [field]: value,
        }));
    };

    const handleDimensionChange = (field: keyof Product['dimensions'], value: number) => {
        setProduct((prev) => ({
            ...prev,
            dimensions: {
                ...prev.dimensions,
                [field]: value,
            },
        }));
    };

    const handleCheckboxChange = (checked: boolean, field: string) => {
        setProduct((prev) => ({
            ...prev,
            specialCharacteristics: {
                ...prev.specialCharacteristics,
                [field]: checked,
            },
            documentCharacteristics: {
                ...prev.documentCharacteristics,
                [field]: checked,
            },
        }));
    };

    const handleAddProduct = () => {
        const { productName, weight, value, quantity } = product;

        if (productName && weight && value && quantity) {
            setProducts([...products, { ...product }]);

            // Cập nhật tổng trọng lượng và giá trị
            setProduct((prev) => ({
                ...prev,
                totalWeight: prev.totalWeight + weight * quantity,
                totalValue: prev.totalValue + value * quantity,
                productName: '',
                weight: 0,
                value: 0,
                quantity: 1,
            }));
        } else {
            alert('Vui lòng nhập đầy đủ thông tin!');
        }
    };

    const handleDeleteProduct = (index: number) => {
        const productToRemove = products[index];
        setProducts(products.filter((_, i) => i !== index));

        setProduct((prev) => ({
            ...prev,
            totalWeight: prev.totalWeight - productToRemove.weight * productToRemove.quantity,
            totalValue: prev.totalValue - productToRemove.value * productToRemove.quantity,
        }));
    };

    useEffect(() => {
        // if (product.productName && product.weight > 0 && product.quantity > 0) {
            onSubmit(product);
            // console.log(product)
        // }
    }, [product, onSubmit]);

    return (
        <div className="border-2 shadow-lg p-4 rounded-lg">
            <h1 className="text-xl font-semibold mb-4">Thông Tin Hàng Hóa</h1>

            <div className="form-section mb-4">
                <Label>Loại Hàng Hóa:</Label>
                <RadioGroup
                    value={product.productType}
                    onValueChange={(value) => handleInputChange('productType', value)}
                    className='flex'>
                    <div className="flex items-center space-x-2">
                        <RadioGroupItem value="buu_kien" id="buu kien" />
                        <Label htmlFor="buu kien">Bưu kiện</Label>
                    </div>
                    <div className="flex items-center space-x-2">
                        <RadioGroupItem value="tai_lieu" id="tai lieu" />
                        <Label htmlFor="tai lieu">Tài liệu</Label>
                    </div>
                </RadioGroup>
            </div>

            <Separator orientation='horizontal' className='mb-2' />

            <div className="space-y-2">
                <h3 className="text-lg font-bold max-h-64 overflow-y-auto rounded-lg">Danh sách hàng hóa đã thêm:</h3>
                {products.length === 0 ? (
                    <p>Chưa có hàng hóa nào được thêm.</p>
                ) : (
                    products.map((product, index) => (
                        <div key={index} className="border p-2 rounded-md flex justify-between items-center">
                            <div>
                                <p><strong>Sản phẩm số {index + 1}</strong>: {product.productName} </p>
                                <p><strong>Trọng Lượng:</strong> {product.weight} g</p>
                                <p><strong>Giá trị:</strong> {product.value} VND</p>
                                <p><strong>Số lượng:</strong> {product.quantity}</p>
                            </div>
                            <button
                                onClick={() => handleDeleteProduct(index)}
                                className="bg-red-500 text-white p-2 rounded-md"
                            >
                                Xóa
                            </button>
                        </div>
                    ))
                )}
            </div>

            <Separator orientation='horizontal' className='mb-2' />

            <div className="form-section mb-4">
                <Label>Tên Hàng:</Label>
                <Input
                    type="text"
                    placeholder="Nhập tên hàng hóa"
                    value={product.productName}
                    onChange={(e) => handleInputChange('productName', e.target.value)}
                    className="w-full"
                />
            </div>

            <div className="form-section mb-4 flex gap-4">
                <div className="flex-1">
                    <Label>Số lượng:</Label>
                    <Input
                        type="number"
                        placeholder="Số lượng"
                        value={product.quantity}
                        onChange={(e) => handleInputChange('quantity', Number(e.target.value))}
                        className="w-full"
                    />
                </div>
                <div className="flex-1">
                    <Label>Trọng Lượng:</Label>
                    <Input
                        type="number"
                        placeholder="Trọng lượng (g)"
                        value={product.weight}
                        onChange={(e) => handleInputChange('weight', Number(e.target.value))}
                        className="w-full"
                    />
                </div>
                <div className="flex-1">
                    <Label>Giá trị Hàng:</Label>
                    <Input
                        type="number"
                        placeholder="Giá trị (VND)"
                        value={product.value}
                        onChange={(e) => handleInputChange('value', Number(e.target.value))}
                        className="w-full"
                    />
                </div>
            </div>

            <Separator orientation='horizontal' className='mb-2' />

            <Button onClick={handleAddProduct} className="mb-4">
                Thêm Hàng Hóa
            </Button>

            <Separator />

            <div className="form-section mb-4 flex justify-around items-center bg-gray-100 p-4 rounded-lg shadow-md">
                <div className="text-center">
                    <p className="text-lg font-semibold text-gray-700">Tổng Khối Lượng</p>
                    <p className="text-2xl font-bold text-blue-600">{product.totalWeight} g</p>
                </div>
                <div className="border-l-2 border-gray-300 h-12 mx-4"></div>
                <div className="text-center">
                    <p className="text-lg font-semibold text-gray-700">Tổng Giá Trị</p>
                    <p className="text-2xl font-bold text-green-600">
                        {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(product.totalValue)}
                    </p>
                </div>
            </div>

            <Separator />

            {product.productType === "buu_kien" ? (
                <div className="form-section mb-4">
                    <Label>Tính Chất Hàng Hóa Đặc Biệt:</Label>
                    <div className="flex flex-wrap gap-4">
                        {Object.keys(product.specialCharacteristics).map((key) => (
                            <div className="flex items-center w-1/3" key={key}>
                                <Checkbox
                                    checked={product.specialCharacteristics[key as keyof typeof product.specialCharacteristics]}
                                    onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, key)}
                                />
                                <Label className="ml-2">{labels[key as keyof typeof labels]}</Label>
                            </div>
                        ))}
                    </div>
                </div>
            ) :  (
                <div className="form-section mb-4">
                    <Label>Tính Chất Hàng Hóa Đặc Biệt:</Label>
                    <div className="flex flex-wrap gap-4">
                        {Object.keys(product.documentCharacteristics).map((key) => (
                            <div className="flex items-center w-1/3" key={key}>
                                <Checkbox
                                    checked={product.documentCharacteristics[key as keyof typeof product.documentCharacteristics]}
                                    onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, key)}
                                />
                                <Label className="ml-2">{labels[key as keyof typeof labels]}</Label>
                            </div>
                        ))}
                    </div>
                </div>
            )}
            <div className="form-section mb-4">
                <Label>Kích Thước (cm):</Label>
                <div className="flex gap-4">
                    <Input
                        type="number"
                        placeholder="Dài (cm)"
                        value={product.dimensions.length}
                        onChange={(e) => handleDimensionChange('length', Number(e.target.value))}
                        className="w-full"
                    />
                    <Input
                        type="number"
                        placeholder="Rộng (cm)"
                        value={product.dimensions.width}
                        onChange={(e) => handleDimensionChange('width', Number(e.target.value))}
                        className="w-full"
                    />
                    <Input
                        type="number"
                        placeholder="Cao (cm)"
                        value={product.dimensions.height}
                        onChange={(e) => handleDimensionChange('height', Number(e.target.value))}
                        className="w-full"
                    />
                </div>

                <div className="form-section mb-4">
                    <Label>Mã Đơn Hàng:</Label>
                    <Input
                        type="text"
                        placeholder="Nhập mã đơn hàng tự tạo"
                        value={product.orderId}
                        className="w-full"
                        disabled
                    />
                </div>
            </div>
        </div>
    );
}