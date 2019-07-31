package example

import chisel3._
import chisel3.util._
import java.io.{File, FileWriter}

object Main {
  case class Config(location: String = "")
  val parser = new scopt.OptionParser[Config]("scopt") {
    head("Example Project")
    opt[String]('o', "output").required().action((x, c) =>
      c.copy(location = x)
    ).text("output location is a required argument")
  }

  def main(args: Array[String]): Unit = {
    parser.parse(args, Config()) match {
      case Some(config) =>
        println("elaborating DoublePassthrough")
        val name: String = "DoublePassthrough"
        chisel3.Driver.dumpFirrtl(chisel3.Driver.elaborate(() => new DoublePassthrough), Some(new File(s"${config.location}/", s"$name.fir")))
      case None =>
        println("couldn't parse config")
    }
  }
  /*val verilog = chisel3.Driver.execute(Array[String](), {() => new DoublePassthrough}) match {
    case s:chisel3.ChiselExecutionSuccess => s.firrtlResultOption match {
      case Some(f:FirrtlExecutionSuccess) => f.emitted
    }
  }
  println(verilog)*/
  //val fw = new FileWriter(new File(dir, name)
}

class PassthroughGenerator(val width: Int) extends Module { 
  val io = IO(new Bundle {
    val in = Input(UInt(width.W))
    val out = Output(UInt(width.W))
  })

  io.out := io.in
}

class DoublePassthrough extends Module {
  val io = IO(new Bundle {
    val in0 = Input(UInt(4.W))
    val in1 = Input(UInt(4.W))
    val out0 = Output(UInt(4.W))
    val out1 = Output(UInt(4.W))
  })

  val pt0 = Module(new PassthroughGenerator(4))
  val pt1 = Module(new PassthroughGenerator(4))
  pt0.io.in := io.in0
  pt1.io.in := io.in1
  io.out0 := pt0.io.out
  io.out1 := pt1.io.out
}

