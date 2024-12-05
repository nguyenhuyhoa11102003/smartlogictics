import { useEffect, useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label, } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Checkbox } from './ui/checkbox';
import { Separator } from "@/components/ui/separator"
import { Product } from '@/modules/catalog/models/Product';

// export default function ProductForm() {

//     const [productType, setProductType] = useState<string>('buu kien');
//     const [productName, setProductName] = useState<string>('');
//     const [weight, setWeight] = useState<number | ''>(0);
//     const [quantity, setQuantity] = useState<number | ''>(1);
//     const [value, setValue] = useState<number | ''>(0);
//     const [totalWeight, setTotalWeight] = useState<number>(0);
//     const [totalValue, setTotalValue] = useState<number>(0);
//     // const [specialCharacteristics, setSpecialCharacteristics] = useState({
//     //     highValue: false,
//     //     invoice: false,
//     //     legalDocs: false,
//     //     bidDocuments: false,
//     // });
//     const [specialCharacteristics, setSpecialCharacteristics] = useState({
//         highValue: false,
//         fragile: false,
//         solidBlock: false,
//         oversized: false,
//         liquid: false,
//         magnetic: false,
//         coldGoods: false,
//     });
//     const [dimensions, setDimensions] = useState({ length: 0, width: 0, height: 0 });
//     const [orderId, setOrderId] = useState<string>('');
//     const [products, setProducts] = useState<Product[]>([]);


//     const handleProductTypeChange = (value: string) => {
//         setProductType(value);
//     };

//     const handleAddProduct = () => {

//         if (weight && value && quantity) {
//             const newProduct: Product = { productName, weight, value, quantity };
//             setProducts([...products, newProduct]);
//             setTotalWeight(weight);
//             setTotalValue(value);

//             // Reset input fields
//             setWeight('');
//             setValue('');
//             setQuantity('');
//             setProductName('');
//         } else {
//             alert('Please fill in all fields');
//         }
//     };

//     const handleCheckboxChange = (checked: boolean, field: string) => {
//         setSpecialCharacteristics((prev) => ({
//             ...prev,
//             [field]: checked,
//         }));
//     };

//     const handleDimensionChange = (e: React.ChangeEvent<HTMLInputElement>, field: string) => {
//         setDimensions((prev) => ({
//             ...prev,
//             [field]: parseInt(e.target.value),
//         }));
//     };

//     const handleDeleteProduct = (index: number) => {
//         setProducts(products.filter((_, i) => i !== index));
//     }

//     return (
//         <div className="border-2 shadow-lg p-4 rounded-lg">
//             <h1 className="text-xl font-semibold mb-4">Thông Tin Hàng Hóa</h1>
//             <div className="form-section mb-4">
//                 <Label>Loại Hàng Hóa:</Label>
//                 <RadioGroup defaultValue={productType} onValueChange={handleProductTypeChange} className='flex'>
//                     <div className="flex items-center space-x-2">
//                         <RadioGroupItem value="buu kien" id="buu kien" />
//                         <Label htmlFor="buu kien">Bưu kiện</Label>
//                     </div>
//                     <div className="flex items-center space-x-2">
//                         <RadioGroupItem value="tai lieu" id="tai lieu" />
//                         <Label htmlFor="tai lieu">Tài liệu</Label>
//                     </div>
//                 </RadioGroup>
//             </div>
//             <Separator orientation='horizontal' className='mb-2' />

//             <div className="space-y-2">
//                 <h3 className="text-lg font-bold  max-h-64 overflow-y-auto rounded-lg">Danh sách hàng hóa đã thêm:</h3>
//                 {products.length === 0 ? (
//                     <p>Chưa có hàng hóa nào được thêm.</p>
//                 ) : (
//                     products.map((product, index) => (
//                         <div key={index} className="border p-2 rounded-md flex justify-between items-center">
//                             <div>
//                                 <p><strong>Sản phẩm số {index + 1}</strong>: {product.productName} </p>
//                                 <p><strong>Trọng Lượng:</strong> {product.weight} g</p>
//                                 <p><strong>Giá trị:</strong> {product.value} VND</p>
//                                 <p><strong>Số lượng:</strong> {product.quantity}</p>
//                             </div>
//                             <button
//                                 onClick={() => handleDeleteProduct(index)}
//                                 className="bg-red-500 text-white p-2 rounded-md"
//                             >
//                                 Xóa
//                             </button>
//                         </div>
//                     )))}
//             </div>
//             <Separator orientation='horizontal' className='mb-2' />

//             <div className="form-section mb-4">
//                 <Label>Tên Hàng:</Label>
//                 <Input
//                     type="text"
//                     placeholder="Nhập tên hàng hóa"
//                     value={productName}
//                     onChange={(e) => setProductName(e.target.value)}
//                     className="w-full"
//                 />
//             </div>

