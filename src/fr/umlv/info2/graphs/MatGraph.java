package fr.umlv.info2.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class MatGraph implements Graph {
	
	private final int[][] mat;
	private final int n;
	private int nEdges;
	
	public MatGraph(int n) {
		this.n = n;
		mat = new int[n][n];
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
		if (mat[i][j] != 0 || value == 0)
			throw new IllegalArgumentException();
		mat[i][j] = value;
		++nEdges;
	}

	@Override
	public boolean isEdge(int i, int j) {
		return mat[i][j] != 0;
	}

	@Override
	public int getWeight(int i, int j) {
		return mat[i][j];
	}

	@Override
	public Iterator<Edge> edgeIterator(int i) {
		var neighbours = new ArrayList<Edge>();
		for (var j = 0 ; j < mat.length ; ++j)
			if (mat[i][j] != 0)
				neighbours.add(new Edge(i, j, mat[i][j]));
//		for (var x = 0 ; x < mat.length ; ++x)
//			if (mat[x][i] != 0)
//				neighbours.add(new Edge(x, i, mat[x][i]));
		return neighbours.iterator();
	}

	@Override
	public void forEachEdge(int i, Consumer<Edge> consumer) {
		if (i >= n)
			throw new IndexOutOfBoundsException();
		edgeIterator(i).forEachRemaining(consumer);
	}
	
	

}
