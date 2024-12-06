import React, { createContext, useContext, useState, useMemo } from 'react';
import { ThemeProvider, createTheme, Theme } from '@mui/material/styles';

interface ThemeContextType {
    toggleTheme: () => void;
    theme: Theme;
}

const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

export const useThemeContext = () => {
    const context = useContext(ThemeContext);
    if (!context) {
        throw new Error('useThemeContext must be used within a ThemeProvider');
    }
    return context;
};

interface ThemeContextProviderProps {
    children: React.ReactNode;
}

export const ThemeContextProvider: React.FC<ThemeContextProviderProps> = ({ children }) => {
    const getSystemTheme = () => {
        if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            return 'dark';
        }
        return 'light';
    };

    const [mode, setMode] = useState<'dark' | 'light'>(getSystemTheme());

    const toggleTheme = () => {
        setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
    };

    const theme = useMemo(() => createTheme({
        shape: {
            borderRadius: 12,
        },
        palette: {
            mode,
            primary: {
                light: mode === 'light' ? '#8abaff' : '#8c97ac', // Lighter shade of the primary color
                main: mode === 'light' ? '#89b4fa' : '#7aa2f7',  // Primary color
                dark: mode === 'light' ? '#5c94e0' : '#364fc7', // Darker shade of the primary color
                contrastText: '#ffffff',
            },
            secondary: {
                light: mode === 'light' ? '#a6c8ff' : '#a4cafe', // Lighter shade of the secondary color
                main: mode === 'light' ? '#7eaaff' : '#89ddff',  // Secondary color
                dark: mode === 'light' ? '#5c94e0' : '#0d47a1',  // Darker shade of the secondary color
                contrastText: '#000000',
            },
            background: {
                default: mode === 'light' ? '#cfe2ff' : '#1a1b26',
                paper: mode === 'light' ? '#e3f2fd' : '#1e222a',
            },
            text: {
                primary: mode === 'light' ? '#0d3b66' : '#c0caf5',
                secondary: mode === 'light' ? '#5b7293' : '#a9b1d6',
            },
            divider: mode === 'light' ? '#89b4fa' : '#3b4261',
        },
        components: {
            MuiButton: {
                styleOverrides: {
                    root: {
                        textTransform: 'none',
                        borderRadius: '12px',
                    },
                },
            },
            MuiPaper: { 
                styleOverrides: {
                    root: {
                        border: `2px solid ${
                            mode === 'light' ? '#89b4fa' : '#364fc7'
                        }`,
                        boxShadow: '0px 6px 15px rgba(0, 0, 0, 0.2)',
                        transition: 'border-color 0.3s ease, box-shadow 0.3s ease',
                        '&:hover': {
                            borderColor: mode === 'light' ? '#5c94e0' : '#7aa2f7',
                            boxShadow: '0px 8px 20px rgba(0, 0, 0, 0.3)', // Deeper shadow on hover
                        },
                        backgroundColor: mode === 'light' ? '#e3f2fd' : '#1e222a',
                    },
                },
            },
        },
        typography: {
            fontFamily: 'Roboto, Arial, sans-serif',
            button: {
                fontWeight: 600,
            },
        },
    }), [mode]);

    return (
        <ThemeContext.Provider value={{ toggleTheme, theme }}>
            <ThemeProvider theme={theme}>{children}</ThemeProvider>
        </ThemeContext.Provider>
    );
};
