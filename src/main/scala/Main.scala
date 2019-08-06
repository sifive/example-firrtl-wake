package example

import chisel3._
import chisel3.internal.firrtl.Circuit
import firrtl.annotations.JsonProtocol
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
        val name: String = "AXI4LiteRegSlice"
        println(s"elaborating $name")

        val circuit: Circuit = chisel3.Driver.elaborate(() => new AXI4LiteRegSlice)
        chisel3.Driver.dumpFirrtl(circuit, Some(new File(s"${config.location}/", s"$name.fir")))

        val annotationFile = new File(s"${config.location}/", s"$name.anno.json")
        val af = new FileWriter(annotationFile)
        af.write(JsonProtocol.serialize(circuit.annotations.map(_.toFirrtl)))
        af.close()
      case None =>
        println("couldn't parse config")
    }
  }
}

