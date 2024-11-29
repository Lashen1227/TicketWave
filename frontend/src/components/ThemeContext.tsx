import React, { createContext, useContext, useState } from 'react';
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

    const [mode, setMode] = useState<'dark' | 'light' | 'system'>(getSystemTheme());

    const toggleTheme = () => {
        setMode((prevMode) => {
            if (prevMode === 'light') return 'dark';
            if (prevMode === 'dark') return 'light';
            return getSystemTheme();
        });
    };

    const theme = createTheme({
        shape: {
            borderRadius: 16,
        },
        palette: {
            mode: mode === 'system' ? getSystemTheme() : mode,
            primary: {
                light: '#64ffd3',
                main: '#00e8a6',
                dark: '#00b07e',
                contrastText: '#000',
            },
            secondary: {
                light: '#00a4d6',
                main: '#007498',
                dark: '#003b4d',
                contrastText: '#fff',
            },
            background: {
                default: mode === 'dark' ? '#444444' : '#f5f5f5',
                paper: mode === 'dark' ? '#111111' : '#fff',
            },
            text: {
                primary: mode === 'dark' ? '#fff' : '#000',
            },
        },
    });

    return (
        <ThemeContext.Provider value={{ toggleTheme, theme }}>
            <ThemeProvider theme={theme}>{children}</ThemeProvider>
        </ThemeContext.Provider>
    );
};