# RNG Test

## You Don't Care

This is just an internal detail about the library and has nothing to do with learning java.

## You are a developer and interested in the AESPRNG internal generator.

This is a tiny project to test the AESPRNG against the dieharder randomness
tests.  It runs in linux or os x, assuming you have the kiss library in the
parent directory and the dieharder test suite (sudo apt-get install 
dieharder/brew install dieharder) installed.

```bash
./runtests
```

Should make and run some tests.  Check your process table (top/activity
monitor) and make sure there are no leftover procesess or just reboot
after you complete the tests.

Notes

* These tests take some time to run.
* Because the p-values are uniformly distributed and there are so many tests, you should expect WEAK (1%) and possible FAIL (0.01%) tests, just not consistently on the same test.
* `rng-*.log` are example log files.








