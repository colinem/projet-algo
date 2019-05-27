package fr.umlv.info2.graphs;

import java.util.Arrays;

public class ShortestPathFromOneVertex {
	private final String algo;
	private final int source;
	private final int destination;
	private final int[] d;
	private final int[] pi;
	private final int nSteps;

	ShortestPathFromOneVertex(String algo, int source, int destination, int[] d, int[] pi, int nSteps) {
		this.algo = algo;
		this.source = source;
		this.destination = destination;
		this.d = d;
		this.pi = pi;
		this.nSteps = nSteps;
	}

	public void printShortestPath() {
		var sb = new StringBuilder(algo).append(" : ").append(nSteps).append(" steps.\n")
				.append("Shortest path of a length of ").append(Math.round(d[destination] * 1.6))
				.append(" from ").append(source+1).append(" to ").append(destination+1)
				.append(" :    ").append(source+1);
		printShortestPathTo(sb, destination);
		System.out.println(sb);
	}
	
	private void printShortestPathTo(StringBuilder sb, int destination) {
		if (source == destination)
			return;
		printShortestPathTo(sb, pi[destination]);
		sb.append(" --> ").append(destination +1); /*+1 because our data structure begins at 0 whereas the file nodes begin at 1*/
	}

	@Override
	public String toString() {
		return "s=" + source + " ; d=" + destination + " ; " + Arrays.toString(d) + " ; " + Arrays.toString(pi);
	}
}
