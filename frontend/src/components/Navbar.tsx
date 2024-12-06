import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import AccountCircle from '@mui/icons-material/AccountCircle';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import ConfirmationNumberIcon from '@mui/icons-material/ConfirmationNumberTwoTone';
import { alpha, Checkbox } from '@mui/material';
import Brightness5RoundedIcon from '@mui/icons-material/Brightness5Rounded';
import BedtimeRoundedIcon from '@mui/icons-material/BedtimeRounded';
import { useThemeContext } from './ThemeContext';

export default function Navbar() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const { toggleTheme, theme } = useThemeContext();
  const token = localStorage.getItem('authToken');
  const userName = localStorage.getItem('userName');
  const userType = localStorage.getItem('userType');
  const navigate = useNavigate();
  const location = useLocation();

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleProfileClick = () => {
    handleClose();
    if (userType === 'CUSTOMER') {
      navigate('/profile');
    }
  };

  const isDarkMode = theme.palette.mode === 'dark';

  const handleLogout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userName');
    localStorage.removeItem('userType');
    window.location.reload();
  };

  const handleLoginClick = () => {
    handleClose();
    navigate('/login');
  };

  const handleRegisterClick = () => {
    handleClose();
    navigate('/register');
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar sx={{
        backdropFilter: "blur(20px)",
        WebkitBackdropFilter: "blur(20px)",
        border: 'none',
        backgroundColor: alpha(isDarkMode ? theme.palette.background.paper : '#335074', 0.9), // opacity 
        color: theme.palette.text.primary,
        boxShadow: 0,
      }}>
        <Toolbar>
          <Box onClick={() => navigate('/')} sx={{
            display: 'flex',
            alignItems: 'center',
            textDecoration: 'none',
            cursor: 'pointer',
            flexGrow: 1,
          }}>
            <ConfirmationNumberIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1, color: 'primary.main' }} />
            <Typography
              variant="h6"
              noWrap
              component="a"
              href="#app-bar-with-responsive-menu"
              sx={{
                mr: 2,
                display: { xs: 'none', md: 'flex' },
                fontFamily: 'Raleway, sans-serif',
                fontWeight: 700,
                letterSpacing: '.2rem',
                color: 'primary.main',
                textDecoration: 'none',
                flexGrow: 1,
              }}
            >
              TicketWave
            </Typography>
          </Box>

          <div>
            <Checkbox
              {...label}
              icon={<Brightness5RoundedIcon />}
              checkedIcon={<BedtimeRoundedIcon />}
              checked={isDarkMode}
              onChange={toggleTheme}
              sx={{ backgroundColor: alpha(theme.palette.common.white, 0.15), marginRight: theme.spacing(1) }}
            />
          </div>

          {location.pathname !== '/login' && location.pathname !== '/register' && (
            <div>
              <IconButton
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleMenu}
                color="inherit"
                sx={{ backgroundColor: alpha(theme.palette.common.white, 0.15) }}
              >
                <AccountCircle />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorEl}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                open={Boolean(anchorEl)}
                onClose={handleClose}
              >
                {token && [
                    <MenuItem key="username" onClick={handleProfileClick}>{userName}</MenuItem>,
                    <MenuItem key="logout" onClick={handleLogout}>Logout</MenuItem>
                ]}
                {!token && [
                    <MenuItem key="login" onClick={handleLoginClick}>Login</MenuItem>,
                    <MenuItem key="register" onClick={handleRegisterClick}>Register</MenuItem>
                ]}
              </Menu>
            </div>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}