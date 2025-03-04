import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Typography, Box, Alert, Paper, FormControlLabel, Switch, CircularProgress } from '@mui/material';
import Navbar from '../components/Navbar';
import { register } from '../services/sessionApi';

interface RegisterProps {
  onRegisterSuccess: () => void;
}

const Register: React.FC<RegisterProps> = ({ onRegisterSuccess }) => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [userType, setUserType] = useState('customers');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setLoading(true);
    try {
      await register(name, email, password, userType);
      onRegisterSuccess();
      navigate('/login');
    } catch (err: unknown) {
      interface ErrorResponse {
        response?: {
          status: number;
        };
      }

      const errorResponse = err as Error & ErrorResponse;
      if (err instanceof Error && errorResponse.response && errorResponse.response.status === 409) {
        setError('Email already exists');
      } else {
        setError('Registration failed');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleUserTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUserType(event.target.checked ? 'vendors' : 'customers');
  };

  return (
    <Paper style={{border: 'none'}}>
      <Navbar />
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
          Register
        </Typography>
        <FormControlLabel
          control={<Switch checked={userType === 'vendors'} onChange={handleUserTypeChange} />}
          label={userType === 'vendors' ? 'Register as Vendor' : 'Register as Customer'}
          sx={{ mt: 2 }}
        />
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="name"
            label="Name"
            name="name"
            autoComplete="name"
            autoFocus
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email"
            name="email"
            autoComplete="email"
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
            disabled={loading}
          >
            {loading ? <CircularProgress size={24} /> : 'Register'}
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => navigate('/login')}
            disabled={loading}
          >
            Login
          </Button>
        </Box>
      </Box>
    </Paper>
  );
};

export default Register;