package example

import chisel3._
import chisel3.util._
import chisel3.experimental._

class AXI4LiteRegSlice extends MultiIOModule {
  // PCIe memory-mapped register address
  val HELLO_WORLD_REG_ADDR = Wire(UInt(32.W))
  HELLO_WORLD_REG_ADDR := "h00000500".U

  // create and connect shell ports 

  /////////////////
  // clock reset
  /////////////////
  val clk_main_a0       = IO(Input(Clock()))      suggestName "clk_main_a0"
  val rst_main_n        = IO(Input(Bool()))       suggestName "rst_main_n"
  
  /////////////////
  // AXI4Lite OCL
  /////////////////
  val sh_ocl_awaddr     = IO(Input(UInt(32.W)))   suggestName "sh_ocl_awaddr"
  val sh_ocl_awvalid    = IO(Input(Bool()))       suggestName "sh_ocl_awvalid"
  val ocl_sh_awready    = IO(Output(Bool()))      suggestName "ocl_sh_awready"

  val sh_ocl_wdata      = IO(Input(UInt(32.W)))   suggestName "sh_ocl_wdata"
  val sh_ocl_wstrb      = IO(Input(UInt(4.W)))    suggestName "sh_ocl_wstrb"
  val sh_ocl_wvalid     = IO(Input(Bool()))       suggestName "sh_ocl_wvalid"
  val ocl_sh_wready     = IO(Output(Bool()))      suggestName "ocl_sh_wready"

  val ocl_sh_bresp      = IO(Output(UInt(2.W)))   suggestName "ocl_sh_bresp"
  val ocl_sh_bvalid     = IO(Output(Bool()))      suggestName "ocl_sh_bvalid"
  val sh_ocl_bready     = IO(Input(Bool()))       suggestName "sh_ocl_bready"

  val sh_ocl_araddr     = IO(Input(UInt(32.W)))   suggestName "sh_ocl_araddr"
  val sh_ocl_arvalid    = IO(Input(Bool()))       suggestName "sh_ocl_arvalid"
  val ocl_sh_arready    = IO(Output(Bool()))      suggestName "ocl_sh_arready"

  val ocl_sh_rdata      = IO(Output(UInt(32.W)))  suggestName "ocl_sh_rdata"
  val ocl_sh_rresp      = IO(Output(UInt(2.W)))   suggestName "ocl_sh_rresp"
  val ocl_sh_rvalid     = IO(Output(Bool()))      suggestName "ocl_sh_rvalid"
  val sh_ocl_rready     = IO(Input(Bool()))       suggestName "sh_ocl_rready"

  //////////////////
  // DDR IO
  //////////////////
	val CLK_300M_DIMM0_DP	= IO(Input(Bool()))       suggestName "CLK_300M_DIMM0_DP"
	val CLK_300M_DIMM0_DN = IO(Input(Bool()))       suggestName "CLK_300M_DIMM0_DN"
	val M_A_ACT_N         = IO(Output(Bool()))      suggestName "M_A_ACT_N"
	val M_A_MA            = IO(Output(UInt(17.W)))  suggestName "M_A_MA"
	val M_A_BA            = IO(Output(UInt(2.W)))   suggestName "M_A_BA"
	val M_A_BG            = IO(Output(UInt(2.W)))   suggestName "M_A_BG"
	val M_A_CKE           = IO(Output(Bool()))      suggestName "M_A_CKE"
	val M_A_ODT           = IO(Output(Bool()))      suggestName "M_A_ODT"
	val M_A_CS_N          = IO(Output(Bool()))      suggestName "M_A_CS_N"
	val M_A_CLK_DN        = IO(Output(Bool()))      suggestName "M_A_CLK_DN"
	val M_A_CLK_DP        = IO(Output(Bool()))      suggestName "M_A_CLK_DP"
	val M_A_PAR           = IO(Output(Bool()))      suggestName "M_A_PAR"
	val M_A_DQ            = IO(Analog(64.W))        suggestName "M_A_DQ"
	val M_A_ECC           = IO(Analog(8.W))         suggestName "M_A_ECC"
	val M_A_DQS_DP        = IO(Analog(18.W))        suggestName "M_A_DQS_DP"
	val M_A_DQS_DN        = IO(Analog(18.W))        suggestName "M_A_DQS_DN"
	val cl_RST_DIMM_A_N   = IO(Output(Bool()))      suggestName "cl_RST_DIMM_A_N"
                                                 
