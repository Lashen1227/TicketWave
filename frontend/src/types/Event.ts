export interface Event {
  id?: number;
  eventName: string;
  eventLocation: string;
  eventDate: string;
  eventTime: string;
  ticketPrice: number;
  details: string;
  image: string;
  vendorId: number;
  vendorName: string;
  availableTickets?: number;
}