export interface Ticket {
    id: number;
    available: boolean;
    ticketPrice: number;
  }
  
  export interface TicketDTO {
    eventId: any;
    eventName: string;
    ticketId: string;
    imageUrl: string;
    dateTime: string;
  }
  