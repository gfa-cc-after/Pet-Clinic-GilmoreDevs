FROM node:20 as runner
WORKDIR /app
COPY package.json .
RUN npm ci
COPY . .
CMD ["npm", "run", "start"]
