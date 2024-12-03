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
            borderRadius: 8,
        },
        palette: {
            mode,
            primary: {
                light: '#8c97ac', // Lighter Tokyo Night Blue
                main: '#7aa2f7', // Tokyo Night Blue
                dark: '#364fc7', // Darker shade of Tokyo Night Blue
                contrastText: '#ffffff', // White text for primary buttons
            },
            secondary: {
                light: '#a4cafe', // Light complementary blue
                main: '#89ddff', // Vibrant secondary blue
                dark: '#0d47a1', // Dark blue
                contrastText: '#000000', // Black text for secondary buttons
            },
            background: {
                default: mode === 'dark' ? '#1a1b26' : '#e3eaf4', // Dark: Tokyo Night Blue dark bg, Light: muted light bg
                paper: mode === 'dark' ? '#1e222a' : '#ffffff', // Card surfaces
            },
            text: {
                primary: mode === 'dark' ? '#c0caf5' : '#1a1b26', // Dark: soft blue, Light: dark blue-gray
                secondary: mode === 'dark' ? '#a9b1d6' : '#4c566a', // Muted for secondary text
            },
            divider: mode === 'dark' ? '#3b4261' : '#cfd9e8', // Subtle dividers
        },
        components: {
            MuiButton: {
                styleOverrides: {
                    root: {
                        textTransform: 'none', // Avoid all caps for text
                        borderRadius: '12px', // Button border radius
                    },
                },
            },
            MuiPaper: {
                styleOverrides: {
                    root: {
                        border: `1px solid ${
                            mode === 'dark' ? '#3b4261' : '#dce4f2'
                        }`, // Border colors for Paper component
                        boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)', // Subtle shadows
                    },
                },
            },
        },
        typography: {
            fontFamily: 'Roboto, Arial, sans-serif', // Clean font stack
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
