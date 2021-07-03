# NodeBreak
Plugin that lets you break nodes more quickly.  
Mine an ore and this will recursively mine other ores in a depth radius

## Config
Here's the default ``config.yml`` file that will be generated in your plugins folder
```yml
# How many times to recurse through finding adjacent blocks to break when hitting a node.
depth: 3

# Tools that can be used that will cause a nodebreak
allowed_tools: ["netherite_pickaxe"]

# What gamemodes players need to be to use nodebreak
allowed_gamemodes: ["survival", "creative"]
```

## Building
These are the steps to manually build this project yourself.  
Probably want to use the releases though.  
1. Use ``gradlew.bat`` to install gradle to be able to build this application if you don't already have it.
2. Build using the ``shadowJar`` gradle task, (Build might work too but I dunno)
3. Final jar will be in ``build/libs/``
