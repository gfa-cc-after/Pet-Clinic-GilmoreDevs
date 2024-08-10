import React from "react";
import reactDom from "react-dom/client";
import { App } from "./App.tsx";
import "./index.css";

// biome-ignore lint: entry point of the application
reactDom.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
