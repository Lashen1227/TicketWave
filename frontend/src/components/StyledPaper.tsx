import { Paper } from '@mui/material'
import React from 'react'

interface StyledPaperProps {
    children: React.ReactNode;
}

const StyledPaper: React.FC<StyledPaperProps> = ({ children }) => {
    return (
        <Paper 
        sx={{ 
            minHeight: "fill-available",
            maxHeight: "content",
            width: "fill-available",
            paddingTop: "2rem",
             }}
             >
            {children}
        </Paper>
    );
};

export default StyledPaper;