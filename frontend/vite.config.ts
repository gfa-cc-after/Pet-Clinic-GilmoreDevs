/// <reference types="vitest" />
/// <reference types="vite/client" />
import react from '@vitejs/plugin-react-swc'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    include: ['**/*.test.tsx'],
    environment: 'jsdom',
    setupFiles: ['./setup-tests.ts'],
    css: true,
  }
})