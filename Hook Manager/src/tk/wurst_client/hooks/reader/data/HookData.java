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
	// TODO: Option for collecting only some parameters
	private boolean collectParams;
	
	public HookData()
	{
		this(false);
	}

	public HookData(boolean collectParams)
	{
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
}
