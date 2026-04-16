FROM mcr.microsoft.com/playwright:v1.59.1-noble

WORKDIR /e2e
COPY package.json playwright.config.js playground.spec.js ./
RUN npm install

CMD ["npx", "playwright", "test", "--reporter=list"]
