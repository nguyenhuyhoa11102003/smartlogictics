import React, { createContext, useState, useEffect, ReactNode, useContext } from 'react';


interface AuthContextType {
    accessToken: string | null;
    setAccessToken: (token: string) => void;
    clearAccessToken: () => void;
}

// Tạo context với giá trị mặc định là undefined
const AuthContext = createContext<AuthContextType | undefined>(undefined);


// Provider cho phép các component con sử dụng context
export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [accessToken, setAccessToken] = useState<string | null>(null);

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            setAccessToken(token);
        }
    }, []);

    const updateAccessToken = (token: string) => {
        setAccessToken(token);
        localStorage.setItem('accessToken', token); // Lưu token vào localStorage
    };


    const clearAccessToken = () => {
        setAccessToken(null);
        localStorage.removeItem('accessToken');
    };


    return (
        <AuthContext.Provider value={{
            accessToken,
            setAccessToken: updateAccessToken,
            clearAccessToken,
        }}>
            {children}
        </AuthContext.Provider>
    )
}


// Hook để dễ dàng sử dụng context trong các component con
export const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};