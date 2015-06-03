/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.analytics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

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
			BufferedReader load =
				new BufferedReader(new FileReader(Constants.Files.GA_COOKIE));
			cookie = gson.fromJson(load, AnalyticsCookie.class);
			load.close();
		}catch(Exception e)
		{
			cookie = new AnalyticsCookie();
		}finally
		{
			saveCookie();
		}
	}
	
	public static void saveCookie()
	{
		try
		{
			PrintWriter save =
				new PrintWriter(new FileWriter(Constants.Files.GA_COOKIE));
			save.println(gson.toJson(cookie));
			save.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
