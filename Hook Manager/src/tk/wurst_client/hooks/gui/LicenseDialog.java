/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.Component;

public class LicenseDialog extends HTMLDialog
{
	public LicenseDialog(Component parent)
	{
		super(parent);
		setTitle("License");
		setResource("license.html");
		setBridge(new Bridge());
	}
	
	public class Bridge
	{
		public void exit()
		{
			LicenseDialog.this.dispose();
		}
	}
}
