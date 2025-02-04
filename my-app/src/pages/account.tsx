
import Head from "next/head";
import AddressList from "@/modules/address/components/AddressList";
export default function Account(){
    return (
        <>
          <Head>
            <title>Quản Lý Địa Chỉ</title>
            <meta name="description" content="Trang quản lý danh sách địa chỉ của người dùng." />
            <link rel="icon" href="/favicon.ico" />
          </Head>
    
          <div className="container mt-4">
            <h1 className="text-center">Quản Lý Địa Chỉ</h1>
            <AddressList />
          </div>
        </>
      );
}