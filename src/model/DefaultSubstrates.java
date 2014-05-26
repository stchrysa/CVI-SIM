package model;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import gui.SimulatorConstants;
import model.components.Link;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateLink;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.Node.Location;

/** Generate hard-coded substrates, e.g. FEDERICA **/
public class DefaultSubstrates {
	
	/** Default substrate. 5 nodes. 7 links **/
	public static final Substrate constructDefaultSubstrate(String id) {
		Substrate substrate = new Substrate(id);
	    	
    	// Creating nodes
        SubstrateRouter r1 = new SubstrateRouter(0);
        r1.setCpu(100);
        r1.setMemory(100);
        r1.setVlans(4096);
        SubstrateSwitch s1 = new SubstrateSwitch(1);
        s1.setCpu(100);
        s1.setMemory(100);
        s1.setVlans(4096);
        Server se1 = new Server(2);
        se1.setCpu(100);
        se1.setMemory(100);
        se1.setVlans(4096);
        se1.setDiskSpace(100);
        SubstrateRouter r2 = new SubstrateRouter(3);
        r2.setCpu(100);
        r2.setMemory(100);
        r2.setVlans(4096);
        SubstrateSwitch s2 = new SubstrateSwitch(4);
        s2.setCpu(100);
        s2.setMemory(100);
        s2.setVlans(4096);
         
        // Adding links & interfaces
        SubstrateLink l = new SubstrateLink(0,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(r1, s1), EdgeType.UNDIRECTED); // This method

        l = new SubstrateLink(1,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(s1, se1), EdgeType.UNDIRECTED);
        
        l = new SubstrateLink(2,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(se1, r2), EdgeType.UNDIRECTED);
        
        l = new SubstrateLink(3,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(s2, r2), EdgeType.UNDIRECTED);
        
        l = new SubstrateLink(4,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(s2, s1), EdgeType.UNDIRECTED);
        
        l = new SubstrateLink(5,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(se1, r1), EdgeType.UNDIRECTED); 
        
        l = new SubstrateLink(6,100);
        substrate.getGraph().addEdge(l, new Pair<Node>(s1, r2), EdgeType.UNDIRECTED);

        ((SubstrateNodeFactory) substrate.getNodeFactory()).setNodeCount(5);
	    ((SubstrateLinkFactory) substrate.getLinkFactory()).setLinkCount(7);
        
		return substrate;
	}
	
	/** test substrate. 50 nodes. 613 links. No interfaces **/
	public static final Substrate constructTestSubstrate(String id) {
		Substrate substrate = new Substrate(id);
	    // Server0
	    Server server0 = new Server(0);
	    server0.setCpu(50);
	    server0.setMemory(100);
	    server0.setDiskSpace(92);
	    server0.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server1
	    Server server1 = new Server(1);
	    server1.setCpu(69);
	    server1.setMemory(97);
	    server1.setDiskSpace(53);
	    server1.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server2
	    Server server2 = new Server(2);
	    server2.setCpu(60);
	    server2.setMemory(86);
	    server2.setDiskSpace(75);
	    server2.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server3
	    Server server3 = new Server(3);
	    server3.setCpu(82);
	    server3.setMemory(77);
	    server3.setDiskSpace(86);
	    server3.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
        // Server4
	    Server server4 = new Server(4);
	    server4.setCpu(69);
	    server4.setMemory(54);
	    server4.setDiskSpace(75);
	    server4.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server5
	    Server server5 = new Server(5);
	    server5.setCpu(72);
	    server5.setMemory(52);
	    server5.setDiskSpace(96);
	    server5.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server6
	    Server server6 = new Server(6);
	    server6.setCpu(73);
	    server6.setMemory(59);
	    server6.setDiskSpace(89);
	    server6.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server7
	    Server server7 = new Server(7);
	    server7.setCpu(70);
	    server7.setMemory(65);
	    server7.setDiskSpace(57);
	    server7.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server8
	    Server server8 = new Server(8);
	    server8.setCpu(76);
	    server8.setMemory(90);
	    server8.setDiskSpace(98);
	    server8.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server9
	    Server server9 = new Server(9);
	    server9.setCpu(82);
	    server9.setMemory(57);
	    server9.setDiskSpace(64);
	    server9.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server10
	    Server server10 = new Server(10);
	    server10.setCpu(90);
	    server10.setMemory(71);
	    server10.setDiskSpace(57);
	    server10.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server11
	    Server server11 = new Server(11);
	    server11.setCpu(76);
	    server11.setMemory(64);
	    server11.setDiskSpace(58);
	    server11.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server12
	    Server server12 = new Server(12);
	    server12.setCpu(72);
	    server12.setMemory(53);
	    server12.setDiskSpace(59);
	    server12.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server13
	    Server server13 = new Server(13);
	    server13.setCpu(70);
	    server13.setMemory(86);
	    server13.setDiskSpace(55);
	    server13.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server14
	    Server server14 = new Server(14);
	    server14.setCpu(70);
	    server14.setMemory(55);
	    server14.setDiskSpace(61);
	    server14.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server15
	    Server server15 = new Server(15);
	    server15.setCpu(100);
	    server15.setMemory(92);
	    server15.setDiskSpace(67);
	    server15.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server16
	    Server server16 = new Server(16);
	    server16.setCpu(79);
	    server16.setMemory(81);
	    server16.setDiskSpace(82);
	    server16.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server17
	    Server server17 = new Server(17);
	    server17.setCpu(58);
	    server17.setMemory(74);
	    server17.setDiskSpace(51);
	    server17.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    	 	    
	    
	    // Router1
	    SubstrateRouter sr1 = new SubstrateRouter(18);
	    sr1.setCpu(80);
	    sr1.setMemory(94);
	    sr1.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router2
	    SubstrateRouter sr2 = new SubstrateRouter(19);
	    sr2.setCpu(54);
	    sr2.setMemory(51);
	    sr2.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router3
	    SubstrateRouter sr3 = new SubstrateRouter(20);
	    sr3.setCpu(86);
	    sr3.setMemory(73);
	    sr3.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router4
	    SubstrateRouter sr4 = new SubstrateRouter(21);
	    sr4.setCpu(79);
	    sr4.setMemory(65);
	    sr4.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router5
	    SubstrateRouter sr5 = new SubstrateRouter(22);
	    sr5.setCpu(60);
	    sr5.setMemory(74);
	    sr5.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router6
	    SubstrateRouter sr6 = new SubstrateRouter(23);
	    sr6.setCpu(86);
	    sr6.setMemory(60);
	    sr6.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router7
	    SubstrateRouter sr7 = new SubstrateRouter(24);
	    sr7.setCpu(99);
	    sr7.setMemory(82);
	    sr7.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router8
	    SubstrateRouter sr8 = new SubstrateRouter(25);
	    sr8.setCpu(85);
	    sr8.setMemory(70);
	    sr8.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router9
	    SubstrateRouter sr9 = new SubstrateRouter(26);
	    sr9.setCpu(56);
	    sr9.setMemory(63);
	    sr9.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router10
	    SubstrateRouter sr10 = new SubstrateRouter(27);
	    sr10.setCpu(66);
	    sr10.setMemory(67);
	    sr10.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router11
	    SubstrateRouter sr11 = new SubstrateRouter(28);
	    sr11.setCpu(78);
	    sr11.setMemory(74);
	    sr11.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router12
	    SubstrateRouter sr12 = new SubstrateRouter(29);
	    sr12.setCpu(88);
	    sr12.setMemory(73);
	    sr12.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router13
	    SubstrateRouter sr13 = new SubstrateRouter(30);
	    sr13.setCpu(76);
	    sr13.setMemory(58);
	    sr13.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router14
	    SubstrateRouter sr14 = new SubstrateRouter(31);
	    sr14.setCpu(95);
	    sr14.setMemory(80);
	    sr14.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router15
	    SubstrateRouter sr15 = new SubstrateRouter(32);
	    sr15.setCpu(62);
	    sr15.setMemory(70);
	    sr15.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // Router16
	    SubstrateRouter sr16 = new SubstrateRouter(33);
	    sr16.setCpu(50);
	    sr16.setMemory(79);
	    sr16.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    
	    
	    // Switch1
	    SubstrateSwitch ss1 = new SubstrateSwitch(34);
	    ss1.setCpu(61);
	    ss1.setMemory(53);
	    ss1.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch2
	    SubstrateSwitch ss2 = new SubstrateSwitch(35);
	    ss2.setCpu(67);
	    ss2.setMemory(69);
	    ss2.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch3
	    SubstrateSwitch ss3 = new SubstrateSwitch(36);
	    ss3.setCpu(81);
	    ss3.setMemory(55);
	    ss3.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch4
	    SubstrateSwitch ss4 = new SubstrateSwitch(37);
	    ss4.setCpu(70);
	    ss4.setMemory(72);
	    ss4.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch5
	    SubstrateSwitch ss5 = new SubstrateSwitch(38);
	    ss5.setCpu(68);
	    ss5.setMemory(50);
	    ss5.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch6
	    SubstrateSwitch ss6 = new SubstrateSwitch(39);
	    ss6.setCpu(65);
	    ss6.setMemory(84);
	    ss6.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch7
	    SubstrateSwitch ss7 = new SubstrateSwitch(40);
	    ss7.setCpu(94);
	    ss7.setMemory(94);
	    ss7.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch8
	    SubstrateSwitch ss8 = new SubstrateSwitch(41);
	    ss8.setCpu(84);
	    ss8.setMemory(82);
	    ss8.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch9
	    SubstrateSwitch ss9 = new SubstrateSwitch(42);
	    ss9.setCpu(84);
	    ss9.setMemory(76);
	    ss9.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch10
	    SubstrateSwitch ss10 = new SubstrateSwitch(43);
	    ss10.setCpu(93);
	    ss10.setMemory(98);
	    ss10.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch11
	    SubstrateSwitch ss11 = new SubstrateSwitch(44);
	    ss11.setCpu(85);
	    ss11.setMemory(98);
	    ss11.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch12
	    SubstrateSwitch ss12 = new SubstrateSwitch(45);
	    ss12.setCpu(64);
	    ss12.setMemory(68);
	    ss12.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch13
	    SubstrateSwitch ss13 = new SubstrateSwitch(46);
	    ss13.setCpu(60);
	    ss13.setMemory(61);
	    ss13.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch14
	    SubstrateSwitch ss14 = new SubstrateSwitch(47);
	    ss14.setCpu(58);
	    ss14.setMemory(93);
	    ss14.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch15
	    SubstrateSwitch ss15 = new SubstrateSwitch(48);
	    ss15.setCpu(93);
	    ss15.setMemory(51);
	    ss15.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // Switch16
	    SubstrateSwitch ss16 = new SubstrateSwitch(49);
	    ss16.setCpu(57);
	    ss16.setMemory(52);
	    ss16.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);	    
	    
