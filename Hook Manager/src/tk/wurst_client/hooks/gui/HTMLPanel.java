/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.GridLayout;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JPanel;

import netscape.javascript.JSObject;
import tk.wurst_client.hooks.util.Constants;

public class HTMLPanel extends JPanel
{
	private JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	private Object bridge;
	
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
				view.setContextMenuEnabled(false);
				engine = view.getEngine();
				jfxPanel.setScene(new Scene(view));
			}
		});
		add(jfxPanel);
	}
	
	public void setHTMLFile(String filename)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				engine.load(getClass().getClassLoader()
					.getResource(Constants.Resources.HTML_DIR + filename)
					.toExternalForm());
			}
		});
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
	
	public Object getBridge()
	{
		return bridge;
	}
	
	public void setBridge(Object bridge)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				((JSObject)engine.executeScript("window")).setMember("java",
					bridge);
			}
		});
		this.bridge = bridge;
	}
	
	public Object executeScript(String script)
	{
		return engine.executeScript(script);
	}
}
