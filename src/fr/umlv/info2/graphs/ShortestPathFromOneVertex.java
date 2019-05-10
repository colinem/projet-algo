package fr.umlv.info2.graphs;

import java.util.Arrays;

public class ShortestPathFromOneVertex {
	private final int source;
	private final int destination;
	private final int[] d;
	private final int[] pi;
	private final int nSteps;

	ShortestPathFromOneVertex(int source, int destination, int[] d, int[] pi, int nSteps) {
		this.source = source;
		this.destination = destination;
		this.d = d;
		this.pi = pi;
		this.nSteps = nSteps;
	}

	public void printShortestPath() {
		var sb = new StringBuilder("Shortest path from ").append(source).append(" to ").append(destination).append(" :    ").append(source);
		printShortestPathTo(sb, destination);
		sb.append("    (weight = ").append(d[destination]).append(" ; done in ").append(nSteps).append(" steps)");
		System.out.println(sb);
	}
	
	private void printShortestPathTo(StringBuilder sb, int destination) {
		if (source == destination)
			return;
		printShortestPathTo(sb, pi[destination]);
		sb.append(" --> ").append(destination);
	}

	@Override
	public String toString() {
		return "s=" + source + " ; d=" + destination + " ; " + Arrays.toString(d) + " ; " + Arrays.toString(pi);
	}
}
