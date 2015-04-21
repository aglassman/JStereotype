package org.gmjm.stereotype.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.gmjm.stereotype.IEntityStereotyper;

public class StereotypeLoader<T> implements IStereotypeLoader<T>{

	private final IEntityStereotyper<T> stereotyper;
	private final IDynamicEntityLoader loader;
	
	public StereotypeLoader(IEntityStereotyper<T> stereotyper, IDynamicEntityLoader loader) {
		this.stereotyper = stereotyper;
		this.loader = loader;
	}
	
	@Override
	public T loadFromFile(File file) throws FileNotFoundException, IOException {
		return stereotyper.stereotype(loader.loadFromFile(file));
	}

	@Override
	public Collection<T> loadAllFromDirectory(File directory) throws FileNotFoundException, IOException {
		return loader.loadAllFromDirectory(directory).stream()
			.map(dynamicEntity -> stereotyper.stereotype(dynamicEntity))
			.collect(Collectors.toList());
	}

}
