import * as React from 'react';
import { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import useMediaQuery from '@mui/material/useMediaQuery';
import { useTheme } from '@mui/material/styles';
import { Event } from '../../types/Event';
import { TransitionProps } from '@mui/material/transitions';
import { alpha, Grow, TextField, Box, Typography, CircularProgress } from '@mui/material';
import { releaseTickets } from '../../services/vendorApi';
import Loader from '../Loader';

interface EventEditorProps {
    open: boolean;
    onClose: () => void;
    event: Event | null;
    onSave: (event: Event) => void;
    vendorId: number | null;
    vendorName: string | null;
    fetchVendorEvents: () => void;
}

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement;
    },
    ref: React.Ref<unknown>,
) {
    return <Grow ref={ref} {...props} />;
});

const EventEditor: React.FC<EventEditorProps> = ({ open, onClose, event, onSave, vendorId, vendorName, fetchVendorEvents }) => {
    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

    const [eventName, setEventName] = useState('');
    const [eventLocation, setEventLocation] = useState('');
    const [eventDate, setEventDate] = useState('');
    const [eventTime, setEventTime] = useState('');
    const [ticketPrice, setTicketPrice] = useState('');
    const [details, setDetails] = useState('');
    const [image, setImage] = useState('');
    const [ticketCount, setTicketCount] = useState<number>(1);
    const [loading, setLoading] = useState<boolean>(false);
    
    const [errors, setErrors] = useState({
        eventName: '',
        eventLocation: '',
        eventDate: '',
        eventTime: '',
        ticketPrice: '',
        details: '',
        image: '',
    });

    useEffect(() => {
        if (event) {
            setEventName(event.eventName);
            setEventLocation(event.eventLocation);
            setEventDate(event.eventDate);
            setEventTime(event.eventTime);
            setTicketPrice(event.ticketPrice.toString());
            setDetails(event.details);
            setImage(event.image);
        } else {
            setEventName('');
            setEventLocation('');
            setEventDate('');
            setEventTime('');
            setTicketPrice('');
            setDetails('');
            setImage('');
        }
    }, [event, open]);

    const validate = () => {
        const tempErrors = { ...errors };
        tempErrors.eventName = eventName.length > 30 ? 'Event Name cannot exceed 50 characters' : '';
        tempErrors.eventLocation = eventLocation.length > 30 ? 'Location cannot exceed 30 characters' : '';
        tempErrors.eventDate = eventDate ? '' : 'Date required';
        tempErrors.eventTime = eventTime ? '' : 'Time required';
        tempErrors.ticketPrice = ticketPrice? '' : 'Ticke price required';
        tempErrors.details = details.length > 1000 ? 'Details cannot exceed 1000 characters' : '';
        tempErrors.image = image.length > 900? 'URL is to long' : '';
        setErrors(tempErrors);
        return Object.values(tempErrors).every(x => x === '');
    };

    const handleSave = async () => {
        if (validate()) {
            setLoading(true);
            try {
                if (event) {
                    const updatedEvent: Event = {
                        ...event,
                        eventName,
                        eventLocation,
                        eventDate,
                        eventTime,
                        ticketPrice: parseFloat(ticketPrice),
                        details,
                        image,
                    };
                    onSave(updatedEvent);
                } else {
                    if (vendorId && vendorName) {
                        const newEvent: Event = {
                            eventName,
                            eventLocation,
                            eventDate,
                            eventTime,
                            ticketPrice: parseFloat(ticketPrice),
                            details,
                            image,
                            vendorId: vendorId,
                            vendorName: vendorName,
                        };
                        onSave(newEvent);
                    } else {
                        console.error('Vendor ID or Name not found');
                    }
                }
            } catch (error) {
                console.error('Error saving event:', error);
            } finally {
                setLoading(false);
            }
        }
    };

    const handleReleaseTickets = async () => {
        if (event && ticketCount > 0 && event.id) {
            setLoading(true);
            try {
                const response = await releaseTickets(event.id, ticketCount);
                setTicketCount(1);
                console.log(response);
                fetchVendorEvents();
            } catch (error) {
                console.error('Error releasing tickets:', error);
            } finally {
                setLoading(false);
            }
        }
    };

    return (
        <Dialog
            fullScreen={fullScreen}
            open={open}
            TransitionComponent={Transition}
            keepMounted
            onClose={onClose}
            aria-labelledby="responsive-dialog-title"
            sx={{
                backdropFilter: "blur(20px)",
                WebkitBackdropFilter: "blur(10px)",
                backgroundColor: alpha(theme.palette.background.default, 0.3),
                color: theme.palette.text.primary,
            }}
        >
            <DialogTitle id="responsive-dialog-title">
                {event ? 'Edit Event' : 'Create Event'}
            </DialogTitle>
            <DialogContent>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="eventName"
                    label="Event Name"
                    name="eventName"
                    autoComplete="eventName"
                    autoFocus
                    value={eventName}
                    onChange={(e) => setEventName(e.target.value)}
                    error={!!errors.eventName}
                    helperText={errors.eventName}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="eventLocation"
                    label="Location"
                    name="eventLocation"
                    autoComplete="eventLocation"
                    value={eventLocation}
                    onChange={(e) => setEventLocation(e.target.value)}
                    error={!!errors.eventLocation}
                    helperText={errors.eventLocation}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="eventDate"
                    label="Date"
                    name="eventDate"
                    type="date"
                    slotProps={{ inputLabel: { shrink: true } }}
                    value={eventDate}
                    onChange={(e) => setEventDate(e.target.value)}
                    error={!!errors.eventDate}
                    helperText={errors.eventDate}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="eventTime"
                    label="Time"
                    name="eventTime"
                    type="time"
                    InputLabelProps={{ shrink: true }}
                    value={eventTime}
                    onChange={(e) => setEventTime(e.target.value)}
                    error={!!errors.eventTime}
                    helperText={errors.eventTime}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="ticketPrice"
                    label="Ticket Price"
                    name="ticketPrice"
                    type="number"
                    value={ticketPrice}
                    onChange={(e) => setTicketPrice(e.target.value)}
                    error={!!errors.ticketPrice}
                    helperText={errors.ticketPrice}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="details"
                    label="Details"
                    name="details"
                    multiline
                    rows={4}
                    value={details}
                    onChange={(e) => setDetails(e.target.value)}
                    error={!!errors.details}
                    helperText={errors.details}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="image"
                    label="Image URL"
                    name="image"
                    value={image}
                    onChange={(e) => setImage(e.target.value)}
                    error={!!errors.image}
                    helperText={errors.image}
                />
                {event && (
                    <>
                        <Box sx={{ marginTop: 2 }}>
                            <Typography variant="h6">Release New Tickets</Typography>
                            <Box sx={{
                                display: 'flex',
                                flexDirection: 'rows',
                                gap: '0.5rem',
                                alignItems: 'center',
                            }}>
                                <TextField
                                    margin="normal"
                                    fullWidth
                                    id="ticketCount"
                                    label="Number of Tickets"
                                    name="ticketCount"
                                    type="number"
                                    value={ticketCount}
                                    onChange={(e) => setTicketCount(parseInt(e.target.value))}
                                />
                                <Typography variant="body2" color="textSecondary" sx={{
                                    marginLeft: '1rem',
                                }}>
                                    Available: {event.availableTickets}
                                </Typography>
                            </Box>
                            <Button variant="contained" onClick={handleReleaseTickets} disabled={loading}>
                                {loading ? <CircularProgress size={24} /> : 'Release Tickets'}
                            </Button>
                        </Box>
                        <Box sx={{ marginTop: 4 }}>
                            <Loader eventId={1}/>
                        </Box>
                    </>
                )}
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} disabled={loading}>
                    Cancel
                </Button>
                <Button onClick={handleSave} variant="contained" disabled={loading}>
                    {loading ? <CircularProgress size={24} /> : 'Save'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EventEditor;