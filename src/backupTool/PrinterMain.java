package backupTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PrinterMain {

	public static void main(String[] args) {

		PrintFiles pf = new PrintFiles();
		Path source = Paths.get(args[0]);
		try {
			Files.walkFileTree(source, pf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void print() {
		System.out.println("hello");
	}

}
