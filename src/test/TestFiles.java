package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.security.Permission;

import java.util.Scanner;

import org.junit.*;
import static org.junit.Assert.*;

import crux.*;

public class TestFiles {

	/* Path to test files root dir. */
	private static final String fileRoot = "tests";

	/* Compiler to use. */
	private crux.Compiler compiler;

	/* Original output stream. */
	private PrintStream outOrg;

	/* Original err stream. */
	private PrintStream errOrg;

	/* Buffer stdout stream. */
	private ByteArrayOutputStream outBuffer;

	/* Buffer stderr stream. */
	private ByteArrayOutputStream errBuffer;

	/* Our output stream. */
	private PrintStream outStream;

	/* Our err stream. */
	private PrintStream errStream;

	@Before
	public void setUp() {
		compiler = new crux.Compiler();	
		outOrg = System.out;
		errOrg = System.err;
		outBuffer = new ByteArrayOutputStream();
		errBuffer = new ByteArrayOutputStream();
		outStream = new PrintStream(outBuffer);
		errStream = new PrintStream(errBuffer);
		// Prevent System.exit()s.
		System.setSecurityManager(new SysExitSecurityManager());
	}

	@After
	public void tearDown() {
		compiler = null;
		useOutBuffer(false);
		System.setSecurityManager(null);
	}

	// Lab 1 tests.
	@Test
	public void testLab1Public() {
		compiler.setLab(crux.Compiler.Lab.LAB1);
		testFilesIn("lab1/public");
	}

	@Test
	public void testLab1Private() {
		compiler.setLab(crux.Compiler.Lab.LAB1);
		testFilesIn("lab1/private");
	}

	@Test
	public void testLab1PrivateSecret() {
		compiler.setLab(crux.Compiler.Lab.LAB1);
		testFilesIn("lab1/private_secret");
	}


	// Lab 2 tests.
	@Test
	public void testLab2Public() {
		compiler.setLab(crux.Compiler.Lab.LAB2);
		testFilesIn("lab2/public");
	}

	@Test
	public void testLab2Private() {
		compiler.setLab(crux.Compiler.Lab.LAB2);
		testFilesIn("lab2/private");
	}

	@Test
	public void testLab2PrivateSecret() {
		compiler.setLab(crux.Compiler.Lab.LAB2);
		testFilesIn("lab2/private_secret");
	}

	// Lab 3 tests.
	@Test
	public void testLab3Public() {
		compiler.setLab(crux.Compiler.Lab.LAB3);
		testFilesIn("lab3/public");
	}

	@Test
	public void testLab3Private() {
		compiler.setLab(crux.Compiler.Lab.LAB3);
		testFilesIn("lab3/private");
	}

	@Test
		public void testLab3PrivateSecret() {
			compiler.setLab(crux.Compiler.Lab.LAB3);
			testFilesIn("lab3/private_secret");
		}

	// Lab 4 tests.
	@Test
	public void testLab4Public() {
		compiler.setLab(crux.Compiler.Lab.LAB4);
		testFilesIn("lab4/public");
	}

	@Test
	public void testLab4Private() {
		compiler.setLab(crux.Compiler.Lab.LAB4);
		testFilesIn("lab4/private");
	}

	@Test
	public void testLab4PrivateSecret() {
		compiler.setLab(crux.Compiler.Lab.LAB4);
		testFilesIn("lab4/private_secret");
	}

	// Lab 5 tests.
	@Test
	public void testLab5Public() {
		compiler.setLab(crux.Compiler.Lab.LAB5);
		testFilesIn("lab5/public");
	}

	@Test
	public void testLab5Private() {
		compiler.setLab(crux.Compiler.Lab.LAB5);
		testFilesIn("lab5/private");
	}

	// Lab 6 tests.
	@Test
	public void testLab6Public() {
		compiler.setLab(crux.Compiler.Lab.LAB6);
		testFilesIn("lab6/public");
	}

	@Test
	public void testLab6Private() {
		compiler.setLab(crux.Compiler.Lab.LAB6);
		testFilesIn("lab6/private");
	}

	// =========== Helper functions.

