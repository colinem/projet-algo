package fr.umlv.info2.graphs;

import org.junit.jupiter.api.Test;

class AstarTest {

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
		// Astar :  steps.
		// Shortest path of a length of 5 from 1 to 5 :    1 --> 2 --> 3 --> 5
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
		// Astar :  steps.
		// Shortest path of a length of 20 from 1 to 6 :    1 --> 3 --> 5 --> 4 --> 6
	}



	@Test
	void test3() {

		//Graph.astar(graph, 1, 7, new int[5][2]).get().printShortestPath();
		// Astar :  steps.
		// Shortest path of a length of 5 from 1 to 5 :    1 --> 2 --> 3 --> 5
	}

}
