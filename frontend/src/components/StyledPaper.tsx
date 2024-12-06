import { Paper } from '@mui/material'
import React from 'react'

interface StyledPaperProps {
    children: React.ReactNode;
}

const StyledPaper: React.FC<StyledPaperProps> = ({ children }) => {
    return (
        <Paper style={{border: 'none'}}
        sx={{ 
            minHeight: "fill-available",
            width: "fill-available",
            margin: "0",
            borderColor: "none",
            borderRadius: "0",
             }}
             >
            {children}
        </Paper>
    );
};

export default StyledPaper;