//             <div className="form-section mb-4 flex gap-4">
//                 <div className="flex-1">
//                     <Label>Số lượng:</Label>
//                     <Input
//                         type="number"
//                         placeholder="Số lượng"
//                         value={quantity}
//                         onChange={(e) => setQuantity(Number(e.target.value))}
//                         className="w-full"
//                     />
//                 </div>
//                 <div className="flex-1">
//                     <Label>Trọng Lượng:</Label>
//                     <Input
//                         type="number"
//                         placeholder="Trọng lượng (g)"
//                         value={weight}
//                         onChange={(e) => setWeight(Number(e.target.value))}
//                         className="w-full"
//                     />
//                 </div>
//                 <div className="flex-1">
//                     <Label>Giá trị Hàng:</Label>
//                     <Input
//                         type="number"
//                         placeholder="Giá trị (VND)"
//                         value={value}
//                         onChange={(e) => setValue(Number(e.target.value))}
//                         className="w-full"
//                     />
//                 </div>
//             </div>
//             <Separator orientation='horizontal' className='mb-2' />
//             <Button onClick={handleAddProduct} className="mb-4">
//                 Thêm Hàng Hóa
//             </Button>
//             <Separator />
//             <div className="form-section mb-4 flex justify-around items-center bg-gray-100 p-4 rounded-lg shadow-md">
//                 <div className="text-center">
//                     <p className="text-lg font-semibold text-gray-700">Tổng Khối Lượng</p>
//                     <p className="text-2xl font-bold text-blue-600">{totalWeight} g</p>
//                 </div>
//                 <div className="border-l-2 border-gray-300 h-12 mx-4"></div>
//                 <div className="text-center">
//                     <p className="text-lg font-semibold text-gray-700">Tổng Giá Trị</p>
//                     <p className="text-2xl font-bold text-green-600">
//                         {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalValue)}
//                     </p>
//                 </div>
//             </div>
//             <Separator />

//             <div className="form-section mb-4">
//                 <Label>Tính Chất Hàng Hóa Đặc Biệt:</Label>
//                 <div className="flex flex-wrap gap-4">
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.highValue}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'highValue')}
//                         />
//                         <Label className="ml-2">Giá trị cao</Label>
//                     </div>
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.fragile}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'fragile')}
//                         />
//                         <Label className="ml-2">Dễ vỡ</Label>
//                     </div>
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.solidBlock}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'solidBlock')}
//                         />
//                         <Label className="ml-2">Nguyên khối</Label>
//                     </div>
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.oversized}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'oversized')}
//                         />
//                         <Label className="ml-2">Quá khổ</Label>
//                     </div>
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.liquid}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'liquid')}
//                         />
//                         <Label className="ml-2">Chất lỏng</Label>
//                     </div>
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.magnetic}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'magnetic')}
//                         />
//                         <Label className="ml-2">Từ tính, Pin</Label>
//                     </div>
//                     <div className="flex items-center w-1/3">
//                         <Checkbox
//                             checked={specialCharacteristics.coldGoods}
//                             onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, 'coldGoods')}
//                         />
//                         <Label className="ml-2">Hàng lạnh</Label>
//                     </div>
//                 </div>
//             </div>

//             <div className="form-section mb-4">
//                 <Label>Kích Thước (cm):</Label>
//                 <div className="flex gap-4">
//                     <Input
//                         type="number"
//                         placeholder="Dài"
//                         value={dimensions.length}
//                         onChange={(e) => handleDimensionChange(e, 'length')}
//                         className="w-1/3"
//                     />
//                     <Input
//                         type="number"
//                         placeholder="Rộng"
//                         value={dimensions.width}
//                         onChange={(e) => handleDimensionChange(e, 'width')}
//                         className="w-1/3"
//                     />
//                     <Input
//                         type="number"
//                         placeholder="Cao"
//                         value={dimensions.height}
//                         onChange={(e) => handleDimensionChange(e, 'height')}
//                         className="w-1/3"
//                     />
//                 </div>
//             </div>

//             <div className="form-section mb-4">
//                 <Label>Mã Đơn Hàng:</Label>
//                 <Input
//                     type="text"
//                     placeholder="Nhập mã đơn hàng tự tạo"
//                     value={orderId}
//                     onChange={(e) => setOrderId(e.target.value)}
//                     className="w-full"
//                 />
//             </div>
//         </div>
//     );
// }

interface ProductFormProps {
    onSubmit: (product: Product) => void;
}


export default function ProductForm({ onSubmit }: ProductFormProps) {
    const [product, setProduct] = useState<Product>({
        productType: '',
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
        dimensions: { length: 0, width: 0, height: 0 },
        orderId: '',
    });

    const [products, setProducts] = useState<Product[]>([]);

    const handleInputChange = <T extends keyof Product>(field: T, value: Product[T]) => {
        setProduct((prev) => ({
            ...prev,
            [field]: value,
        }));
    };

    const handleDimensionChange = (field: string, value: number) => {
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
        if (product.productName && product.weight > 0 && product.quantity > 0) {
            console.log(product)
            // onSubmit(product);
        }
    }, [product, onSubmit]);

    return (
        <div className="border-2 shadow-lg p-4 rounded-lg">
            <h1 className="text-xl font-semibold mb-4">Thông Tin Hàng Hóa</h1>

            <div className="form-section mb-4">
                <Label>Loại Hàng Hóa:</Label>
                <RadioGroup value={product.productType} onValueChange={(value) => handleInputChange('productType', value)} className='flex'>
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

            <div className="form-section mb-4">
                <Label>Tính Chất Hàng Hóa Đặc Biệt:</Label>
                <div className="flex flex-wrap gap-4">
                    {Object.keys(product.specialCharacteristics).map((key) => (
                        <div className="flex items-center w-1/3" key={key}>
                            <Checkbox
                                checked={product.specialCharacteristics[key as keyof typeof product.specialCharacteristics]}
                                onCheckedChange={(checked: boolean) => handleCheckboxChange(checked, key)}
                            />
                            <Label className="ml-2">{key}</Label>
                        </div>
                    ))}
                </div>
            </div>

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
                        onChange={(e) => handleInputChange('orderId', e.target.value)}
                        className="w-full"
                    />
                </div>
            </div>
        </div>
    );
}