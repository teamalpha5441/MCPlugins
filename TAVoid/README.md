TAVoid
======

Use this simple plugin as a world generator that generates completely empty chunks.
The plugin will be started before the worlds are loaded, so Multiverse can detect it.

Usage
-----

You cannot interact with the plugin directly.
Use this example command to generate an empty world:

/mv create MyEmptyWorld normal -g TAVoid

A bedrock block will be generated at 0/63/0 (configurable).
The spawn is fixed and set at location ...
- With the bedrock block: 0.5/64/0.5
- Without the bedrock block: 0/64/0

Now you can teleport to the generated world and start building.
