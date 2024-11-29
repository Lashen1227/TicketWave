import { Card, Dialog } from '@mui/material';
import { styled } from '@mui/system';

const StyledCard = styled(Card)(({ theme }) => ({
    // padding: theme.spacing(2),
    margin: theme.spacing(1.5),
    minWidth: 275,
    width: 350,
    // borderRadius: 10,
}));

const StyledDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialog-paper': {
        backgroundColor: theme.palette.background.paper,
    },
}));

export { StyledDialog, StyledCard };