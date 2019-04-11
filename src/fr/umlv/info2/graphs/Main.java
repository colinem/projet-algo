package fr.umlv.info2.graphs;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import parsers.Parser;

public class Main {

	public static void main(String[] args) {
		
		try {
			Graph.astar(Parser.parse(Path.of(args[0] + ".gr")), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		} catch (IndexOutOfBoundsException | InvalidPathException | NumberFormatException e) {
			usage();
		}

	}
	
	private static void usage() {
		System.out.println("arguments souhaites : nom_du_fichier_sans_extension num_sommet_source num_sommet_arrivee");
	}

}
