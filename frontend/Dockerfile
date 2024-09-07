#create frontend container
FROM node:lts AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
ARG API_BACKEND_URL
ENV VITE_BACKEND_URL=${API_BACKEND_URL}
RUN npm run build

#run http server with the generated frontend
FROM nginx:latest
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]