# Command Debug DevKit

![Stars](https://img.shields.io/github/stars/Jaffe2718/Socket-Injection-Debugger?style=flat-square)
![Forks](https://img.shields.io/github/forks/Jaffe2718/Socket-Injection-Debugger?style=flat-square)
![Issues](https://img.shields.io/github/issues/Jaffe2718/Socket-Injection-Debugger?style=flat-square)
![Licence](https://img.shields.io/github/license/Jaffe2718/Socket-Injection-Debugger?style=flat-square)

by Jaffe2718

## Introduction
This is a mod that provides a method to execute and debug Minecraft commands by external programs.
It uses socket to communicate with external programs.
When the mod is loaded, it will open a socket server on a random port, then you can connect to it and send commands to it.
The mod will execute the commands and send the result back to the external program once it receives a command from socket client.

For developers, you can make your own program to connect to the socket server and send commands to it.
Or you can develop a plugin for your IDE to make it easier to debug Minecraft commands based on this mod.

## Setup
1. Install Minecraft with Fabric Loader and Fabric API.
2. Download the mod from [GitHub Releases](https://github.com/Jaffe2718/Socket-Injection-Debugger/releases).
3. Put the mod into the `mods` folder and run the game once.

## Usage
1. Run the game and join a world, than the mod will open a socket server on a random port and show it to you in the chat, there are too socket servers,
one is for executing commands, the other is for getting the suggestions of completing commands.
2. Connect to the socket server with your external program, then you can send commands to it or get the suggestions of completing commands. The external program can be a socket client or a plugin for your IDE.

## Example
Unfinished

## Warning
When you use this mod, you should be careful.
Because it can execute any command.
Do not send the host and port to any untusted program to avoid being attacked.
Do not install this mod on your multiplayer server to avoid being attacked by DDOS or other attacks.

## Update and Cooperation
If you have any questions or suggestions, please open an issue or pull request.
Next, I will consider developing a plugin on Jetbrains IDE or VSCode to cooperate with this mod.
Currently, this mod only supports Minecraft 1.20.1 with Fabric, unfortunately, I am not familiar with Forge, NeoForge, Quilt, etc, so I hope someone can help me to port this mod to other platforms.
If you are interested in this, please contact me or give me a pull request.