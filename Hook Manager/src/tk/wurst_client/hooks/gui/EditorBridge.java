/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import tk.wurst_client.hooks.reader.data.ClassData;
import tk.wurst_client.hooks.reader.data.HookData;
import tk.wurst_client.hooks.reader.data.HookPosition;
import tk.wurst_client.hooks.reader.data.MethodData;

public class EditorBridge
{
	private ClassData classData;
	private HTMLPanel editor;
	private String className;
	
	public EditorBridge(HTMLPanel editor)
	{
		this.editor = editor;
	}
	
	public void setClassData(String className, ClassData classData)
	{
		this.className = className;
		this.classData = classData;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public String[] getMethodNames()
	{
		if(classData != null)
			return classData.getMethodNames().toArray(
				new String[classData.getMethodNames().size()]);
		else
			return null;
	}
	
	public void showSelectClassMessage()
	{
		editor.setHTMLFile("editor-message.html");
		editor
			.doWhenFinished(() -> editor
				.executeScriptAsync("setMessage('Select a class to start injecting hooks.')"));
	}
	
	public void toggleHook(String methodName, String position)
	{
		MethodData methodData = classData.getMethod(methodName);
		HookPosition pos = HookPosition.valueOf(position);
		if(methodData.hasHookAt(pos))
			methodData.removeHook(pos);
		else
			methodData.addHook(pos, new HookData());
	}
	
	public boolean hasHookAt(String methodName, String position)
	{
		return classData.getMethod(methodName).hasHookAt(
			HookPosition.valueOf(position));
	}
	
	public void toggleCollectParams(String methodName, String position)
	{
		HookData hook =
			classData.getMethod(methodName).getHook(
				HookPosition.valueOf(position));
		hook.setCollectParams(!hook.collectsParams());
	}
}
