# craps - kiss craps 

Shows how PRNG is repeatable for tests, but random for run.

It also illustrates testing subcomponents by wrapping the
creation of these objects with `test()`.

To build application to target directory:

```sh
mvn compile
```

To run application after building:

```sh
mvn -q exec:exec
```


