package fr.umlv.info2.graphs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class AdjGraph implements Graph {
	
	private final ArrayList<LinkedList<Edge>> adj;
	private final int n;
	private int nEdges;
	
	public AdjGraph(int n) {
		this.n = n;
		adj = new ArrayList<>(n);
		for (var i = 0 ; i < n ; ++i)
			adj.add(i, new LinkedList<>());
	}

	@Override
	public int numberOfEdges() {
		return nEdges;
	}

	@Override
	public int numberOfVertices() {
		return n;
	}

	@Override
	public void addEdge(int i, int j, int value) {
		if (j < 0 || j >= n)
			throw new IndexOutOfBoundsException();
		if (value == 0)
			throw new IllegalArgumentException();
		var neighbours = edgeIterator(i);
		while (neighbours.hasNext()) {
			var edge = neighbours.next();
			if (edge.getStart() == i && edge.getEnd() == j)
				throw new IllegalArgumentException();
		}
		adj.get(i).addFirst(new Edge(i, j, value));
		++nEdges;
	}

	@Override
	public boolean isEdge(int i, int j) {
		if (j < 0 || j >= n)
			throw new IndexOutOfBoundsException();
		var neighbours = edgeIterator(i);
		while (neighbours.hasNext()) {
			var edge = neighbours.next();
			if (edge.getStart() == i && edge.getEnd() == j)
				return true;
		}
		return false;
	}

	@Override
	public int getWeight(int i, int j) {
		if (j < 0 || j >= n)
			throw new IndexOutOfBoundsException();
		for (var edge : adj.get(i))
			if (edge.getEnd() == j)
				return edge.getValue();
		return 0;
	}

	@Override
	public Iterator<Edge> edgeIterator(int i) {
		var neighbours = adj.get(i);
		neighbours.sort(new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.getEnd() < o2.getEnd() ? -1 : 1;
			}
		});
		return neighbours.iterator();
	}

	@Override
	public void forEachEdge(int i, Consumer<Edge> consumer) {
		edgeIterator(i).forEachRemaining(consumer);
	}

}
