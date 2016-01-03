/**
 * 
 */
package fr.tbr.iamcore.tests.services.dao.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.tbr.iamcore.datamodel.Identity;
import fr.tbr.iamcore.services.dao.IdentityDAO;
import fr.tbr.iamcore.tests.services.match.Matcher;
import fr.tbr.iamcore.tests.services.match.impl.StartsWithIdentityMatchStrategy;

/**
 * @author tbrou
 *
 */
public class IdentityFileDAO implements IdentityDAO{

	// TODO change this path, make it configurable
	private final static String filePath = "/tests/iam/identities.txt";

	// Writer to insert identities into the file
	private PrintWriter writer;

	// Used to get the data from the file
	private Scanner scanner;

	// TODO we would like this to be configuration driven
	private Matcher<Identity> matcher = new StartsWithIdentityMatchStrategy();

	public IdentityFileDAO() throws Exception{
			File file = ensureFileExists(filePath);
			// open in write mode, but with append directive activated (the
			// "true"
			// parameter)
			initIO(file);
	

	}

	@Override
	public void create(Identity identity) {
		writeAnIdentity(identity, this.writer);
	}

	@Override
	public List<Identity> search(Identity criteria) {
		// Create the result structure
		List<Identity> identities = new ArrayList<Identity>();

		// While we have some remaining lines to read
		while (this.scanner.hasNext()) {
			Identity identity = readAnIdentity();

			// check if the identity is matching the criteria
			if (this.matcher.match(criteria, identity)) {
				// put that newly created identity in the list
				identities.add(identity);
			}

		}
		// reset the scanner so we will be able to read from the beginning of
		// the file in the next call
		resetScanner();
		return identities;

	}

	public List<Identity> readAll() {
		// Create the result structure
		List<Identity> identities = new ArrayList<Identity>();

		// While we have some remaining lines to read
		while (this.scanner.hasNext()) {
			Identity identity = readAnIdentity();
			// put that newly created identity in the list
			identities.add(identity);
		}
		// reset the scanner so we will be able to read from the beginning of
		// the file in the next call
		resetScanner();
		return identities;
	}

	@Override
	public void update(Identity identity) throws IOException {
		// be ready to copy into a new file
		File newFile = ensureFileExists(filePath + "-new");
		PrintWriter newWriter = new PrintWriter(newFile);
		// 1 - *
		copyIdentitiesExcept(identity, newWriter);

		// 2 - append the updated version of the identity
		writeAnIdentity(identity, newWriter);

		// 3 - Close everything and delete the file
		this.close();
		newWriter.close();

		// 4 - *
		File file = replaceFile(new File(filePath), newFile);

		initIO(file);
	}

	@Override
	public void delete(Identity identity) throws IOException {
		// be ready to copy into a new file
		File newFile = ensureFileExists(filePath + "-new");
		PrintWriter newWriter = new PrintWriter(newFile);

		// 1 - *
		copyIdentitiesExcept(identity, newWriter);

		// 3 - Close everything and delete the file
		this.close();
		newWriter.close();

		File file = replaceFile(new File(filePath), newFile);

		initIO(file);
	}

	private void initIO(File file) throws FileNotFoundException {
		this.writer = new PrintWriter(new FileOutputStream(file, true));
		this.scanner = new Scanner(file);
	}

	private void writeAnIdentity(Identity identity, PrintWriter aWriter) {
		aWriter.println("--- identity:begin ---");
		aWriter.println(identity.getDisplayName());
		aWriter.println(identity.getEmailAddress());
		aWriter.println(identity.getUid());
		aWriter.println("--- identity:end ---");
		aWriter.flush();
	}

	private void resetScanner() {
		this.scanner.close();
		try {
			this.scanner = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Identity readAnIdentity() {
		this.scanner.nextLine(); // we do nothing with this line because it is
		// "--- identity:begin ---"
		String displayName = this.scanner.nextLine();
		String emailAddress = this.scanner.nextLine();
		String uid = this.scanner.nextLine();
		this.scanner.nextLine(); // we do nothing with this line because it is
		// "--- identity:end ---"
		// create an identity with the read properties
		Identity identity = new Identity(displayName, emailAddress, uid);
		return identity;
	}

	private void copyIdentitiesExcept(Identity identity, PrintWriter newWriter) {
		// 1 - recopy everything except the given identity
		while (this.scanner.hasNext()) {
			Identity readIndentity = readAnIdentity();
			if (!readIndentity.getUid().equals(identity.getUid())) {
				writeAnIdentity(readIndentity, newWriter);
			}
		}
		resetScanner();
	}

	public void close() {
		this.scanner.close();
		this.writer.close();
	}

	private File replaceFile(File oldFile, File newFile) throws IOException {
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);

		// 4 - move the new file to the old file location
		Files.move(newFile.toPath(), oldFilePath);
		return oldFile;
	}

	/**
	 * This method will check that the file exists or create it if it doesn't
	 * 
	 * @param pathname
	 * @throws IOException
	 */
	public static File ensureFileExists(String pathname) throws IOException {
		File file = new File(pathname);
		if (!file.exists()) {
			// creation code after
			System.out.println("the file does not exists");
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			file.createNewFile();
			System.out.println("file was successfully created");
		} else {
			System.out.println("the file already exists");
		}
		return file;
	}

}