	val CLK_300M_DIMM1_DP = IO(Input(Bool()))       suggestName "CLK_300M_DIMM1_DP"
	val CLK_300M_DIMM1_DN = IO(Input(Bool()))       suggestName "CLK_300M_DIMM1_DN"
	val M_B_ACT_N         = IO(Output(Bool()))      suggestName "M_B_ACT_N"
	val M_B_MA            = IO(Output(UInt(17.W)))  suggestName "M_B_MA"
	val M_B_BA            = IO(Output(UInt(2.W)))   suggestName "M_B_BA"
	val M_B_BG            = IO(Output(UInt(2.W)))   suggestName "M_B_BG"
	val M_B_CKE           = IO(Output(Bool()))      suggestName "M_B_CKE"
	val M_B_ODT           = IO(Output(Bool()))      suggestName "M_B_ODT"
	val M_B_CS_N          = IO(Output(Bool()))      suggestName "M_B_CS_N"
	val M_B_CLK_DN        = IO(Output(Bool()))      suggestName "M_B_CLK_DN"
	val M_B_CLK_DP        = IO(Output(Bool()))      suggestName "M_B_CLK_DP"
	val M_B_PAR           = IO(Output(Bool()))      suggestName "M_B_PAR"
	val M_B_DQ            = IO(Analog(64.W))        suggestName "M_B_DQ"
	val M_B_ECC           = IO(Analog(8.W))         suggestName "M_B_ECC"
	val M_B_DQS_DP        = IO(Analog(18.W))        suggestName "M_B_DQS_DP"
	val M_B_DQS_DN        = IO(Analog(18.W))        suggestName "M_B_DQS_DN"
	val cl_RST_DIMM_B_N   = IO(Output(Bool()))      suggestName "cl_RST_DIMM_B_N"
                                                  
	val CLK_300M_DIMM3_DP = IO(Input(Bool()))       suggestName "CLK_300M_DIMM3_DP"
	val CLK_300M_DIMM3_DN = IO(Input(Bool()))       suggestName "CLK_300M_DIMM3_DN"
	val M_D_ACT_N         = IO(Output(Bool()))      suggestName "M_D_ACT_N"
	val M_D_MA            = IO(Output(UInt(17.W)))  suggestName "M_D_MA"
	val M_D_BA            = IO(Output(UInt(2.W)))   suggestName "M_D_BA"
	val M_D_BG            = IO(Output(UInt(2.W)))   suggestName "M_D_BG"
	val M_D_CKE           = IO(Output(Bool()))      suggestName "M_D_CKE"
	val M_D_ODT           = IO(Output(Bool()))      suggestName "M_D_ODT"
	val M_D_CS_N          = IO(Output(Bool()))      suggestName "M_D_CS_N"
	val M_D_CLK_DN        = IO(Output(Bool()))      suggestName "M_D_CLK_DN"
	val M_D_CLK_DP        = IO(Output(Bool()))      suggestName "M_D_CLK_DP"
	val M_D_PAR           = IO(Output(Bool()))      suggestName "M_D_PAR"
	val M_D_DQ            = IO(Analog(64.W))        suggestName "M_D_DQ"
	val M_D_ECC           = IO(Analog(8.W))         suggestName "M_D_ECC"
	val M_D_DQS_DP        = IO(Analog(18.W))        suggestName "M_D_DQS_DP"
	val M_D_DQS_DN        = IO(Analog(18.W))        suggestName "M_D_DQS_DN"
	val cl_RST_DIMM_D_N		= IO(Output(Bool()))      suggestName "cl_RST_DIMM_D_N"

