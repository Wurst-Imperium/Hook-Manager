/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader.data;

import java.util.HashMap;
import java.util.Set;

public class ClassData
{
	private HashMap<String, MethodData> methods =
		new HashMap<String, MethodData>();
	private int hooks;
	
	public MethodData getMethod(String name)
	{
		return methods.get(name);
	}
	
	public MethodData addMethod(String name, MethodData data)
	{
		return methods.put(name, data);
	}
	
	public MethodData removeMethod(String name)
	{
		return methods.remove(name);
	}
	
	public Set<String> getMethodNames()
	{
		return methods.keySet();
	}
	
	public boolean hasHooks()
	{
		return hooks > 0;
	}
	
	protected void addHook()
	{
		hooks++;
	}
	
	protected void removeHook()
	{
		hooks--;
	}
}
