const { test, expect } = require('@playwright/test');

async function waitForReady(page) {
  await page.goto('/');
  await expect(page.locator('#console')).toContainText('Ready.', { timeout: 120_000 });
}

async function runMode(page, example, mode) {
  if (example) await page.selectOption('#examples', { label: example });
  await page.selectOption('#run-mode', mode);
  await page.click('#run-btn');
  await expect(page.locator('#console')).toContainText(/--- Done ---/, { timeout: 120_000 });
  return await page.locator('#console').innerText();
}

test('Fall: Tests Only', async ({ page }) => {
  await waitForReady(page);
  const out = await runMode(page, 'Fall', 'test');
  expect(out).toContain('testEarth: ended');
  expect(out).toContain('testMoon: ended');
});

test('Fall: Single test matches framework output', async ({ page }) => {
  await waitForReady(page);
  // First compile
  await runMode(page, 'Fall', 'test');
  // Run single test
  await page.selectOption('#run-mode', 'single:App.testEarth');
  await page.click('#run-btn');
  await expect(page.locator('#console')).toContainText('--- Done ---', { timeout: 120_000 });
  const out = await page.locator('#console').innerText();
  // Should have framework-style output (started/ended), not PASS/FAIL
  expect(out).toContain('testEarth: started');
  expect(out).toContain('testEarth: ended');
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

  await expect(page.locator('#console')).toContainText('time(seconds)?', { timeout: 120_000 });
  await page.fill('#stdin-line', '1');
  await page.press('#stdin-line', 'Enter');

  await expect(page.locator('#console')).toContainText('gravity', { timeout: 30_000 });
  await page.fill('#stdin-line', '9.8');
  await page.press('#stdin-line', 'Enter');

  await expect(page.locator('#console')).toContainText('490 centimeters', { timeout: 30_000 });
});

test('Fall: Second run gets fresh input', async ({ page }) => {
  await waitForReady(page);
  await page.selectOption('#examples', { label: 'Fall' });
  await page.selectOption('#run-mode', 'run');

  // First run: 1, 9.8 -> 490 cm
  await page.click('#run-btn');
  await expect(page.locator('#console')).toContainText('time(seconds)?', { timeout: 120_000 });
  await page.fill('#stdin-line', '1');
  await page.press('#stdin-line', 'Enter');
  await expect(page.locator('#console')).toContainText('gravity', { timeout: 30_000 });
  await page.fill('#stdin-line', '9.8');
  await page.press('#stdin-line', 'Enter');
  await expect(page.locator('#console')).toContainText('490 centimeters', { timeout: 30_000 });

  // Second run: 2, 1.6 -> 320 cm
  await page.click('#run-btn');
  await expect(page.locator('#console')).toContainText('time(seconds)?', { timeout: 30_000 });
  await page.fill('#stdin-line', '2');
  await page.press('#stdin-line', 'Enter');
  await expect(page.locator('#console')).toContainText('gravity', { timeout: 30_000 });
  await page.fill('#stdin-line', '1.6');
  await page.press('#stdin-line', 'Enter');
  await expect(page.locator('#console')).toContainText('320 centimeters', { timeout: 30_000 });
});