  val ddr_sh_stat_ack0  = IO(Output(Bool()))      suggestName "ddr_sh_stat_ack0"
  val ddr_sh_stat_ack1  = IO(Output(Bool()))      suggestName "ddr_sh_stat_ack1"
  val ddr_sh_stat_ack2  = IO(Output(Bool()))      suggestName "ddr_sh_stat_ack2"

  ////////////////
  // PCIe IDs
  ////////////////
  val cl_sh_id0 = IO(Output(UInt(32.W))) suggestName "cl_sh_id0"
  val cl_sh_id1 = IO(Output(UInt(32.W))) suggestName "cl_sh_id1"
  cl_sh_id0 := "hf0001d0f".U
  cl_sh_id1 := "h1d51fedd".U

  def blacklist(name: String): Boolean = {
    name match {
			case "clk_main_a0" => true
			case "rst_main_n" => true
			case "sh_ocl_awaddr" => true
			case "sh_ocl_awvalid" => true
			case "ocl_sh_awready" => true
			case "sh_ocl_wdata" => true
			case "sh_ocl_wstrb" => true
			case "sh_ocl_wvalid" => true
			case "ocl_sh_wready" => true
			case "ocl_sh_bresp" => true
			case "ocl_sh_bvalid" => true
			case "sh_ocl_bready" => true
			case "sh_ocl_araddr" => true
			case "sh_ocl_arvalid" => true
			case "ocl_sh_arready" => true
			case "ocl_sh_rdata" => true
			case "ocl_sh_rresp" => true
			case "ocl_sh_rvalid" => true
			case "sh_ocl_rready" => true
      case "cl_sh_id0" => true
      case "cl_sh_id1" => true
      case "reset" => true
      case "CLK_300M_DIMM0_DP" => true
      case "CLK_300M_DIMM0_DN" => true
      case "M_A_ACT_N" => true
      case "M_A_MA" => true
      case "M_A_BA" => true
      case "M_A_BG" => true
      case "M_A_CKE" => true
      case "M_A_ODT" => true
      case "M_A_CS_N" => true
      case "M_A_CLK_DN" => true
      case "M_A_CLK_DP" => true
      case "M_A_PAR" => true
      case "M_A_DQ" => true
      case "M_A_ECC" => true
      case "M_A_DQS_DP" => true
      case "M_A_DQS_DN" => true
      case "cl_RST_DIMM_A_N" => true
      case "CLK_300M_DIMM1_DP" => true
      case "CLK_300M_DIMM1_DN" => true
      case "M_B_ACT_N" => true
      case "M_B_MA" => true
      case "M_B_BA" => true
      case "M_B_BG" => true
      case "M_B_CKE" => true
      case "M_B_ODT" => true
      case "M_B_CS_N" => true
      case "M_B_CLK_DN" => true
      case "M_B_CLK_DP" => true
      case "M_B_PAR" => true
      case "M_B_DQ" => true
      case "M_B_ECC" => true
      case "M_B_DQS_DP" => true
      case "M_B_DQS_DN" => true
      case "cl_RST_DIMM_B_N" => true
      case "CLK_300M_DIMM3_DP" => true
      case "CLK_300M_DIMM3_DN" => true
      case "M_D_ACT_N" => true
      case "M_D_MA" => true
      case "M_D_BA" => true
      case "M_D_BG" => true
      case "M_D_CKE" => true
      case "M_D_ODT" => true
      case "M_D_CS_N" => true
      case "M_D_CLK_DN" => true
      case "M_D_CLK_DP" => true
      case "M_D_PAR" => true
      case "M_D_DQ" => true
      case "M_D_ECC" => true
      case "M_D_DQS_DP" => true
      case "M_D_DQS_DN" => true
      case "cl_RST_DIMM_D_N" => true
      case "ddr_sh_stat_ack0" => true
      case "ddr_sh_stat_ack1" => true
      case "ddr_sh_stat_ack2" => true
      case _ => false
    }
  }
  // automatic port generation
	for ( (name, direction, htype, width) <- CLPorts.elements ) {
    if (!blacklist(name)) {
      //println(s"creating port $name")
		  val hw: Data = htype match {
        case "B" => Bool()
        case "U" => UInt(width.W)
        case "A" => Analog(width.W)
        case _ => throw new Exception("unknown htype")
		  }
      direction match {
        case "I" =>
          val port = IO(Input(hw)).suggestName(name)
        case "O" =>
          val port = IO(Output(hw)).suggestName(name)
          if (name == "cl_sh_ddr_awburst" || name == "cl_sh_ddr_arburst") {
            port := 1.U(2.W)
          } else {
            port := 0.U(width.W)
          }
        case "U" =>
          val port = IO(hw).suggestName(name)
      }
    }
	}
  
