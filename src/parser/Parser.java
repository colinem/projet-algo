package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.umlv.info2.graphs.AdjGraph;
import fr.umlv.info2.graphs.Graph;

public class Parser {

	private static final Logger LOG = Logger.getLogger(Parser.class.getName());

	public static Graph parse(Path path) throws IOException {
		Graph graph = null;
		try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
			String line;
			while ((line = reader.readLine()) != null) {
				var tokens = line.split(" ");
				if ("p".equals(tokens[0])) {
					graph = new AdjGraph(Integer.parseInt(tokens[2]));
					break;
				}
			}
			final var g = graph;
			Files.lines(path).map(l -> l.split(" ")).forEach(t -> {
				if ("a".equals(t[0])) {
					var s = Integer.parseInt(t[1]) - 1; // -1 because in the file nodes begin at 1 whereas our data structure begins at 0.
					var d = Integer.parseInt(t[2]) - 1;
					try {
						g.addEdge(s, d, Integer.parseInt(t[3]));
					} catch (IllegalArgumentException e) {
						LOG.info("There is a duplicate line.");
					}
				}
			});
			graph = g;
		} catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			LOG.log(Level.SEVERE, "File path is ill-formed.", e);
		}
		return graph;

	}

}
