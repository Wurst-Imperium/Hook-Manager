/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.injector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import tk.wurst_client.hooks.reader.data.ClassData;
import tk.wurst_client.hooks.reader.data.JarData;

public class JarHookInjector
{
	public void injectHooks(File inputFile, File outputFile, JarData settings)
		throws IOException
	{
		JarFile inputJar = new JarFile(inputFile);
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
				if(entry.getName().equals("META-INF/MANIFEST.MF"))
					continue;
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
				ClassData classData =
					settings.getClass(entry.getName().substring(0,
						entry.getName().length() - 6));
				ClassHookInjector hookInjector =
					new ClassHookInjector(Opcodes.ASM4, writer, classData);
				reader.accept(hookInjector, 0);
				output.write(writer.toByteArray());
				output.closeEntry();
			}
		input.close();
		
		output.putNextEntry(new JarEntry("tk/"));
		output.closeEntry();
		output.putNextEntry(new JarEntry("tk/wurst_client/"));
		output.closeEntry();
		output.putNextEntry(new JarEntry("tk/wurst_client/hooks/"));
		output.closeEntry();
		
		output.putNextEntry(new JarEntry(
			"tk/wurst_client/hooks/HookManager.class"));
		InputStream input2 =
			getClass().getClassLoader().getResourceAsStream(
				"tk/wurst_client/hooks/HookManager.class");
		byte[] buffer = new byte[8192];
		for(int length; (length = input2.read(buffer)) != -1;)
			output.write(buffer, 0, length);
		output.closeEntry();
		input2.close();
		
		output.putNextEntry(new JarEntry(
			"tk/wurst_client/hooks/HookManager$Hook.class"));
		input2 =
			getClass().getClassLoader().getResourceAsStream(
				"tk/wurst_client/hooks/HookManager$Hook.class");
		buffer = new byte[8192];
		for(int length; (length = input2.read(buffer)) != -1;)
			output.write(buffer, 0, length);
		output.closeEntry();
		input2.close();
		
		output.close();
	}
}
