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
            width: "fill-available",
            margin: "0",
            borderRadius: "0",
             }}
             >
            {children}
        </Paper>
    );
};

export default StyledPaper;