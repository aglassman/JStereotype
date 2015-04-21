package org.gmjm.stereotype.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface IStereotypeLoader<T> {
	T loadFromFile(File file) throws FileNotFoundException, IOException;
	Collection<T> loadAllFromDirectory(File directory) throws FileNotFoundException, IOException;
}
