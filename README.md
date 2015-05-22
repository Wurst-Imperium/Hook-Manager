# Hook Manager [![latest release](https://img.shields.io/github/release/Wurst-Imperium/Hook-Manager.svg?label=latest%20release)](https://github.com/Wurst-Imperium/Hook-Manager/releases/latest) [![](https://img.shields.io/github/license/Wurst-Imperium/Hook-Manager.svg)](https://github.com/Wurst-Imperium/Hook-Manager/blob/master/LICENSE)

:anchor: A tool for injecting hooks into Java bytecode.

> <q>The best way to deobfuscate a Java program is to not deobfuscate it at all.</q>  
> -Alexander01998

## The problem with deobfuscation
All Java deobfuscation tools have one thing in common: They produce **a ton** of errors.

When trying to work with a big obfuscated program, fixing all the errors is nearly impossible. I have tried many things to deobfuscate Minecraft 1.8.3 (which has roughly 1.5k classes and 20k methods), such as
- making my own mappings for Enigma,
- modifying Enigma to make it work without mappings,
- using regular expressions to reduce the number of errors
- and modifying MCP to make it work with any Minecraft version.

## How Hook Manager helps
I figured out that the best way to deobfuscate a Java program is to not deobfuscate it at all. When the goal is to modify the executable Jar, converting it into editable source code and then converting it back into executable bytecode is just a lot of unnecessary work, especially when dealing with obfuscated code.

So instead of wasting time and energy doing that, Hook Manager applies small modifications directly to the bytecode (known as hooks) that allow you to connect your modifications to the original code. This way, there are absoluely zero errors to fix, no matter how strong the obfuscation is.
