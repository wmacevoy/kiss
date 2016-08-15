# RNG Test

## You Don't Care

This is just an internal detail about the library and has nothing to do with learning java.

## You are a developer and interested in the AESPRNG internal generator.

This is a tiny project to test the AESPRNG against the dieharder randomness
tests.  It runs in ubuntu, assuming you have the kiss library in the parent
directory and the dieharder test suite (sudo apt-get install dieharder)
installed.

make clean all

Should make and run some tests (there is an exception that kills the rng
at the end from the dd command but that is expected).  Check your process
table (top/activity monitor) and make sure there are no leftover procesess
or just reboot after you complete the tests.

Notes

* These tests take some time to run.
* Because the p-values are uniformly distributed and there are so many tests, you should expect WEAK (1%) and possible FAIL (0.01%) tests, just not consistently on the same test.
* `jaesprng-*.log` are example log files.








