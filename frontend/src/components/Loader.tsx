import styled from "styled-components";
import { useWebSocket } from "../hooks/useWebSocket";

const Loader = ({ eventId }: { eventId: number | null }) => {
  const { logs } = useWebSocket(eventId);

  return (
    <StyledWrapper>
      <div className="terminal-loader">
        <div className="terminal-header">
          <div className="terminal-title">Real-Time Ticket Release Status</div>
          <div className="terminal-controls">
            <div className="control close" />
            <div className="control minimize" />
            <div className="control maximize" />
          </div>
        </div>
        <div className="content">
          {logs.length === 0 ? (
            <div className="text">Loading...</div>
          ) : (
            logs.map((log, index) => (
              <div key={index} className="text">
                {log}
              </div>
            ))
          )}
        </div>
      </div>
    </StyledWrapper>
  );
};

const StyledWrapper = styled.div`
  @keyframes blinkCursor {
    50% {
      border-right-color: transparent;
    }
  }

  @keyframes typeAndDelete {
    0%,
    10% {
      width: 0;
    }

    45%,
    55% {
      width: 5em;
    }

    90%,
    100% {
      width: 0;
    }
  }

  .terminal-loader {
    width: 96%;
    max-width: 96%;
    height: 300px; /* Increased height */
    background: #1c1c1c;
    border: 1px solid #ffffff3e;
    border-radius: 10px;
    overflow: hidden;
    margin: 20px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.4);
  }

  .terminal-header {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #343434;
    padding: 6px;
  }

  .terminal-controls {
    position: absolute;
    left: 10px;
    display: flex;
    gap: 7px;
  }

  .control {
    display: inline-block;
    width: 15px;
    height: 15px;
    border-radius: 50%;
    background-color: #777;
  }

  .control.close {
    background-color: #e33;
  }

  .control.minimize {
    background-color: #ee0;
  }

  .control.maximize {
    background-color: #0b0;
  }

  .terminal-title {
    color: #eeeeeec1;
  }

  .content {
    padding: 10px;
    max-height: 210px;
    overflow-y: auto;
    scrollbar-width: thin;
    scrollbar-color: #555 #1c1c1c;
  }

  .content::-webkit-scrollbar {
    width: 8px;
  }

  .content::-webkit-scrollbar-thumb {
    background-color: #555;
    border-radius: 20px;
  }

  .content::-webkit-scrollbar-track {
    background: #1c1c1c;
  }

  .text {
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    border-right: 2px solid green;
    animation: typeAndDelete 3s steps(30), blinkCursor 0.5s step-end infinite alternate;
    color: rgb(0, 196, 0);
    font-weight: 600;
  }
`;

export default Loader;


// Template: https://uiverse.io/vikas7754/tasty-insect-29