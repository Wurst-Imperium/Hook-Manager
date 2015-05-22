/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader.data;

public class HookData
{
	private final HookPosition position;
	// TODO: Option for collecting only some parameters
	private boolean collectParams;
	
	public HookData(HookPosition position)
	{
		this.position = position;
	}
	
	public HookData(HookPosition position, boolean collectParams)
	{
		this.position = position;
		this.collectParams = collectParams;
	}
	
	public boolean collectsParams()
	{
		return collectParams;
	}
	
	public void setCollectParams(boolean collectParams)
	{
		this.collectParams = collectParams;
	}
	
	public HookPosition getPosition()
	{
		return position;
	}
}
