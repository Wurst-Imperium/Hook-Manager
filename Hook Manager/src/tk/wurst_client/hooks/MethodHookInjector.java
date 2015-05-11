/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodHookInjector extends MethodVisitor
{
	private String methodName;
	
	public MethodHookInjector(int api, MethodVisitor mv, String methodName)
	{
		super(api, mv);
		this.methodName = methodName;
	}
	
	@Override
	public void visitCode()
	{
		super.visitCode();
		super.visitLdcInsn(methodName);
		// TODO: Class name + method name
		super.visitMethodInsn(Opcodes.INVOKESTATIC,
			"tk/wurst_client/hooks/HookManager" /* TODO: Custom class path */,
			"hook", "(Ljava/lang/String;)V", false);
	}
}
