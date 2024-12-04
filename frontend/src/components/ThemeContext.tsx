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
                light: mode === 'light' ? '#ffa94d' : '#8c97ac', // Orange for light mode, lighter Tokyo Night Blue for dark
                main: mode === 'light' ? '#ff8c42' : '#7aa2f7', // Vibrant orange for light mode, Tokyo Night Blue for dark
                dark: mode === 'light' ? '#e0701f' : '#364fc7', // Darker orange or blue
                contrastText: '#ffffff', // White text for primary buttons
            },
            secondary: {
                light: mode === 'light' ? '#ffd8a8' : '#a4cafe', // Soft orange for light mode
                main: mode === 'light' ? '#ffc078' : '#89ddff', // Vibrant complementary orange
                dark: mode === 'light' ? '#e8590c' : '#0d47a1', // Dark orange or blue
                contrastText: '#000000', // Black text for secondary buttons
            },
            background: {
                default: mode === 'light' ? '#fff4e6' : '#1a1b26', // Light: muted orange, Dark: Tokyo Night Blue
                paper: mode === 'light' ? '#fff1e0' : '#1e222a', // Light: soft paper orange, Dark: paper background
            },
            text: {
                primary: mode === 'light' ? '#5f370e' : '#c0caf5', // Light: deep brown, Dark: soft blue
                secondary: mode === 'light' ? '#8f5833' : '#a9b1d6', // Muted orange-brown or blue
            },
            divider: mode === 'light' ? '#ffc078' : '#3b4261', // Subtle divider
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
                            mode === 'light' ? '#ff8c42' : '#364fc7'
                        }`, // Shiny border for cards
                        boxShadow: '0px 6px 15px rgba(0, 0, 0, 0.2)', // Subtle shadow
                        transition: 'border-color 0.3s ease, box-shadow 0.3s ease',
                        '&:hover': {
                            borderColor: mode === 'light' ? '#e0701f' : '#7aa2f7', // Highlight border on hover
                            boxShadow: '0px 8px 20px rgba(0, 0, 0, 0.3)', // Deeper shadow on hover
                        },
                        backgroundColor: mode === 'light' ? '#fff1e0' : '#1e222a',
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
