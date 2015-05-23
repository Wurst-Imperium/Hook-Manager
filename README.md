# Hook Manager [![latest release](https://img.shields.io/github/release/Wurst-Imperium/Hook-Manager.svg?label=latest%20release)](https://github.com/Wurst-Imperium/Hook-Manager/releases/latest) [![](https://img.shields.io/github/license/Wurst-Imperium/Hook-Manager.svg)](https://github.com/Wurst-Imperium/Hook-Manager/blob/master/LICENSE)

Modify Java files. Any Java files!

## The problem with deobfuscation
All Java deobfuscation tools have one thing in common: They produce **a ton** of errors.

When trying to work with a big obfuscated program, fixing all the errors is nearly impossible. For instance, I have tried many things to deobfuscate Minecraft 1.8.3 (which has roughly 1.5k classes and 20k methods), such as
- making my own mappings for Enigma,
- modifying Enigma to make it work without mappings,
- using regular expressions to reduce the number of errors
- and modifying MCP to make it work with any Minecraft version.

None of these methods really worked for me - it always ended up with thousands of errors. Searge and his team have somehow managed to fix all of these errors, but it takes them a very long time to do that - every time a Minecraft update is released, they spend at least a month updating the MCP.

## How Hook Manager helps
If the goal is to modify the executable Jar, converting the bytecode into source code (decompiling & deobfuscating) and then converting it back into bytecode (recompiling) isn't really neccessary. It's just what everyone does.

So instead, Hook Manager applies small modifications directly to the bytecode (known as hooks) that allow you to connect your modifications to the original code. There are absoluely zero errors to fix, no matter how strong the obfuscation is.

Does that mean you have to learn bytecode? No! All the bytecode editing is handled by Hook Manager. You just need to write your classes as you would normally, compile them and add them to the Jar. It's as easy as that!
