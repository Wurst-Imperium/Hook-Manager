/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class JarReader
{
	private JTree tree;
	
	public JarReader(JTree tree)
	{
		this.tree = tree;
	}
	
	public void read(File file) throws IOException
	{
		DefaultMutableTreeNode root =
			new DefaultMutableTreeNode(file.getName());
		JarInputStream input = new JarInputStream(new FileInputStream(file));
		for(JarEntry entry; (entry = input.getNextJarEntry()) != null;)
			if(entry.isDirectory())
				root.add(new DefaultMutableTreeNode(entry.getName()
					.substring(0, entry.getName().length() - 1)
					.replace("/", "."), true));
			else if(!entry.getName().endsWith(".class"))
			{
				if(entry.getName().contains("/"))
					find(
						root,
						entry.getName()
							.substring(0, entry.getName().lastIndexOf("/"))
							.replace("/", ".")).add(
						new DefaultMutableTreeNode(entry, false));
				else
					root.add(new DefaultMutableTreeNode(entry, false));
			}else // TODO: Read class content (methods, etc.)
			if(entry.getName().contains("/"))
				find(
					root,
					entry.getName()
						.substring(0, entry.getName().lastIndexOf("/"))
						.replace("/", ".")).add(
					new DefaultMutableTreeNode(entry.getName().substring(0,
						entry.getName().length() - 6), false));
			else
				root.add(new DefaultMutableTreeNode(entry.getName().substring(
					0, entry.getName().length() - 6), false));
		input.close();
		tree.setModel(new DefaultTreeModel(root));
	}
	
	private DefaultMutableTreeNode find(DefaultMutableTreeNode root, String s)
	{
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> nodes =
			root.depthFirstEnumeration();
		while(nodes.hasMoreElements())
		{
			DefaultMutableTreeNode node = nodes.nextElement();
			if(node.toString().equalsIgnoreCase(s))
				return node;
		}
		throw new NullPointerException("Node \"" + s
			+ "\" does not exist in root \"" + root + "\".");
	}
}
