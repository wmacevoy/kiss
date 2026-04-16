const { test, expect } = require('@playwright/test');

async function waitForReady(page) {
  await page.goto('/');
  await expect(page.locator('#console')).toContainText('Ready.', { timeout: 120_000 });
}

async function runMode(page, example, mode) {
  if (example) await page.selectOption('#examples', { label: example });
  await page.selectOption('#run-mode', mode);
  await page.click('#run-btn');
  await expect(page.locator('#console')).toContainText(/--- Done ---|PASS:|FAIL:|ERROR:/, { timeout: 120_000 });
  return await page.locator('#console').innerText();
}

test('Fall: Tests Only', async ({ page }) => {
  await waitForReady(page);
  const out = await runMode(page, 'Fall', 'test');
  expect(out).toContain('testEarth: ended');
  expect(out).toContain('testMoon: ended');
});

test('Fall: Single test', async ({ page }) => {
  await waitForReady(page);
  await runMode(page, 'Fall', 'test');
  await page.selectOption('#run-mode', 'single:App.testEarth');
  await page.click('#run-btn');
  await expect(page.locator('#console')).toContainText('PASS: App.testEarth', { timeout: 120_000 });
});

test('Hello World: Test & Run', async ({ page }) => {
  await waitForReady(page);
  const out = await runMode(page, 'Hello World', 'all');
  expect(out).toContain('Hello, World!');
});

test('Hello World: Run Only', async ({ page }) => {
  await waitForReady(page);
  const out = await runMode(page, 'Hello World', 'run');
  expect(out).not.toContain('testRun: started');
  expect(out).toContain('Hello, World!');
});

test('Fall: Interactive stdin', async ({ page }) => {
  await waitForReady(page);
  await page.selectOption('#examples', { label: 'Fall' });
  await page.selectOption('#run-mode', 'run');
  await page.click('#run-btn');

  // Wait for first prompt
  await expect(page.locator('#console')).toContainText('time(seconds)?', { timeout: 120_000 });

  // Type first input via the stdin line
  await page.fill('#stdin-line', '1');
  await page.press('#stdin-line', 'Enter');

  // Wait for second prompt
  await expect(page.locator('#console')).toContainText('gravity', { timeout: 30_000 });

  // Type second input
  await page.fill('#stdin-line', '9.8');
  await page.press('#stdin-line', 'Enter');

  // Wait for result
  await expect(page.locator('#console')).toContainText('490 centimeters', { timeout: 30_000 });
  await expect(page.locator('#console')).toContainText('--- Done ---', { timeout: 10_000 });
});
