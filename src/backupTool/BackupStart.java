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

import pathValidation.PathValidator;
import validation.contract.IValidation;

/**
 * Checks given paths validity and copies files
 * 
 * @author Roberto Cuervo
 * 
 */
public class BackupStart {

	private static void checkArgs(final String[] args) throws ArrayIndexOutOfBoundsException {
		if (args.length < 1 && args.length < 2) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void copyFiles(final String[] args) {
		final IValidation pathValidator = new PathValidator();
		try {
			BackupStart.checkArgs(args);
			if (pathValidator.isValidPath(args[0]) && pathValidator.isValidPath(args[1])) {
				final Path source = Paths.get(args[0]);
				final Path target = Paths.get(args[1]);
				final PrintFiles pf = new PrintFiles();
				Files.walkFileTree(source, pf);
				Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
						Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
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
									System.err.println("this file don't exists: "
											+ dir.getFileName());
								}
								return CONTINUE;
							}

							@Override
							public FileVisitResult visitFile(final Path file,
									final BasicFileAttributes attrs) throws IOException {
								Files.deleteIfExists(target.resolve(source.relativize(file)));
								Files.copy(file, target.resolve(source.relativize(file)));
								return CONTINUE;
							}
						});

			}
		} catch (final ArrayIndexOutOfBoundsException e) {
			System.err.println("Source or target path not exists");

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
