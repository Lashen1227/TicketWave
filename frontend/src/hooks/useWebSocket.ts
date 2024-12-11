import { useState, useEffect } from "react";

export const useWebSocket = (eventId: number | null) => {
    const [logs, setLogs] = useState<string[]>([]);

    useEffect(() => {
        const wsLogs = new WebSocket("ws://localhost:8080/ws/logs");

        wsLogs.onmessage = (event) => {
            setLogs((prevLogs) => [...prevLogs, event.data]);
        };

        return () => {
            wsLogs.close();
        };
    }, [eventId]);

    return {logs };
};
