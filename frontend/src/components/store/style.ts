import { Card, Dialog } from '@mui/material';
import { styled } from '@mui/system';

{/*MUI componnet to desplay event details */}
const StyledCard = styled(Card)(({ theme }) => ({
    margin: theme.spacing(1.5),
    minWidth: 275,
    width: 350,
}));

const StyledDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialog-paper': {
        backgroundColor: theme.palette.background.paper,
    },
}));

export { StyledDialog, StyledCard };