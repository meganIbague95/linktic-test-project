FROM node:22-alpine AS build

WORKDIR /app

# 👇 COPIA TODO PRIMERO (CLAVE)
COPY . .

# 👇 LUEGO instala
RUN npm install

# build
RUN npm run build

# nginx
FROM nginx:alpine

COPY --from=build /app/dist/spa /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]