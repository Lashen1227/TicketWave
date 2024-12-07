import React from 'react';
import { ListItemButton, ListItemAvatar, Avatar, ListItemText } from '@mui/material';
import { TicketDTO } from '../../types/Ticket';

// component to display a ticket list item
interface TicketListItemProps {
  ticket: TicketDTO;
  onClick: (eventId: number) => void;
}

const TicketListItem: React.FC<TicketListItemProps> = ({ ticket, onClick }) => {
  return (
    <ListItemButton onClick={() => onClick(ticket.eventId)} sx={{
        borderRadius: 16,
        marginBottom: '10px',
        padding: "10px 16px",
        '&:hover': {
            backgroundColor: 'theme.palette.action.hover',
        },
    }} >
      <ListItemAvatar>
        <Avatar src={ticket.imageUrl} alt={ticket.eventName} />
      </ListItemAvatar>
      <ListItemText
        primary={ticket.eventName}
        secondary={`Date: ${ticket.dateTime} | Ticket: #${ticket.ticketId}`}
      />
    </ListItemButton>
  );
};

export default TicketListItem;