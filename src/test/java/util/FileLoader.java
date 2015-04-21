package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class FileLoader {
	public static File load(String classpathLocation) {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(classpathLocation);

		if (url == null) {
			throw new RuntimeException(new FileNotFoundException(
					"File could not be found on classpath: "
							+ classpathLocation));
		}

		File file = new File(url.getFile());
		return file;
	}
}
