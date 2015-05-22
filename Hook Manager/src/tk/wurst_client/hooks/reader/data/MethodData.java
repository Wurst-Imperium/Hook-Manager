/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader.data;

import java.util.HashMap;

public class MethodData
{
	HashMap<String, HookData> hooks = new HashMap<String, HookData>();
	
	public HookData getHook(String name)
	{
		return hooks.get(name);
	}
	
	public HookData addHook(String name, HookData data)
	{
		return hooks.put(name, data);
	}
	
	public HookData removeHook(String name)
	{
		return hooks.remove(name);
	}
}
