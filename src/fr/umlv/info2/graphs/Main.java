package fr.umlv.info2.graphs;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

import parser.Parser;

public class Main {

	public static void main(String[] args) throws IOException {

		try {
			Optional<ShortestPathFromOneVertex> path = null;
			if (args.length == 1){
				var graph = Parser.parseGraph(Path.of("docs/" + args[0] + ".gr"));
				var coord = Parser.parseCoordonates(Path.of("docs/" + args[0] + ".co"));
				path = Graph.astar(graph, coord);

			}
			else if (args.length == 3){
				var graph = Parser.parseGraph(Path.of("docs/" + args[0] + ".gr"));
				var coord = Parser.parseCoordonates(Path.of("docs/" + args[0] + ".co"));
				path = Graph.astar(graph, Integer.parseInt(args[1])-1,
						Integer.parseInt(args[2])-1, coord);
			}

			else {
				usage();
				return;
			}
			
			path.ifPresentOrElse(p -> p.printShortestPath(),
					() -> System.out.println("The destination is not accessible from the source."));
			
		} catch (InvalidPathException | NumberFormatException e) {
			usage();
		}

	}

	private static void usage() {
		System.out.println("arguments souhaites : nom_du_fichier_sans_extension num_sommet_source num_sommet_arrivee");
	}

}