	/**
	 * Test all files found in this directory.
	 * @param subdir The directory to look in.
	 */
	private void testFilesIn(String subdir) {
		File dir = new File(fileRoot + "/" + subdir);
		String[] cruxFiles = dir.list(new FilenameFilter() {
 	 	 	public boolean accept(File dir, String name) {
 	 	 		return name.matches(".*\\.crx$");
 	 	 	}
		});

		for (String cruxFile: cruxFiles) {
			String[] nameParts = cruxFile.split("\\.");
			if (nameParts.length != 2) {
				System.err.println("File format changed?");
				System.exit(1);
			}
			String outFile = nameParts[0] + ".out";
			testFile(fileRoot + "/" + subdir + "/" + cruxFile, fileRoot + "/" + subdir + "/" + outFile);
		}
		//System.out.printf("In %s: ", subdir);
		//System.out.printf("All tests files passed!\n");
	}

	/**
	 * Test all MIPOS files found in this directory.
	 * @param subdir The directory to look in.
	 */
	private void testMIPSFilesIn(String subdir) {
		File dir = new File(fileRoot + "/" + subdir);
		String partPath = fileRoot + "/" + subdir + "/";
		String[] cruxFiles = dir.list(new FilenameFilter() {
 	 	 	public boolean accept(File dir, String name) {
 	 	 		return name.matches(".*\\.crx$");
 	 	 	}
		});

		for (String cruxFile: cruxFiles) {
			String[] nameParts = cruxFile.split("\\.");
			if (nameParts.length != 2) {
				System.err.println("File format changed?");
				System.exit(1);
			}
			String inFile = nameParts[0] + ".in";
			String outFile = nameParts[0] + ".out";
			testMIPSFile(partPath + cruxFile, partPath + inFile, partPath + outFile);
		}
	}

	/**
	 * Compile input file and compare with reference.
	 * @param cruxFileName The input crux file.
	 * @param outFileName The reference expected output.
	 */
	private void testFile(String cruxFileName, String outFileName) {
		System.out.printf("Testing crux file \"%s\", with the expected output in \"%s\"\n", cruxFileName, outFileName);
		useOutBuffer(true);
		SysExitException exitException = null;
		try {
			compiler.compile(cruxFileName);
		} catch (SysExitException see) {
			exitException = see;
		}
		String actual = outBuffer.toString();
		String errStr = errBuffer.toString();
		useOutBuffer(false);

		String expected = slurpFile(outFileName);
		actual = actual.replaceAll("\\r", ""); // So tests can be run under Windoze.

		if (!expected.equals(actual)) {
			StringBuilder errBuilder = new StringBuilder();
			errBuilder.append("Wrong compiler output.\n");
			if (exitException != null) {
				errBuilder.append("(cux-)Compilation caused a System.exit(");
				errBuilder.append(exitException.getExitCode()).append(")\n");
			}
			if (!errStr.isEmpty()) {
				errBuilder.append(String.format("Compiler gave this stderr output: {\n%s\n}\n", errStr));
			}
			// Use JUnits smarter diff displayer.
			//errBuilder.append(String.format("exp={\n%s\n}\nact={\n%s\n}\n", expected, actual));
			assertEquals(errBuilder.toString(), expected, actual);
		}
	}

