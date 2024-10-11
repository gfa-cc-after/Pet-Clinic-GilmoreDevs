/// <reference types="vitest" />
/// <reference types="vite/client" />
import react from "@vitejs/plugin-react-swc";
import { defineConfig } from "vite";
import path from "node:path";

// https://vitejs.dev/config/
// biome-ignore lint: viteconfig signature
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
