FROM node:18-alpine
WORKDIR /app
COPY package*.json /app/
RUN npm install -g @ionic/cli
RUN npm install
COPY ./ /app/
CMD [ "ionic", "serve", "--external" ]
EXPOSE 8100