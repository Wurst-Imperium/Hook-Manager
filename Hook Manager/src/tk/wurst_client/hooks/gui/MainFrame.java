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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import tk.wurst_client.analytics.Analytics;
import tk.wurst_client.analytics.AnalyticsCookieManager;
import tk.wurst_client.hooks.injector.JarHookInjector;
import tk.wurst_client.hooks.reader.JarDataReader;
import tk.wurst_client.hooks.reader.data.ClassData;
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
	private EditorBridge editorBridge;
	private File inputFile;
	private Analytics analytics;
	
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
						{
							Util.openInBrowser(Constants.URLs.GITHUB_PAGE
								+ "/releases/latest");
							System.exit(0);
						}
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
		analytics = new Analytics("UA-63411855-2", "client.hook-manager.tk");
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(
			MainFrame.class.getResource("/tk/wurst_client/hooks/icon.png")));
		setMinimumSize(new Dimension(1024, 640));
		setLocationByPlatform(true);
		setTitle("Hook Manager v" + Constants.VERSION);
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
				if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
					try
					{
						inputFile = fileChooser.getSelectedFile();
						settings = jarDataReader.read(inputFile);
						editorBridge.showSelectClassMessage();
					}catch(IOException e1)
					{
						e1.printStackTrace();
					}
			}
		});
		mnFile.add(mntmOpenInputJar);
		
		JMenuItem mntmSaveHookedJar = new JMenuItem("Save Hooked Jar...");
		mntmSaveHookedJar.setMnemonic(KeyEvent.VK_J);
		mntmSaveHookedJar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
			InputEvent.CTRL_MASK));
		mntmSaveHookedJar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Jar file", "jar"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					try
					{
						new JarHookInjector().injectHooks(inputFile,
							fileChooser.getSelectedFile(), settings);
					}catch(IOException e1)
					{
						e1.printStackTrace();
					}
			}
		});
		mnFile.add(mntmSaveHookedJar);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmLoadSettings = new JMenuItem("Load Settings...");
		mnFile.add(mntmLoadSettings);
		
		JMenuItem mntmSaveSettings = new JMenuItem("Save Settings...");
		mntmSaveSettings.setMnemonic(KeyEvent.VK_S);
		mntmSaveSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
			InputEvent.CTRL_MASK));
		mntmSaveSettings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Hook Manager settings files", "hms"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					String path = fileChooser.getSelectedFile().getPath();
					if(!path.endsWith(".hms"))
						path += ".hms";
					settings.save(new File(path));
				}
			}
		});
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
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnGoogleAnalytics = new JMenu("Google Analytics");
		mnOptions.add(mnGoogleAnalytics);
		
		JCheckBoxMenuItem chckbxmntmEnabled =
			new JCheckBoxMenuItem("Enabled",
				AnalyticsCookieManager.getCookie().enabled);
		chckbxmntmEnabled.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(chckbxmntmEnabled.isSelected())
				{
					AnalyticsCookieManager.getCookie().enabled = true;
					analytics.trackEvent("analytics", "enable");
				}else
				{
					analytics.trackEvent("analytics", "disable");
					AnalyticsCookieManager.getCookie().enabled = false;
				}
				AnalyticsCookieManager.saveCookie();
			}
		});
		mnGoogleAnalytics.add(chckbxmntmEnabled);
		
		JMenuItem mntmPrivacyPolicy = new JMenuItem("Privacy Policy");
		mntmPrivacyPolicy.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				analytics.trackEvent("analytics", "view privacy policy");
				Util.openInBrowser("https://www.google.com/policies/privacy/");
			}
		});
		mnGoogleAnalytics.add(mntmPrivacyPolicy);
		
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
		tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(TreeSelectionEvent e)
			{
				String path = "";
				try
				{
					path =
						((DefaultMutableTreeNode)e.getPath()
							.getPathComponent(1)).toString().replace(".", "/");
					for(int i = 2; i < e.getPath().getPath().length; i++)
						path += "/" + e.getPath().getPathComponent(i);
				}catch(IllegalArgumentException e1)
				{	
					
				}
				ClassData classData = settings.getClass(path);
				editorBridge.setClassData(
					path.substring(path.lastIndexOf("/") + 1), classData);
				editor.setHTMLFile("editor-edit.html");
				editor.doWhenFinished(() -> editor.setBridge(editorBridge));
			}
		});
		jarDataReader = new JarDataReader(tree);
		scrollPane.setViewportView(tree);
		
		editor = new HTMLPanel();
		editor.setHTMLFile("editor-message.html");
		editorBridge = new EditorBridge(editor);
		editor.doWhenFinished(new Runnable()
		{
			@Override
			public void run()
			{
				editor
					.executeScriptAsync("$(document).ready(function(){setMessage('Press <kbd>Ctrl+O</kbd> to open a Jar.');});");
			}
		});
		splitPane.setRightComponent(editor);
		
		analytics.trackPageView("/", "Hook Manager");
	}
}