	    // Links
	    SubstrateLink sl1 = new SubstrateLink(0,55);
	    SubstrateLink sl2 = new SubstrateLink(1,56);
	    SubstrateLink sl3 = new SubstrateLink(2,97);
	    SubstrateLink sl4 = new SubstrateLink(3,91);
	    SubstrateLink sl5 = new SubstrateLink(4,75);
	    SubstrateLink sl6 = new SubstrateLink(5,97);
	    SubstrateLink sl7 = new SubstrateLink(6,94);
	    SubstrateLink sl8 = new SubstrateLink(7,77);
	    SubstrateLink sl9 = new SubstrateLink(8,82);
	    SubstrateLink sl10 = new SubstrateLink(9,91);
	    SubstrateLink sl11 = new SubstrateLink(10,86);
	    SubstrateLink sl12 = new SubstrateLink(11,70);
	    SubstrateLink sl13 = new SubstrateLink(12,51);
	    SubstrateLink sl14 = new SubstrateLink(13,81);
	    SubstrateLink sl15 = new SubstrateLink(14,63);
	    SubstrateLink sl16 = new SubstrateLink(15,55);
	    SubstrateLink sl17 = new SubstrateLink(16,82);
	    SubstrateLink sl18 = new SubstrateLink(17,97);
	    SubstrateLink sl19 = new SubstrateLink(18,57);
	    SubstrateLink sl20 = new SubstrateLink(19,82);
	    SubstrateLink sl21 = new SubstrateLink(20,64);
	    SubstrateLink sl22 = new SubstrateLink(21,85);
	    SubstrateLink sl23 = new SubstrateLink(22,59);
	    SubstrateLink sl24 = new SubstrateLink(23,98);
	    SubstrateLink sl25 = new SubstrateLink(24,56);
	    SubstrateLink sl26 = new SubstrateLink(25,93);
	    SubstrateLink sl27 = new SubstrateLink(26,81);
	    SubstrateLink sl28 = new SubstrateLink(27,67);
	    SubstrateLink sl29 = new SubstrateLink(28,70);
	    SubstrateLink sl30 = new SubstrateLink(29,95);
	    SubstrateLink sl31 = new SubstrateLink(30,92);
	    SubstrateLink sl32 = new SubstrateLink(31,68);
	    SubstrateLink sl33 = new SubstrateLink(32,85);
	    SubstrateLink sl34 = new SubstrateLink(33,86);
	    SubstrateLink sl35 = new SubstrateLink(34,94);
	    SubstrateLink sl36 = new SubstrateLink(35,78);
	    SubstrateLink sl37 = new SubstrateLink(36,86);
	    SubstrateLink sl38 = new SubstrateLink(37,58);
	    SubstrateLink sl39 = new SubstrateLink(38,69);
	    SubstrateLink sl40 = new SubstrateLink(39,96);
	    SubstrateLink sl41 = new SubstrateLink(40,87);
	    SubstrateLink sl42 = new SubstrateLink(41,69);
	    SubstrateLink sl43 = new SubstrateLink(42,59);
	    SubstrateLink sl44 = new SubstrateLink(43,87);
	    SubstrateLink sl45 = new SubstrateLink(44,79);
	    SubstrateLink sl46 = new SubstrateLink(45,52);
	    SubstrateLink sl47 = new SubstrateLink(46,90);
	    SubstrateLink sl48 = new SubstrateLink(47,71);
	    SubstrateLink sl49 = new SubstrateLink(48,96);
	    SubstrateLink sl50 = new SubstrateLink(49,61);
	    SubstrateLink sl51 = new SubstrateLink(50,80);
	    SubstrateLink sl52 = new SubstrateLink(51,51);
	    SubstrateLink sl53 = new SubstrateLink(52,64);
	    SubstrateLink sl54 = new SubstrateLink(53,98);
	    SubstrateLink sl55 = new SubstrateLink(54,89);
	    SubstrateLink sl56 = new SubstrateLink(55,74);
	    SubstrateLink sl57 = new SubstrateLink(56,50);
	    SubstrateLink sl58 = new SubstrateLink(57,95);
	    SubstrateLink sl59 = new SubstrateLink(58,82);
	    SubstrateLink sl60 = new SubstrateLink(59,81);
	    SubstrateLink sl61 = new SubstrateLink(60,55);
	    SubstrateLink sl62 = new SubstrateLink(61,79);
	    SubstrateLink sl63 = new SubstrateLink(62,73);
	    SubstrateLink sl64 = new SubstrateLink(63,78);
	    SubstrateLink sl65 = new SubstrateLink(64,52);
	    SubstrateLink sl66 = new SubstrateLink(65,91);
	    SubstrateLink sl67 = new SubstrateLink(66,53);
	    SubstrateLink sl68 = new SubstrateLink(67,100);
	    SubstrateLink sl69 = new SubstrateLink(68,67);
	    SubstrateLink sl70 = new SubstrateLink(69,82);
	    SubstrateLink sl71 = new SubstrateLink(70,82);
	    SubstrateLink sl72 = new SubstrateLink(71,79);
	    SubstrateLink sl73 = new SubstrateLink(72,60);
	    SubstrateLink sl74 = new SubstrateLink(73,51);
	    SubstrateLink sl75 = new SubstrateLink(74,91);
	    SubstrateLink sl76 = new SubstrateLink(75,81);
	    SubstrateLink sl77 = new SubstrateLink(76,74);
	    SubstrateLink sl78 = new SubstrateLink(77,57);
	    SubstrateLink sl79 = new SubstrateLink(78,91);
	    SubstrateLink sl80 = new SubstrateLink(79,95);
	    SubstrateLink sl81 = new SubstrateLink(80,53);
	    SubstrateLink sl82 = new SubstrateLink(81,75);
	    SubstrateLink sl83 = new SubstrateLink(82,52);
	    SubstrateLink sl84 = new SubstrateLink(83,84);
	    SubstrateLink sl85 = new SubstrateLink(84,82);
	    SubstrateLink sl86 = new SubstrateLink(85,52);
	    SubstrateLink sl87 = new SubstrateLink(86,91);
	    SubstrateLink sl88 = new SubstrateLink(87,95);
	    SubstrateLink sl89 = new SubstrateLink(88,60);
	    SubstrateLink sl90 = new SubstrateLink(89,58);
	    SubstrateLink sl91 = new SubstrateLink(90,52);
	    SubstrateLink sl92 = new SubstrateLink(91,80);
	    SubstrateLink sl93 = new SubstrateLink(92,80);
	    SubstrateLink sl94 = new SubstrateLink(93,50);
	    SubstrateLink sl95 = new SubstrateLink(94,60);
	    SubstrateLink sl96 = new SubstrateLink(95,86);
	    SubstrateLink sl97 = new SubstrateLink(96,52);
	    SubstrateLink sl98 = new SubstrateLink(97,61);
	    SubstrateLink sl99 = new SubstrateLink(98,71);
	    SubstrateLink sl100 = new SubstrateLink(99,73);
	    SubstrateLink sl101 = new SubstrateLink(100,88);
	    SubstrateLink sl102 = new SubstrateLink(101,82);
	    SubstrateLink sl103 = new SubstrateLink(102,81);
	    SubstrateLink sl104 = new SubstrateLink(103,93);
	    SubstrateLink sl105 = new SubstrateLink(104,80);
	    SubstrateLink sl106 = new SubstrateLink(105,70);
	    SubstrateLink sl107 = new SubstrateLink(106,82);
	    SubstrateLink sl108 = new SubstrateLink(107,91);
	    SubstrateLink sl109 = new SubstrateLink(108,62);
	    SubstrateLink sl110 = new SubstrateLink(109,76);
	    SubstrateLink sl111 = new SubstrateLink(110,73);
	    SubstrateLink sl112 = new SubstrateLink(111,70);
	    SubstrateLink sl113 = new SubstrateLink(112,74);
	    SubstrateLink sl114 = new SubstrateLink(113,74);
	    SubstrateLink sl115 = new SubstrateLink(114,60);
	    SubstrateLink sl116 = new SubstrateLink(115,60);
	    SubstrateLink sl117 = new SubstrateLink(116,56);
	    SubstrateLink sl118 = new SubstrateLink(117,77);
	    SubstrateLink sl119 = new SubstrateLink(118,54);
	    SubstrateLink sl120 = new SubstrateLink(119,75);
	    SubstrateLink sl121 = new SubstrateLink(120,64);
	    SubstrateLink sl122 = new SubstrateLink(121,52);
	    SubstrateLink sl123 = new SubstrateLink(122,81);
	    SubstrateLink sl124 = new SubstrateLink(123,82);
	    SubstrateLink sl125 = new SubstrateLink(124,82);
	    SubstrateLink sl126 = new SubstrateLink(125,89);
	    SubstrateLink sl127 = new SubstrateLink(126,66);
	    SubstrateLink sl128 = new SubstrateLink(127,56);
	    SubstrateLink sl129 = new SubstrateLink(128,66);
	    SubstrateLink sl130 = new SubstrateLink(129,56);
	    SubstrateLink sl131 = new SubstrateLink(130,57);
	    SubstrateLink sl132 = new SubstrateLink(131,85);
	    SubstrateLink sl133 = new SubstrateLink(132,92);
	    SubstrateLink sl134 = new SubstrateLink(133,84);
	    SubstrateLink sl135 = new SubstrateLink(134,79);
	    SubstrateLink sl136 = new SubstrateLink(135,62);
	    SubstrateLink sl137 = new SubstrateLink(136,59);
	    SubstrateLink sl138 = new SubstrateLink(137,83);
	    SubstrateLink sl139 = new SubstrateLink(138,94);
	    SubstrateLink sl140 = new SubstrateLink(139,94);
	    SubstrateLink sl141 = new SubstrateLink(140,94);
	    SubstrateLink sl142 = new SubstrateLink(141,72);
	    SubstrateLink sl143 = new SubstrateLink(142,72);
	    SubstrateLink sl144 = new SubstrateLink(143,51);
	    SubstrateLink sl145 = new SubstrateLink(144,79);
	    SubstrateLink sl146 = new SubstrateLink(145,75);
	    SubstrateLink sl147 = new SubstrateLink(146,93);
	    SubstrateLink sl148 = new SubstrateLink(147,86);
	    SubstrateLink sl149 = new SubstrateLink(148,53);
	    SubstrateLink sl150 = new SubstrateLink(149,80);
	    SubstrateLink sl151 = new SubstrateLink(150,68);
	    SubstrateLink sl152 = new SubstrateLink(151,80);
	    SubstrateLink sl153 = new SubstrateLink(152,82);
	    SubstrateLink sl154 = new SubstrateLink(153,80);
	    SubstrateLink sl155 = new SubstrateLink(154,75);
	    SubstrateLink sl156 = new SubstrateLink(155,81);
	    SubstrateLink sl157 = new SubstrateLink(156,67);
	    SubstrateLink sl158 = new SubstrateLink(157,77);
	    SubstrateLink sl159 = new SubstrateLink(158,79);
	    SubstrateLink sl160 = new SubstrateLink(159,73);
	    SubstrateLink sl161 = new SubstrateLink(160,76);
	    SubstrateLink sl162 = new SubstrateLink(161,81);
	    SubstrateLink sl163 = new SubstrateLink(162,88);
	    SubstrateLink sl164 = new SubstrateLink(163,56);
	    SubstrateLink sl165 = new SubstrateLink(164,76);
	    SubstrateLink sl166 = new SubstrateLink(165,65);
	    SubstrateLink sl167 = new SubstrateLink(166,94);
	    SubstrateLink sl168 = new SubstrateLink(167,96);
	    SubstrateLink sl169 = new SubstrateLink(168,91);
	    SubstrateLink sl170 = new SubstrateLink(169,97);
	    SubstrateLink sl171 = new SubstrateLink(170,63);
	    SubstrateLink sl172 = new SubstrateLink(171,82);
	    SubstrateLink sl173 = new SubstrateLink(172,57);
	    SubstrateLink sl174 = new SubstrateLink(173,85);
	    SubstrateLink sl175 = new SubstrateLink(174,65);
	    SubstrateLink sl176 = new SubstrateLink(175,77);
	    SubstrateLink sl177 = new SubstrateLink(176,71);
	    SubstrateLink sl178 = new SubstrateLink(177,98);
	    SubstrateLink sl179 = new SubstrateLink(178,87);
	    SubstrateLink sl180 = new SubstrateLink(179,73);
	    SubstrateLink sl181 = new SubstrateLink(180,55);
	    SubstrateLink sl182 = new SubstrateLink(181,97);
	    SubstrateLink sl183 = new SubstrateLink(182,50);
	    SubstrateLink sl184 = new SubstrateLink(183,98);
	    SubstrateLink sl185 = new SubstrateLink(184,84);
	    SubstrateLink sl186 = new SubstrateLink(185,56);
	    SubstrateLink sl187 = new SubstrateLink(186,83);
	    SubstrateLink sl188 = new SubstrateLink(187,77);
	    SubstrateLink sl189 = new SubstrateLink(188,75);
	    SubstrateLink sl190 = new SubstrateLink(189,54);
	    SubstrateLink sl191 = new SubstrateLink(190,53);
	    SubstrateLink sl192 = new SubstrateLink(191,79);
	    SubstrateLink sl193 = new SubstrateLink(192,50);
	    SubstrateLink sl194 = new SubstrateLink(193,55);
	    SubstrateLink sl195 = new SubstrateLink(194,58);
	    SubstrateLink sl196 = new SubstrateLink(195,86);
	    SubstrateLink sl197 = new SubstrateLink(196,92);
	    SubstrateLink sl198 = new SubstrateLink(197,62);
	    SubstrateLink sl199 = new SubstrateLink(198,89);
	    SubstrateLink sl200 = new SubstrateLink(199,76);
	    SubstrateLink sl201 = new SubstrateLink(200,60);
	    SubstrateLink sl202 = new SubstrateLink(201,96);
	    SubstrateLink sl203 = new SubstrateLink(202,51);
	    SubstrateLink sl204 = new SubstrateLink(203,53);
	    SubstrateLink sl205 = new SubstrateLink(204,51);
	    SubstrateLink sl206 = new SubstrateLink(205,95);
	    SubstrateLink sl207 = new SubstrateLink(206,91);
	    SubstrateLink sl208 = new SubstrateLink(207,62);
	    SubstrateLink sl209 = new SubstrateLink(208,68);
	    SubstrateLink sl210 = new SubstrateLink(209,52);
	    SubstrateLink sl211 = new SubstrateLink(210,100);
	    SubstrateLink sl212 = new SubstrateLink(211,80);
	    SubstrateLink sl213 = new SubstrateLink(212,54);
	    SubstrateLink sl214 = new SubstrateLink(213,54);
	    SubstrateLink sl215 = new SubstrateLink(214,60);
	    SubstrateLink sl216 = new SubstrateLink(215,91);
	    SubstrateLink sl217 = new SubstrateLink(216,50);
	    SubstrateLink sl218 = new SubstrateLink(217,99);
	    SubstrateLink sl219 = new SubstrateLink(218,92);
	    SubstrateLink sl220 = new SubstrateLink(219,76);
	    SubstrateLink sl221 = new SubstrateLink(220,59);
	    SubstrateLink sl222 = new SubstrateLink(221,81);
	    SubstrateLink sl223 = new SubstrateLink(222,53);
	    SubstrateLink sl224 = new SubstrateLink(223,66);
	    SubstrateLink sl225 = new SubstrateLink(224,82);
	    SubstrateLink sl226 = new SubstrateLink(225,65);
	    SubstrateLink sl227 = new SubstrateLink(226,51);
	    SubstrateLink sl228 = new SubstrateLink(227,95);
	    SubstrateLink sl229 = new SubstrateLink(228,93);
	    SubstrateLink sl230 = new SubstrateLink(229,87);
	    SubstrateLink sl231 = new SubstrateLink(230,81);
	    SubstrateLink sl232 = new SubstrateLink(231,56);
	    SubstrateLink sl233 = new SubstrateLink(232,62);
	    SubstrateLink sl234 = new SubstrateLink(233,89);
	    SubstrateLink sl235 = new SubstrateLink(234,77);
	    SubstrateLink sl236 = new SubstrateLink(235,60);
	    SubstrateLink sl237 = new SubstrateLink(236,71);
	    SubstrateLink sl238 = new SubstrateLink(237,87);
	    SubstrateLink sl239 = new SubstrateLink(238,86);
	    SubstrateLink sl240 = new SubstrateLink(239,57);
	    SubstrateLink sl241 = new SubstrateLink(240,54);
	    SubstrateLink sl242 = new SubstrateLink(241,86);
	    SubstrateLink sl243 = new SubstrateLink(242,72);
	    SubstrateLink sl244 = new SubstrateLink(243,68);
	    SubstrateLink sl245 = new SubstrateLink(244,74);
	    SubstrateLink sl246 = new SubstrateLink(245,99);
	    SubstrateLink sl247 = new SubstrateLink(246,54);
	    SubstrateLink sl248 = new SubstrateLink(247,99);
	    SubstrateLink sl249 = new SubstrateLink(248,100);
	    SubstrateLink sl250 = new SubstrateLink(249,71);
	    SubstrateLink sl251 = new SubstrateLink(250,74);
	    SubstrateLink sl252 = new SubstrateLink(251,96);
	    SubstrateLink sl253 = new SubstrateLink(252,79);
	    SubstrateLink sl254 = new SubstrateLink(253,65);
	    SubstrateLink sl255 = new SubstrateLink(254,53);
	    SubstrateLink sl256 = new SubstrateLink(255,92);
	    SubstrateLink sl257 = new SubstrateLink(256,99);
	    SubstrateLink sl258 = new SubstrateLink(257,58);
	    SubstrateLink sl259 = new SubstrateLink(258,68);
	    SubstrateLink sl260 = new SubstrateLink(259,89);
	    SubstrateLink sl261 = new SubstrateLink(260,64);
	    SubstrateLink sl262 = new SubstrateLink(261,62);
	    SubstrateLink sl263 = new SubstrateLink(262,63);
	    SubstrateLink sl264 = new SubstrateLink(263,79);
	    SubstrateLink sl265 = new SubstrateLink(264,89);
	    SubstrateLink sl266 = new SubstrateLink(265,67);
	    SubstrateLink sl267 = new SubstrateLink(266,81);
	    SubstrateLink sl268 = new SubstrateLink(267,79);
	    SubstrateLink sl269 = new SubstrateLink(268,53);
	    SubstrateLink sl270 = new SubstrateLink(269,93);
	    SubstrateLink sl271 = new SubstrateLink(270,86);
	    SubstrateLink sl272 = new SubstrateLink(271,55);
	    SubstrateLink sl273 = new SubstrateLink(272,91);
	    SubstrateLink sl274 = new SubstrateLink(273,92);
	    SubstrateLink sl275 = new SubstrateLink(274,78);
	    SubstrateLink sl276 = new SubstrateLink(275,70);
	    SubstrateLink sl277 = new SubstrateLink(276,91);
	    SubstrateLink sl278 = new SubstrateLink(277,77);
	    SubstrateLink sl279 = new SubstrateLink(278,68);
	    SubstrateLink sl280 = new SubstrateLink(279,55);
	    SubstrateLink sl281 = new SubstrateLink(280,81);
	    SubstrateLink sl282 = new SubstrateLink(281,57);
	    SubstrateLink sl283 = new SubstrateLink(282,59);
	    SubstrateLink sl284 = new SubstrateLink(283,88);
	    SubstrateLink sl285 = new SubstrateLink(284,52);
	    SubstrateLink sl286 = new SubstrateLink(285,59);
	    SubstrateLink sl287 = new SubstrateLink(286,89);
	    SubstrateLink sl288 = new SubstrateLink(287,96);
	    SubstrateLink sl289 = new SubstrateLink(288,76);
	    SubstrateLink sl290 = new SubstrateLink(289,69);
	    SubstrateLink sl291 = new SubstrateLink(290,73);
	    SubstrateLink sl292 = new SubstrateLink(291,85);
	    SubstrateLink sl293 = new SubstrateLink(292,97);
	    SubstrateLink sl294 = new SubstrateLink(293,61);
	    SubstrateLink sl295 = new SubstrateLink(294,85);
	    SubstrateLink sl296 = new SubstrateLink(295,95);
	    SubstrateLink sl297 = new SubstrateLink(296,70);
	    SubstrateLink sl298 = new SubstrateLink(297,72);
	    SubstrateLink sl299 = new SubstrateLink(298,60);
	    SubstrateLink sl300 = new SubstrateLink(299,81);	    
	    SubstrateLink sl301 = new SubstrateLink(300,88);
	    SubstrateLink sl302 = new SubstrateLink(301,81);
	    SubstrateLink sl303 = new SubstrateLink(302,85);
	    SubstrateLink sl304 = new SubstrateLink(303,79);
	    SubstrateLink sl305 = new SubstrateLink(304,90);
	    SubstrateLink sl306 = new SubstrateLink(305,88);
	    SubstrateLink sl307 = new SubstrateLink(306,62);
	    SubstrateLink sl308 = new SubstrateLink(307,69);
	    SubstrateLink sl309 = new SubstrateLink(308,73);
	    SubstrateLink sl310 = new SubstrateLink(309,89);
	    SubstrateLink sl311 = new SubstrateLink(310,53);
	    SubstrateLink sl312 = new SubstrateLink(311,77);
	    SubstrateLink sl313 = new SubstrateLink(312,74);
	    SubstrateLink sl314 = new SubstrateLink(313,79);
	    SubstrateLink sl315 = new SubstrateLink(314,55);
	    SubstrateLink sl316 = new SubstrateLink(315,70);
	    SubstrateLink sl317 = new SubstrateLink(316,73);
	    SubstrateLink sl318 = new SubstrateLink(317,52);
	    SubstrateLink sl319 = new SubstrateLink(318,80);
	    SubstrateLink sl320 = new SubstrateLink(319,91);
	    SubstrateLink sl321 = new SubstrateLink(320,72);
	    SubstrateLink sl322 = new SubstrateLink(321,93);
	    SubstrateLink sl323 = new SubstrateLink(322,51);
	    SubstrateLink sl324 = new SubstrateLink(323,93);
	    SubstrateLink sl325 = new SubstrateLink(324,87);
	    SubstrateLink sl326 = new SubstrateLink(325,64);
	    SubstrateLink sl327 = new SubstrateLink(326,99);
	    SubstrateLink sl328 = new SubstrateLink(327,54);
	    SubstrateLink sl329 = new SubstrateLink(328,89);
	    SubstrateLink sl330 = new SubstrateLink(329,80);
	    SubstrateLink sl331 = new SubstrateLink(330,99);
	    SubstrateLink sl332 = new SubstrateLink(331,77);
	    SubstrateLink sl333 = new SubstrateLink(332,92);
	    SubstrateLink sl334 = new SubstrateLink(333,69);
	    SubstrateLink sl335 = new SubstrateLink(334,58);
	    SubstrateLink sl336 = new SubstrateLink(335,65);
	    SubstrateLink sl337 = new SubstrateLink(336,52);
	    SubstrateLink sl338 = new SubstrateLink(337,95);
	    SubstrateLink sl339 = new SubstrateLink(338,64);
	    SubstrateLink sl340 = new SubstrateLink(339,91);
	    SubstrateLink sl341 = new SubstrateLink(340,83);
	    SubstrateLink sl342 = new SubstrateLink(341,100);
	    SubstrateLink sl343 = new SubstrateLink(342,79);
	    SubstrateLink sl344 = new SubstrateLink(343,50);
	    SubstrateLink sl345 = new SubstrateLink(344,55);
	    SubstrateLink sl346 = new SubstrateLink(345,91);
	    SubstrateLink sl347 = new SubstrateLink(346,97);
	    SubstrateLink sl348 = new SubstrateLink(347,70);
	    SubstrateLink sl349 = new SubstrateLink(348,90);
	    SubstrateLink sl350 = new SubstrateLink(349,95);
	    SubstrateLink sl351 = new SubstrateLink(350,54);
	    SubstrateLink sl352 = new SubstrateLink(351,53);
	    SubstrateLink sl353 = new SubstrateLink(352,69);
	    SubstrateLink sl354 = new SubstrateLink(353,78);
	    SubstrateLink sl355 = new SubstrateLink(354,74);
	    SubstrateLink sl356 = new SubstrateLink(355,67);
	    SubstrateLink sl357 = new SubstrateLink(356,90);
	    SubstrateLink sl358 = new SubstrateLink(357,78);
	    SubstrateLink sl359 = new SubstrateLink(358,69);
	    SubstrateLink sl360 = new SubstrateLink(359,60);
	    SubstrateLink sl361 = new SubstrateLink(360,98);
	    SubstrateLink sl362 = new SubstrateLink(361,96);
	    SubstrateLink sl363 = new SubstrateLink(362,65);
	    SubstrateLink sl364 = new SubstrateLink(363,68);
	    SubstrateLink sl365 = new SubstrateLink(364,93);
	    SubstrateLink sl366 = new SubstrateLink(365,95);
	    SubstrateLink sl367 = new SubstrateLink(366,100);
	    SubstrateLink sl368 = new SubstrateLink(367,72);
	    SubstrateLink sl369 = new SubstrateLink(368,66);
	    SubstrateLink sl370 = new SubstrateLink(369,92);
	    SubstrateLink sl371 = new SubstrateLink(370,85);
	    SubstrateLink sl372 = new SubstrateLink(371,60);
	    SubstrateLink sl373 = new SubstrateLink(372,88);
	    SubstrateLink sl374 = new SubstrateLink(373,64);
	    SubstrateLink sl375 = new SubstrateLink(374,96);
	    SubstrateLink sl376 = new SubstrateLink(375,95);
	    SubstrateLink sl377 = new SubstrateLink(376,75);
	    SubstrateLink sl378 = new SubstrateLink(377,76);
	    SubstrateLink sl379 = new SubstrateLink(378,91);
	    SubstrateLink sl380 = new SubstrateLink(379,90);
	    SubstrateLink sl381 = new SubstrateLink(380,96);
	    SubstrateLink sl382 = new SubstrateLink(381,70);
	    SubstrateLink sl383 = new SubstrateLink(382,96);
	    SubstrateLink sl384 = new SubstrateLink(383,91);
	    SubstrateLink sl385 = new SubstrateLink(384,77);
	    SubstrateLink sl386 = new SubstrateLink(385,84);
	    SubstrateLink sl387 = new SubstrateLink(386,83);
	    SubstrateLink sl388 = new SubstrateLink(387,71);
	    SubstrateLink sl389 = new SubstrateLink(388,92);
	    SubstrateLink sl390 = new SubstrateLink(389,65);
	    SubstrateLink sl391 = new SubstrateLink(390,92);
	    SubstrateLink sl392 = new SubstrateLink(391,75);
	    SubstrateLink sl393 = new SubstrateLink(392,53);
	    SubstrateLink sl394 = new SubstrateLink(393,62);
	    SubstrateLink sl395 = new SubstrateLink(394,75);
	    SubstrateLink sl396 = new SubstrateLink(395,59);
	    SubstrateLink sl397 = new SubstrateLink(396,77);
	    SubstrateLink sl398 = new SubstrateLink(397,100);
	    SubstrateLink sl399 = new SubstrateLink(398,68);
	    SubstrateLink sl400 = new SubstrateLink(399,97);
	    SubstrateLink sl401 = new SubstrateLink(400,71);
	    SubstrateLink sl402 = new SubstrateLink(401,69);
	    SubstrateLink sl403 = new SubstrateLink(402,80);
	    SubstrateLink sl404 = new SubstrateLink(403,67);
	    SubstrateLink sl405 = new SubstrateLink(404,88);
	    SubstrateLink sl406 = new SubstrateLink(405,66);
	    SubstrateLink sl407 = new SubstrateLink(406,62);
	    SubstrateLink sl408 = new SubstrateLink(407,73);
	    SubstrateLink sl409 = new SubstrateLink(408,97);
	    SubstrateLink sl410 = new SubstrateLink(409,64);
	    SubstrateLink sl411 = new SubstrateLink(410,90);
	    SubstrateLink sl412 = new SubstrateLink(411,68);
	    SubstrateLink sl413 = new SubstrateLink(412,86);
	    SubstrateLink sl414 = new SubstrateLink(413,89);
	    SubstrateLink sl415 = new SubstrateLink(414,93);
	    SubstrateLink sl416 = new SubstrateLink(415,56);
	    SubstrateLink sl417 = new SubstrateLink(416,53);
	    SubstrateLink sl418 = new SubstrateLink(417,92);
	    SubstrateLink sl419 = new SubstrateLink(418,83);
	    SubstrateLink sl420 = new SubstrateLink(419,93);
	    SubstrateLink sl421 = new SubstrateLink(420,91);
	    SubstrateLink sl422 = new SubstrateLink(421,89);
	    SubstrateLink sl423 = new SubstrateLink(422,74);
	    SubstrateLink sl424 = new SubstrateLink(423,100);
	    SubstrateLink sl425 = new SubstrateLink(424,65);
	    SubstrateLink sl426 = new SubstrateLink(425,77);
	    SubstrateLink sl427 = new SubstrateLink(426,60);
	    SubstrateLink sl428 = new SubstrateLink(427,86);
	    SubstrateLink sl429 = new SubstrateLink(428,50);
	    SubstrateLink sl430 = new SubstrateLink(429,76);
	    SubstrateLink sl431 = new SubstrateLink(430,51);
	    SubstrateLink sl432 = new SubstrateLink(431,98);
	    SubstrateLink sl433 = new SubstrateLink(432,76);
	    SubstrateLink sl434 = new SubstrateLink(433,59);
	    SubstrateLink sl435 = new SubstrateLink(434,54);
	    SubstrateLink sl436 = new SubstrateLink(435,100);
	    SubstrateLink sl437 = new SubstrateLink(436,54);
	    SubstrateLink sl438 = new SubstrateLink(437,63);
	    SubstrateLink sl439 = new SubstrateLink(438,89);
	    SubstrateLink sl440 = new SubstrateLink(439,84);
	    SubstrateLink sl441 = new SubstrateLink(440,99);
	    SubstrateLink sl442 = new SubstrateLink(441,56);
	    SubstrateLink sl443 = new SubstrateLink(442,62);
	    SubstrateLink sl444 = new SubstrateLink(443,72);
	    SubstrateLink sl445 = new SubstrateLink(444,69);
	    SubstrateLink sl446 = new SubstrateLink(445,90);
	    SubstrateLink sl447 = new SubstrateLink(446,61);
	    SubstrateLink sl448 = new SubstrateLink(447,89);
	    SubstrateLink sl449 = new SubstrateLink(448,86);
	    SubstrateLink sl450 = new SubstrateLink(449,90);
	    SubstrateLink sl451 = new SubstrateLink(450,98);
	    SubstrateLink sl452 = new SubstrateLink(451,90);
	    SubstrateLink sl453 = new SubstrateLink(452,94);
	    SubstrateLink sl454 = new SubstrateLink(453,69);
	    SubstrateLink sl455 = new SubstrateLink(454,86);
	    SubstrateLink sl456 = new SubstrateLink(455,62);
	    SubstrateLink sl457 = new SubstrateLink(456,99);
	    SubstrateLink sl458 = new SubstrateLink(457,77);
	    SubstrateLink sl459 = new SubstrateLink(458,51);
	    SubstrateLink sl460 = new SubstrateLink(459,53);
	    SubstrateLink sl461 = new SubstrateLink(460,60);
	    SubstrateLink sl462 = new SubstrateLink(461,74);
	    SubstrateLink sl463 = new SubstrateLink(462,88);
	    SubstrateLink sl464 = new SubstrateLink(463,92);
	    SubstrateLink sl465 = new SubstrateLink(464,53);
	    SubstrateLink sl466 = new SubstrateLink(465,67);
	    SubstrateLink sl467 = new SubstrateLink(466,82);
	    SubstrateLink sl468 = new SubstrateLink(467,82);
	    SubstrateLink sl469 = new SubstrateLink(468,78);
	    SubstrateLink sl470 = new SubstrateLink(469,52);
	    SubstrateLink sl471 = new SubstrateLink(470,79);
	    SubstrateLink sl472 = new SubstrateLink(471,99);
	    SubstrateLink sl473 = new SubstrateLink(472,93);
	    SubstrateLink sl474 = new SubstrateLink(473,61);
	    SubstrateLink sl475 = new SubstrateLink(474,92);
	    SubstrateLink sl476 = new SubstrateLink(475,87);
	    SubstrateLink sl477 = new SubstrateLink(476,52);
	    SubstrateLink sl478 = new SubstrateLink(477,55);
	    SubstrateLink sl479 = new SubstrateLink(478,72);
	    SubstrateLink sl480 = new SubstrateLink(479,59);
	    SubstrateLink sl481 = new SubstrateLink(480,81);
	    SubstrateLink sl482 = new SubstrateLink(481,63);
	    SubstrateLink sl483 = new SubstrateLink(482,91);
	    SubstrateLink sl484 = new SubstrateLink(483,82);
	    SubstrateLink sl485 = new SubstrateLink(484,69);
	    SubstrateLink sl486 = new SubstrateLink(485,50);
	    SubstrateLink sl487 = new SubstrateLink(486,62);
	    SubstrateLink sl488 = new SubstrateLink(487,67);
	    SubstrateLink sl489 = new SubstrateLink(488,53);
	    SubstrateLink sl490 = new SubstrateLink(489,83);
	    SubstrateLink sl491 = new SubstrateLink(490,80);
	    SubstrateLink sl492 = new SubstrateLink(491,92);
	    SubstrateLink sl493 = new SubstrateLink(492,82);
	    SubstrateLink sl494 = new SubstrateLink(493,78);
	    SubstrateLink sl495 = new SubstrateLink(494,51);
	    SubstrateLink sl496 = new SubstrateLink(495,55);
	    SubstrateLink sl497 = new SubstrateLink(496,86);
	    SubstrateLink sl498 = new SubstrateLink(497,70);
	    SubstrateLink sl499 = new SubstrateLink(498,72);
	    SubstrateLink sl500 = new SubstrateLink(499,84);
	    SubstrateLink sl501 = new SubstrateLink(500,86);
	    SubstrateLink sl502 = new SubstrateLink(501,96);
	    SubstrateLink sl503 = new SubstrateLink(502,55);
	    SubstrateLink sl504 = new SubstrateLink(503,68);
	    SubstrateLink sl505 = new SubstrateLink(504,97);
	    SubstrateLink sl506 = new SubstrateLink(505,51);
	    SubstrateLink sl507 = new SubstrateLink(506,70);
	    SubstrateLink sl508 = new SubstrateLink(507,75);
	    SubstrateLink sl509 = new SubstrateLink(508,79);
	    SubstrateLink sl510 = new SubstrateLink(509,89);
	    SubstrateLink sl511 = new SubstrateLink(510,92);
	    SubstrateLink sl512 = new SubstrateLink(511,77);
	    SubstrateLink sl513 = new SubstrateLink(512,72);
	    SubstrateLink sl514 = new SubstrateLink(513,97);
	    SubstrateLink sl515 = new SubstrateLink(514,95);
	    SubstrateLink sl516 = new SubstrateLink(515,67);
	    SubstrateLink sl517 = new SubstrateLink(516,68);
	    SubstrateLink sl518 = new SubstrateLink(517,82);
	    SubstrateLink sl519 = new SubstrateLink(518,78);
	    SubstrateLink sl520 = new SubstrateLink(519,56);
	    SubstrateLink sl521 = new SubstrateLink(520,90);
	    SubstrateLink sl522 = new SubstrateLink(521,58);
	    SubstrateLink sl523 = new SubstrateLink(522,83);
	    SubstrateLink sl524 = new SubstrateLink(523,67);
	    SubstrateLink sl525 = new SubstrateLink(524,64);
	    SubstrateLink sl526 = new SubstrateLink(525,85);
	    SubstrateLink sl527 = new SubstrateLink(526,53);
	    SubstrateLink sl528 = new SubstrateLink(527,91);
	    SubstrateLink sl529 = new SubstrateLink(528,75);
	    SubstrateLink sl530 = new SubstrateLink(529,78);
	    SubstrateLink sl531 = new SubstrateLink(530,64);
	    SubstrateLink sl532 = new SubstrateLink(531,86);
	    SubstrateLink sl533 = new SubstrateLink(532,64);
	    SubstrateLink sl534 = new SubstrateLink(533,53);
	    SubstrateLink sl535 = new SubstrateLink(534,76);
	    SubstrateLink sl536 = new SubstrateLink(535,98);
	    SubstrateLink sl537 = new SubstrateLink(536,78);
	    SubstrateLink sl538 = new SubstrateLink(537,72);
	    SubstrateLink sl539 = new SubstrateLink(538,73);
	    SubstrateLink sl540 = new SubstrateLink(539,51);
	    SubstrateLink sl541 = new SubstrateLink(540,81);
	    SubstrateLink sl542 = new SubstrateLink(541,56);
	    SubstrateLink sl543 = new SubstrateLink(542,88);
	    SubstrateLink sl544 = new SubstrateLink(543,69);
	    SubstrateLink sl545 = new SubstrateLink(544,88);
	    SubstrateLink sl546 = new SubstrateLink(545,93);
	    SubstrateLink sl547 = new SubstrateLink(546,52);
	    SubstrateLink sl548 = new SubstrateLink(547,94);
	    SubstrateLink sl549 = new SubstrateLink(548,62);
	    SubstrateLink sl550 = new SubstrateLink(549,60);
	    SubstrateLink sl551 = new SubstrateLink(550,76);
	    SubstrateLink sl552 = new SubstrateLink(551,90);
	    SubstrateLink sl553 = new SubstrateLink(552,81);
	    SubstrateLink sl554 = new SubstrateLink(553,77);
	    SubstrateLink sl555 = new SubstrateLink(554,80);
	    SubstrateLink sl556 = new SubstrateLink(555,60);
	    SubstrateLink sl557 = new SubstrateLink(556,91);
	    SubstrateLink sl558 = new SubstrateLink(557,53);
	    SubstrateLink sl559 = new SubstrateLink(558,50);
	    SubstrateLink sl560 = new SubstrateLink(559,53);
	    SubstrateLink sl561 = new SubstrateLink(560,69);
	    SubstrateLink sl562 = new SubstrateLink(561,55);
	    SubstrateLink sl563 = new SubstrateLink(562,75);
	    SubstrateLink sl564 = new SubstrateLink(563,61);
	    SubstrateLink sl565 = new SubstrateLink(564,50);
	    SubstrateLink sl566 = new SubstrateLink(565,87);
	    SubstrateLink sl567 = new SubstrateLink(566,82);
	    SubstrateLink sl568 = new SubstrateLink(567,70);
	    SubstrateLink sl569 = new SubstrateLink(568,54);
	    SubstrateLink sl570 = new SubstrateLink(569,71);
	    SubstrateLink sl571 = new SubstrateLink(570,83);
	    SubstrateLink sl572 = new SubstrateLink(571,60);
	    SubstrateLink sl573 = new SubstrateLink(572,100);
	    SubstrateLink sl574 = new SubstrateLink(573,89);
	    SubstrateLink sl575 = new SubstrateLink(574,100);
	    SubstrateLink sl576 = new SubstrateLink(575,57);
	    SubstrateLink sl577 = new SubstrateLink(576,80);
	    SubstrateLink sl578 = new SubstrateLink(577,51);
	    SubstrateLink sl579 = new SubstrateLink(578,82);
	    SubstrateLink sl580 = new SubstrateLink(579,93);
	    SubstrateLink sl581 = new SubstrateLink(580,77);
	    SubstrateLink sl582 = new SubstrateLink(581,90);
	    SubstrateLink sl583 = new SubstrateLink(582,66);
	    SubstrateLink sl584 = new SubstrateLink(583,77);
	    SubstrateLink sl585 = new SubstrateLink(584,68);
	    SubstrateLink sl586 = new SubstrateLink(585,56);
	    SubstrateLink sl587 = new SubstrateLink(586,100);
	    SubstrateLink sl588 = new SubstrateLink(587,55);
	    SubstrateLink sl589 = new SubstrateLink(588,53);
	    SubstrateLink sl590 = new SubstrateLink(589,64);
	    SubstrateLink sl591 = new SubstrateLink(590,68);
	    SubstrateLink sl592 = new SubstrateLink(591,96);
	    SubstrateLink sl593 = new SubstrateLink(592,100);
	    SubstrateLink sl594 = new SubstrateLink(593,60);
	    SubstrateLink sl595 = new SubstrateLink(594,60);
	    SubstrateLink sl596 = new SubstrateLink(595,67);
	    SubstrateLink sl597 = new SubstrateLink(596,78);
	    SubstrateLink sl598 = new SubstrateLink(597,70);
	    SubstrateLink sl599 = new SubstrateLink(598,93);
	    SubstrateLink sl600 = new SubstrateLink(599,99);
	    SubstrateLink sl601 = new SubstrateLink(600,58);
	    SubstrateLink sl602 = new SubstrateLink(601,85);
	    SubstrateLink sl603 = new SubstrateLink(602,78);
	    SubstrateLink sl604 = new SubstrateLink(603,76);
	    SubstrateLink sl605 = new SubstrateLink(604,61);
	    SubstrateLink sl606 = new SubstrateLink(605,94);
	    SubstrateLink sl607 = new SubstrateLink(606,62);
	    SubstrateLink sl608 = new SubstrateLink(607,60);
	    SubstrateLink sl609 = new SubstrateLink(608,71);
	    SubstrateLink sl610 = new SubstrateLink(609,62);
	    SubstrateLink sl611 = new SubstrateLink(610,86);
	    SubstrateLink sl612 = new SubstrateLink(611,85);
	    SubstrateLink sl613 = new SubstrateLink(612,59);
	    
