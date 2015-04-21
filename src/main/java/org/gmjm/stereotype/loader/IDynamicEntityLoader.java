package org.gmjm.stereotype.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.gmjm.stereotype.entity.IDynamicEntity;

public interface IDynamicEntityLoader {

	public abstract IDynamicEntity loadFromFile(File dynamicItemFile)
			throws FileNotFoundException, IOException;

	public abstract List<IDynamicEntity> loadAllFromDirectory(
			File dynamicItemPropertiesDirectory) throws FileNotFoundException,
			IOException;

}