package backupTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PrinterMain {

	public static void main(final String[] args) {

		final PrintFiles pf = new PrintFiles();
		final Path source = Paths.get(args[0]);
		try {
			Files.walkFileTree(source, pf);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
