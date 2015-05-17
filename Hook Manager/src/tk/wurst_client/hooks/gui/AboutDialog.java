/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.Component;

import tk.wurst_client.hooks.util.Constants;
import tk.wurst_client.hooks.util.Util;
import tk.wurst_client.update.Updater;

public class AboutDialog extends HTMLDialog
{
	public AboutDialog(Component parent)
	{
		super(parent);
		setTitle("About Hook Manager");
		setResource("tk/wurst_client/hooks/gui/html/about.html");
		setBridge(new Bridge());
	}
	
	public class Bridge
	{
		public String getVersion()
		{
			return Updater.CURRENT_VERSION;
		}
		
		public String getWebsite()
		{
			return Constants.URLs.WEBSITE;
		}
		
		public void exit()
		{
			AboutDialog.this.dispose();
		}
		
		public void openWebsite()
		{
			Util.openInBrowser(Constants.URLs.WEBSITE);
		}
	}
}
