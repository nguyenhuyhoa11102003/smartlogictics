import { useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label, } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Checkbox } from './ui/checkbox';
import { Separator } from "@/components/ui/separator"



interface Product {
    productName: string;
    weight: number;
    value: number;
    quantity: number;
}

export default function ProductForm() {
    const [productType, setProductType] = useState<string>('buu kien');
    const [productName, setProductName] = useState<string>('');
    const [weight, setWeight] = useState<number | ''>(0);
    const [quantity, setQuantity] = useState<number | ''>(1);
    const [value, setValue] = useState<number | ''>(0);
    const [totalWeight, setTotalWeight] = useState<number>(0);
    const [totalValue, setTotalValue] = useState<number>(0);
    // const [specialCharacteristics, setSpecialCharacteristics] = useState({
    //     highValue: false,
    //     invoice: false,
    //     legalDocs: false,
    //     bidDocuments: false,
    // });
    const [specialCharacteristics, setSpecialCharacteristics] = useState({
        highValue: false,
        fragile: false,
        solidBlock: false,
        oversized: false,
        liquid: false,
        magnetic: false,
        coldGoods: false,
    });
    const [dimensions, setDimensions] = useState({ length: 0, width: 0, height: 0 });
    const [orderId, setOrderId] = useState<string>('');
    const [products, setProducts] = useState<Product[]>([]);


    const handleProductTypeChange = (value: string) => {
        setProductType(value);
    };

    const handleAddProduct = () => {

        if (weight && value && quantity) {
            const newProduct: Product = { productName, weight, value, quantity };
            setProducts([...products, newProduct]);
            setTotalWeight(weight);
            setTotalValue(value);

            // Reset input fields
            setWeight('');
            setValue('');
            setQuantity('');
            setProductName('');
        } else {
            alert('Please fill in all fields');
        }
    };

    const handleCheckboxChange = (checked: boolean, field: string) => {
        setSpecialCharacteristics((prev) => ({
            ...prev,
            [field]: checked,
        }));
    };

    const handleDimensionChange = (e: React.ChangeEvent<HTMLInputElement>, field: string) => {
        setDimensions((prev) => ({
            ...prev,
            [field]: parseInt(e.target.value),
        }));
    };

    const handleDeleteProduct = (index: number) => {
        setProducts(products.filter((_, i) => i !== index));
    }

    return (
        <div className="border-2 shadow-lg p-4 rounded-lg">
            <h1 className="text-xl font-semibold mb-4">Thông Tin Hàng Hóa</h1>
            <div className="form-section mb-4">
                <Label>Loại Hàng Hóa:</Label>
                <RadioGroup defaultValue={productType} onValueChange={handleProductTypeChange} className='flex'>
                    <div className="flex items-center space-x-2">
                        <RadioGroupItem value="buu kien" id="buu kien" />
                        <Label htmlFor="buu kien">Bưu kiện</Label>
                    </div>
                    <div className="flex items-center space-x-2">
                        <RadioGroupItem value="tai lieu" id="tai lieu" />
                        <Label htmlFor="tai lieu">Tài liệu</Label>
                    </div>
                </RadioGroup>
            </div>
            <Separator orientation='horizontal' className='mb-2' />

            <div className="space-y-2">
                <h3 className="text-lg font-bold  max-h-64 overflow-y-auto rounded-lg">Danh sách hàng hóa đã thêm:</h3>
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
                    )))}
            </div>
            <Separator orientation='horizontal' className='mb-2' />

            <div className="form-section mb-4">
                <Label>Tên Hàng:</Label>
                <Input
                    type="text"
                    placeholder="Nhập tên hàng hóa"
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                    className="w-full"
                />
            </div>

            <div className="form-section mb-4 flex gap-4">
                <div className="flex-1">
                    <Label>Số lượng:</Label>
                    <Input
                        type="number"
                        placeholder="Số lượng"
                        value={quantity}
                        onChange={(e) => setQuantity(Number(e.target.value))}
                        className="w-full"
                    />
                </div>
                <div className="flex-1">
                    <Label>Trọng Lượng:</Label>
                    <Input
                        type="number"
                        placeholder="Trọng lượng (g)"
                        value={weight}
                        onChange={(e) => setWeight(Number(e.target.value))}
                        className="w-full"
                    />
                </div>
                <div className="flex-1">
                    <Label>Giá trị Hàng:</Label>
                    <Input
                        type="number"
                        placeholder="Giá trị (VND)"
                        value={value}
                        onChange={(e) => setValue(Number(e.target.value))}
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
                    <p className="text-2xl font-bold text-blue-600">{totalWeight} g</p>
                </div>
                <div className="border-l-2 border-gray-300 h-12 mx-4"></div>
                <div className="text-center">
                    <p className="text-lg font-semibold text-gray-700">Tổng Giá Trị</p>
                    <p className="text-2xl font-bold text-green-600">
                        {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalValue)}
                    </p>
                </div>
            </div>
            <Separator />

            <div className="form-section mb-4">
                <Label>Tính Chất Hàng Hóa Đặc Biệt:</Label>
                <div className="flex flex-wrap gap-4">
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.highValue}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'highValue')}
                        />
                        <Label className="ml-2">Giá trị cao</Label>
                    </div>
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.fragile}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'fragile')}
                        />
                        <Label className="ml-2">Dễ vỡ</Label>
                    </div>
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.solidBlock}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'solidBlock')}
                        />
                        <Label className="ml-2">Nguyên khối</Label>
                    </div>
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.oversized}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'oversized')}
                        />
                        <Label className="ml-2">Quá khổ</Label>
                    </div>
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.liquid}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'liquid')}
                        />
                        <Label className="ml-2">Chất lỏng</Label>
                    </div>
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.magnetic}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'magnetic')}
                        />
                        <Label className="ml-2">Từ tính, Pin</Label>
                    </div>
                    <div className="flex items-center w-1/3">
                        <Checkbox
                            checked={specialCharacteristics.coldGoods}
                            onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'coldGoods')}
                        />
                        <Label className="ml-2">Hàng lạnh</Label>
                    </div>
                </div>
            </div>

            <div className="form-section mb-4">
                <Label>Kích Thước (cm):</Label>
                <div className="flex gap-4">
                    <Input
                        type="number"
                        placeholder="Dài"
                        value={dimensions.length}
                        onChange={(e) => handleDimensionChange(e, 'length')}
                        className="w-1/3"
                    />
                    <Input
                        type="number"
                        placeholder="Rộng"
                        value={dimensions.width}
                        onChange={(e) => handleDimensionChange(e, 'width')}
                        className="w-1/3"
                    />
                    <Input
                        type="number"
                        placeholder="Cao"
                        value={dimensions.height}
                        onChange={(e) => handleDimensionChange(e, 'height')}
                        className="w-1/3"
                    />
                </div>
            </div>

            <div className="form-section mb-4">
                <Label>Mã Đơn Hàng:</Label>
                <Input
                    type="text"
                    placeholder="Nhập mã đơn hàng tự tạo"
                    value={orderId}
                    onChange={(e) => setOrderId(e.target.value)}
                    className="w-full"
                />
            </div>
        </div>
    );
}
