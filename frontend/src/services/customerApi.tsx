import axios from 'axios';
import config from '../config';
import { TicketDTO } from '../types/Ticket';

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

export const getCustomerTickets = async (customerId: number): Promise<TicketDTO[]> => {
  try {
    const response = await axios.get(`${API_URL}/${customerId}/tickets`);
    return response.data;
  } catch (error) {
    console.error('Error fetching customer tickets:', error);
    throw error;
  }
};