  val rst_main = Wire(Bool())
  rst_main := !rst_main_n
  withClockAndReset(clk_main_a0, rst_main) {
    // register
    val hello_world_q = RegInit(0.U(32.W))
    val hello_world_swapped = Wire(UInt(32.W))
    hello_world_swapped := Cat(hello_world_q(7,0), hello_world_q(15,8), hello_world_q(23,16), hello_world_q(31,24))
    
    // axi signals
	  val awready   = Wire(Bool())
    val awvalid   = Wire(Bool())
	  val awaddr 	  = Wire(UInt(32.W))
	  val arvalid_q = RegInit(false.B)
    val araddr_q  = RegInit(0.U(32.W))

	  val wready 	= Wire(Bool())
    val wvalid 	= Wire(Bool())
	  val wdata 	= Wire(UInt(32.W))
	  val wstrb 	= Wire(UInt(4.W))

	  val arready = Wire(Bool())
	  val arvalid = Wire(Bool())
	  val araddr 	= Wire(UInt(32.W))

	  val bready 	= Wire(Bool())
	  val bvalid 	= RegInit(false.B)
	  val bresp 	= Wire(UInt(2.W))

	  val rready 	= Wire(Bool())
	  val rvalid 	= RegInit(false.B)
	  val rdata 	= RegInit(0.U(32.W))
	  val rresp 	= RegInit(0.U(2.W))

	  val wr_active = RegInit(false.B)
	  val wr_addr   = RegInit(0.U(32.W))

    // AXI logic
    // write req
    /*when (rst_main) { 
      wr_active := false.B
      wr_addr := 0.U(32.W)
      
      bvalid := false.B

      arvalid_q := false.B
      araddr_q := 0.U(32.W)
      
      rvalid := false.B
      rdata := 0.U(32.W)
      rresp := 0.U(2.W)
      
      hello_world_q := 0.U(32.W)
    } .otherwise {*/

    // wr_active logic
    when (wr_active && bvalid && bready) {
      wr_active := false.B
    } .elsewhen (!wr_active && !awvalid) {
      wr_active := true.B
    } .otherwise {
      wr_active := wr_active
    }
    // wr_addr logic
    when (awvalid && !wr_active) {
      wr_addr := awaddr
    } .otherwise {
      wr_addr := wr_addr
    }
    // bvalid logic
    when (bvalid && bready) {
      bvalid := false.B
    } .elsewhen (!bvalid && wready) {
      bvalid := true.B
    } .otherwise {
      bvalid := bvalid
    }
    // arvalid_q
    arvalid_q := arvalid
    // araddr_q
    when (arvalid) {
      araddr_q := araddr
    } .otherwise {
      araddr_q := araddr_q
    }
    // rvalid, rdata, rresp
    when (rvalid && rready) {
      rvalid := false.B
      rdata := 0.U(32.W)
      rresp := 0.U(2.W)
    } .elsewhen (arvalid_q) {
      rvalid := true.B
      rresp := 0.U(2.W)
      when (araddr_q === HELLO_WORLD_REG_ADDR) {
        rdata := hello_world_swapped
      } .otherwise {
        rdata := "hdeadbeef".U
      }
    } .otherwise {
      rvalid := rvalid
      rdata := rdata
      rresp := rresp
    }
    // hello_world_q
    when (wready && (wr_addr === HELLO_WORLD_REG_ADDR)) {
      hello_world_q := wdata
    } .otherwise {
      hello_world_q := hello_world_q
    }
    

    awready := !wr_active
    wready := wr_active && wvalid
    bresp := 0.U(2.W)
    arready := !arvalid_q && !rvalid
    
    val AXIReg = Module(new axi_register_slice_light)

    AXIReg.io.aclk          := clk_main_a0.asUInt
    AXIReg.io.aresetn       := rst_main_n

    ////////////////////
    // slave interface
    ////////////////////
    AXIReg.io.s_axi_awaddr  := sh_ocl_awaddr
    //AXIReg.io.s_axi_awprot  := 0.U(2.W)
    AXIReg.io.s_axi_awvalid := sh_ocl_awvalid
    ocl_sh_awready          := AXIReg.io.s_axi_awready

    AXIReg.io.s_axi_wdata   := sh_ocl_wdata
    AXIReg.io.s_axi_wstrb   := sh_ocl_wstrb
    AXIReg.io.s_axi_wvalid  := sh_ocl_wvalid
    ocl_sh_wready           := AXIReg.io.s_axi_wready
    
    ocl_sh_bresp            := AXIReg.io.s_axi_bresp
    ocl_sh_bvalid           := AXIReg.io.s_axi_bvalid
    AXIReg.io.s_axi_bready  := sh_ocl_bready
    
    AXIReg.io.s_axi_araddr  := sh_ocl_araddr
    //AXIReg.io.s_axi_arprot  := sh_ocl_arprot 
    AXIReg.io.s_axi_arvalid := sh_ocl_arvalid
    ocl_sh_arready          := AXIReg.io.s_axi_arready
    
    ocl_sh_rdata            := AXIReg.io.s_axi_rdata
    ocl_sh_rresp            := AXIReg.io.s_axi_rresp
    ocl_sh_rvalid           := AXIReg.io.s_axi_rvalid
    AXIReg.io.s_axi_rready  := sh_ocl_rready
 
    /////////////////////
    // master interface
    /////////////////////
    awaddr                  := AXIReg.io.m_axi_awaddr
    //awprot           := AXIReg.io.m_axi_awprot
    awvalid                 := AXIReg.io.m_axi_awvalid
    AXIReg.io.m_axi_awready := awready
    
    wdata                   := AXIReg.io.m_axi_wdata
    wstrb                   := AXIReg.io.m_axi_wstrb
    wvalid                  := AXIReg.io.m_axi_wvalid
    AXIReg.io.m_axi_wready  := wready
    
    AXIReg.io.m_axi_bresp   := bresp
    AXIReg.io.m_axi_bvalid  := bvalid
    bready                  := AXIReg.io.m_axi_bready
    
    araddr                  := AXIReg.io.m_axi_araddr
    //arprot                  := AXIReg.io.m_axi_arprot
    arvalid                 := AXIReg.io.m_axi_arvalid
    AXIReg.io.m_axi_arready := arready
    
    AXIReg.io.m_axi_rdata   := rdata
    AXIReg.io.m_axi_rresp   := rresp
    AXIReg.io.m_axi_rvalid  := rvalid
    rready                  := AXIReg.io.m_axi_rready

    val ddr = Module(new ddrwrapper(Seq(false, false, false)))
    val tie_zero        = VecInit.tabulate(3)(n => 0.U(1.W))
    val tie_zero_burst  = VecInit.tabulate(3)(n => 1.U(2.W))
    val tie_zero_id     = VecInit.tabulate(3)(n => 0.U(16.W))
    val tie_zero_addr   = VecInit.tabulate(3)(n => 0.U(64.W))
    val tie_zero_len    = VecInit.tabulate(3)(n => 0.U(8.W))
    val tie_zero_data   = VecInit.tabulate(3)(n => 0.U(512.W))
    val tie_zero_strb   = VecInit.tabulate(3)(n => 0.U(64.W))
 

    ddr.io.clk := clk_main_a0.asUInt
    ddr.io.rst_n := rst_main_n
    ddr.io.stat_clk := clk_main_a0.asUInt
    ddr.io.stat_rst_n := clk_main_a0.asUInt

    ddr.io.cl_sh_ddr_awid    := tie_zero_id
    ddr.io.cl_sh_ddr_awaddr  := tie_zero_addr
    ddr.io.cl_sh_ddr_awlen   := tie_zero_len
    ddr.io.cl_sh_ddr_awvalid := tie_zero
    ddr.io.cl_sh_ddr_awburst := tie_zero_burst
    //ddr.io.sh_cl_ddr_awready := 

    ddr.io.cl_sh_ddr_wid     := tie_zero_id
    ddr.io.cl_sh_ddr_wdata   := tie_zero_data
    ddr.io.cl_sh_ddr_wstrb   := tie_zero_strb
    ddr.io.cl_sh_ddr_wlast   := tie_zero
    ddr.io.cl_sh_ddr_wvalid  := tie_zero
    //ddr.io.sh_cl_ddr_wready  := 

    //ddr.io.sh_cl_ddr_bid     := 
    //ddr.io.sh_cl_ddr_bresp   := 
    //ddr.io.sh_cl_ddr_bvalid  := 
    ddr.io.cl_sh_ddr_bready  := tie_zero

    ddr.io.cl_sh_ddr_arid    := tie_zero_id
    ddr.io.cl_sh_ddr_araddr  := tie_zero_addr
    ddr.io.cl_sh_ddr_arlen   := tie_zero_len
    ddr.io.cl_sh_ddr_arvalid := tie_zero
    ddr.io.cl_sh_ddr_arburst := tie_zero_burst
    //ddr.io.sh_cl_ddr_arready :=

    //ddr.io.sh_cl_ddr_is_ready :=

    ddr.io.sh_ddr_stat_addr0  := 0.U(8.W)
    ddr.io.sh_ddr_stat_wr0    := 0.U(1.W)
    ddr.io.sh_ddr_stat_rd0    := 0.U(1.W)
    ddr.io.sh_ddr_stat_wdata0 := 0.U(32.W)
    //ddr.io.ddr_sh_stat_ack0   :=
    //ddr.io.ddr_sh_stat_rdata0 := 
    //ddr.io.ddr_sh_stat_int0   :=
    ddr.io.sh_ddr_stat_addr1  := 0.U(8.W)
    ddr.io.sh_ddr_stat_wr1    := 0.U(1.W)
    ddr.io.sh_ddr_stat_rd1    := 0.U(1.W)
    ddr.io.sh_ddr_stat_wdata1 := 0.U(32.W)
    //ddr.io.ddr_sh_stat_ack1   :=
    //ddr.io.ddr_sh_stat_rdata1 :=
    //ddr.io.ddr_sh_stat_int1   :=
    ddr.io.sh_ddr_stat_addr2  := 0.U(8.W)
    ddr.io.sh_ddr_stat_wr2    := 0.U(1.W)
    ddr.io.sh_ddr_stat_rd2    := 0.U(1.W)
    ddr.io.sh_ddr_stat_wdata2 := 0.U(32.W)
    //ddr.io.ddr_sh_stat_ack2   :=
    //ddr.io.ddr_sh_stat_rdata2 :=
    //ddr.io.ddr_sh_stat_int2   :=
    
    // tie-offs needed to not hang interface
    ddr_sh_stat_ack0 := false.B 
    ddr_sh_stat_ack1 := false.B 
    ddr_sh_stat_ack2 := false.B 

    ddr.io.CLK_300M_DIMM0_DP	:= CLK_300M_DIMM0_DP	
    ddr.io.CLK_300M_DIMM0_DN  := CLK_300M_DIMM0_DN 
    M_A_ACT_N                 := ddr.io.M_A_ACT_N         
    M_A_MA                    := ddr.io.M_A_MA            
    M_A_BA                    := ddr.io.M_A_BA            
    M_A_BG                    := ddr.io.M_A_BG            
    M_A_CKE                   := ddr.io.M_A_CKE           
    M_A_ODT                   := ddr.io.M_A_ODT           
    M_A_CS_N                  := ddr.io.M_A_CS_N          
    M_A_CLK_DN                := ddr.io.M_A_CLK_DN        
    M_A_CLK_DP                := ddr.io.M_A_CLK_DP        
    M_A_PAR                   := ddr.io.M_A_PAR           
    attach(ddr.io.M_A_DQ,      M_A_DQ)
    attach(ddr.io.M_A_ECC,     M_A_ECC)
    attach(ddr.io.M_A_DQS_DP,  M_A_DQS_DP)
    attach(ddr.io.M_A_DQS_DN,  M_A_DQS_DN)
    cl_RST_DIMM_A_N           := ddr.io.cl_RST_DIMM_A_N

    ddr.io.CLK_300M_DIMM1_DP	:= CLK_300M_DIMM1_DP	
    ddr.io.CLK_300M_DIMM1_DN  := CLK_300M_DIMM1_DN 
    M_B_ACT_N                 := ddr.io.M_B_ACT_N         
    M_B_MA                    := ddr.io.M_B_MA            
    M_B_BA                    := ddr.io.M_B_BA            
    M_B_BG                    := ddr.io.M_B_BG            
    M_B_CKE                   := ddr.io.M_B_CKE           
    M_B_ODT                   := ddr.io.M_B_ODT           
    M_B_CS_N                  := ddr.io.M_B_CS_N          
    M_B_CLK_DN                := ddr.io.M_B_CLK_DN        
    M_B_CLK_DP                := ddr.io.M_B_CLK_DP        
    M_B_PAR                   := ddr.io.M_B_PAR           
    attach(ddr.io.M_B_DQ,      M_B_DQ)
    attach(ddr.io.M_B_ECC,     M_B_ECC)
    attach(ddr.io.M_B_DQS_DP,  M_B_DQS_DP)
    attach(ddr.io.M_B_DQS_DN,  M_B_DQS_DN)
    cl_RST_DIMM_B_N           := ddr.io.cl_RST_DIMM_B_N

    ddr.io.CLK_300M_DIMM3_DP	:= CLK_300M_DIMM3_DP	
    ddr.io.CLK_300M_DIMM3_DN  := CLK_300M_DIMM3_DN 
    M_D_ACT_N                 := ddr.io.M_D_ACT_N         
    M_D_MA                    := ddr.io.M_D_MA            
    M_D_BA                    := ddr.io.M_D_BA            
    M_D_BG                    := ddr.io.M_D_BG            
    M_D_CKE                   := ddr.io.M_D_CKE           
    M_D_ODT                   := ddr.io.M_D_ODT           
    M_D_CS_N                  := ddr.io.M_D_CS_N          
    M_D_CLK_DN                := ddr.io.M_D_CLK_DN        
    M_D_CLK_DP                := ddr.io.M_D_CLK_DP        
    M_D_PAR                   := ddr.io.M_D_PAR           
    attach(ddr.io.M_D_DQ,      M_D_DQ)
    attach(ddr.io.M_D_ECC,     M_D_ECC)
    attach(ddr.io.M_D_DQS_DP,  M_D_DQS_DP)
    attach(ddr.io.M_D_DQS_DN,  M_D_DQS_DN)
    cl_RST_DIMM_D_N           := ddr.io.cl_RST_DIMM_D_N
  }
}
