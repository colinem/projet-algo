package fr.umlv.info2.graphs;

import org.junit.jupiter.api.Test;

class GraphTest {

	@Test
	void test1() {
		var graph = new AdjGraph(5);
		graph.addEdge(0, 1, 2);
		graph.addEdge(0, 2, 5);
		graph.addEdge(1, 2, 1);
		graph.addEdge(1, 4, 4);
		graph.addEdge(2, 3, 4);
		graph.addEdge(2, 4, 2);
		graph.addEdge(3, 2, 2);
		Graph.astar(graph, 0, 4, new int[5][2]).get().printShortestPath();
		// Shortest path from 0 to 4 :    0 --> 1 --> 2 --> 4    (weight = 5)
	}

	@Test
	void test2() {
		var graph = new AdjGraph(6);
		graph.addEdge(0, 1, 4);
		graph.addEdge(0, 2, 2);
		graph.addEdge(1, 2, 5);
		graph.addEdge(1, 3, 10);
		graph.addEdge(2, 4, 3);
		graph.addEdge(3, 5, 11);
		graph.addEdge(4, 3, 4);
		Graph.astar(graph, 0, 5, new int[6][2]).get().printShortestPath();
		// Shortest path from 0 to 5 :    0 --> 2 --> 4 --> 3 --> 5    (weight = 20)
	}

}
