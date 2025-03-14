import { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import EventCard from '../components/store/EventCard';
import StyledPaper from '../components/StyledPaper';
import { Container, Paper, Typography } from '@mui/material';
import { fetchEvents, getEventById } from '../services/eventApi';
import { Event } from '../types/Event';
import EventDetails from '../components/store/EventDetails';


const Store = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [searchQuery] = useState<string>('');
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [eventLoading, setEventLoading] = useState<boolean>(false);
  const userName = localStorage.getItem('userName');
  const token = localStorage.getItem('authToken');
  const customerId = localStorage.getItem('userId') ? parseInt(localStorage.getItem('userId')!) : null;

  useEffect(() => {
    getTickets();
  }, []);

  const getTickets = async () => {
    try {
      const data = await fetchEvents();
      setEvents(data);
    } catch (error) {
      console.error('Error fetching tickets:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCardClick = async (event: Event) => {
    setEventLoading(true);
    try {
      if (!event.id) {
        throw new Error('Event ID not found');
      } else {
      const eventDetails = await getEventById(event.id);
      setSelectedEvent(eventDetails);
      setDialogOpen(true);
      }
    } catch (error) {
      console.error('Error fetching event details:', error);
    } finally {
      setEventLoading(false);
    }
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
    setSelectedEvent(null);
    getTickets();
  };

  const filteredEvents = events.filter(event =>
    event.eventName.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <Paper sx={{
      width: '100vw',
      height: '100vh',
      border: 'none',
      marginX: '0',
    }}>
      <Navbar />
      <StyledPaper>
        <Container sx={{ paddingTop: '5rem', width: '100%' }}>
          {token && (
            <Typography variant="h5" sx={{ textAlign: 'center', marginY: '2rem' }}>
              Welcome  <span style={{ color: 'primary.main' }}>{userName}</span>
            </Typography>
          )}
          <Container sx={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center', width: '95vw', borderColor: 'orange' }}>
            {loading ? (
              <></>
            ) : (
              filteredEvents.map(event => (
                <EventCard key={event.id} event={event} onClick={() => handleCardClick(event)} />
              ))
            )}
          </Container>
        </Container>
      </StyledPaper>
      <EventDetails open={dialogOpen} onClose={handleDialogClose} event={selectedEvent} isSignedIn={!!token} customerId={customerId} loading={eventLoading} />
    </Paper>
  );
};

export default Store;