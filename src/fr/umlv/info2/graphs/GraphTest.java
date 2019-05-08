package fr.umlv.info2.graphs;

import org.junit.jupiter.api.Test;

class GraphTest {

	@Test
	void test() {
		var graph = new AdjGraph(5);
		graph.addEdge(0, 1, 2);
		graph.addEdge(0, 2, 5);
		graph.addEdge(1, 2, 1);
		graph.addEdge(1, 4, 4);
		graph.addEdge(2, 3, 4);
		graph.addEdge(2, 4, 2);
		graph.addEdge(3, 2, 2);
		Graph.astar(graph, 0, 4, new int[5][2]).get().printShortestPath();
	}

}
