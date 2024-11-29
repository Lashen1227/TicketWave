import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Typography, Box, Alert, Paper, FormControlLabel, Switch } from '@mui/material';
import Navbar from '../components/Navbar';
import { login } from '../services/sessionApi';

interface LoginProps {
  onLoginSuccess: (token: string, userId: number, name: string, email: string, userType: string) => void;
}

const Login: React.FC<LoginProps> = ({ onLoginSuccess }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [userType, setUserType] = useState('CUSTOMER');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const data = await login(email, password, userType);
      onLoginSuccess(data.token, data.userId, data.name, data.email, data.userType);
      if (data.userType === 'VENDOR') {
        navigate('/vendor');
      } else {
        navigate('/');
      }
    } catch (err) {
      setError('Invalid email or password');
    }
  };

  const handleUserTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUserType(event.target.checked ? 'VENDOR' : 'CUSTOMER');
  };

  return (
    <Paper>
      <Navbar onSearch={() => { }} />
      <Box sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        maxWidth: '400px',
        margin: 'auto',
      }}>
        <Typography component="h1" variant="h5">
          Login to Ticketin
        </Typography>
        <FormControlLabel
          control={<Switch checked={userType === 'VENDOR'} onChange={handleUserTypeChange} />}
          label={userType === 'VENDOR' ? 'Login as Vendor' : 'Login as Customer'}
          sx={{ mt: 2 }}
        />
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            autoFocus
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {error && <Alert severity="error">{error}</Alert>}
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Login
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => navigate('/register')}
          >
            Register
          </Button>

        </Box>
        {/* warning on demo website */}
        <Box sx={{ mt: 4, backgroundColor: 'background.paper', p: 2, borderRadius: 1 }}>
          <Typography variant="caption" sx={{ color: 'error.main' }}>
            Warning: This is a demo website. Do not use real email, password, or any sensitive information.
          </Typography>
        </Box>
      </Box>
    </Paper>
  );
};

export default Login;