	    ((SubstrateNodeFactory) substrate.getNodeFactory()).setNodeCount(50);
	    ((SubstrateLinkFactory) substrate.getLinkFactory()).setLinkCount(613);

	    // Adding links
	    substrate.getGraph().addEdge(sl1,server0, server1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl2,server0, server2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl3,server0, server3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl4,server0, server4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl5,server0, server5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl6,server0, server6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl7,server0, server7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl8,server0, server8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl9,server0, server9, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl10,server0, server10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl11,server0, server11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl12,server0, server12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl13,server0, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl14,server0, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl15,server0, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl16,server0, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl17,server0, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl18,server0, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl19,server0, sr2, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl20,server0, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl21,server0, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl22,server0, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl23,server0, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl24,server0, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl25,server0, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl26,server1, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl27,server1, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl28,server1, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl29,server1, sr12, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl30,server1, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl31,server1, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl32,server1, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl33,server1, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl34,server1, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl35,server1, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl36,server1, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl37,server1, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl38,server1, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl39,server1, ss6, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl40,server1, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl41,server1, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl42,server1, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl43,server1, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl44,server1, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl45,server1, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl46,server1, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl47,server1, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl48,server1, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl49,server1, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl50,server2, server3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl51,server2, server4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl52,server2, server5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl53,server2, server6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl54,server2, server7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl55,server2, server8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl56,server2, server9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl57,server2, server10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl58,server2, server11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl59,server2, server12, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl60,server2, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl61,server2, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl62,server2, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl63,server2, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl64,server2, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl65,server2, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl66,server2, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl67,server2, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl68,server2, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl69,server2, sr5, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl70,server2, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl71,server2, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl72,server2, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl73,server2, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl74,server2, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl75,server3, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl76,server3, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl77,server3, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl78,server3, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl79,server3, sr13, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl80,server3, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl81,server3, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl82,server3, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl83,server3, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl84,server3, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl85,server3, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl86,server3, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl87,server3, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl88,server3, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl89,server3, ss7, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl90,server3, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl91,server3, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl92,server3, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl93,server3, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl94,server3, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl95,server3, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl96,server3, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl97,server3, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl98,server3, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl99,server4, server5, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl100,server4, server6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl101,server4, server7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl102,server4, server8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl103,server4, server9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl104,server4, server10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl105,server4, server11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl106,server4, server12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl107,server4, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl108,server4, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl109,server4, server15, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl110,server4, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl111,server4, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl112,server4, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl113,server4, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl114,server4, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl115,server4, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl116,server4, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl117,server4, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl118,server4, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl119,server4, sr8, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl120,server4, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl121,server4, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl122,server4, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl123,server4, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl124,server5, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl125,server5, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl126,server5, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl127,server5, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl128,server5, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl129,server5, sr14, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl130,server5, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl131,server5, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl132,server5, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl133,server5, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl134,server5, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl135,server5, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl136,server5, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl137,server5, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl138,server5, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl139,server5, ss8, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl140,server5, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl141,server5, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl142,server5, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl143,server5, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl144,server5, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl145,server5, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl146,server5, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl147,server5, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl148,server6, server7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl149,server6, server8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl150,server6, server9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl151,server6, server10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl152,server6, server11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl153,server6, server12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl154,server6, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl155,server6, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl156,server6, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl157,server6, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl158,server6, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl159,server6, sr1, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl160,server6, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl161,server6, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl162,server6, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl163,server6, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl164,server6, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl165,server6, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl166,server6, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl167,server6, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl168,server6, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl169,server6, sr11, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl170,server6, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl171,server6, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl172,server6, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl173,server7, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl174,server7, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl175,server7, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl176,server7, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl177,server7, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl178,server7, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl179,server7, sr15, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl180,server7, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl181,server7, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl182,server7, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl183,server7, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl184,server7, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl185,server7, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl186,server7, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl187,server7, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl188,server7, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl189,server7, ss9, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl190,server7, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl191,server7, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl192,server7, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl193,server7, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl194,server7, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl195,server7, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl196,server7, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl197,server8, server9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl198,server8, server10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl199,server8, server11, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl200,server8, server12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl201,server8, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl202,server8, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl203,server8, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl204,server8, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl205,server8, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl206,server8, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl207,server8, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl208,server8, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl209,server8, sr4, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl210,server8, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl211,server8, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl212,server8, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl213,server8, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl214,server8, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl215,server8, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl216,server8, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl217,server8, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl218,server8, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl219,server8, sr14, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl220,server8, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl221,server8, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl222,server9, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl223,server9, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl224,server9, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl225,server9, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl226,server9, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl227,server9, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl228,server9, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl229,server9, sr16, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl230,server9, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl231,server9, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl232,server9, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl233,server9, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl234,server9, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl235,server9, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl236,server9, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl237,server9, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl238,server9, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl239,server9, ss10, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl240,server9, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl241,server9, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl242,server9, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl243,server9, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl244,server9, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl245,server9, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl246,server10, server11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl247,server10, server12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl248,server10, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl249,server10, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl250,server10, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl251,server10, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl252,server10, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl253,server10, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl254,server10, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl255,server10, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl256,server10, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl257,server10, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl258,server10, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl259,server10, sr7, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl260,server10, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl261,server10, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl262,server10, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl263,server10, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl264,server10, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl265,server10, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl266,server10, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl267,server10, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl268,server10, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl269,server10, ss1, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl270,server10, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl271,server11, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl272,server11, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl273,server11, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl274,server11, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl275,server11, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl276,server11, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl277,server11, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl278,server11, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl279,server11, ss1, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl280,server11, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl281,server11, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl282,server11, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl283,server11, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl284,server11, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl285,server11, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl286,server11, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl287,server11, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl288,server11, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl289,server11, ss11, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl290,server11, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl291,server11, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl292,server11, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl293,server11, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl294,server11, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl295,server12, server13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl296,server12, server14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl297,server12, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl298,server12, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl299,server12, server17, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl300,server12, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl301,server12, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl302,server12, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl303,server12, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl304,server12, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl305,server12, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl306,server12, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl307,server12, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl308,server12, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl309,server12, sr10, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl310,server12, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl311,server12, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl312,server12, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl313,server12, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl314,server12, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl315,server12, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl316,server12, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl317,server12, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl318,server12, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl319,server12, ss4, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl320,server13, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl321,server13, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl322,server13, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl323,server13, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl324,server13, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl325,server13, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl326,server13, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl327,server13, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl328,server13, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl329,server13, ss2, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl330,server13, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl331,server13, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl332,server13, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl333,server13, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl334,server13, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl335,server13, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl336,server13, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl337,server13, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl338,server13, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl339,server13, ss12, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl340,server13, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl341,server13, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl342,server13, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl343,server13, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl344,server14, server15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl345,server14, server16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl346,server14, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl347,server14, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl348,server14, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl349,server14, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl350,server14, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl351,server14, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl352,server14, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl353,server14, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl354,server14, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl355,server14, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl356,server14, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl357,server14, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl358,server14, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl359,server14, sr13, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl360,server14, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl361,server14, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl362,server14, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl363,server14, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl364,server14, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl365,server14, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl366,server14, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl367,server14, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl368,server14, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl369,server15, sr9, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl370,server15, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl371,server15, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl372,server15, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl373,server15, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl374,server15, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl375,server15, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl376,server15, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl377,server15, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl378,server15, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl379,server15, ss3, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl380,server15, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl381,server15, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl382,server15, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl383,server15, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl384,server15, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl385,server15, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl386,server15, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl387,server15, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl388,server15, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl389,server15, ss13, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl390,server15, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl391,server15, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl392,server15, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl393,server16, server17, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl394,server16, sr1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl395,server16, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl396,server16, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl397,server16, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl398,server16, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl399,server16, sr6, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl400,server16, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl401,server16, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl402,server16, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl403,server16, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl404,server16, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl405,server16, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl406,server16, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl407,server16, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl408,server16, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl409,server16, sr16, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl410,server16, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl411,server16, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl412,server16, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl413,server16, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl414,server16, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl415,server16, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl416,server16, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl417,server16, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl418,server17, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl419,server17, sr10, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl420,server17, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl421,server17, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl422,server17, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl423,server17, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl424,server17, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl425,server17, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl426,server17, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl427,server17, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl428,server17, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl429,server17, ss4, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl430,server17, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl431,server17, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl432,server17, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl433,server17, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl434,server17, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl435,server17, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl436,server17, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl437,server17, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl438,server17, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl439,server17, ss14, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl440,server17, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl441,server17, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl442,sr1, sr2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl443,sr1, sr3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl444,sr1, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl445,sr1, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl446,sr1, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl447,sr1, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl448,sr1, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl449,sr1, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl450,sr1, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl451,sr1, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl452,sr1, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl453,sr1, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl454,sr1, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl455,sr1, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl456,sr1, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl457,sr1, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl458,sr1, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl459,sr1, ss3, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl460,sr1, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl461,sr1, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl462,sr1, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl463,sr1, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl464,sr1, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl465,sr1, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl466,sr1, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl467,sr2, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl468,sr2, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl469,sr2, sr11, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl470,sr2, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl471,sr2, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl472,sr2, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl473,sr2, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl474,sr2, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl475,sr2, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl476,sr2, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl477,sr2, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl478,sr2, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl479,sr2, ss5, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl480,sr2, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl481,sr2, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl482,sr2, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl483,sr2, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl484,sr2, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl485,sr2, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl486,sr2, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl487,sr2, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl488,sr2, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl489,sr2, ss15, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl490,sr2, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl491,sr3, sr4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl492,sr3, sr5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl493,sr3, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl494,sr3, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl495,sr3, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl496,sr3, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl497,sr3, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl498,sr3, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl499,sr3, sr12, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl500,sr3, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl501,sr3, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl502,sr3, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl503,sr3, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl504,sr3, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl505,sr3, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl506,sr3, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl507,sr3, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl508,sr3, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl509,sr3, ss6, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl510,sr3, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl511,sr3, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl512,sr3, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl513,sr3, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl514,sr3, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl515,sr3, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl516,sr4, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl517,sr4, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl518,sr4, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl519,sr4, sr12, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl520,sr4, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl521,sr4, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl522,sr4, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl523,sr4, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl524,sr4, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl525,sr4, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl526,sr4, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl527,sr4, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl528,sr4, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl529,sr4, ss6, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl530,sr4, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl531,sr4, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl532,sr4, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl533,sr4, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl534,sr4, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl535,sr4, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl536,sr4, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl537,sr4, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl538,sr4, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl539,sr4, ss16, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl540,sr5, sr6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl541,sr5, sr7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl542,sr5, sr8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl543,sr5, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl544,sr5, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl545,sr5, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl546,sr5, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl547,sr5, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl548,sr5, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl549,sr5, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl550,sr5, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl551,sr5, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl552,sr5, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl553,sr5, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl554,sr5, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl555,sr5, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl556,sr5, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl557,sr5, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl558,sr5, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl559,sr5, ss9, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl560,sr5, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl561,sr5, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl562,sr5, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl563,sr5, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl564,sr5, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl565,sr6, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl566,sr6, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl567,sr6, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl568,sr6, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl569,sr6, sr13, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl570,sr6, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl571,sr6, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl572,sr6, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl573,sr6, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl574,sr6, ss2, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl575,sr6, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl576,sr6, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl577,sr6, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl578,sr6, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl579,sr6, ss7, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl580,sr6, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl581,sr6, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl582,sr6, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl583,sr6, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl584,sr6, ss12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl585,sr6, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl586,sr6, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl587,sr6, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl588,sr6, ss16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl589,sr7, sr8, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl590,sr7, sr9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl591,sr7, sr10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl592,sr7, sr11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl593,sr7, sr12, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl594,sr7, sr13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl595,sr7, sr14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl596,sr7, sr15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl597,sr7, sr16, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl598,sr7, ss1, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl599,sr7, ss2, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl600,sr7, ss3, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl601,sr7, ss4, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl602,sr7, ss5, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl603,sr7, ss6, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl604,sr7, ss7, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl605,sr7, ss8, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl606,sr7, ss9, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl607,sr7, ss10, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl608,sr7, ss11, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl609,sr7, ss12, EdgeType.UNDIRECTED); 
	    substrate.getGraph().addEdge(sl610,sr7, ss13, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl611,sr7, ss14, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl612,sr7, ss15, EdgeType.UNDIRECTED);
	    substrate.getGraph().addEdge(sl613,sr7, ss16, EdgeType.UNDIRECTED);
	    
