/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class Prototype
{
	public static void main(String[] args)
	{
		try
		{
			run();
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
		JFileChooser filechooser = new JFileChooser(".");
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		filechooser.setDialogTitle("Select input file (Jar)");
		filechooser
			.setFileFilter(new FileNameExtensionFilter("Jar file", "jar"));
		filechooser.setAcceptAllFileFilterUsed(false);
		filechooser.setApproveButtonText("Inject Hooks");
		if(filechooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			return;
		
		File inputFile = filechooser.getSelectedFile();
		InputStream input = new FileInputStream(inputFile);
		ClassReader reader = new ClassReader(input);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassHookInjector hookInjector =
			new ClassHookInjector(Opcodes.ASM4, writer);
		reader.accept(hookInjector, 0);
		
		File outputFile =
			new File(inputFile.getParent(), inputFile.getName().substring(0,
				inputFile.getName().lastIndexOf(".jar"))
				+ "-hooked.jar");
		outputFile.getParentFile().mkdirs();
		DataOutputStream output =
			new DataOutputStream(new FileOutputStream(outputFile));
		output.write(writer.toByteArray());
		output.close();
	}
}
