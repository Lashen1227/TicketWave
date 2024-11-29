import axios from 'axios';
import config from '../config';

const API_URL = config.API_BASE_URL;

export const login = async (email: string, password: string, userType: string) => {
  const response = await axios.post(`${API_URL}/api/auth/login`, { email, password, userType });
  return response.data;
};

export const register = async (name: string, email: string, password: string, userType: string) => {
  const response = await axios.post(`${API_URL}/${userType}`, { name, email, password });
  return response.data;
};