import React from "react";
import reactDom from "react-dom/client";
import { App } from "./App.tsx";
import "./index.css";
import { ChakraProvider, theme, } from "@chakra-ui/react";

// biome-ignore lint: entry point of the application
reactDom.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ChakraProvider theme={theme}>
      <App />
    </ChakraProvider>
  </React.StrictMode>,
);
