/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class HTMLDialog extends JDialog
{
	private HTMLPanel htmlPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			HTMLDialog dialog = new HTMLDialog(null);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 * @param parent 
	 */
	public HTMLDialog(Component parent)
	{
		setSize(640, 480);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(new BorderLayout());
		{
			htmlPanel = new HTMLPanel();
			getContentPane().add(htmlPanel, BorderLayout.CENTER);
		}
	}
	
	public void setResource(String resource)
	{
		htmlPanel.setResource(resource);
	}
	
	public void setHTML(String html)
	{
		htmlPanel.setHTML(html);
	}
	
	public <T> void setBridge(T bridge)
	{
		htmlPanel.setBridge(bridge);
	}
	
	public void executeScript(String script)
	{
		htmlPanel.executeScript(script);
	}
}
