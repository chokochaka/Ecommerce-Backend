import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    target: "es2020", // Adjust target based on browser support
    outDir: "dist"
  },
  server: {
    host: "0.0.0.0", // Bind to all interfaces (optional)
    port: 3000 // Development server port (default: 3000)
  },
  // Resolve Configuration (Optional)
  resolve: {
    alias: {
      // Create aliases for commonly used paths
      "@components": "./components" // Example alias for src directory
    }
  }
});
