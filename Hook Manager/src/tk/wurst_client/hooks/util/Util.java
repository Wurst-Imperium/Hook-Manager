/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.util;

import java.awt.Desktop;
import java.net.URI;

public class Util
{	
	public static void openInBrowser(String url)
	{
		try
		{
			Desktop.getDesktop().browse(new URI(url));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
