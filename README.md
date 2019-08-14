# example-firrtl-wake

This repository is an example of a wake flow that generates verilog from an example Chisel module.
The top module is a "chiselized" version of the AWS F1 example `cl_hello_world` project, and has been tested on the AWS platform

## Quick Start

Depends on `protobuf`, `wit`, and `wake` software packages

```
$ wit init workspace -a git@github.com:sifive/example-firrtl-wake.git
$ cd workspace
$ wit fetch-scala
$ export WAKE_PATH=$PATH
$ wake --init .
$ wake 'getVerilog "verilog"'
```

If you want to use your own Chisel module, just change which module gets elaborated in [Main.scala](src/main/scala/Main.scala):

```Scala
// replace ??? with your Chisel module
val circuit: Circuit = chisel3.Driver.elaborate(() => new ???)
```
