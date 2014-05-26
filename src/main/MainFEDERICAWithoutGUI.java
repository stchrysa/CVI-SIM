package main;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import gui.SimulatorConstants;

import model.Algorithm;
import model.Request;
import model.Simulation;
import model.Substrate;
import model.components.Node;
import model.components.Node.Location;
import model.components.RequestLink;
import model.components.RequestRouter;
import model.components.Server;
import model.components.SubstrateLink;
import model.components.SubstrateRouter;
import model.components.VirtualMachine;

public class MainFEDERICAWithoutGUI {

	public static void main(String[] args) {
		
		List<Request> requests = new ArrayList<Request>();
		List<Substrate> substrates = new ArrayList<Substrate>();
		Substrate InPs = new Substrate("InPs");
		
		Node node0 = new Node(0);
		InPs.getGraph().addVertex(node0);		
		
		// FEDERICA substrate
	    Substrate substrate = new Substrate("FEDERICA");
	    Algorithm algorithm = new Algorithm("GSP-Exact");
	    
      
	    // Hardcoded FEDERICA Substrate
	    /** Portugal (FCCN)**/
	    // vnode1.lis.pt
	    Server vnode1LisPt = new Server(0);
	    vnode1LisPt.setName("vnode1.lis.pt");
	    vnode1LisPt.setLocation(Location.Portugal);
	    vnode1LisPt.setCpu(8);
	    vnode1LisPt.setMemory(32);
	    vnode1LisPt.setDiskSpace(1000);
	    vnode1LisPt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Spain - Madrid (RedIRIS) **/
	    // vnode1.mad.es
	    Server vnode1MadEs = new Server(1);
	    vnode1MadEs.setName("vnode1.mad.es");
	    vnode1MadEs.setLocation(Location.Spain);
	    vnode1MadEs.setCpu(8);
	    vnode1MadEs.setMemory(32);
	    vnode1MadEs.setDiskSpace(1000);
	    vnode1MadEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Spain - Barcelona (i2CAT) **/
	    // vnode1.bar.es
	    Server vnode1BarEs = new Server(2);
	    vnode1BarEs.setName("vnode1.bar.es");
	    vnode1BarEs.setLocation(Location.Spain);
	    vnode1BarEs.setCpu(8);
	    vnode1BarEs.setMemory(32);
	    vnode1BarEs.setDiskSpace(1000);
	    vnode1BarEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Ireland (HEANet) **/
	    // vnode1.dub.ie
	    Server vnode1DubIe = new Server(3);
	    vnode1DubIe.setName("vnode1.dub.ie");
	    vnode1DubIe.setLocation(Location.Ireland);
	    vnode1DubIe.setCpu(8);
	    vnode1DubIe.setMemory(32);
	    vnode1DubIe.setDiskSpace(1000);
	    vnode1DubIe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Greece (GRNET) **/
	    // vnode1.ntu.ie
	    Server vnode1Grnet = new Server(4);
	    vnode1Grnet.setName("vnode1.grent.gr");
	    vnode1Grnet.setLocation(Location.Greece);
	    vnode1Grnet.setCpu(8);
	    vnode1Grnet.setMemory(32);
	    vnode1Grnet.setDiskSpace(1000);
	    vnode1Grnet.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    //vnode1.ntu.ie
	    Server vnode1NtuGr = new Server(5);
	    vnode1NtuGr.setName("vnode2.ntu.ie");
	    vnode1NtuGr.setLocation(Location.Greece);
	    vnode1NtuGr.setCpu(8);
	    vnode1NtuGr.setMemory(32);
	    vnode1NtuGr.setDiskSpace(1000);
	    vnode1NtuGr.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Italy (GARR) **/
	    // r1.mil.it
	    SubstrateRouter r1MilIt = new SubstrateRouter(6);
	    r1MilIt.setName("r1.mil.it");
	    r1MilIt.setLocation(Location.Italy);
	    r1MilIt.setLogicalInstances(15);
	    //r1MilIt.setCpu(100);
	    //r1MilIt.setMemory(100);
	    //r1MilIt.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // vnode1.mil.it
	    Server vnode1MilIt = new Server(7);
	    vnode1MilIt.setName("vnode1.mil.it");
	    vnode1MilIt.setLocation(Location.Italy);
	    vnode1MilIt.setCpu(8);
	    vnode1MilIt.setMemory(32);
	    vnode1MilIt.setDiskSpace(1000);
	    vnode1MilIt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.mil.it
	    Server vnode2MilIt = new Server(8);
	    vnode2MilIt.setName("vnode2.mil.it");
	    vnode2MilIt.setLocation(Location.Italy);
	    vnode2MilIt.setCpu(8);
	    vnode2MilIt.setMemory(32);
	    vnode2MilIt.setDiskSpace(1000);
	    vnode2MilIt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Switzerland (SWITCH) **/
	    // vnode1.gen.ch
	    Server vnode1GenCh = new Server(9);
	    vnode1GenCh.setName("vnode1.gen.ch");
	    vnode1GenCh.setLocation(Location.Switzerland);
	    vnode1GenCh.setCpu(8);
	    vnode1GenCh.setMemory(32);
	    vnode1GenCh.setDiskSpace(1000);
	    vnode1GenCh.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Hungary (HUNGARNET) **/
	    // vnode1.bud.hu
	    Server vnode1BudHu = new Server(10);
	    vnode1BudHu.setName("vnode1.bud.hu");
	    vnode1BudHu.setLocation(Location.Hungary);
	    vnode1BudHu.setCpu(8);
	    vnode1BudHu.setMemory(32);
	    vnode1BudHu.setDiskSpace(1000);
	    vnode1BudHu.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Germany (DFN) **/
	    // r1.erl.de
	    SubstrateRouter r1ErlDe = new SubstrateRouter(11);
	    r1ErlDe.setName("r1.erl.de");
	    r1ErlDe.setLocation(Location.Germany);
	    r1ErlDe.setLogicalInstances(15);
	    //r1ErlDe.setCpu(100);
	    //r1ErlDe.setMemory(100);
	    r1ErlDe.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // vnode1.erl.de
	    Server vnode1ErlDe = new Server(12);
	    vnode1ErlDe.setName("vnode1.erl.de");
	    vnode1ErlDe.setLocation(Location.Germany);
	    vnode1ErlDe.setCpu(8);
	    vnode1ErlDe.setMemory(32);
	    vnode1ErlDe.setDiskSpace(1000);
	    vnode1ErlDe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.erl.de
	    Server vnode2ErlDe = new Server(13);
	    vnode2ErlDe.setName("vnode2.erl.de");
	    vnode2ErlDe.setLocation(Location.Germany);
	    vnode2ErlDe.setCpu(8);
	    vnode2ErlDe.setMemory(32);
	    vnode2ErlDe.setDiskSpace(1000);
	    vnode2ErlDe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    /** Czech Republic (CESNET) **/
	    // r1.pra.cz
	    SubstrateRouter r1PraCz = new SubstrateRouter(14);
	    r1PraCz.setName("r1.pra.cz");
	    r1PraCz.setLocation(Location.Czech_Republic);
	    r1PraCz.setLogicalInstances(15);
	    //r1PraCz.setCpu(100);
	    //r1PraCz.setMemory(100);
	    r1PraCz.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);	    
	    // vnode1.pra.cz
	    Server vnode1PraCz = new Server(15);
	    vnode1PraCz.setName("vnode1.pra.cz");
	    vnode1PraCz.setLocation(Location.Czech_Republic);
	    vnode1PraCz.setCpu(8);
	    vnode1PraCz.setMemory(32);
	    vnode1PraCz.setDiskSpace(1000);
	    vnode1PraCz.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.pra.cz
	    Server vnode2PraCz = new Server(16);
	    vnode2PraCz.setName("vnode2.pra.cz");
	    vnode2PraCz.setLocation(Location.Czech_Republic);
	    vnode2PraCz.setCpu(8);
	    vnode2PraCz.setMemory(32);
	    vnode2PraCz.setDiskSpace(1000);
	    vnode2PraCz.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    /** Poland (PSNC) **/
	    // r1.poz.pl
	    SubstrateRouter r1PozPl = new SubstrateRouter(17);
	    r1PozPl.setName("r1.poz.pl");
	    r1PozPl.setLocation(Location.Poland);
	    r1PozPl.setLogicalInstances(15);
	    //r1PozPl.setCpu(100);
	    //r1PozPl.setMemory(100);
	    r1PozPl.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);	
	    // vnode1.poz.pl
	    Server vnode1PozPl = new Server(18);
	    vnode1PozPl.setName("vnode1.poz.pl");
	    vnode1PozPl.setLocation(Location.Poland);
	    vnode1PozPl.setCpu(8);
	    vnode1PozPl.setMemory(32);
	    vnode1PozPl.setDiskSpace(1000);
	    vnode1PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.poz.pl
	    Server vnode2PozPl = new Server(19);
	    vnode2PozPl.setName("vnode2.poz.pl");
	    vnode2PozPl.setLocation(Location.Poland);
	    vnode2PozPl.setCpu(8);
	    vnode2PozPl.setMemory(32);
	    vnode2PozPl.setDiskSpace(1000);
	    vnode2PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Sweden (KTH) **/
	    // vnode1.sth.se
	    Server vnode1SthSe = new Server(20);
	    vnode1SthSe.setName("vnode1.sth.se");
	    vnode1SthSe.setLocation(Location.Sweden);
	    vnode1SthSe.setCpu(8);
	    vnode1SthSe.setMemory(32);
	    vnode1SthSe.setDiskSpace(1000);
	    vnode1SthSe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    /** Transit **/
	    SubstrateRouter nord = new SubstrateRouter(21);
	    nord.setName("nord");
	    nord.setLocation(Location.Poland);
	    nord.setLogicalInstances(0);
	    
	   
	    // Adding links & interfaces
	    // vnode1.lis.pt - vnode1.mad.es
	    SubstrateLink link = new SubstrateLink(0,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1LisPt, vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - vnode1.bar.es
	    link = new SubstrateLink(1,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1MadEs,vnode1BarEs), EdgeType.UNDIRECTED); 


	    // vnode1.mad.es - vnode1.bar.es
	    link = new SubstrateLink(2,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1MadEs,vnode1BarEs), EdgeType.UNDIRECTED);
	    

	    // vnode1.mad.es - r1.mil.it 
	    link = new SubstrateLink(3,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1MadEs,r1MilIt), EdgeType.UNDIRECTED);
	    
	    // r1.mil.it  - vnode1.mil.it 
	    link = new SubstrateLink(4,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,vnode1MilIt), EdgeType.UNDIRECTED);
	    
	    // r1.mil.it - vnode2.mil.it 
	    link = new SubstrateLink(5,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,vnode2MilIt), EdgeType.UNDIRECTED);
	    
	    // r1.mil.it - r1.erl.de 
	    link = new SubstrateLink(6,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,r1ErlDe), EdgeType.UNDIRECTED);
	    
	    // r1.erl.de - vnode1.erl.de 
	    link = new SubstrateLink(7,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode1ErlDe), EdgeType.UNDIRECTED);
	    
	    // r1.erl.de - vnode2.erl.de 
	    link = new SubstrateLink(8,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode2ErlDe), EdgeType.UNDIRECTED);
	    
	    // r1.erl.de - vnode1.gen.ch
	    link = new SubstrateLink(9,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode1GenCh), EdgeType.UNDIRECTED);	    
	    
	    // vnode1.gen.ch - vnode1.dub.ie
	    link = new SubstrateLink(10,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1GenCh,vnode1DubIe), EdgeType.UNDIRECTED);
	    
	    //  vnode1.dub.ie - nord
	    link = new SubstrateLink(11,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1DubIe, nord), EdgeType.UNDIRECTED);	    

	    // nord - vnode1.sth.se
	    link = new SubstrateLink(12,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(nord,vnode1SthSe), EdgeType.UNDIRECTED);
	    
	    // nord - r1.poz.pl
	    link = new SubstrateLink(13,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(nord,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.poz.pl - vnode1.poz.pl
	    link = new SubstrateLink(14,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,vnode1PozPl), EdgeType.UNDIRECTED);	    
	    
	    // r1.poz.pl - vnode2.poz.pl
	    link = new SubstrateLink(15,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,vnode2PozPl), EdgeType.UNDIRECTED);	    
	    
	    // r1.poz.pl - r1.erl.de 
	    link = new SubstrateLink(16,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,r1ErlDe), EdgeType.UNDIRECTED);	    
	    
	    // r1.poz.pl - r1.pra.cz 
	    link = new SubstrateLink(17,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,r1PraCz), EdgeType.UNDIRECTED);	
	    
	    // r1.pra.cz - r1.erl.de 
	    link = new SubstrateLink(18,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PraCz,r1ErlDe), EdgeType.UNDIRECTED);	
	    
	    // r1.pra.cz - r1.mil.it
	    link = new SubstrateLink(19,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PraCz,r1MilIt), EdgeType.UNDIRECTED);	    
	
	    // r1.pra.cz - vnode1.pra.cz
	    link = new SubstrateLink(20,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PraCz,vnode1PraCz), EdgeType.UNDIRECTED);
	    
	    // r1.pra.cz - vnode2.pra.cz
	    link = new SubstrateLink(21,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PraCz,vnode2PraCz), EdgeType.UNDIRECTED);	    
	     
	    
	    // r1.pra.cz - vnode1.grnet.gr
	    link = new SubstrateLink(22,1000);
	    substrate.getGraph().addEdge(link, new Pair<Node>(r1PraCz,vnode1Grnet), EdgeType.UNDIRECTED);    
	    
	    // vnode1.grnet.gr - vnode1.ntu.ie
	    link = new SubstrateLink(23,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1Grnet,vnode1NtuGr), EdgeType.UNDIRECTED);	    
	    
	    // vnode1.ntu.ie - vnode1.mad.es
	    link = new SubstrateLink(24,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1NtuGr,vnode1MadEs), EdgeType.UNDIRECTED);	    
	    
	    // vnode1.ntu.ie - vnode1.bud.hu
	    link = new SubstrateLink(25,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1NtuGr,vnode1BudHu), EdgeType.UNDIRECTED);	    
	    
	    // vnode1.bud.hu - vnode1.gen.ch
	    link = new SubstrateLink(26,1000);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1BudHu,vnode1GenCh), EdgeType.UNDIRECTED);    
	    
	    substrates.add(substrate);
	  
		    
	    //Request 0
	    Request req0=new Request("req0");
	    
	    VirtualMachine vm01= new VirtualMachine(0);
	    vm01.setCpu(4);
	    vm01.setMemory(4);
	    vm01.setDiskSpace(50);  
		vm01.setName("vm1");
	    
	    VirtualMachine vm02= new VirtualMachine(1);
	    vm02.setCpu(2);
	    vm02.setMemory(2);
	    vm02.setDiskSpace(20);
	    vm02.setName("vm2");
	    
	    VirtualMachine vm03= new VirtualMachine(2);
	    vm03.setCpu(1);
	    vm03.setMemory(1);
	    vm03.setDiskSpace(8);
	    vm03.setName("vm3");
	    
	    VirtualMachine vm04= new VirtualMachine(3);
	    vm04.setCpu(4);
	    vm04.setMemory(4);
	    vm04.setDiskSpace(50);
	    vm04.setName("vm4");
	    
	    VirtualMachine vm05= new VirtualMachine(4);
	    vm05.setCpu(2);
	    vm05.setMemory(2);
	    vm05.setDiskSpace(20);
	    vm05.setName("vm5");
	    
	    VirtualMachine vm06= new VirtualMachine(5);
	    vm06.setCpu(2);
	    vm06.setMemory(2);
	    vm06.setDiskSpace(20);
	    vm06.setName("vm6");
	    
	    RequestRouter r01=new RequestRouter(6);
	    RequestRouter r02=new RequestRouter(7);
	    RequestRouter r03=new RequestRouter(8);
	    RequestRouter r04=new RequestRouter(9);
	    
	    RequestLink l00 = new RequestLink(0,100);
	    RequestLink l01 = new RequestLink(1,100);
	    RequestLink l02 = new RequestLink(2,100);
	    RequestLink l03 = new RequestLink(3,100);
	    RequestLink l04 = new RequestLink(4,100);
	    RequestLink l05 = new RequestLink(5,100);
	    RequestLink l06 = new RequestLink(6,100);
	    RequestLink l07 = new RequestLink(7,100);
	    RequestLink l08 = new RequestLink(8,100);
	    RequestLink l09 = new RequestLink(9,100);
	    
	    req0.getGraph().addEdge(l00 ,vm01, r01, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l01 ,vm04, r02, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l02 ,vm02, r01, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l03 ,r01, r02, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l04 ,vm03, r02, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l05 ,r01, r03, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l06 ,r02, r04, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l07 ,vm05, r04, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l08 ,r04, r03, EdgeType.UNDIRECTED);
	    req0.getGraph().addEdge(l09 ,vm06,r03 , EdgeType.UNDIRECTED);
	    
	    req0.setStartDate(0);
	    req0.setEndDate(12);
	    requests.add(req0);
	    
	    //Request1
	    Request req1= new Request("req1");
	    
	    VirtualMachine vm11= new VirtualMachine(0);
	    vm11.setCpu(2);
	    vm11.setMemory(4);
	    vm11.setDiskSpace(50);
	    
	    VirtualMachine vm12= new VirtualMachine(1);
	    vm12.setCpu(2);
	    vm12.setMemory(4);
	    vm12.setDiskSpace(50);
	    
	    VirtualMachine vm13= new VirtualMachine(2);
	    vm13.setCpu(2);
	    vm13.setMemory(4);
	    vm13.setDiskSpace(50);
	    
	    VirtualMachine vm14= new VirtualMachine(3);
	    vm14.setCpu(2);
	    vm14.setMemory(4);
	    vm14.setDiskSpace(50);
	    
	    RequestLink l10 = new RequestLink(0,100);
	    RequestLink l11 = new RequestLink(1,100);
	    RequestLink l12 = new RequestLink(2,100);
	    RequestLink l13 = new RequestLink(3,100);
	    
	    req1.getGraph().addEdge(l10 ,vm11, vm12, EdgeType.UNDIRECTED);
	    req1.getGraph().addEdge(l11 ,vm12, vm14, EdgeType.UNDIRECTED);
	    req1.getGraph().addEdge(l12 ,vm14, vm13, EdgeType.UNDIRECTED);
	    req1.getGraph().addEdge(l13 ,vm13, vm11, EdgeType.UNDIRECTED);
	    
	    req1.setStartDate(1);
	    req1.setEndDate(12);
	    requests.add(req1);
	    
	    //Request2
	    
	    Request req2 = new Request("Req2");
	    
	    VirtualMachine vm21= new VirtualMachine(0);
	    vm21.setCpu(2);
	    vm21.setMemory(4);
	    vm21.setDiskSpace(8);
	    
	    VirtualMachine vm22= new VirtualMachine(1);
	    vm22.setCpu(2);
	    vm22.setMemory(4);
	    vm22.setDiskSpace(8);
	   
	    RequestLink l20 = new RequestLink(0,100);
	    
	    req2.getGraph().addEdge(l20 ,vm21, vm22, EdgeType.UNDIRECTED);
	    
	    req2.setStartDate(2);
	    req2.setEndDate(12);
	    requests.add(req2);
	    
	    //Request 3
	    Request req3 = new Request ("Req3");
	    
	    VirtualMachine vm31= new VirtualMachine(0);
	    vm31.setCpu(2);
	    vm31.setMemory(4);
	    vm31.setDiskSpace(50);
	    
	    VirtualMachine vm32= new VirtualMachine(1);
	    vm32.setCpu(2);
	    vm32.setMemory(4);
	    vm32.setDiskSpace(50);
	    
	    VirtualMachine vm33= new VirtualMachine(2);
	    vm33.setCpu(2);
	    vm33.setMemory(4);
	    vm33.setDiskSpace(50);
	    
	    VirtualMachine vm34= new VirtualMachine(3);
	    vm34.setCpu(2);
	    vm34.setMemory(4);
	    vm34.setDiskSpace(50);
	    
	    
	    RequestLink l30 = new RequestLink(0,100);
	    RequestLink l31 = new RequestLink(1,100);
	    RequestLink l32 = new RequestLink(2,100);
	    RequestLink l33 = new RequestLink(3,100);
	    RequestLink l34 = new RequestLink(4,100);
	    RequestLink l35 = new RequestLink(5,100);
	    
	    req3.getGraph().addEdge(l30 ,vm31, vm34, EdgeType.UNDIRECTED);
	    req3.getGraph().addEdge(l31 ,vm31, vm33, EdgeType.UNDIRECTED);
	    req3.getGraph().addEdge(l32 ,vm31, vm32, EdgeType.UNDIRECTED);
	    req3.getGraph().addEdge(l33 ,vm34, vm33, EdgeType.UNDIRECTED);
	    req3.getGraph().addEdge(l34 ,vm34, vm32, EdgeType.UNDIRECTED);
	    req3.getGraph().addEdge(l35 ,vm32, vm33, EdgeType.UNDIRECTED);
	    
	    req3.setStartDate(3);
	    req3.setEndDate(12);
	    requests.add(req3);

	    //Request 4
	    Request req4 = new Request("req4");
	   
	    VirtualMachine vm41= new VirtualMachine(0);
	    vm41.setName("vm1");
	    vm41.setCpu(4);
	    vm41.setMemory(2);
	    vm41.setDiskSpace(50);
	    
	    VirtualMachine vm42= new VirtualMachine(1);
	    vm42.setName("vm2");
	    vm42.setCpu(4);
	    vm42.setMemory(2);
	    vm42.setDiskSpace(100);
	    
	    VirtualMachine vm43= new VirtualMachine(2);
	    vm43.setName("vm3");
	    vm43.setCpu(4);
	    vm43.setMemory(2);
	    vm43.setDiskSpace(100);
	    
	    VirtualMachine vm44= new VirtualMachine(3);
	    vm44.setName("vm4");
	    vm44.setCpu(4);
	    vm44.setMemory(2);
	    vm44.setDiskSpace(50);
	    
	    RequestRouter r41 = new RequestRouter(4);
	    r41.setName("lr1");
	    
	    RequestRouter r42 = new RequestRouter(5);
	    r42.setName("lr2");
	    
	    RequestLink l40 = new RequestLink(0,100);
	    RequestLink l41 = new RequestLink(1,100);
	    RequestLink l42 = new RequestLink(2,100);
	    RequestLink l43 = new RequestLink(3,100);
	    RequestLink l44 = new RequestLink(4,100);
	    
	    req4.getGraph().addEdge(l40 ,vm41, r41, EdgeType.UNDIRECTED);
	    req4.getGraph().addEdge(l41 ,vm42, r41, EdgeType.UNDIRECTED);
	    req4.getGraph().addEdge(l42 ,r41, r42, EdgeType.UNDIRECTED);
	    req4.getGraph().addEdge(l43 ,r42, vm44, EdgeType.UNDIRECTED);
	    req4.getGraph().addEdge(l44 ,r42, vm43, EdgeType.UNDIRECTED);
	    
	    req4.setStartDate(4);
	    req4.setEndDate(12);
	    requests.add(req4);
	    
	    
	    // Creating simulation
	    Simulation simulation = new Simulation(InPs, substrates, requests, algorithm);
	    launchLaunchSimulation(simulation);
	    
	}
	
	private static void launchLaunchSimulation(Simulation simulation) {
		List<Request> requests = simulation.getRequests();
		Substrate substrate = simulation.getInPs();
		List<Substrate> substrates = simulation.getSubstrates();
		Algorithm algorithm = simulation.getAlgorithm();
		//chrisap
		algorithm.addInPs(substrate);
		algorithm.addSubstrate(substrates);
		algorithm.addRequests(requests);
		algorithm.runAlgorithm();
	}
	
}