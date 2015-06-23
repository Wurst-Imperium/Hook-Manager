/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks;

import java.util.HashMap;
import java.util.HashSet;

public class HookManager
{
	private static HashMap<String, HashSet<Hook>> hooks = new HashMap<>();
	
	public static void hook(String method)
	{
		hook(method, null);
	}
	
	public static void hook(String method, Object[] params)
	{
		if(hooks.containsKey(method))
			for(Hook hook : hooks.get(method))
				hook.hook(params);
	}
	
	public static void addHook(String method, Hook hook)
	{
		if(!hooks.containsKey(method))
			hooks.put(method, new HashSet<>());
		hooks.get(method).add(hook);
	}
	
	public static void removeHook(String method, Hook hook)
	{
		hooks.get(method).remove(hook);
	}
	
	public static interface Hook
	{
		public void hook(Object[] params);
	}
}
