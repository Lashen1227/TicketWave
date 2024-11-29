import axios from 'axios';
import { Ticket } from '../types/Ticket';
import config from '../config';

const API_URL = `${config.API_BASE_URL}/tickets/list`;

export const fetchTickets = async (): Promise<Ticket[]> => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching tickets:', error);
    throw error;
  }
};