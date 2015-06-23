/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class Constants
{
	public static final String VERSION = "0.3";
	public static final String CURRENT_DIRECTORY;
	public static final String HMS_VERSION = "1.0";
	
	static
	{
		String currentDirectory = null;
		try
		{
			currentDirectory =
				URLDecoder.decode(Constants.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath(), "UTF-8");
			currentDirectory = new File(currentDirectory).getAbsolutePath();
		}catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		CURRENT_DIRECTORY = currentDirectory;
	}
	
	public static final class URLs
	{
		public static final String GITHUB_PAGE =
			"https://github.com/Wurst-Imperium/Hook-Manager";
		public static final String WEBSITE = "https://www.hook-manager.tk/";
		
		public static final String BUGS = GITHUB_PAGE + "/labels/bug";
		public static final String CONTRIBUTE = GITHUB_PAGE + "/fork";
		public static final String QUESTIONS = GITHUB_PAGE
			+ "/issues?q=label%3Aquestion";
		public static final String SUGGESTIONS = GITHUB_PAGE
			+ "/labels/enhancement";
		public static final String WIKI = GITHUB_PAGE + "/wiki";
	}
	
	public static final class Resources
	{
		public static final String HTML_DIR = "html/";
	}
	
	public static final class Files
	{
		public static final String GA_COOKIE = CURRENT_DIRECTORY
			+ File.separator + "ga-cookie.json";
	}
}
