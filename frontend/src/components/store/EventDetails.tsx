import * as React from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import useMediaQuery from '@mui/material/useMediaQuery';
import { useTheme } from '@mui/material/styles';
import { Event } from '../../types/Event';
import { TransitionProps } from '@mui/material/transitions';
import { alpha, Box, Grow } from '@mui/material';
import ConfirmationNumberRoundedIcon from '@mui/icons-material/ConfirmationNumberRounded';
import BookmarkAddRoundedIcon from '@mui/icons-material/BookmarkAddRounded';
import { purchaseTicket } from '../../services/customerApi';

interface EventDetailsProps {
    open: boolean;
    onClose: () => void;
    event: Event | null;
    isSignedIn: boolean;
    customerId: number | null;
}

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
) {
    return <Grow ref={ref} {...props} />;
});

const EventDetails: React.FC<EventDetailsProps> = ({ open, onClose, event, isSignedIn, customerId }) => {
    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down('md'));
    const [purchaseSuccess, setPurchaseSuccess] = useState<boolean>(false);

    const buyTicket = async () => {
        if (customerId && event?.id) {
            try {
                const response = await purchaseTicket(customerId, event.id);
                console.log(response);
                setPurchaseSuccess(true);
            } catch (error) {
                console.error('Error purchasing ticket:', error);
            }
        }
    };

    const handleClose = () => {
        setPurchaseSuccess(false);
        onClose();
    };

    return (
        <Dialog
            fullScreen={fullScreen}
            open={open}
            TransitionComponent={Transition}
            keepMounted
            onClose={handleClose}
            aria-labelledby="responsive-dialog-title"
            sx={{
                backdropFilter: "blur(20px)",
                WebkitBackdropFilter: "blur(10px)",
                backgroundColor: alpha(theme.palette.background.default, 0.3),
                color: theme.palette.text.primary,
            }}
        >
            {purchaseSuccess ? (
                <Box sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100%',
                    padding: '2rem',
                }}>
                    <DialogTitle id="responsive-dialog-title">
                        Ticket Purchased Successfully
                    </DialogTitle>
                    <DialogActions>
                        <Button onClick={handleClose} variant="contained">
                            Close
                        </Button>
                    </DialogActions>
                </Box>
            ) : (
                <>
                    <Box component="img" src={event?.image ?? 'https://www.svgrepo.com/show/508699/landscape-placeholder.svg'} alt="event image" sx={{
                        height: '100%',
                        width: '100%',
                        objectFit: 'cover',
                        objectPosition: 'center',
                        minHeight: '80vh',
                    }} />
                    <Box sx={{
                        position: 'absolute',
                        bottom: 0,
                        width: "fill-available",
                        margin: 1,
                        borderRadius: 1,
                        backdropFilter: "blur(20px)",
                        WebkitBackdropFilter: "blur(20px)",
                        backgroundColor: alpha(theme.palette.background.paper, 0.7),
                        color: theme.palette.text.primary,
                        transition: 'all 0.3s ease-in-out',
                    }}>
                        <DialogTitle id="responsive-dialog-title">
                            {event?.eventName}
                        </DialogTitle>
                        <DialogContent sx={{
                            display: 'flex',
                            flexDirection: 'column',
                        }}>
                            <DialogContentText>
                                {event?.details}
                            </DialogContentText>
                            <Box sx={{
                                display: 'flex',
                                gap: '0.5rem',
                            }}>
                                <DialogContentText>
                                    {event?.eventDate}
                                </DialogContentText>
                                <DialogContentText>
                                    {event?.eventTime}
                                </DialogContentText>
                            </Box>
                            <DialogContentText>
                                Location: {event?.eventLocation}
                            </DialogContentText>
                            <br />
                            <DialogContentText>
                                By: {event?.vendorName}
                            </DialogContentText>
                            <DialogContentText>
                                Available Tickets: {event?.availableTickets}
                            </DialogContentText>
                        </DialogContent>
                        <DialogActions sx={{
                            padding: '1rem',
                            display: 'flex',
                            flexWrap: 'wrap',
                            gap: '0.5rem',
                        }}>
                            <Button autoFocus onClick={handleClose}>
                                Close
                            </Button>
                            {isSignedIn ? (
                                <>
                                    <Button variant="outlined" endIcon={<BookmarkAddRoundedIcon />}>
                                        Watchlist
                                    </Button>
                                    <Button 
                                        variant="contained" 
                                        endIcon={<ConfirmationNumberRoundedIcon />} 
                                        onClick={buyTicket} 
                                        disabled={event?.availableTickets === 0}
                                    >
                                        {event?.availableTickets === 0 ? 'Out of stock, Check back later' : `Buy Ticket $${event?.ticketPrice}`}
                                    </Button>
                                </>
                            ) : (
                                <Button variant="contained" endIcon={<ConfirmationNumberRoundedIcon />} onClick={() => window.location.href = '/login'}>
                                    Login to Buy Ticket
                                </Button>
                            )}
                        </DialogActions>
                    </Box>
                </>
            )}
        </Dialog>
    );
};

export default EventDetails;