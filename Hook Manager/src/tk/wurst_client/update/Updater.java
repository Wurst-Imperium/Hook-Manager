/*
 * Copyright © 2014 - 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import tk.wurst_client.hooks.util.Constants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Updater
{
	
	private int currentMajor;
	private int currentMinor;
	private int currentPatch;
	private int currentPreRelease;
	
	private String latestVersion;
	private int latestMajor;
	private int latestMinor;
	private int latestPatch;
	private int latestPreRelease;
	
	private boolean outdated;
	private JsonArray json;
	private File currentDirectory;
	
	private static File update;
	private static ProgressDialog progress;
	
	public void checkForUpdate()
	{
		try
		{
			outdated = false;
			try
			{
				String[] parts = Constants.VERSION.split("\\.");
				currentMajor = Integer.parseInt(parts[0]);
				if(Constants.VERSION.contains("pre"))
					currentPreRelease =
						Integer.parseInt(Constants.VERSION
							.substring(Constants.VERSION.indexOf("pre") + 3));
				else
					currentPreRelease = 0;
				if(parts.length > 2)
					if(currentPreRelease == 0)
						currentPatch = Integer.parseInt(parts[2]);
					else
						currentPatch =
							Integer.parseInt(parts[2].substring(0,
								parts[2].indexOf("pre")));
				else
					currentPatch = 0;
				if(currentPreRelease == 0 || currentPatch > 0)
					currentMinor = Integer.parseInt(parts[1]);
				else
					currentMinor =
						Integer.parseInt(parts[1].substring(0,
							parts[1].indexOf("pre")));
			}catch(Exception e)
			{
				System.err.println("Current version \"" + Constants.VERSION
					+ "\" doesn't follow the semver.org syntax!");
				e.printStackTrace();
			}
			HttpsURLConnection connection =
				(HttpsURLConnection)new URL(
					"https://api.github.com/repos/Wurst-Imperium/Hook-Manager/releases")
					.openConnection();
			BufferedReader load =
				new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String content = load.readLine();
			for(String line = ""; (line = load.readLine()) != null;)
				content += "\n" + line;
			load.close();
			json = new JsonParser().parse(content).getAsJsonArray();
			JsonObject latestRelease = new JsonObject();
			for(JsonElement release : json)
				if(!release.getAsJsonObject().get("prerelease").getAsBoolean()
					|| currentPreRelease > 0)
				{
					latestRelease = release.getAsJsonObject();
					break;
				}
			try
			{
				latestVersion =
					latestRelease.get("tag_name").getAsString().substring(1);
				String[] parts = latestVersion.split("\\.");
				latestMajor = Integer.parseInt(parts[0]);
				if(latestVersion.contains("pre"))
					latestPreRelease =
						Integer.parseInt(latestVersion.substring(latestVersion
							.indexOf("pre") + 3));
				else
					latestPreRelease = 0;
				if(parts.length > 2)
					if(latestPreRelease == 0)
						latestPatch = Integer.parseInt(parts[2]);
					else
						latestPatch =
							Integer.parseInt(parts[2].substring(0,
								parts[2].indexOf("pre")));
				else
					latestPatch = 0;
				if(latestPreRelease == 0 || latestPatch > 0)
					latestMinor = Integer.parseInt(parts[1]);
				else
					latestMinor =
						Integer.parseInt(parts[1].substring(0,
							parts[1].indexOf("pre")));
			}catch(Exception e)
			{
				System.err.println("Latest version (\"" + latestVersion
					+ "\") doesn't follow the semver.org syntax!");
				e.printStackTrace();
			}
			try
			{
				outdated = isLatestVersionHigher();
			}catch(Exception e)
			{
				outdated = !latestVersion.equals(Constants.VERSION);
			}
			if(outdated)
				System.out.println("Update found: " + latestVersion);
			else
				System.out.println("No update found.");
		}catch(Exception e)
		{
			System.err.println("Unable to check for updates!");
			e.printStackTrace();
		}
	}
	
	private boolean isLatestVersionHigher()
	{
		if(latestMajor > currentMajor)
			return true;
		else if(latestMajor < currentMajor)
			return false;
		else if(latestMinor > currentMinor)
			return true;
		else if(latestMinor < currentMinor)
			return false;
		else if(latestPatch > currentPatch)
			return true;
		else if(latestPatch < currentPatch)
			return false;
		else if(latestPreRelease > currentPreRelease || latestPreRelease == 0
			&& currentPreRelease > 0)
			return true;
		else
			return false;
	}
	
	public void update()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					currentDirectory =
						new File(Updater.class.getProtectionDomain()
							.getCodeSource().getLocation().getPath()
							.replace("%20", " ")).getParentFile();
					String id;
					if(currentPreRelease > 0)
						id =
							json.get(0).getAsJsonObject().get("id")
								.getAsString();
					else
						id =
							new JsonParser()
								.parse(
									new InputStreamReader(
										new URL(
											"https://api.github.com/repos/Wurst-Imperium/Hook-Manager/releases/latest")
											.openStream())).getAsJsonObject()
								.get("id").getAsString();
					progress = new ProgressDialog();
					Thread thread = new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							// Thread needed because setVisible() freezes
							progress.setVisible(true);
						}
					});
					thread.start();
					download(id);
					progress.updateProgress("Update ready", "");
				}catch(Exception e)
				{
					System.err.println("Could not update!");
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public boolean isOutdated()
	{
		return outdated;
	}
	
	public String getLatestVersion()
	{
		return latestVersion;
	}
	
	private void download(String id) throws Exception
	{
		JsonArray json =
			new JsonParser().parse(
				new InputStreamReader(new URL(
					"https://api.github.com/repos/Wurst-Imperium/Hook-Manager/releases/"
						+ id + "/assets").openStream())).getAsJsonArray();
		update =
			new File(currentDirectory, json.get(0).getAsJsonObject()
				.get("name").getAsString());
		URL downloadUrl =
			new URL(json.get(0).getAsJsonObject().get("browser_download_url")
				.getAsString());
		long bytesTotal = downloadUrl.openConnection().getContentLengthLong();
		InputStream input = downloadUrl.openStream();
		FileOutputStream output = new FileOutputStream(update);
		byte[] buffer = new byte[8192];
		long bytesDownloaded = 0;
		for(int length; (length = input.read(buffer)) != -1;)
		{
			bytesDownloaded += length;
			if(bytesDownloaded > 0)
			{
				String percent =
					(short)((float)bytesDownloaded / (float)bytesTotal * 1000F)
						/ 10F + "%";
				String data =
					(int)(bytesDownloaded * 1000F / 1048576F) / 1000F + " / "
						+ (int)(bytesTotal * 1000F / 1048576F) / 1000F + " Mb";
				progress.updateProgress("Downloading Update: " + percent, data);
				System.out.println("Downloading Update: " + percent + " ("
					+ data + ")");
			}
			output.write(buffer, 0, length);
		}
		input.close();
		output.close();
	}
}
