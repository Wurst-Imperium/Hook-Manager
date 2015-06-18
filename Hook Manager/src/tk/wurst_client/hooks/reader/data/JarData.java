/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import tk.wurst_client.hooks.util.Constants;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JarData
{
	HashMap<String, ClassData> classes = new HashMap<String, ClassData>();
	
	public ClassData getClass(String path)
	{
		return classes.get(path);
	}
	
	public ClassData addClass(String path, ClassData data)
	{
		return classes.put(path, data);
	}
	
	public ClassData removeClass(String path)
	{
		return classes.remove(path);
	}
	
	public void save(File file)
	{
		JsonObject json = new JsonObject();
		json.addProperty("version", Constants.HMS_VERSION);
		JsonObject jsonClasses = new JsonObject();
		Iterator<Entry<String, ClassData>> itr = classes.entrySet().iterator();
		while(itr.hasNext())
		{
			Entry<String, ClassData> entry = itr.next();
			if(entry.getValue().hasHooks())
				jsonClasses.add(entry.getKey(), entry.getValue().toJson());
		}
		json.add("classes", jsonClasses);
		try(FileOutputStream output = new FileOutputStream(file))
		{
			output.write(new GsonBuilder().setPrettyPrinting().create()
				.toJson(json).getBytes("UTF-8"));
		}catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void load(File file) throws IOException
	{
		JsonElement json = new JsonParser().parse(new FileReader(file));
		// TODO
	}
}
