import axios from 'axios';
import config from '../config';

const API_URL = `${config.API_BASE_URL}/customers`;

export const purchaseTicket = async (customerId: number, eventItemId: number): Promise<string> => {
  try {
    const response = await axios.get(`${API_URL}/${customerId}/buy/${eventItemId}`);
    return response.data;
  } catch (error) {
    console.error('Error purchasing ticket:', error);
    throw error;
  }
};