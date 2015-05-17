/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JPanel;

import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class HTMLPanel extends JPanel
{
	private JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	
	/**
	 * Create the panel.
	 */
	public HTMLPanel()
	{
		setLayout(new GridLayout());
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				WebView view = new WebView();
				engine = view.getEngine();
				jfxPanel.setScene(new Scene(view));
			}
		});
		add(jfxPanel);
	}
	
	public void setResource(String resource)
	{
		try
		{
			InputStream input =
				getClass().getClassLoader().getResourceAsStream(resource);
			final char[] buffer = new char[8192];
			StringBuilder output = new StringBuilder();
			Reader reader = new InputStreamReader(input);
			for(int length; (length = reader.read(buffer, 0, 8192)) > 0;)
				output.append(buffer, 0, length);
			reader.close();
			setHTML(output.toString());
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setHTML(String html)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				engine.loadContent(html);
			}
		});
	}
	
	public <T> void setBridge(T bridge)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				((JSObject)engine.executeScript("window")).setMember("java", bridge);
			}
		});
	}
	
	public void executeScript(String script)
	{
		engine.executeScript(script);
	}
}
