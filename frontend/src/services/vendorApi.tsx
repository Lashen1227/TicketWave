import axios from 'axios';
import config from '../config';

const API_URL = `${config.API_BASE_URL}/vendors`;

export const releaseTickets = async (eventId: number, ticketCount: number): Promise<string> => {
  try {
    const response = await axios.get(`${API_URL}/${eventId}/release`, {
      params: { ticketCount }
    });
    return response.data;
  } catch (error) {
    console.error('Error releasing tickets:', error);
    throw error;
  }
};