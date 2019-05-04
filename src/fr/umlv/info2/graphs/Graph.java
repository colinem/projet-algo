package fr.umlv.info2.graphs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Graph {
	/**
	 * Le nombre d'arêtes du graphe.
	 * @return le nombre d'arêtes du graphe
	 */
	int numberOfEdges();
	
	/**
	 * Le nombre de sommets du graphe.
	 * @return le nombre de sommets du graphe
	 */
	int numberOfVertices();

	/**
	 * Permet d'ajouter une arête orientée et pondérée au graphe.
	 * @param i la première extrémité de l'arête
	 * @param j la seconde extrémité de l'arête
	 * @param value le poids de l'arête
	 * @throws IndexOutOfBoundsException si i ou j n'est pas un sommet du graphe
	 * @throws IllegalArgumentException si value vaut 0 ou si il existe déjà une arête entre i et j
	 */
	void addEdge(int i, int j, int value);

	/**
	 * Teste l'existence d'une arête donnée
	 * @param i la première extrémité de l'arête
	 * @param j la seconde extrémité de l'arête
	 * @return true s'il existe une arête entre i et j; false sinon
	 * @throws IndexOutOfBoundsException si i ou j n'est pas un sommet du graphe
	 */
	boolean isEdge(int i, int j);

	/**
	 * Renvoie le poids d'une arête donnée.
	 * @param i la première extrémité de l'arête
	 * @param j la seconde extrémité de l'arête
	 * @return le poids de l'arête entre i et j
	 * @throws IndexOutOfBoundsException si i ou j n'est pas un sommet du graphe
	 */
	int getWeight(int i, int j);

	/**
	 * Un itérateur sur tous les voisins d'un sommet donné.
	 * @param i le sommet à partir duquel partent les arêtes fournies par l'itérateur
	 * @return un itérateur sur tous les voisins du sommet i
	 * @throws IndexOutOfBoundsException si i n'est pas un sommet du graphe
	 */
	Iterator<Edge> edgeIterator(int i);

	/**
	 * Effectue une action sur tous les voisins (les arêtes) d'un sommet donné.
	 * @param i le sommet à partir duquel partent les arêtes traitées
	 * @param consumer l'acction effectuée sur toutes les arêtes voisines de i
	 * @throws IndexOutOfBoundsException si i n'est pas un sommet du graphe
	 */
	void forEachEdge(int i, Consumer<Edge> consumer);

	/**
	 * Fournit une réprésentation du graphe au format .dot
	 * @return une réprésentation du graphe au format .dot
	 */
	default String toGraphviz() { 
		return null; // TODO à implémenter 
	}
	
	/**
	 * Création d'un graphe aléatoire avec un nombre de sommets et d'arêtes fixé
	 * @param n nombre de sommets
	 * @param nbEdges nombre d'arêtes
	 * @param wmax poids maximal (en valeur absolue) sur les arêtes
	 * @param factory une méthode qui étant donné un nombre de sommets n, fabrique et renvoie yun graphe vide à n sommets
	 * @return un graphe aléatoire construit à l'aide de factory yant exactement n sommets et nbEdges arêtes
	 * @throws IllegalArgumentException si le nombre d'arêtes est trop élevé par rapport au nombre de sommets
	 */
	public static Graph makeRandomGraph(int n, int nbEdges, int wmax, IntFunction<Graph> factory){
		if (nbEdges > n+n)
			throw new IllegalArgumentException();
		var graph = factory.apply(n);
		var rand = new Random();
		for (var i = 0 ; i < nbEdges ; ++i)
			try {
				graph.addEdge(rand.nextInt(n), rand.nextInt(n), rand.nextInt(wmax+wmax+1)-wmax);
			} catch (IllegalArgumentException e) {
				--i;
			}
		return graph;
	}

	/**
	 * Création d'un graphe à partir d'un fichier contenant le nombre de sommets et sa matrice
	 * @param path le chemin du fichier contenant la matrice du graphe
	 * @param factory une méthode qui étant donné un nombre de sommet n, fabrique et renvoie yun graphe vide à n sommet
	 * @return un graphe construit à l'aide de factory et dont les arêtes sont données dans le fihier indiqué dans path
	 * @throws IOException
	 */
	public static Graph makeGraphFromMatrixFile(Path path, IntFunction<Graph> factory) throws IOException {
		try {
			var lines = (String[]) Files.lines(path).toArray();
			var n = Integer.parseInt(lines[0]);
			var graph = factory.apply(n);
			for (var i = 0 ; i < n ; ++i) {
				var weights = lines[i+1].split(" ");
				for (var j = 0 ; j < n ; ++j)
					if (j != 0)
						graph.addEdge(i, j, Integer.parseInt(weights[j]));
			}
			return graph;
		} catch (IOException | NumberFormatException e) {
			System.out.println("File error.");
			e.printStackTrace();
			throw e;
		}
	}
	
	public static List<Integer> DFS(Graph g) {
		return DFS(g, 0);
	}
	
	public static List<Integer> DFS(Graph g, int s0) {
		var values = new ArrayList<Integer>();
		var nVertices = g.numberOfVertices();
		var visibility = new boolean[nVertices];
		for (var vertice = s0 ; vertice < nVertices ; ++vertice)
			DFS(g, vertice, visibility, values, new int[nVertices][2], new LongAdder());
		for (var vertice = 0 ; vertice < s0 ; ++vertice)
			DFS(g, vertice, visibility, values, new int[nVertices][2], new LongAdder());
		return values;
	}
	
	private static void DFS(Graph g, int vertice, boolean[] visibility, List<Integer> listOfValues, int[][] values, LongAdder adder) {
		if (visibility[vertice])
			return;
		adder.increment();
		visibility[vertice] = true;
		listOfValues.add(vertice);
		values[vertice][0] = adder.intValue();
		g.forEachEdge(vertice, edge -> DFS(g, edge.getEnd(), visibility, listOfValues, values, adder));
		adder.increment();
		values[vertice][1] = adder.intValue();
	}
	
	public static int[][] timedDFS(Graph g) {
		return timedDFS(g, 0);
	}
	
	public static int[][] timedDFS(Graph g, int s0) {
		var nVertices = g.numberOfVertices();
		var values = new int[nVertices][2];
		var visibility = new boolean[nVertices];
		var adder = new LongAdder();
		adder.decrement();
		for (var vertice = s0 ; vertice < nVertices ; ++vertice)
			DFS(g, vertice, visibility, new LinkedList<Integer>(), values, adder);
		for (var vertice = 0 ; vertice < s0 ; ++vertice)
			DFS(g, vertice, visibility, new LinkedList<Integer>(), values, adder);
		return values;
	}
	
	public static List<Integer> BFS(Graph g) {
		return BFS(g, 0);
	}
	
	public static List<Integer> BFS(Graph g, int s0) {
		var values = new ArrayList<Integer>();
		var nVertices = g.numberOfVertices();
		var visibility = new boolean[nVertices];
		var queue = new ArrayBlockingQueue<Integer>(nVertices);
		for (var vertice = s0 ; vertice < nVertices ; ++vertice)
			BFSPerVertice(g, values, visibility, queue, vertice);
		for (var vertice = 0 ; vertice < s0 ; ++vertice)
			BFSPerVertice(g, values, visibility, queue, vertice);
		return values;
	}

	private static void BFSPerVertice(Graph g, ArrayList<Integer> values, boolean[] visibility,
			ArrayBlockingQueue<Integer> queue, int vertice) {
		queue.offer(vertice);
		while (!queue.isEmpty()) {
			var v = queue.poll();
			if (!visibility[v]) {
				visibility[v] = true;
				values.add(v);
				g.forEachEdge(v, e -> queue.offer(e.getEnd()));
			}
		}
	}	
	
	public static List<Integer> topologicalSort(Graph g) {
		return topologicalSort(g, () -> {});
	}
	
	public static List<Integer> topologicalSort(Graph g, Runnable toDoIfCycle) {
		var nVertices = g.numberOfVertices();
		var visibility = new boolean[nVertices];
		var values = new int[nVertices][2];
		for (var vertice = 0 ; vertice < nVertices ; ++vertice)
			DFS(g, vertice, visibility, new ArrayList<Integer>(), values, new LongAdder());
		return Stream.of(values).sorted((v1, v2) -> v1[1] > v2[1] ? v1[1] : v2[1]).map(v -> v[0]).collect(Collectors.toList());
		// TODO doesn't work ._.
	}
	
	public static ShortestPathFromOneVertex bellmanFord(Graph g, int source) {
		var d = new int[g.numberOfVertices()];
		var p = new int[g.numberOfVertices()];
		for (var i = 0 ; i < g.numberOfVertices() ; ++i) {
			d[i] = Integer.MAX_VALUE;
			p[i] = Integer.MAX_VALUE;
		}
		d[source] = 0;
		p[source] = source;
		for (var i = 0 ; i < g.numberOfVertices() ; ++i)
			for (var v = 0 ; v < g.numberOfVertices() ; ++v)
				g.forEachEdge(v, e -> {
					if (d[e.getEnd()] > d[e.getStart()]+e.getValue()) {
						d[e.getEnd()] = d[e.getStart()]+e.getValue();
						p[e.getEnd()] = e.getStart();
					}					
				});
		for (var v = 0 ; v < g.numberOfVertices() ; ++v)
			g.forEachEdge(v, e -> {
				if (d[e.getEnd()] > d[e.getStart()]+e.getValue())
					throw new IllegalArgumentException("The graph has a negative circle.");
			});
		return new ShortestPathFromOneVertex(source, d, p);
	}


	public static ShortestPathFromOneVertex dijkstra(Graph g, int source) {
		List<Integer> F = new ArrayList<>();
		int[] d = new int[g.numberOfVertices()];
		int[] pi = new int[g.numberOfVertices()];

		for(int t=0; t<g.numberOfVertices(); t++) {
			F.add(t);
			pi[t] = -1;
			if(t == source) {
				d[t] = 0;
			} else {
				d[t] = Integer.MAX_VALUE;
			}
		}

		while(F.size() != 0) {
			int t = extractMin(F, d);
			g.forEachEdge(t, e -> {
				int s = e.getEnd();
				if(d[t] + g.getWeight(t, s) < d[s]) {
					d[s] = d[t] + g.getWeight(t, s);
					pi[s] = t;
				}
			});
		}
		return new ShortestPathFromOneVertex(source, d, pi);
	}

	private static int extractMin(List<Integer> F, int[] d) {
		int min = Integer.MAX_VALUE;
		int vertice = 0;
		for(int i=0; i<d.length; i++) {
			if(d[i] < min && F.contains(i)) {
				min = d[i];
				vertice = i;
			}
		}
		F.remove((Integer) vertice);
		return vertice;
	}


	private static int[] initApproximateArray(int[][] coordArray, int t) {
		var result = new int[coordArray.length];
		for (var x = 0 ; x < coordArray.length ; ++x)
			result[x] = (int) Math.sqrt(Math.pow(coordArray[t][0] - coordArray[x][0], 2)
					+ Math.pow(coordArray[t][1] - coordArray[x][1], 2));
		return result;
	}

	public static ShortestPathFromOneVertex astar(Graph graph, int s, int t, int[][] coord){
		// Initialiser f, g, h
		int[] f = new int[graph.numberOfVertices()];
		int[] g = new int[graph.numberOfVertices()];
		int[] h = initApproximateArray(coord, t);

		for(int tmp = 0; tmp < graph.numberOfVertices(); tmp++) {
			if(tmp != s) {
				f[tmp] = Integer.MAX_VALUE;
				g[tmp] = Integer.MAX_VALUE;
			}
		}

		var border = new ArrayList<Integer>();
		var computed = new ArrayList<Integer>();
		border.add(s);
		computed.add(s);


		while(!border.isEmpty()){
			int x = extractMin(border, f);
			if(x == t){
                return new ShortestPathFromOneVertex(f[x], g, f); // Vérifier que c'est bien ça
			}
			border.remove(x);
			graph.forEachEdge(x, y ->{
				if(computed.contains(y.getEnd())){
					if(g[y.getEnd()] > g[x] + graph.getWeight(x, y.getEnd())){
						g[y.getEnd()] = g[x] + graph.getWeight(x, y.getEnd());
						f[y.getEnd()] = g[y.getEnd()] + h[y.getEnd()];
						if(!border.contains(y.getEnd())){
							border.add(y.getEnd());
						}
					}
				}
				else{
					g[y.getEnd()] = g[x] + graph.getWeight(x, y.getEnd());
					f[y.getEnd()] = g[y.getEnd()] + h[y.getEnd()];
					border.add(y.getEnd());
					computed.add(y.getEnd());
				}
			});
		}
		return new ShortestPathFromOneVertex(s, g, f); // Vérifier que c'est bien g et f les arguments
	}
	
}
