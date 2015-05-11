/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ClassHookInjector extends ClassVisitor
{
	private int api;
	
	public ClassHookInjector(int api, ClassWriter cv)
	{
		super(api, cv);
		this.api = api;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
		String signature, String[] exceptions)
	{
		
		MethodVisitor mv =
			super.visitMethod(access, name, desc, signature, exceptions);
		MethodHookInjector mvw = new MethodHookInjector(api, mv, name);
		return mvw;
	}
}
