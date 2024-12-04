import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import StyledPaper from '../components/StyledPaper';
import { fetchEventsByVendor, createEvent } from '../services/eventApi';
import { Box, Button, Card, Container, Typography } from '@mui/material';
import EventCard from '../components/store/EventCard';
import { Event } from '../types/Event';
import EventEditor from '../components/vendor/EventEditor';

interface VendorAccountProps {
  userId: number | null;
  userName: string | null;
}

const VendorAccount: React.FC<VendorAccountProps> = ({ userId, userName }) => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);

  const fetchVendorEvents = async () => {
    try {
      const data = await fetchEventsByVendor(userId!);
      setEvents(data);
    } catch (error) {
      console.error('Error fetching tickets:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchVendorEvents();
  }, []);

  const handleCardClick = (event: Event) => {
    setSelectedEvent(event);
    setDialogOpen(true);
  };

  const handleCreateEventClick = () => {
    setSelectedEvent(null);
    setDialogOpen(true);
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
    setSelectedEvent(null);
  };

  const handleSaveEvent = async (event: Event) => {
    try {
      if (event.id) {
        // Update existing event (WIP)
        console.log('Event updated:', event);
      } else {
        // Create new event
        const newEvent = await createEvent(event);
        console.log('Event created:', newEvent);
      }
      await fetchVendorEvents(); // Re-fetch events after saving
    } catch (error) {
      console.error('Error saving event:', error);
    } finally {
      handleDialogClose();
    }
  };

  return (
    <>
      <Navbar />
      <StyledPaper>
        <Container sx={{ paddingTop: '3rem', height: '100vh' }}>
          <Typography variant="h5" sx={{ textAlign: 'center', marginY: '2rem' }}>
            Welcome, <span style={{ color: 'primary.main' }}>{userName}</span>
          </Typography>
          <Card variant='outlined' sx={{ padding: '1rem' }}>
            <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', paddingX: '1rem' }}>
              <Typography variant="h6" sx={{ textAlign: 'left', flexGrow: 1 }}>
                Event Management
              </Typography>
              <Button variant='contained' color='primary' sx={{ margin: '1rem' }} onClick={handleCreateEventClick}>
                Create Event
              </Button>
            </Box>
            <Container sx={{ display: 'flex', flexDirection: 'row', overflowX: 'scroll', marginY: '1rem' }}>
              {loading ? (
                <Box sx={{
                  display: 'flex',
                  justifyContent: 'center',
                  alignItems: 'center',
                  height: '75vh',
                }}>
                  Loading ...
                </Box>
              ) : (
                events.map(event => (
                  <EventCard key={event.id} event={event} onClick={() => handleCardClick(event)} />
                ))
              )}
            </Container>
          </Card>
        </Container>
      </StyledPaper>
      <EventEditor 
        open={dialogOpen} 
        onClose={handleDialogClose} 
        event={selectedEvent} 
        onSave={handleSaveEvent} 
        vendorId={userId} 
        vendorName={userName} 
        fetchVendorEvents={fetchVendorEvents}
      />
    </>
  );
}

export default VendorAccount;