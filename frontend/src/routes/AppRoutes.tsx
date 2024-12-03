import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { useState } from "react";
import Store from "../pages/Store";
import CustomerProfile from "../pages/CustomerProfile";
import VendorAccount from "../pages/VendorAccount";
import Login from "../pages/Login";
import Register from "../pages/Register";

import { ReactElement } from "react";

interface PrivateRouteProps {
  element: ReactElement;
  token: string | null;
}
const PrivateRoute = ({ element, token }: PrivateRouteProps) => {
  return token ? element : <Navigate to="/login" />;
};

const AppRoutes = () => {
  const [token, setToken] = useState<string | null>(localStorage.getItem('authToken'));
  const [userId, setUserId] = useState<number | null>(parseInt(localStorage.getItem('userId') || '0'));
  const [name, setName] = useState<string | null>(localStorage.getItem('userName'));
  const [userType, setUserType] = useState<string | null>(localStorage.getItem('userType'));

  const handleLoginSuccess = (token: string, userId: number, name: string, userType: string) => {
    setToken(token);
    setUserId(userId);
    setName(name);
    setUserType(userType);
    localStorage.setItem('authToken', token);
    localStorage.setItem('userId', userId.toString());
    localStorage.setItem('userName', name);
    localStorage.setItem('userType', userType);
    console.log('Login success:', token, userId, name, userType);
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={userType === 'VENDOR' ? <Navigate to="/vendor" /> : <Store/>} />
        <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
        <Route path="/register" element={<Register onRegisterSuccess={() => {}} />} />
        <Route path="/profile" element={<PrivateRoute token={token} element={<CustomerProfile userId={userId} userName={name} />} />} />
        <Route path="/vendor" element={<PrivateRoute token={token} element={<VendorAccount userId={userId} userName={name} />} />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRoutes;