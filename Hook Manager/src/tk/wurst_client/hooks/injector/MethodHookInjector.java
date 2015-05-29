/*
 * Copyright © 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.hooks.injector;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodHookInjector extends MethodVisitor
{
	private String methodName;
	private String className;
	
	public MethodHookInjector(int api, MethodVisitor mv, String className,
		String methodName)
	{
		super(api, mv);
		this.methodName = methodName;
		this.className = className;
	}
	
	@Override
	public void visitCode()
	{
		super.visitCode();
		super.visitLdcInsn(className + "." + methodName + "|start");
		super.visitMethodInsn(Opcodes.INVOKESTATIC,
			"tk/wurst_client/hooks/HookManager" /* TODO: Custom class path */,
			"hook", "(Ljava/lang/String;)V", false);
	}
	
	@Override
	public void visitInsn(int opcode)
	{
		switch(opcode)
		{
			case Opcodes.ARETURN:
			case Opcodes.DRETURN:
			case Opcodes.FRETURN:
			case Opcodes.IRETURN:
			case Opcodes.LRETURN:
			case Opcodes.RETURN:
				super.visitLdcInsn(className + "." + methodName + "|end");
				super.visitMethodInsn(Opcodes.INVOKESTATIC,
					"tk/wurst_client/hooks/HookManager"
					/* TODO: Custom class path */, "hook",
					"(Ljava/lang/String;)V", false);
				break;
			default:
				break;
		}
		super.visitInsn(opcode);
	}
}
