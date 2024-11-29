import { Ticket } from "./Ticket";

export interface TicketPool {
    poolId: number;
    maxPoolSize: number;
    availableTickets: number;
    tickets: Ticket[];
    eventName: string;
  }