package fr.umlv.info2.graphs;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import parser.Parser;

public class Main {

	public static void main(String[] args) throws IOException {
		
		try {
			Graph.astar(Parser.parseGraph(Path.of("docs/" + args[0] + ".gr")), Integer.parseInt(args[1])-1, Integer.parseInt(args[2])-1, Parser.parseCoordonates(Path.of("docs/" + args[0] + ".co")));
		} catch (IndexOutOfBoundsException | InvalidPathException | NumberFormatException e) {
			usage();
		}

	}
	
	private static void usage() {
		System.out.println("arguments souhaites : nom_du_fichier_sans_extension num_sommet_source num_sommet_arrivee");
	}

}
