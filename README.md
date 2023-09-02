# Command Debug DevKit

![Stars](https://img.shields.io/github/stars/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Forks](https://img.shields.io/github/forks/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Issues](https://img.shields.io/github/issues/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Licence](https://img.shields.io/github/license/Jaffe2718/Command-Debug-DevKit?style=flat-square)

![icon](idea-plugin/src/main/resources/META-INF/pluginIcon.svg) 
by Jaffe2718

## Introduction
This project is written for debugging Minecraft. In this project, there are serveral subprojects:
- `fabric`: The mod for fabric, used to be a connection kit for IDE. It can create two socket server to provide code completion and code execution service.
- `ide-debug-tool`: This is a command line tool which can connect to the socket server opened by the mod. It can send code to the server and get the result. It can be a debug tool for IDE or a command line tool to execute *.mcfunction file directly.
- `idea-plugin`: This is a plugin for IntelliJ IDEA. It can connect to the socket server opened by the mod. It provides code completion and code execution service for IDE. The plugin contains a compiled `ide-debug-tool` so you don't need to install it manually.

## Setup

### Fabric Mod
1. Install Minecraft with `Fabric Loader` and `Fabric API` for your Minecraft version.
2. Download the mod from [release page](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [Modrinth](https://modrinth.com/mod/command-debug-service).
3. Put the mod into your `mods` folder.

### IDE Debug Tool
The tool is a `jar` file, you can just download and you can use it without any installation steps.

### Idea Plugin
1. Download the tool from [release page](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/22587-minecraft-command-devkit), choose the version suitable for the mod.
2. Install the plugin in your IDEA: click `File` -> `Settings` -> `Plugins` -> `⚙️` -> `Install Plugin from Disk...` -> choose the downloaded file, or install it directly from JetBrains Marketplace inside IDE.

### Quilt Mod
1. Install Minecraft with `Quilt Loader` and `Quilted Fabric API (QFAPI) and Quilt Standard Libraries (QSL)` for your Minecraft version.
2. Download the mod from [release page](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [Modrinth](https://modrinth.com/mod/command-debug-service).
3. Put the mod into your `mods` folder.

## Usage
1. Start your Minecraft game and join or create a world. The mod will create two socket server and show the host and port information in the game chat.
2. Start your IDE, create or open a project.
3. Create or open a file with suffix `.mcfunction`.
4. Connect to these two socket servers by entering the host and port which shown in the game chat. Then you can use the code completion and code execution service.
5. You can also use the command line tool to connect to the socket server and execute the code.


## Warning
When you use this mod, you should be careful.
Because it can execute any command.
Do not send the host and port to any untusted program to avoid being attacked.
Do not install this mod on your multiplayer server to avoid being attacked by DDOS or other attacks.

## License
This project is licensed under the MIT License.
You can use this project for any purpose for free.
See the [LICENSE](LICENSE) file for details.

## Update and Cooperation
Currently, this project is still in development. 
If you have any idea or suggestion,
please open an issue or pull request.
And the plugin is only for `IntelliJ Platform IDEs`, 
if you want to use other IDEs such as `Eclipse` and `VS Code`,
you can create a plugin for it and pull request to this project.
What's more, the mod is only for `Fabric` and `Quilt` currently,
you can rewrite it for `Forge` or other mod loaders and pull request to this project.
I will be very grateful if you can help me to improve this project.