/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import tk.wurst_client.hooks.util.Constants;
import tk.wurst_client.hooks.util.Util;
import tk.wurst_client.update.Updater;

public class MainFrame extends JFrame
{
	
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
		splitPane.setResizeWeight(0.5);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "name_14174167421138");
		
		JLabel lblSelectAMethod =
			new JLabel(
				"<html>\r\n<body width=256px>\r\n<center>\r\n<h1 color=#808080>\r\n<br>\r\n<br>\r\nNothing Selected\r\n</h1>\r\n<h3 color=#808080>\r\nSelect a method on the left to manage the hooks for it.");
		panel_1.add(lblSelectAMethod);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, "name_13972871414243");
		
		JCheckBox chckbxInjectHookBefore = new JCheckBox("Inject Hook before");
		
		JCheckBox chckbxCollectParameters = new JCheckBox("Collect parameters");
		
		JCheckBox chckbxInjectHookAfter = new JCheckBox("Inject Hook after");
		
		JCheckBox chckbxCollectParameters_1 =
			new JCheckBox("Collect parameters");
		
		JLabel lblMethodName =
			new JLabel("<html>\r\n<body width=256px>\r\n<h1>\r\nMethod Name");
		
		JLabel lbltodoMoreOptions = new JLabel("// TODO: More options");
		lbltodoMoreOptions.setFont(new Font("Courier New", Font.PLAIN, 11));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(
			Alignment.LEADING).addGroup(
			gl_panel_2
				.createSequentialGroup()
				.addContainerGap()
				.addGroup(
					gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
							gl_panel_2.createSequentialGroup().addGap(21)
								.addComponent(chckbxCollectParameters_1))
						.addComponent(chckbxInjectHookBefore)
						.addComponent(lblMethodName)
						.addGroup(
							gl_panel_2.createSequentialGroup().addGap(21)
								.addComponent(chckbxCollectParameters))
						.addComponent(chckbxInjectHookAfter)
						.addComponent(lbltodoMoreOptions))
				.addContainerGap(145, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
			Alignment.LEADING).addGroup(
			gl_panel_2.createSequentialGroup().addContainerGap()
				.addComponent(lblMethodName).addGap(18)
				.addComponent(chckbxInjectHookBefore)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxCollectParameters).addGap(18)
				.addComponent(chckbxInjectHookAfter)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxCollectParameters_1).addGap(18)
				.addComponent(lbltodoMoreOptions)
				.addContainerGap(68, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);
	}
}
