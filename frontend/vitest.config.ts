import react from "@vitejs/plugin-react-swc";
import { defineConfig } from "vitest/config";

const threshold: number = 70 as const;

// biome-ignore lint: define config sigrature
export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    // include: ["**/*.test.ts", "**/*.test.tsx"],
    environment: "jsdom",
    setupFiles: ["./setup-tests.ts"],
    css: true,
    typecheck: {
      tsconfig: "./tsconfig.test.json",
    },
    coverage: {
      exclude: [
        "src/**/*.test.tsx",
        "src/**/*.test.ts",
        "src/main.tsx",
        "vite.config.ts",
        "dist/**/*",
        "node_modules/**/*",
        "coverage/**/*",
        ".eslintrc.cjs",
        "src/**/*.d.ts",
      ],
      provider: "v8",
      reporter: ["text", "json", "html"],
      thresholds: {
        "src/**/*.{ts,tsx}": {
          functions: threshold,
          branches: threshold,
          statements: threshold,
          lines: threshold,
        },
      },
    },
  },
});
