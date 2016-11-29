/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.components.Link;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateRouter;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import gui.SimulatorConstants;

/**
 * Generates a random graph using the Erdos-Renyi binomial model
 * (each pair of vertices is connected with probability p).
 * 
 *  @author William Giordano, Scott White, Joshua O'Madadhain
 */
public class ER<V,E> implements GraphGenerator<Node,Link> {
    private int mNumVertices;
    private double mEdgeConnectionProbability;
    private static Random mRandom = new Random(SimulatorConstants.RANDOM_SEED);
    Factory<UndirectedGraph<Node,Link>> graphFactory;
    Factory<Node> vertexFactory;
    Factory<Link> edgeFactory;

    /**
     *
     * @param numVertices number of vertices graph should have
     * @param p Connection's probability between 2 vertices
     */
	public ER(Factory<UndirectedGraph<Node,Link>> graphFactory,
			Factory<Node> vertexFactory, Factory<Link> edgeFactory,
			int numVertices,double p)
    {
        if (numVertices <= 0) {
            throw new IllegalArgumentException("A positive # of vertices must be specified.");
        }
        mNumVertices = numVertices;
        if (p < 0 || p > 1) {
            throw new IllegalArgumentException("p must be between 0 and 1.");
        }
        this.graphFactory = graphFactory;
        this.vertexFactory = vertexFactory;
        this.edgeFactory = edgeFactory;
        mEdgeConnectionProbability = p;
	}

    /**
     * Returns a graph in which each pair of vertices is connected by 
     * an undirected edge with the probability specified by the constructor.
     */
	public Graph<Node,Link> create() {
        UndirectedGraph<Node,Link> g = graphFactory.create();
        for(int i=0; i<mNumVertices; i++) {
        	g.addVertex(vertexFactory.create());
        }
        List<Node> list = new ArrayList<Node>(g.getVertices());

		for (int i = 0; i < mNumVertices-1; i++) {
            Node v_i = list.get(i);
			for (int j = i+1; j < mNumVertices; j++) {
                Node v_j = list.get(j);
                if (((v_i instanceof Server) && (v_j instanceof SubstrateRouter)) || 
                		((v_i instanceof SubstrateRouter) && (v_j instanceof SubstrateRouter)) ){
                	if (mRandom.nextDouble() < mEdgeConnectionProbability) {
                		g.addEdge(edgeFactory.create(), v_i, v_j);
                	}
                }
			}
		}
        return g;
    }

    /**
     * Sets the seed of the internal random number generator to {@code seed}.
     * Enables consistent behavior.
     */
    public void setSeed(long seed) {
        mRandom.setSeed(seed);
    }
}