	/**
	 * Compile input file and compare with MIPS reference.
	 * @param cruxFileName The input crux file.
	 * @param inFileName The inut to the program.
	 * @param outFileName The reference expected output.
	 */
	private void testMIPSFile(String cruxFileName, String inFileName, String outFileName) {
		System.out.printf("Testing crux file \"%s\", with input from \"%s\" and expected output in \"%s\"\n", cruxFileName, inFileName, outFileName);
		useOutBuffer(true);
		SysExitException exitException = null;
		try {
			compiler.compile(cruxFileName);
		} catch (SysExitException see) {
			exitException = see;
		}
		String stdout = outBuffer.toString();
		String errStr = errBuffer.toString();
		useOutBuffer(false);
		assertEquals(stdout, ""); // No errors should occur.
		
		String expected = slurpFile(outFileName);

		// run spim
		Runtime env = Runtime.getRuntime();
		Process spim = null;
		RandomAccessFile inFile = null;
		byte[] spimInput = null;
		try {
			spim = env.exec("spim -file " + cruxFileName);
			inFile = new RandomAccessFile(inFileName, "r");
			spimInput =  new byte[(int) inFile.length()];
			inFile.read(spimInput);
		} catch (IOException ioe) {
			fail(ioe.getMessage());
		}

		OutputStream spimOutStr = spim.getOutputStream();
		InputStream spimInStr = spim.getInputStream();
		InputStream spimErrStr = spim.getErrorStream();

		try {
			spimOutStr.write(spimInput);
			spimOutStr.flush();
			inFile.close();
			spim.waitFor();
		} catch (IOException ioe) {
			fail(ioe.getMessage());
		} catch (InterruptedException ie) {
			fail(ie.getMessage());
		}

		String actual = slurpStream(spimInStr);

		if (!expected.equals(actual)) {
			StringBuilder errBuilder = new StringBuilder();
			errBuilder.append("Wrong compiler output.\n");
			if (exitException != null) {
				errBuilder.append("(cux-)Compilation caused a System.exit(");
				errBuilder.append(exitException.getExitCode()).append(")\n");
			}
			if (!errStr.isEmpty()) {
				errBuilder.append(String.format("Compiler gave this stderr output: {\n%s\n}\n", errStr));
			}
			try {
				if (spimErrStr.available() != 0) {
					String errString = slurpStream(spimErrStr);
					errBuilder.append("Spim gave this on stderr" + errString);
				}
			} catch (IOException ioe) {
				fail(ioe.getMessage());
			}

			// Use JUnits smarter diff displayer.
			//errBuilder.append(String.format("exp={\n%s\n}\nact={\n%s\n}\n", expected, actual));
			assertEquals(errBuilder.toString(), expected, actual);
		}
	}

	/**
	 * Read the contents from a stream to a string
	 * @param inStream The stream to read from.
	 * @return The read string.
	 */
	public String slurpStream(InputStream inStream) {
		Scanner scanner = new Scanner(inStream);
		return scanner.useDelimiter("\\Z").next();
	}

	/**
	 * Read file to string and fix linefeed.
	 * @param fileName the file to read.
	 */
	private String slurpFile(String fileName) {
		File outFile = new File(fileName);
		java.util.Scanner outScanner = null;
		try {
			outScanner = new java.util.Scanner(outFile);
		} catch (FileNotFoundException fnfe) {
			System.err.printf("Bad fileName \"%s\"!\n", fileName);
			fail();
			return null;
		}
		String expected = outScanner.useDelimiter("\\Z").next();
		expected += '\n'; // Needed apparently.
		return expected;
	}

	/**
	 * Toggles the output buffers.
	 * @param active If the buffers should be used or not.
	 */
	private void useOutBuffer(boolean active) {
		if (active) {
			System.setOut(outStream);
			System.setErr(errStream);
		} else {
			try {
				outBuffer.flush();
				errBuffer.flush();
			} catch (IOException ioe) {
				System.err.println("Could not flush buffers.");
				System.exit(1);
			}
			outBuffer.reset();
			errBuffer.reset();
			System.setOut(outOrg);
			System.setErr(errOrg);
		}
	}


	/**
	 * A SecurityManager that allows catches System.exit() events and throws SysExitException instead of exiting.
	 */
	private static class SysExitSecurityManager extends SecurityManager {
    	public void checkPermission(Permission perm) {
    		// Allow all.
    	}

    	public void checkPermission(Permission perm, Object context) {
    		// Allow all.
    	}

		/**
		 * Throw SysExitException instead of exiting.
		 * @param exitCode The exit code.
		 */
    	public void checkExit(int exitCode) {
    		super.checkExit(exitCode);
    		throw new SysExitException(exitCode);
    	}
	}

	/**
	 * Exception thrown when the program tried to do a System.exit().
	 */
	private static class SysExitException extends SecurityException {
		private static final long serialVersionUID = 1L;

		/* The exit code that was used during System.exit(). */
		private int exitCode;
		/* Construct a exception with an exitcode.
		 * @param exitCode The exit code that was used in System.exit();
		 */
		SysExitException(int exitCode) {
			super("No exit'ing here!");
			this.exitCode = exitCode;
		}

		/**
		 * Get the exit code.
		 * @return The exit code.
		 */
		public int getExitCode() {
			return exitCode;
		}
	}
}
