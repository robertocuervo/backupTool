package backupTool;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PrintFiles extends SimpleFileVisitor<Path> {

	// Print each directory visited.
	@Override
	public FileVisitResult postVisitDirectory(final Path dir,
			final IOException exc) {
		System.out.format("Directory: %s%n", dir);
		return CONTINUE;
	}

	// Print information about
	// each type of file.
	@Override
	public FileVisitResult visitFile(final Path file,
			final BasicFileAttributes attr) {
		if (attr.isSymbolicLink()) {
			System.out.format("Symbolic link: %s ", file);
		} else if (attr.isRegularFile()) {
			System.out.format("Regular file: %s ", file);
		} else {
			System.out.format("Other: %s ", file);
		}
		System.out.println("(" + attr.size() + " bytes)");
		return CONTINUE;
	}

	// If there is some error accessing
	// the file, let the user know.
	// If you don't override this method
	// and an error occurs, an IOException
	// is thrown.
	@Override
	public FileVisitResult visitFileFailed(final Path file,
			final IOException exc) {
		System.err.println(exc);
		return CONTINUE;
	}
}