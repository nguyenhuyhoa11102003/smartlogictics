import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { useRouter } from 'next/router';
import { Helmet } from 'react-helmet-async';
import * as yup from 'yup';
import Input from '@/components/Input';
import { useContext } from 'react';
// import { AppContext } from '../contexts/app.context'; // Adjust path as needed
// import { isAxiosError, isAxiosUnprocessableEntityError } from '../utils/utils'; // Adjust path as needed
const loginSchema = yup.object().shape({
    email: yup.string().email('Invalid email').required('Email is required'),
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

    //   const loginAccountMutation = useMutation(authApi.loginAccount, {
    //     onSuccess: (data) => {
    //     //   setProfile(data.data.user);
    //     //   setIsAuthenticated(true);
    //       router.push('/');
    //     },
    //     onError: (error) => {
    //       if (isAxiosUnprocessableEntityError(error)) {
    //         const formError = error.response?.data?.data;
    //         if (formError) {
    //           Object.keys(formError).forEach((key) => {
    //             setError(key, { type: 'Server', message: formError[key] }, { shouldFocus: true });
    //           });
    //         }
    //       } else if (isAxiosError(error)) {
    //         console.error('Error:', error);
    //       }
    //     },
    //   });

    const onSubmit = handleSubmit((data) => {
        // loginAccountMutation.mutate(data);
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
                            type="email"
                            id="email"
                            placeholder="Nhập email của bạn"
                            name="email"
                            register={register}
                            errorMessage={errors.email?.message}
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
                            type="submit"
                            className="w-full px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600 focus:ring-2 focus:ring-blue-400"
                        >
                            Đăng nhập
                        </button>
                    </div>
                </form>
                <p className="text-sm text-center text-gray-600">
                    Chưa có tài khoản?{' '}
                    <a href="/register" className="text-blue-500 hover:underline">
                        Đăng ký
                    </a>
                </p>
            </div>
        </div>
        // <div className="h-[600px] bg-orange">
        //     {/* <Helmet>
        //     <title>Login | Shopee Clone</title>
        //     <meta name="description" content="Login | Shopee Clone" />
        //   </Helmet> */}
        //     <div className="container bg-shopee bg-contain bg-center bg-no-repeat">
        //         <div className="grid grid-cols-1 py-12 lg:h-[470px] lg:grid-cols-5 lg:pr-10">
        //             <div className="md:col-span-2 md:col-start-4 md:mx-8">
        //                 <form className="rounded bg-white p-10 shadow-sm" onSubmit={onSubmit}>
        //                     <div className="text-2xl">Login</div>
        //                     <Input
        //                         className="mt-6"
        //                         type="email"
        //                         placeholder="Email"
        //                         name="email"
        //                         register={register}
        //                         errorMessage={errors.email?.message}
        //                     />
        //                     <Input
        //                         className="my-2"
        //                         type="password"
        //                         placeholder="Password"
        //                         name="password"
        //                         register={register}
        //                         errorMessage={errors.password?.message}
        //                     />
        //                     {/* <Button
        //             type="submit"
        //             isLoading={loginAccountMutation.isLoading}
        //             disabled={loginAccountMutation.isLoading}
        //             className="flex w-full justify-center bg-red-500 px-2 py-4 text-sm uppercase text-white hover:bg-red-600"
        //           >
        //             Login
        //           </Button>
        //           <div className="mt-9 flex items-center justify-center gap-1 text-center">
        //             <span className="text-gray-400">Do not have an account?</span>
        //             <a href="/register" className="text-red-400">
        //               Register
        //             </a>
        //           </div> */}
        //                 </form>
        //             </div>
        //         </div>
        //     </div>
        // </div>
    );
}
