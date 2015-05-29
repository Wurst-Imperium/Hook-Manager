/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.reader;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import tk.wurst_client.hooks.reader.data.ClassData;
import tk.wurst_client.hooks.reader.data.JarData;
import tk.wurst_client.hooks.reader.data.MethodData;

public class ClassDataReader extends ClassVisitor
{
	private JarData jarData;
	private ClassData classData;
	
	public ClassDataReader(int api, JarData jarData)
	{
		super(api, new ClassWriter(ClassWriter.COMPUTE_MAXS));
		this.jarData = jarData;
		classData = new ClassData();
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
		String superName, String[] interfaces)
	{
		super.visit(version, access, name, signature, superName, interfaces);
		jarData.addClass(name, classData);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
		String signature, String[] exceptions)
	{
		classData.addMethod(name + desc, new MethodData(classData));
		return super.visitMethod(access, name, desc, signature, exceptions);
	}
}
