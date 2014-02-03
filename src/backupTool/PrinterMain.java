package backupTool;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class PrinterMain {

	public static void main(final String[] args) {
		final Path source = Paths.get(args[0]);
		final Path target = Paths.get(args[1]);
		final PrintFiles pf = new PrintFiles();
		try {
			Files.walkFileTree(source, pf);
			Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
					new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult preVisitDirectory(final Path dir,
								final BasicFileAttributes attrs) throws IOException {
							final Path targetdir = target.resolve(source.relativize(dir));
							try {
								Files.copy(dir, targetdir);
							} catch (final FileAlreadyExistsException e) {
								if (!Files.isDirectory(targetdir)) {
									throw e;
								}

							} catch (final NoSuchFileException e) {
								System.err.println("this file don't exists: " + dir.getFileName());
							}
							return CONTINUE;
						}

						@Override
						public FileVisitResult visitFile(final Path file,
								final BasicFileAttributes attrs) throws IOException {
							// Files.deleteIfExists(file);
							Files.copy(file, target.resolve(source.relativize(file)));
							return CONTINUE;
						}
					});

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
