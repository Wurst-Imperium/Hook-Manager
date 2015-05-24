/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import tk.wurst_client.hooks.reader.data.ClassData;

public class EditorBridge
{
	private ClassData classData;
	
	public void setClassData(ClassData classData)
	{
		this.classData = classData;
	}
	
	public String[] getMethodNames()
	{
		if(classData != null)
			return classData.getMethodNames().toArray(
				new String[classData.getMethodNames().size()]);
		else
			return null;
	}
}
