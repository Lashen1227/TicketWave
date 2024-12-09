import { useState, useEffect } from 'react';

export const useWebSocket = (eventId: number | null) => {
    const [purchases, setPurchases] = useState<string[]>([]);
    const [availableTickets, setAvailableTickets] = useState<number>(0);
    const [socket, setSocket] = useState<WebSocket | null>(null);

    useEffect(() => {
        if (eventId) {
            const wsPurchases = new WebSocket(`ws://localhost:8080/ws/event/${eventId}/purchases`);
            wsPurchases.onmessage = (event) => {
                const timestamp = new Date().toLocaleTimeString();
                setPurchases((prevPurchases) => [...prevPurchases, `${timestamp} - ${event.data}`]);
            };

            const wsTickets = new WebSocket(`ws://localhost:8080/ws/event/${eventId}/tickets`);
            wsTickets.onmessage = (event) => {
                setAvailableTickets(parseInt(event.data.split(': ')[1]));
            };

            setSocket(wsPurchases);

            return () => {
                wsPurchases.close();
                wsTickets.close();
            };
        }
    }, [eventId]);

    const resetPurchases = () => {
        setPurchases([]);
    };

    return { purchases, availableTickets, socket, resetPurchases };
};