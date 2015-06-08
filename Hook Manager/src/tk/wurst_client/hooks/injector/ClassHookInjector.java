/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.injector;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import tk.wurst_client.hooks.reader.data.ClassData;

public class ClassHookInjector extends ClassVisitor
{
	private int api;
	private String className;
	private ClassData classData;
	
	public ClassHookInjector(int api, ClassVisitor cv, ClassData classData)
	{
		super(api, cv);
		this.api = api;
		this.classData = classData;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
		String superName, String[] interfaces)
	{
		super.visit(version, access, name, signature, superName, interfaces);
		className = name;
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc,
		String signature, Object value)
	{
		access =
			access & ~Opcodes.ACC_PRIVATE & ~Opcodes.ACC_PROTECTED
				| Opcodes.ACC_PUBLIC;
		return super.visitField(access, name, desc, signature, value);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
		String signature, String[] exceptions)
	{
		access =
			access & ~Opcodes.ACC_PRIVATE & ~Opcodes.ACC_PROTECTED
				| Opcodes.ACC_PUBLIC;
		MethodVisitor mv =
			super.visitMethod(access, name, desc, signature, exceptions);
		if(classData.hasHooks() && classData.getMethod(name + desc).hasHooks())
			return new MethodHookInjector(api, mv, classData.getMethod(name
				+ desc), className, name);
		else
			return mv;
	}
}
