const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  testDir: '.',
  timeout: 300_000,
  expect: { timeout: 180_000 },
  use: {
    baseURL: 'http://server:8080',
    headless: true,
  },
});
