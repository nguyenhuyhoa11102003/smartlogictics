import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { useRouter } from 'next/router';
import { Helmet } from 'react-helmet-async';
import * as yup from 'yup';
import Input from '@/components/Input';
import { useContext } from 'react';
import Link from 'next/link';
// import { AppContext } from '../contexts/app.context'; // Adjust path as needed
import { isAxiosError, isAxiosUnprocessableEntityError } from '@/utils/utils'; // Adjust path as needed
import authApi from '@/modules/login/services/AuthService';
import { LoginRequest } from '@/modules/login/dto/LoginRequest';
import { toast } from 'react-toastify';


const loginSchema = yup.object().shape({
    username: yup.string()
        // .email('Invalid email').required('Email is required'),
        .required('Username hoặc Email là bắt buộc'),
    password: yup.string().required('Password is required'),
});

export default function Login() {
    //   const { setIsAuthenticated, setProfile } = useContext(AppContext);
    const router = useRouter();

    const {
        handleSubmit,
        register,
        setError,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(loginSchema),
    });

    const loginAccountMutation = useMutation({
        mutationFn: (data: LoginRequest) => {
            return authApi.loginAccount(data);
        },
        onSuccess: (data) => {
            //   setProfile(data.data.user);
            //   setIsAuthenticated(true);
            alert('Login Successful! Please wait 3s move to Dashboash Page.')
            setTimeout(() => {
                router.push('/dashboard');
            }, 3000);
        },
        onError: (error) => {
            // alert('Thông tin đăng nhập ko hợp lệ')
            console.log(JSON.stringify(error))
        },
    });

    const onSubmit = handleSubmit((data) => {
        console.log(JSON.stringify(data))
        loginAccountMutation.mutate(data); // trigger 
    });
    return (
        <div className="flex min-h-screen items-center justify-center bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-lg">
                <h2 className="text-2xl font-bold text-center text-gray-800">Đăng nhập</h2>
                <form className="space-y-4">
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                            Email
                        </label>
                        <Input
                            className=" px-4 py-2 mt-1 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            // type="email"
                            id="email"
                            placeholder="Nhập email của bạn"
                            name="username"
                            register={register}
                            errorMessage={errors.username?.message}
                            required
                        />
                    </div>

                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                            Mật khẩu
                        </label>
                        <Input
                            className="px-4 py-2 mt-1  rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            type="password"
                            id="password"
                            placeholder="Nhập mật khẩu của bạn"
                            name="password"
                            register={register}
                            errorMessage={errors.password?.message}
                            required
                        />
                    </div>
                    <div>
                        <button
                            // type="submit"
                            className="w-full px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600 focus:ring-2 focus:ring-blue-400"
                            onClick={onSubmit}
                        >
                            Đăng nhập
                        </button>
                    </div>
                </form>
                <p className="text-sm text-center text-gray-600">
                    Chưa có tài khoản?{' '}
                    <Link href="/register" className="text-blue-500 hover:underline">
                        Đăng ký
                    </Link>
                </p>
            </div>
        </div>
    );
}
