# RNG Test

This is a tiny project to test the AESPRNG against the dieharder randomness
tests.  It runs in ubuntu, assuming you have the kiss library and the
dieharder test suite (sudo apt-get install dieharder) installed.

These tests take some time to run.  Because the p-values are uniformly
distributed and there are so many tests, you should expect WEAK (1%) and
possible FAIL (0.01%) tests.

`jaesprng-1.log` is the log file from one full test.








