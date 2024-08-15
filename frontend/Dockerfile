#create frontend container
FROM node:20 AS frontend-builder
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

#run http server with the generated frontend
FROM nginx AS server-runner
COPY --from=frontend-builder /app/dist /usr/share/nginx/html 
EXPOSE 80
CMD  [ "nginx", "-g", "daemon off;" ]