		return substrate;
	}
	
	/** test substrate based on FEDERICA substrate **/
	public static final Substrate constructFEDERICASubstrate(String id) {
		
		Substrate substrate = new Substrate(id);
		
		/** Portugal (FCCN)**/
	    // sw1.lis.pt
	    SubstrateSwitch sw1LisPt = new SubstrateSwitch(0);
	    sw1LisPt.setName("sw1.lis.pt");
	    sw1LisPt.setLocation(Location.Portugal);
	    sw1LisPt.setCpu(100);
	    sw1LisPt.setMemory(100);
	    sw1LisPt.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.lis.pt
	    Server vnode1LisPt = new Server(1);
	    vnode1LisPt.setName("vnode1.lis.pt");
	    vnode1LisPt.setLocation(Location.Portugal);
	    vnode1LisPt.setCpu(100);
	    vnode1LisPt.setMemory(100);
	    vnode1LisPt.setDiskSpace(100);
	    vnode1LisPt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Spain - Madrid (RedIRIS) **/
	    // sw1.mad.es
	    SubstrateSwitch sw1MadEs = new SubstrateSwitch(2);
	    sw1MadEs.setName("sw1.mad.es");
	    sw1MadEs.setLocation(Location.Spain);
	    sw1MadEs.setCpu(100);
	    sw1MadEs.setMemory(100);
	    sw1MadEs.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.mad.es
	    Server vnode1MadEs = new Server(3);
	    vnode1MadEs.setName("vnode1.mad.es");
	    vnode1MadEs.setLocation(Location.Spain);
	    vnode1MadEs.setCpu(100);
	    vnode1MadEs.setMemory(100);
	    vnode1MadEs.setDiskSpace(100);
	    vnode1MadEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Spain - Barcelona (i2CAT) **/
	    // sw1.bar.es
	    SubstrateSwitch sw1BarEs = new SubstrateSwitch(4);
	    sw1BarEs.setName("sw1.bar.es");
	    sw1BarEs.setLocation(Location.Spain);
	    sw1BarEs.setCpu(100);
	    sw1BarEs.setMemory(100);
	    sw1BarEs.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.bar.es
	    Server vnode1BarEs = new Server(5);
	    vnode1BarEs.setName("vnode1.bar.es");
	    vnode1BarEs.setLocation(Location.Spain);
	    vnode1BarEs.setCpu(100);
	    vnode1BarEs.setMemory(100);
	    vnode1BarEs.setDiskSpace(100);
	    vnode1BarEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.bar.es
	    Server vnode2BarEs = new Server(6);
	    vnode2BarEs.setName("vnode2.bar.es");
	    vnode2BarEs.setLocation(Location.Spain);
	    vnode2BarEs.setCpu(100);
	    vnode2BarEs.setMemory(100);
	    vnode2BarEs.setDiskSpace(100);
	    vnode2BarEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Ireland (HEANet) **/
	    // sw1.dub.ie
	    SubstrateSwitch sw1DubIe = new SubstrateSwitch(7);
	    sw1DubIe.setName("sw1.dub.ie");
	    sw1DubIe.setLocation(Location.Ireland);
	    sw1DubIe.setCpu(100);
	    sw1DubIe.setMemory(100);
	    sw1DubIe.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.dub.ie
	    Server vnode1DubIe = new Server(8);
	    vnode1DubIe.setName("vnode1.dub.ie");
	    vnode1DubIe.setLocation(Location.Ireland);
	    vnode1DubIe.setCpu(100);
	    vnode1DubIe.setMemory(100);
	    vnode1DubIe.setDiskSpace(100);
	    vnode1DubIe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Greece (GRNET) **/
	    // sw1.eie.gr
	    SubstrateSwitch sw1EieGr = new SubstrateSwitch(9);
	    sw1EieGr.setName("sw1.eie.gr");
	    sw1EieGr.setLocation(Location.Greece);
	    sw1EieGr.setCpu(100);
	    sw1EieGr.setMemory(100);
	    sw1EieGr.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // sw1.ntu.gr
	    SubstrateSwitch sw1NtuGr = new SubstrateSwitch(10);
	    sw1NtuGr.setName("sw1.ntu.gr");
	    sw1NtuGr.setLocation(Location.Greece);
	    sw1NtuGr.setCpu(100);
	    sw1NtuGr.setMemory(100);
	    sw1NtuGr.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.ntu.ie
	    Server vnode1NtuGr = new Server(11);
	    vnode1NtuGr.setName("vnode1.ntu.ie");
	    vnode1NtuGr.setLocation(Location.Greece);
	    vnode1NtuGr.setCpu(100);
	    vnode1NtuGr.setMemory(100);
	    vnode1NtuGr.setDiskSpace(100);
	    vnode1NtuGr.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Italy (GARR) **/
	    // r1.mil.it
	    SubstrateRouter r1MilIt = new SubstrateRouter(12);
	    r1MilIt.setName("r1.mil.it");
	    r1MilIt.setLocation(Location.Italy);
	    r1MilIt.setCpu(100);
	    r1MilIt.setMemory(100);
	    r1MilIt.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // vnode1.mil.it
	    Server vnode1MilIt = new Server(13);
	    vnode1MilIt.setName("vnode1.mil.it");
	    vnode1MilIt.setLocation(Location.Italy);
	    vnode1MilIt.setCpu(100);
	    vnode1MilIt.setMemory(100);
	    vnode1MilIt.setDiskSpace(100);
	    vnode1MilIt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.mil.it
	    Server vnode2MilIt = new Server(14);
	    vnode2MilIt.setName("vnode2.mil.it");
	    vnode2MilIt.setLocation(Location.Italy);
	    vnode2MilIt.setCpu(100);
	    vnode2MilIt.setMemory(100);
	    vnode2MilIt.setDiskSpace(100);
	    vnode2MilIt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Switzerland (SWITCH) **/
	    // sw1.gen.ch
	    SubstrateSwitch sw1GenCh = new SubstrateSwitch(15);
	    sw1GenCh.setName("sw1.gen.ch");
	    sw1GenCh.setLocation(Location.Switzerland);
	    sw1GenCh.setCpu(100);
	    sw1GenCh.setMemory(100);
	    sw1GenCh.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.gen.ch
	    Server vnode1GenCh = new Server(16);
	    vnode1GenCh.setName("vnode1.gen.ch");
	    vnode1GenCh.setLocation(Location.Switzerland);
	    vnode1GenCh.setCpu(100);
	    vnode1GenCh.setMemory(100);
	    vnode1GenCh.setDiskSpace(100);
	    vnode1GenCh.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Hungary (HUNGARNET) **/
	    // sw1.bud.hu
	    SubstrateSwitch sw1BudHu = new SubstrateSwitch(17);
	    sw1BudHu.setName("sw1.bud.hu");
	    sw1BudHu.setLocation(Location.Hungary);
	    sw1BudHu.setCpu(100);
	    sw1BudHu.setMemory(100);
	    sw1BudHu.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.bud.hu
	    Server vnode1BudHu = new Server(18);
	    vnode1BudHu.setName("vnode1.bud.hu");
	    vnode1BudHu.setLocation(Location.Hungary);
	    vnode1BudHu.setCpu(100);
	    vnode1BudHu.setMemory(100);
	    vnode1BudHu.setDiskSpace(100);
	    vnode1BudHu.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    /** Germany (DFN) **/
	    // r1.erl.de
	    SubstrateRouter r1ErlDe = new SubstrateRouter(19);
	    r1ErlDe.setName("r1.erl.de");
	    r1ErlDe.setLocation(Location.Germany);
	    r1ErlDe.setCpu(100);
	    r1ErlDe.setMemory(100);
	    r1ErlDe.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // vnode1.erl.de
	    Server vnode1ErlDe = new Server(20);
	    vnode1ErlDe.setName("vnode1.erl.de");
	    vnode1ErlDe.setLocation(Location.Germany);
	    vnode1ErlDe.setCpu(100);
	    vnode1ErlDe.setMemory(100);
	    vnode1ErlDe.setDiskSpace(100);
	    vnode1ErlDe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.erl.de
	    Server vnode2ErlDe = new Server(21);
	    vnode2ErlDe.setName("vnode2.erl.de");
	    vnode2ErlDe.setLocation(Location.Germany);
	    vnode2ErlDe.setCpu(100);
	    vnode2ErlDe.setMemory(100);
	    vnode2ErlDe.setDiskSpace(100);
	    vnode2ErlDe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    /** Czech Republic (CESNET) **/
	    // r1.pra.cz
	    SubstrateRouter r1PraCz = new SubstrateRouter(22);
	    r1PraCz.setName("r1.pra.cz");
	    r1PraCz.setLocation(Location.Czech_Republic);
	    r1PraCz.setCpu(100);
	    r1PraCz.setMemory(100);
	    r1PraCz.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);	    
	    // vnode1.pra.cz
	    Server vnode1PraCz = new Server(23);
	    vnode1PraCz.setName("vnode1.pra.cz");
	    vnode1PraCz.setLocation(Location.Czech_Republic);
	    vnode1PraCz.setCpu(100);
	    vnode1PraCz.setMemory(100);
	    vnode1PraCz.setDiskSpace(100);
	    vnode1PraCz.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.pra.cz
	    Server vnode2PraCz = new Server(24);
	    vnode2PraCz.setName("vnode2.pra.cz");
	    vnode2PraCz.setLocation(Location.Czech_Republic);
	    vnode2PraCz.setCpu(100);
	    vnode2PraCz.setMemory(100);
	    vnode2PraCz.setDiskSpace(100);
	    vnode2PraCz.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    /** Poland (PSNC) **/
	    // r1.poz.pl
	    SubstrateRouter r1PozPl = new SubstrateRouter(25);
	    r1PozPl.setName("r1.poz.pl");
	    r1PozPl.setLocation(Location.Poland);
	    r1PozPl.setCpu(100);
	    r1PozPl.setMemory(100);
	    r1PozPl.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);	
	    // vnode1.poz.pl
	    Server vnode1PozPl = new Server(26);
	    vnode1PozPl.setName("vnode1.poz.pl");
	    vnode1PozPl.setLocation(Location.Poland);
	    vnode1PozPl.setCpu(100);
	    vnode1PozPl.setMemory(100);
	    vnode1PozPl.setDiskSpace(100);
	    vnode1PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.poz.pl
	    Server vnode2PozPl = new Server(27);
	    vnode2PozPl.setName("vnode2.poz.pl");
	    vnode2PozPl.setLocation(Location.Poland);
	    vnode2PozPl.setCpu(100);
	    vnode2PozPl.setMemory(100);
	    vnode2PozPl.setDiskSpace(100);
	    vnode2PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode3.poz.pl
	    Server vnode3PozPl = new Server(28);
	    vnode3PozPl.setName("vnode3.poz.pl");
	    vnode3PozPl.setLocation(Location.Poland);
	    vnode3PozPl.setCpu(100);
	    vnode3PozPl.setMemory(100);
	    vnode3PozPl.setDiskSpace(100);
	    vnode3PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	
	    // vnode4.poz.pl
	    Server vnode4PozPl = new Server(29);
	    vnode4PozPl.setName("vnode4.poz.pl");
	    vnode4PozPl.setLocation(Location.Poland);
	    vnode4PozPl.setCpu(100);
	    vnode4PozPl.setMemory(100);
	    vnode4PozPl.setDiskSpace(100);
	    vnode4PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	
	    /** Sweden (KTH) **/
	    // sw1.sth.se
	    SubstrateSwitch sw1SthSe = new SubstrateSwitch(30);
	    sw1SthSe.setName("sw1.sth.se");
	    sw1SthSe.setLocation(Location.Sweden);
	    sw1SthSe.setCpu(100);
	    sw1SthSe.setMemory(100);
	    sw1SthSe.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.sth.se
	    Server vnode1SthSe = new Server(31);
	    vnode1SthSe.setName("vnode1.sth.se");
	    vnode1SthSe.setLocation(Location.Sweden);
	    vnode1SthSe.setCpu(100);
	    vnode1SthSe.setMemory(100);
	    vnode1SthSe.setDiskSpace(100);
	    vnode1SthSe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.sth.se
	    Server vnode2SthSe = new Server(32);
	    vnode2SthSe.setName("vnode2.sth.se");
	    vnode2SthSe.setLocation(Location.Sweden);
	    vnode2SthSe.setCpu(100);
	    vnode2SthSe.setMemory(100);
	    vnode2SthSe.setDiskSpace(100);
	    vnode2SthSe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    
	   
	    // Adding links & interfaces
	    // sw1.lis.pt - vnode1.lis.pt
	    SubstrateLink link = new SubstrateLink(0,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1LisPt,vnode1LisPt), EdgeType.UNDIRECTED);
	    
	    // sw1.lis.pt - sw1.mad.es
	    link = new SubstrateLink(1,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1LisPt,sw1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - sw1.mad.es (1)
	    link = new SubstrateLink(2,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - sw1.mad.es (2)
	    link = new SubstrateLink(3,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - sw1.mad.es (3)
	    link = new SubstrateLink(4,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // sw1.mad.es - sw1.bar.es (1)
	    link = new SubstrateLink(5,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1BarEs), EdgeType.UNDIRECTED);
	    
	    // sw1.mad.es - sw1.bar.es (2)
	    link = new SubstrateLink(6,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1BarEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.bar.es - sw1.bar.es
	    link = new SubstrateLink(7,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1BarEs,sw1BarEs), EdgeType.UNDIRECTED);
	    
	    // vnode2.bar.es - sw1.bar.es
	    link = new SubstrateLink(8,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2BarEs,sw1BarEs), EdgeType.UNDIRECTED);	    
	    
	    // sw1.mad.es - sw1.ntu.gr
	    link = new SubstrateLink(9,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1NtuGr), EdgeType.UNDIRECTED);
	    
	    // sw1.mad.es - sw1.dub.ie
	    link = new SubstrateLink(10,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1DubIe), EdgeType.UNDIRECTED);	    

	    // sw1.mad.es - r1.mil.it
	    link = new SubstrateLink(11,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,r1MilIt), EdgeType.UNDIRECTED);
	    
	    // sw1.ntu.gr - vnode1.ntu.gr
	    link = new SubstrateLink(12,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1NtuGr,sw1NtuGr), EdgeType.UNDIRECTED);
	    
	    // sw1.ntu.gr - r1.pra.cz
	    link = new SubstrateLink(13,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PraCz,sw1NtuGr), EdgeType.UNDIRECTED);	    
	    
	    // sw1.eie.gr - sw1.bud.hu
	    link = new SubstrateLink(14,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,sw1EieGr), EdgeType.UNDIRECTED);	    
	    
	    // sw1.bud.hu - vnode1.bud.hu (1)
	    link = new SubstrateLink(15,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,vnode1BudHu), EdgeType.UNDIRECTED);	    
	    
	    // sw1.bud.hu - vnode1.bud.hu (2)
	    link = new SubstrateLink(16,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,vnode1BudHu), EdgeType.UNDIRECTED);	
	    
	    // sw1.bud.hu - vnode1.bud.hu (3)
	    link = new SubstrateLink(17,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,vnode1BudHu), EdgeType.UNDIRECTED);	
	    
	    // sw1.bud.hu - sw1.gen.ch
	    link = new SubstrateLink(18,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,sw1GenCh), EdgeType.UNDIRECTED);	    
	
	    // sw1.gen.ch - vnode1.gen.ch
	    link = new SubstrateLink(19,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1GenCh,sw1GenCh), EdgeType.UNDIRECTED);
	    
	    // sw1.gen.ch - r1.erl.de
	    link = new SubstrateLink(20,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,sw1GenCh), EdgeType.UNDIRECTED);	    
	    
	    // sw1.gen.ch - sw1.dub.ie
	    link = new SubstrateLink(21,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1DubIe,sw1GenCh), EdgeType.UNDIRECTED);	    
	    
	    // sw1.dub.ie - vnode1.dub.ie
	    link = new SubstrateLink(22,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1DubIe,vnode1DubIe), EdgeType.UNDIRECTED);    
	    
	    // sw1.dub.ie - sw1.sth.se
	    link = new SubstrateLink(23,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1DubIe,sw1SthSe), EdgeType.UNDIRECTED);	    
	    
	    // sw1.sth.se - vnode1.sth.se
	    link = new SubstrateLink(24,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1SthSe,sw1SthSe), EdgeType.UNDIRECTED);	    
	    
	    // sw1.sth.se - vnode2.sth.se
	    link = new SubstrateLink(25,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2SthSe,sw1SthSe), EdgeType.UNDIRECTED);	    
	    
	    // sw1.sth.se - r1.poz.pl
	    link = new SubstrateLink(26,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,sw1SthSe), EdgeType.UNDIRECTED);    
	    
	    // r1.mil.it - vnode1.mil.it
	    link = new SubstrateLink(27,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,vnode1MilIt), EdgeType.UNDIRECTED);    
	    
	    // r1.mil.it - vnode2.mil.it
	    link = new SubstrateLink(28,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,vnode2MilIt), EdgeType.UNDIRECTED); 	    
	    
	    // r1.mil.it - r1.poz.pl
	    link = new SubstrateLink(29,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,r1PozPl), EdgeType.UNDIRECTED); 
	    
	    // r1.mil.it - r1.pra.cz
	    link = new SubstrateLink(30,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,r1PraCz), EdgeType.UNDIRECTED);
	    
	    // r1.pra.cz - vnode1.pra.cz
	    link = new SubstrateLink(31,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1PraCz,r1PraCz), EdgeType.UNDIRECTED);	    
	    
	    // r1.pra.cz - vnode2.pra.cz
	    link = new SubstrateLink(32,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2PraCz,r1PraCz), EdgeType.UNDIRECTED);    
	    
	    // r1.pra.cz - r1.poz.pl
	    link = new SubstrateLink(33,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,r1PraCz), EdgeType.UNDIRECTED);    
	    
	    // r1.pra.cz - r1.erl.de
	    link = new SubstrateLink(34,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,r1PraCz), EdgeType.UNDIRECTED);	    
	    
	    // r1.erl.de - vnode1.erl.de
	    link = new SubstrateLink(35,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode1ErlDe), EdgeType.UNDIRECTED); 
	    
	    // r1.erl.de - vnode2.erl.de
	    link = new SubstrateLink(36,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode2ErlDe), EdgeType.UNDIRECTED);   
	    
	    // r1.erl.de - r1.poz.pl
	    link = new SubstrateLink(37,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,r1PozPl), EdgeType.UNDIRECTED);	    
	    
	    // r1.poz.pl - vnode1.poz.pl
	    link = new SubstrateLink(38,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1PozPl,r1PozPl), EdgeType.UNDIRECTED);  
	    
	    // r1.poz.pl - vnode2.poz.pl
	    link = new SubstrateLink(39,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2PozPl,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.poz.pl - vnode3.poz.pl
	    link = new SubstrateLink(40,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode3PozPl,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.poz.pl - vnode4.poz.pl
	    link = new SubstrateLink(41,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode4PozPl,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.erl.de - r1.mil.it
	    link = new SubstrateLink(42,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,r1MilIt), EdgeType.UNDIRECTED);
	    
	    ((SubstrateNodeFactory) substrate.getNodeFactory()).setNodeCount(33);
	    ((SubstrateLinkFactory) substrate.getLinkFactory()).setLinkCount(42);
	    
	 // Setting FEDERICA layout
	    Layout<Node, Link> layout = new StaticLayout<Node, Link>(substrate.getGraph(), new Transformer<Node, Point2D>() {
			@Override
			public Point2D transform(Node node) {
				switch (node.getId()) {
					case 0:
						return new Point(80,450);
					case 1:
						return new Point(90,510);
					case 2:
						return new Point(180,400);
					case 3:
						return new Point(130,350);
					case 4:
						return new Point(280,350);
					case 5:
						return new Point(300,475);
					case 6:
						return new Point(235,450);
					case 7:
						return new Point(100,210);
					case 8:
						return new Point(120,55);
					case 9:
						return new Point(770,455);
					case 10:
						return new Point(700,465);
					case 11:
						return new Point(660,510);
					case 12:
						return new Point(405,325);
					case 13:
						return new Point(415,395);
					case 14:
						return new Point(470,375);
					case 15:
						return new Point(345,255);
					case 16:
						return new Point(280,280);
					case 17:
						return new Point(735,280);
					case 18:
						return new Point(775,335);
					case 19:
						return new Point(415,205);
					case 20:
						return new Point(415,140);
					case 21:
						return new Point(340,155);
					case 22:
						return new Point(560,300);
					case 23:
						return new Point(630,255);
					case 24:
						return new Point(650,305);
					case 25:
						return new Point(565,190);
					case 26:
						return new Point(560,85);
					case 27:
						return new Point(630,105);
					case 28:
						return new Point(690,135);
					case 29:
						return new Point(700,195);
					case 30:
						return new Point(430,65);
					case 31:
						return new Point(325,35);
					case 32:
						return new Point(505,35);
				}
				return new Point(400,250);
			}
		});
	    
	    substrate.setGraphLayout(layout);
	    
	    return substrate;
	}

}
