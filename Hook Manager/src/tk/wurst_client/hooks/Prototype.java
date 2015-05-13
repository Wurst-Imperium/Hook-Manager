/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import tk.wurst_client.update.Updater;

public class Prototype
{
	public static void main(String[] args)
	{
		try
		{
			run();
			System.exit(0);
			// Forces updater window to close
		}catch(Throwable e)
		{
			e.printStackTrace();
			StringWriter traceWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(traceWriter));
			JOptionPane.showMessageDialog(null, traceWriter.toString(),
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void run() throws Throwable
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Updater updater = new Updater();
		updater.checkForUpdate();
		if(updater.isOutdated())
		{
			if(JOptionPane.showConfirmDialog(null,
				"Version " + updater.getLatestVersion() + " is available.\n"
					+ "Would you like to update?", "Update Available",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				updater.update();
		}
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("Select input file (Jar)");
		fileChooser
			.setFileFilter(new FileNameExtensionFilter("Jar file", "jar"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setApproveButtonText("Inject Hooks");
		if(fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			return;
		File inputFile = fileChooser.getSelectedFile();
		JarFile inputJar = new JarFile(inputFile);
		File outputFile =
			new File(inputFile.getParent(), inputFile.getName().substring(0,
				inputFile.getName().lastIndexOf(".jar"))
				+ "-hooked.jar");
		outputFile.getParentFile().mkdirs();
		JarInputStream input =
			new JarInputStream(new FileInputStream(inputFile));
		JarOutputStream output =
			new JarOutputStream(new FileOutputStream(outputFile),
				inputJar.getManifest());
		inputJar.close();
		for(JarEntry entry; (entry = input.getNextJarEntry()) != null;)
			if(entry.isDirectory())
			{
				output.putNextEntry(entry);
				output.closeEntry();
			}else if(!entry.getName().endsWith(".class"))
			{
				output.putNextEntry(entry);
				byte[] buffer = new byte[8192];
				for(int length; (length = input.read(buffer)) != -1;)
					output.write(buffer, 0, length);
				output.closeEntry();
			}else
			{
				output.putNextEntry(new JarEntry(entry.getName()));
				ClassReader reader = new ClassReader(input);
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				ClassHookInjector hookInjector =
					new ClassHookInjector(Opcodes.ASM4, writer);
				reader.accept(hookInjector, 0);
				output.write(writer.toByteArray());
				output.closeEntry();
			}
		output.close();
		input.close();
	}
}
