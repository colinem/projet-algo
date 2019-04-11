package parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.umlv.info2.graphs.Graph;

public class Parser {

	public static Graph parse(Path path) throws IOException {
		
		Files.lines(path).map(line -> line.split(" ")).map(line -> {
			;
		});
		return null;

	}

}
