package fr.umlv.info2.graphs;

import java.util.Arrays;

public class ShortestPathFromOneVertex {
	private final int source;
	private final int[] d;
	private final int[] pi;

	ShortestPathFromOneVertex(int source, int[] d, int[] pi) {
		this.source = source;
		this.d = d;
		this.pi = pi;
	}

	public void printShortestPathTo(int destination) {
		var sb = new StringBuilder("Shortest path from ").append(source).append(" to ").append(destination).append(" :    ").append(source);
		printShortestPathTo(sb, destination);
		System.out.println(sb);
	}
	
	private void printShortestPathTo(StringBuilder sb, int destination) {
		if (source == destination)
			return;
		printShortestPathTo(sb, pi[destination]);
		sb.append(" --> ").append(destination);
	}

	public void printShortestPaths() {
		for (int i = 0; i < d.length; i++) {
			if (i == source) {
				continue;
			}
			printShortestPathTo(i);
		}
	}

	@Override
	public String toString() {
		return source + " " + Arrays.toString(d) + " " + Arrays.toString(pi);
	}
}
