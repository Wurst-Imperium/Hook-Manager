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
		JarInputStream input = new JarInputStream(new FileInputStream(file));
		DefaultMutableTreeNode root =
			new DefaultMutableTreeNode(file.getName());
		DefaultTreeModel model = new DefaultTreeModel(root);
		// XXX
		// new DefaultMutableTreeNode("JTree") {
		// {
		// DefaultMutableTreeNode node_1;
		// node_1 = new DefaultMutableTreeNode("colors");
		// node_1.add(new DefaultMutableTreeNode("blue"));
		// node_1.add(new DefaultMutableTreeNode("violet"));
		// node_1.add(new DefaultMutableTreeNode("red"));
		// node_1.add(new DefaultMutableTreeNode("yellow"));
		// add(node_1);
		// node_1 = new DefaultMutableTreeNode("sports");
		// node_1.add(new DefaultMutableTreeNode("basketball"));
		// node_1.add(new DefaultMutableTreeNode("soccer"));
		// node_1.add(new DefaultMutableTreeNode("football"));
		// node_1.add(new DefaultMutableTreeNode("hockey"));
		// add(node_1);
		// node_1 = new DefaultMutableTreeNode("food");
		// node_1.add(new DefaultMutableTreeNode("hot dogs"));
		// node_1.add(new DefaultMutableTreeNode("pizza"));
		// node_1.add(new DefaultMutableTreeNode("ravioli"));
		// node_1.add(new DefaultMutableTreeNode("bananas"));
		// node_1.add(new DefaultMutableTreeNode("tacos"));
		// add(node_1);
		// }
		// }
		for(JarEntry entry; (entry = input.getNextJarEntry()) != null;)
			if(entry.isDirectory())
			{
				root.add(new DefaultMutableTreeNode(entry.getName()
					.substring(0, entry.getName().length() - 1)
					.replace("/", "."), true));
			}else if(!entry.getName().endsWith(".class"))
			{
				// TODO
				// output.putNextEntry(entry);
				// byte[] buffer = new byte[8192];
				// for(int length; (length = input.read(buffer)) != -1;)
				// output.write(buffer, 0, length);
				// output.closeEntry();
			}else
			{
				// TODO
				// output.putNextEntry(new JarEntry(entry.getName()));
				// ClassReader reader = new ClassReader(input);
				// ClassWriter writer = new
				// ClassWriter(ClassWriter.COMPUTE_MAXS);
				// ClassHookInjector hookInjector =
				// new ClassHookInjector(Opcodes.ASM4, writer);
				// reader.accept(hookInjector, 0);
				// output.write(writer.toByteArray());
				// output.closeEntry();
			}
		// output.close();
		input.close();
		tree.setModel(model);
	}
}
