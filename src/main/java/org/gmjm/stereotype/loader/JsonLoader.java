package org.gmjm.stereotype.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.gmjm.stereotype.entity.HashMapDynamicEntity;
import org.gmjm.stereotype.entity.IDynamicEntity;
import org.gmjm.stereotype.property.IPropertyDescriptor;


public class JsonLoader implements IDynamicEntityLoader {

	Collection<IPropertyDescriptor> buildableProperties = new ArrayList<>();

	IJsonToJavaMapper jsonToJavaMapper;
	
	public JsonLoader(IJsonToJavaMapper jsonToJavaMapper, Collection<IPropertyDescriptor> buildableProperties) {
		this.jsonToJavaMapper = jsonToJavaMapper;
		this.buildableProperties.addAll(buildableProperties);
	}

	@Override
	public IDynamicEntity loadFromFile(File jsonFile) throws FileNotFoundException, IOException {
		
		JsonReader jsonReader = Json.createReader(new FileInputStream(jsonFile));
		JsonObject rootJsonObject = jsonReader.readObject();

		IDynamicEntity dynamicEntity = new HashMapDynamicEntity();

		for (IPropertyDescriptor propDescriptor : buildableProperties) {
			jsonToJavaMapper.createAndMap(rootJsonObject, dynamicEntity, propDescriptor);
		}

		return dynamicEntity;
	}



	@Override
	public List<IDynamicEntity> loadAllFromDirectory(
			File dynamicItemPropertiesDirectory) throws FileNotFoundException,
			IOException {
		List<IDynamicEntity> loadedEntities = new ArrayList<>();

		for (File file : dynamicItemPropertiesDirectory.listFiles())
			loadedEntities.add(loadFromFile(file));

		return loadedEntities;
	}

}
