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

import tk.wurst_client.hooks.reader.data.HookPosition;
import tk.wurst_client.hooks.reader.data.MethodData;

public class MethodHookInjector extends MethodVisitor
{
	private String methodName;
	private String className;
	private MethodData methodData;
	
	public MethodHookInjector(int api, MethodVisitor mv, MethodData methodData,
		String className, String methodName)
	{
		super(api, mv);
		this.methodName = methodName;
		this.className = className;
		this.methodData = methodData;
	}
	
	@Override
	public void visitCode()
	{
		super.visitCode();
		if(methodData.hasHookAt(HookPosition.METHOD_START))
		{
			super.visitLdcInsn(className + "." + methodName + "|start");
			
			int paramCount = 0;
			if(methodName.contains(";"))
				paramCount = methodName.substring(methodName.indexOf("("),
					methodName.lastIndexOf(")")).split(";").length;
			
			super.visitIntInsn(Opcodes.BIPUSH, paramCount);
			super.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
			for(byte i = 0; i < paramCount; i++)
			{
				super.visitInsn(Opcodes.DUP);
				super.visitIntInsn(Opcodes.BIPUSH, i);
				super.visitVarInsn(Opcodes.ALOAD, i + 1);
				super.visitInsn(Opcodes.AASTORE);
			}
			
			// TODO: Custom class path
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
				"tk/wurst_client/hooks/HookManager", "hook",
				"(Ljava/lang/String;[Ljava/lang/Object;)V", false);
		}
	}
	
	@Override
	public void visitInsn(int opcode)
	{
		if(methodData.hasHookAt(HookPosition.METHOD_END))
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
					// TODO: Custom class path
					super.visitMethodInsn(Opcodes.INVOKESTATIC,
						"tk/wurst_client/hooks/HookManager", "hook",
						"(Ljava/lang/String;)V", false);
					break;
				default:
					break;
			}
		}
		super.visitInsn(opcode);
	}
}
