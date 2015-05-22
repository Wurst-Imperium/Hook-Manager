/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.injector;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassHookInjector extends ClassVisitor
{
	private int api;
	private String className;

	public ClassHookInjector(int api, ClassVisitor cv)
	{
		super(api, cv);
		this.api = api;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
		String superName, String[] interfaces)
	{
		super.visit(version, access, name, signature, superName, interfaces);
		className = name;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
		String signature, String[] exceptions)
	{

		MethodVisitor mv =
			super.visitMethod(access, name, desc, signature, exceptions);
		MethodHookInjector mvw =
			new MethodHookInjector(api, mv, className, name);
		return mvw;
	}
}
