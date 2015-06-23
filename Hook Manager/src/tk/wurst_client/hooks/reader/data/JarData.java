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

import javax.swing.JOptionPane;

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
		JsonObject json =
			new JsonParser().parse(new FileReader(file)).getAsJsonObject();
		checkCompatibility(json.get("version").getAsString().split("\\."));
		JsonObject jsonClasses = json.get("classes").getAsJsonObject();
		Iterator<Entry<String, JsonElement>> classItr =
			jsonClasses.entrySet().iterator();
		while(classItr.hasNext())
		{
			Entry<String, JsonElement> classEntry = classItr.next();
			if(!classes.containsKey(classEntry.getKey()))
				throw new IOException("Unknown class: " + classEntry.getKey());
			ClassData classData = classes.get(classEntry.getKey());
			JsonObject jsonMethods =
				classEntry.getValue().getAsJsonObject()
					.getAsJsonObject("methods");
			Iterator<Entry<String, JsonElement>> methodItr =
				jsonMethods.entrySet().iterator();
			while(methodItr.hasNext())
			{
				Entry<String, JsonElement> methodEntry = methodItr.next();
				if(!classData.hasMethod(methodEntry.getKey()))
					throw new IOException("Unknown method: "
						+ methodEntry.getKey());
				MethodData methodData =
					classData.getMethod(methodEntry.getKey());
				JsonObject jsonHooks =
					methodEntry.getValue().getAsJsonObject()
						.getAsJsonObject("hooks");
				Iterator<Entry<String, JsonElement>> hookItr =
					jsonHooks.entrySet().iterator();
				while(hookItr.hasNext())
				{
					Entry<String, JsonElement> hookEntry = hookItr.next();
					if(HookPosition.valueOf(hookEntry.getKey()) == null)
						throw new IOException("Unknown hook position: "
							+ hookEntry.getKey());
					HookData hookData =
						new HookData(hookEntry.getValue().getAsJsonObject()
							.get("collectsParams").getAsBoolean());
					methodData.addHook(
						HookPosition.valueOf(hookEntry.getKey()), hookData);
				}
			}
			
		}
	}
	
	private void checkCompatibility(String[] fileVersion) throws IOException
	{
		for(int i = 0; i < fileVersion.length; i++)
		{
			try
			{
				Integer.parseInt(fileVersion[i]);
			}catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null,
					"An error occurred while trying to check the version of\n"
						+ "this HMS file. The file is either broken or\n"
						+ "incompatible with this Hook Manager version.",
					"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				throw new IOException();
			}
		}
		
		String[] hmsVersion = Constants.HMS_VERSION.split("\\.");
		if(!fileVersion[0].equals(hmsVersion[0]))
		{
			JOptionPane
				.showMessageDialog(
					null,
					"This HMS file requires "
						+ (Integer.parseInt(fileVersion[0]) > Integer
							.parseInt(hmsVersion[0]) ? "a newer" : "an older")
						+ " Hook Manager\nversion than the one you have installed.",
					"Incompatible File", JOptionPane.ERROR_MESSAGE);
			throw new IOException();
		}
		
		if(Integer.parseInt(fileVersion[1]) > Integer.parseInt(hmsVersion[1]))
		{
			JOptionPane.showMessageDialog(null,
				"This HMS file was created with a newer Hook Manager version\n"
					+ "than the one you have installed. Not all of the\n"
					+ "features in this file can be applied.",
				"Partially Incompatible File", JOptionPane.WARNING_MESSAGE);
		}
	}
}
