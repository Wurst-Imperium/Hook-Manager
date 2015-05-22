/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader.data;

import java.util.HashMap;

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
}
