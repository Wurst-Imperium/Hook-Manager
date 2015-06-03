/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.analytics;

import java.io.FileReader;
import java.io.FileWriter;

import tk.wurst_client.hooks.util.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AnalyticsCookieManager
{
	private static AnalyticsCookie cookie;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
		.create();
	
	public static AnalyticsCookie getCookie()
	{
		if(cookie == null)
			loadCookie();
		return cookie;
	}
	
	private static void loadCookie()
	{
		try
		{
			cookie =
				gson.fromJson(new FileReader(Constants.Files.GA_COOKIE),
					AnalyticsCookie.class);
		}catch(Exception e)
		{
			cookie = new AnalyticsCookie();
		}
	}
	
	public static void saveCookie()
	{
		try
		{
			new FileWriter(Constants.Files.GA_COOKIE)
				.write(gson.toJson(cookie));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
