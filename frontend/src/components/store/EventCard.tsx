import React, { useState } from 'react';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import CardActionArea from '@mui/material/CardActionArea';
import { Event } from '../../types/Event';
import { StyledCard } from './style';
import { alpha } from '@mui/material';
import { useThemeContext } from '../ThemeContext';

interface EventCardProps {
  event: Event;
  onClick: () => void;
}

const EventCard: React.FC<EventCardProps> = ({ event, onClick }) => {
  const { theme } = useThemeContext();
  const [isHovered, setIsHovered] = useState(false);

  return (
    <StyledCard
      onClick={onClick}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <CardActionArea>
        <CardMedia
          component="img"
          height="350"
          image={event.image ?? 'https://www.svgrepo.com/show/508699/landscape-placeholder.svg'}
          alt="green iguana"
        />
        <CardContent sx={{
          position: 'absolute',
          bottom: 0,
          width: "fill-available",
          margin: 1,
          borderRadius: 1,
          backdropFilter: "blur(20px)",
          WebkitBackdropFilter: "blur(20px)",
          backgroundColor: alpha(theme.palette.background.paper, 0.7),
          color: theme.palette.text.primary,
          transition: theme.transitions.create('height'),
        }}>
          <Typography gutterBottom variant="h5" component="div">
            {event.eventName}
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
            {event.eventDate} - {event.eventTime}
          </Typography>
          <Typography variant="body2" sx={{
            color: 'text.secondary',
            marginTop: 1,
            height: isHovered ? 20 : 0,
            opacity: isHovered ? 1 : 0,
            transition: theme.transitions.create(['height', 'opacity']),
          }}>
            More details →
          </Typography>
        </CardContent>
      </CardActionArea>
    </StyledCard>
  );
};

export default EventCard;