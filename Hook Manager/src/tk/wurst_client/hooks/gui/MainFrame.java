/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import tk.wurst_client.hooks.reader.JarDataReader;
import tk.wurst_client.hooks.reader.data.JarData;
import tk.wurst_client.hooks.util.Constants;
import tk.wurst_client.hooks.util.Util;
import tk.wurst_client.update.Updater;

public class MainFrame extends JFrame
{
	private JTree tree;
	private JarDataReader jarDataReader;
	private JarData settings;
	private HTMLPanel editor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
					Updater updater = new Updater();
					updater.checkForUpdate();
					if(updater.isOutdated())
						if(JOptionPane.showConfirmDialog(null, "Version "
							+ updater.getLatestVersion() + " is available.\n"
							+ "Would you like to update?", "Update Available",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
							updater.update();
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public MainFrame()
	{
		setMinimumSize(new Dimension(600, 360));
		setLocationByPlatform(true);
		setTitle("HookManager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenInputJar = new JMenuItem("Open Input Jar...");
		mntmOpenInputJar.setMnemonic(KeyEvent.VK_O);
		mntmOpenInputJar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
			InputEvent.CTRL_MASK));
		mntmOpenInputJar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Jar file", "jar"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					try
					{
						settings =
							jarDataReader.read(fileChooser.getSelectedFile());
						editor
							.executeScriptAsync("setMessage('Select a class to start injecting hooks.')");
					}catch(IOException e1)
					{
						e1.printStackTrace();
					}
			}
		});
		mnFile.add(mntmOpenInputJar);
		
		JMenuItem mntmSaveHookedJar = new JMenuItem("Save Hooked Jar...");
		mnFile.add(mntmSaveHookedJar);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmLoadSettings = new JMenuItem("Load Settings...");
		mnFile.add(mntmLoadSettings);
		
		JMenuItem mntmSaveSettings = new JMenuItem("Save Settings...");
		mnFile.add(mntmSaveSettings);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHookManagerWiki = new JMenuItem("Hook Manager Wiki");
		mntmHookManagerWiki.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.WIKI);
			}
		});
		mnHelp.add(mntmHookManagerWiki);
		
		JMenuItem mntmQuestionsAnswers = new JMenuItem("Questions & Answers");
		mntmQuestionsAnswers.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.QUESTIONS);
			}
		});
		mnHelp.add(mntmQuestionsAnswers);
		
		JSeparator separator_2 = new JSeparator();
		mnHelp.add(separator_2);
		
		JMenuItem mntmContribute = new JMenuItem("Contribute <3");
		mntmContribute.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.CONTRIBUTE);
			}
		});
		mnHelp.add(mntmContribute);
		
		JMenuItem mntmReportBugs = new JMenuItem("Report Bugs");
		mntmReportBugs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.BUGS);
			}
		});
		mnHelp.add(mntmReportBugs);
		
		JMenuItem mntmSuggestFeatures = new JMenuItem("Suggest Features");
		mntmSuggestFeatures.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.SUGGESTIONS);
			}
		});
		mnHelp.add(mntmSuggestFeatures);
		
		JSeparator separator_3 = new JSeparator();
		mnHelp.add(separator_3);
		
		JMenuItem mntmAboutHookManager = new JMenuItem("About Hook Manager");
		mntmAboutHookManager.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new AboutDialog(MainFrame.this).setVisible(true);
			}
		});
		mnHelp.add(mntmAboutHookManager);
		
		JMenuItem mntmOfficialWebsite = new JMenuItem("Official Website");
		mntmOfficialWebsite.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.WEBSITE);
			}
		});
		mnHelp.add(mntmOfficialWebsite);
		
		JMenuItem mntmOfficialGithubPage =
			new JMenuItem("Official GitHub Page");
		mntmOfficialGithubPage.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Util.openInBrowser(Constants.URLs.GITHUB_PAGE);
			}
		});
		mnHelp.add(mntmOfficialGithubPage);
		
		JMenuItem mntmViewLicense = new JMenuItem("View License");
		mntmViewLicense.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new LicenseDialog(MainFrame.this).setVisible(true);
			}
		});
		mnHelp.add(mntmViewLicense);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.25);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tree = new JTree(new DefaultMutableTreeNode());
		jarDataReader = new JarDataReader(tree);
		scrollPane.setViewportView(tree);
		
		editor = new HTMLPanel();
		editor.setHTMLFile("editor-message.html");
		editor.setBridge(new EditorBridge());
		editor.doWhenFinished(new Runnable()
		{
			@Override
			public void run()
			{
				editor
					.executeScript("$(document).ready(function(){setMessage('Press <kbd>Ctrl+O</kbd> to open a Jar.');});");
			}
		});
		splitPane.setRightComponent(editor);
	}
}
