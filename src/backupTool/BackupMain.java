package backupTool;

/**
 * @author Roberto Cuervo
 * 
 */
public class BackupMain {

	public static void main(final String[] args) {
		final BackupStart backup = new BackupStart();
		backup.copyFiles(args);
	}
}
