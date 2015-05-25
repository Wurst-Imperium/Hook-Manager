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
	private HashMap<HookPosition, HookData> hooks =
		new HashMap<HookPosition, HookData>();
	
	public HookData getHook(HookPosition pos)
	{
		return hooks.get(pos);
	}
	
	public HookData addHook(HookPosition pos, HookData data)
	{
		return hooks.put(pos, data);
	}
	
	public HookData removeHook(HookPosition pos)
	{
		return hooks.remove(pos);
	}
}
