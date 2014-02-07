package pathValidation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import validation.contract.IValidation;

public class PathValidator implements IValidation {

	public boolean isValidPath(final String path) {
		boolean isValidPath = false;
		if (path == null || path == "") {
			System.out.println("Error, no valid input");
			return false;
		} else {
			final Path fileToCheck = Paths.get(path);
			if (fileToCheck != null) {
				isValidPath = Files.exists(fileToCheck) & !Files.notExists(fileToCheck)
						& Files.isWritable(fileToCheck) & Files.isReadable(fileToCheck)
						& Files.isExecutable(fileToCheck);
			}
			if (!isValidPath) {
				System.err.println("Wrong path: " + path);
			}
			return isValidPath;
		}
	}
}
