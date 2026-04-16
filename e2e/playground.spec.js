const { test, expect } = require('@playwright/test');

// Helper: wait for CheerpJ to initialize
async function waitForReady(page) {
  await page.goto('/');
  await expect(page.locator('#console')).toContainText('Ready.', { timeout: 120_000 });
}

// Helper: select example, pick mode, click Go, wait for completion
async function runMode(page, example, mode, pattern) {
  if (example) {
    await page.selectOption('#examples', { label: example });
  }
  await page.selectOption('#run-mode', mode);
  await page.click('#run-btn');
  const pat = pattern || /--- Done ---|PASS:|FAIL:|ERROR:/;
  await expect(page.locator('#console')).toContainText(pat, { timeout: 120_000 });
  return await page.locator('#console').innerText();
}

test.describe('KISS Playground - Fall example', () => {

  test('Tests Only', async ({ page }) => {
    await waitForReady(page);
    const output = await runMode(page, 'Fall', 'test');

    expect(output).toContain('testEarth: started');
    expect(output).toContain('testEarth: ended');
    expect(output).toContain('testMoon: started');
    expect(output).toContain('testMoon: ended');
    expect(output).toContain('--- Done ---');
  });

  test('Single test: App.testEarth', async ({ page }) => {
    await waitForReady(page);

    // First compile with Tests Only
    await runMode(page, 'Fall', 'test');

    // Now run single test (no recompile)
    await page.selectOption('#run-mode', 'single:App.testEarth');
    await page.click('#run-btn');
    await expect(page.locator('#console')).toContainText('PASS:', { timeout: 120_000 });

    const output = await page.locator('#console').innerText();
    expect(output).toContain('PASS: App.testEarth');
  });

});

test.describe('KISS Playground - Hello World', () => {

  test('Test & Run', async ({ page }) => {
    await waitForReady(page);
    const output = await runMode(page, 'Hello World', 'all');

    expect(output).toContain('testRun: started');
    expect(output).toContain('testRun: ended');
    expect(output).toContain('Hello, World!');
    expect(output).toContain('--- Done ---');
  });

  test('Run Only', async ({ page }) => {
    await waitForReady(page);
    const output = await runMode(page, 'Hello World', 'run');

    expect(output).not.toContain('testRun: started');
    expect(output).toContain('Hello, World!');
    expect(output).toContain('--- Done ---');
  });

});
