package fr.umlv.info2.graphs;

import org.junit.jupiter.api.Test;
import parser.Parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
		Graph.dijkstra(graph, 0, graph.numberOfVertices() - 1, new int[5][2]).get().printShortestPath();
		Graph.astar(graph, 0, 4, new int[5][2]).get().printShortestPath();
		// Astar :  steps.
		// Shortest path of a length of 8 (miles) from 1 to 5 :    1 --> 2 --> 3 --> 5
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
		Graph.dijkstra(graph, 0, graph.numberOfVertices() - 1, new int[6][2]).get().printShortestPath();
		Graph.astar(graph, 0, 5, new int[6][2]).get().printShortestPath();


		// Astar :  steps.
		// Shortest path of a length of 32 (miles) from 1 to 6 :    1 --> 3 --> 5 --> 4 --> 6
	}



	@Test
	void test3() {


		try {
			Optional<ShortestPathFromOneVertex> path = Optional.empty();

			var graph = Parser.parseGraph(Path.of("resources/test.gr"));
			var coord = Parser.parseCoordonates(Path.of("resources/test.co"));
			Graph.dijkstra(graph, 0, graph.numberOfVertices() - 1, coord).get().printShortestPath();
			Graph.astar(graph, coord).get().printShortestPath();

			// Astar :  steps.
			// Shortest path of a length of 5 (miles) from 1 to 5 :    1 --> 2 --> 3 --> 5
		} catch (IOException e) {
			System.out.println("Echec du test");
		}

	}




}
