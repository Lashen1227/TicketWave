import { Container, Typography, Box, List, CircularProgress, Card } from '@mui/material';
import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import StyledPaper from '../components/StyledPaper';
import { getCustomerTickets } from '../services/customerApi';
import { getEventById } from '../services/eventApi';
import { TicketDTO } from '../types/Ticket';
import EventDetails from '../components/store/EventDetails';
import { Event } from '../types/Event';
import TicketListItem from '../components/customer/TicketListItem';

interface CustomerProfileProps {
  userId: number | null;
  userName: string | null;
}

const CustomerProfile: React.FC<CustomerProfileProps> = ({ userId, userName }) => {
  const [tickets, setTickets] = useState<TicketDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);
  const [eventLoading, setEventLoading] = useState<boolean>(false);

  useEffect(() => {
    const fetchTickets = async () => {
      if (userId) {
        try {
          const data = await getCustomerTickets(userId);
          setTickets(data);
        } catch (error) {
          console.error('Error fetching customer tickets:', error);
        } finally {
          setLoading(false);
        }
      }
    };

    fetchTickets();
  }, [userId]);

  const handleTicketClick = async (eventId: number) => {
    setEventLoading(true);
    try {
      const event = await getEventById(eventId);
      setSelectedEvent(event);
      setDialogOpen(true);
    } catch (error) {
      console.error('Error fetching event details:', error);
    } finally {
      setEventLoading(false);
    }
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
    setSelectedEvent(null);
  };

  return (
    <div>
      <Navbar onSearch={() => { }} />
      <StyledPaper>
        <Container sx={{ paddingTop: '3rem', height: '100vh' }}>
          <Typography variant="h5" sx={{ textAlign: 'center', marginY: '2rem' }}>
            Welcome, <span style={{ color: 'primary.main' }}>{userName}</span>
          </Typography>
          <Card variant='outlined' sx={{ padding: '1rem' }}>
            <Typography variant="h6" sx={{ textAlign: 'center', marginY: '1rem' }}>
              Your Tickets
            </Typography>
            {loading ? (
              <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '50vh' }}>
                <CircularProgress />
              </Box>
            ) : (
              <List>
                {tickets.length > 0 ? (
                  tickets.map((ticket, index) => (
                    <TicketListItem key={index} ticket={ticket} onClick={handleTicketClick} />
                  ))
                ) : (
                  <Typography variant="body1" sx={{ textAlign: 'center' }}>
                    You have no tickets.
                  </Typography>
                )}
              </List>
            )}
          </Card>
        </Container>
      </StyledPaper>
      <EventDetails open={dialogOpen} onClose={handleDialogClose} event={selectedEvent} isSignedIn={true} customerId={userId} loading={eventLoading} />
    </div>
  );
};

export default CustomerProfile;