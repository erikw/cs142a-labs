package test;

import java.io.File;

import org.junit.*;
import static org.junit.Assert.*;

public class TestFiles {

	/* Path to test files root dir. */
	private static final String fileRoot = "tests";

	public TestFiles() {

	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testPublicFiles() {
		File dir = new File(fileRoot + "/public");
		String[] files = dir.list();
		for (String file: files) {
			System.out.println(file);
		}
	}

}
