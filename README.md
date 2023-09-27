# Command Debug DevKit

![icon](idea-plugin/src/main/resources/META-INF/pluginIcon.svg)
![Stars](https://img.shields.io/github/stars/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Forks](https://img.shields.io/github/forks/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Issues](https://img.shields.io/github/issues/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Licence](https://img.shields.io/github/license/Jaffe2718/Command-Debug-DevKit?style=flat-square)

by Jaffe2718

## Introduction 📖
This project is written for debugging Minecraft commands. In this project, there are serveral subprojects:
- `fabric`: The mod for fabric, used to be a connection kit for IDE. It can create two socket server to provide code completion and code execution service.
- `ide-debug-tool`: This is a command line tool which can connect to the socket server opened by the mod. It can send code to the server and get the result. It can be a debug tool for IDE or a command line tool to execute *.mcfunction file directly.
- `idea-plugin`: This is a plugin for IntelliJ IDEA. It can connect to the socket server opened by the mod. It provides code completion and code execution service for IDE. The plugin contains a compiled `ide-debug-tool` so you don't need to install it manually.
- `quilt`: The mod for quilt, used to be a connection kit for IDE. It can create two socket server to provide code completion and code execution service.

## Setup 🛠️

### Mods
![follows](https://img.shields.io/modrinth/followers/command-debug-service)
![latestVersion](https://img.shields.io/modrinth/v/command-debug-service)
![mcVersion](https://img.shields.io/modrinth/game-versions/command-debug-service)
![modDownloads](https://img.shields.io/modrinth/dt/command-debug-service)
<br>
[![Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.1.2/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/command-debug-service)

#### Fabric/Quilt Mod
1. Install Minecraft with`Fabric/Quilt Loader` and 
[![Fabric-API](https://img.shields.io/badge/Fabric%20API-000000?logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgcm9sZT0iaW1nIj4gPGc%2BICA8cmVjdCB4PSIxMSIgeT0iMiIgd2lkdGg9IjEiIGhlaWdodD0iOCIgZmlsbD0icmdiKDcsMCwwKSIvPiAgPHJlY3QgeD0iNCIgeT0iNSIgd2lkdGg9IjIiIGhlaWdodD0iMiIgZmlsbD0icmdiKDkzLDg1LDcwKSIvPiAgPHJlY3QgeD0iMTIiIHk9IjciIHdpZHRoPSIyIiBoZWlnaHQ9IjMiIGZpbGw9InJnYig0MiwzNiwyNCkiLz4gIDxyZWN0IHg9IjYiIHk9IjMiIHdpZHRoPSI3IiBoZWlnaHQ9IjQiIGZpbGw9InJnYigyMzQsMjE5LDE4OSkiLz4gIDxyZWN0IHg9IjgiIHk9IjAiIHdpZHRoPSIyIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iNyIgeT0iMSIgd2lkdGg9IjIiIGhlaWdodD0iMyIgZmlsbD0icmdiKDE0MiwxMzEsMTEyKSIvPiAgPHJlY3QgeD0iNyIgeT0iMSIgd2lkdGg9IjEiIGhlaWdodD0iMiIgZmlsbD0icmdiKDI0LDE4LDgpIi8%2BICA8cmVjdCB4PSI5IiB5PSIxIiB3aWR0aD0iMiIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjA2LDE5MiwxNjcpIi8%2BICA8cmVjdCB4PSIxMCIgeT0iMSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDksNSwwKSIvPiAgPHJlY3QgeD0iNiIgeT0iMyIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI3LDI0LDE2KSIvPiAgPHJlY3QgeD0iMTIiIHk9IjMiIHdpZHRoPSIyIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iNSIgeT0iNCIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI0LDE4LDgpIi8%2BICA8cmVjdCB4PSI2IiB5PSI0IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMTI0LDExNCw5OCkiLz4gIDxyZWN0IHg9IjkiIHk9IjQiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigxNDIsMTMxLDExMikiLz4gIDxyZWN0IHg9IjE0IiB5PSI0IiB3aWR0aD0iMSIgaGVpZ2h0PSI1IiBmaWxsPSJyZ2IoMjAsMjAsMTApIi8%2BICA8cmVjdCB4PSI0IiB5PSI1IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoNiwwLDApIi8%2BICA8cmVjdCB4PSIxMCIgeT0iNSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDc5LDcyLDU5KSIvPiAgPHJlY3QgeD0iMTMiIHk9IjQiIHdpZHRoPSIxIiBoZWlnaHQ9IjQiIGZpbGw9InJnYigxMDQsOTUsODApIi8%2BICA8cmVjdCB4PSIzIiB5PSI2IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoNiwwLDApIi8%2BICA8cmVjdCB4PSI0IiB5PSI2IiB3aWR0aD0iOCIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjQwLDIyNCwxOTMpIi8%2BICA8cmVjdCB4PSIxMSIgeT0iNiIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDU2LDUwLDM3KSIvPiAgPHJlY3QgeD0iMTUiIHk9IjUiIHdpZHRoPSIxIiBoZWlnaHQ9IjMiIGZpbGw9InJnYig1MSw0NSwzMykiLz4gIDxyZWN0IHg9IjIiIHk9IjciIHdpZHRoPSIyIiBoZWlnaHQ9IjciIGZpbGw9InJnYig2LDAsMCkiLz4gIDxyZWN0IHg9IjMiIHk9IjciIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig5LDUsMCkiLz4gIDxyZWN0IHg9IjE0IiB5PSI1IiB3aWR0aD0iMSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoNzksNzIsNTkpIi8%2BICA8cmVjdCB4PSIxIiB5PSI4IiB3aWR0aD0iMSIgaGVpZ2h0PSI1IiBmaWxsPSJyZ2IoNiwwLDApIi8%2BICA8cmVjdCB4PSIyIiB5PSI4IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoOTMsODUsNzApIi8%2BICA8cmVjdCB4PSIyIiB5PSI4IiB3aWR0aD0iOSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjQwLDIyNCwxOTMpIi8%2BICA8cmVjdCB4PSIwIiB5PSI5IiB3aWR0aD0iMSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoNTEsNDUsMzMpIi8%2BICA8cmVjdCB4PSIxMCIgeT0iMTAiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig5Myw4NSw3MCkiLz4gIDxyZWN0IHg9IjIiIHk9IjExIiB3aWR0aD0iMiIgaGVpZ2h0PSIyIiBmaWxsPSJyZ2IoODEsNzQsNTkpIi8%2BICA8cmVjdCB4PSIzIiB5PSIxMSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDIwNiwxOTIsMTY3KSIvPiAgPHJlY3QgeD0iNSIgeT0iMTMiIHdpZHRoPSIyIiBoZWlnaHQ9IjMiIGZpbGw9InJnYigyMTYsMjA1LDE3MikiLz4gIDxyZWN0IHg9IjQiIHk9IjExIiB3aWR0aD0iNSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjI2LDIxMSwxODIpIi8%2BICA8cmVjdCB4PSI5IiB5PSIxMSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDEyNCwxMTQsOTgpIi8%2BICA8cmVjdCB4PSIxMCIgeT0iMTEiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iOCIgeT0iMTIiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigxMDQsOTYsODApIi8%2BICA8cmVjdCB4PSI5IiB5PSIxMiIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI3LDI0LDE2KSIvPiAgPHJlY3QgeD0iOCIgeT0iMTMiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iNCIgeT0iMTQiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigzMiwyNSwxMikiLz4gIDxyZWN0IHg9IjciIHk9IjE0IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoNDQsNDAsMjgpIi8%2BICA8cmVjdCB4PSI4IiB5PSIxMyIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI3LDI0LDE2KSIvPiAgPHJlY3QgeD0iNSIgeT0iMTUiIHdpZHRoPSIzIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig0MiwzNiwyNCkiLz4gPC9nPjwvc3ZnPg%3D%3D)](https://modrinth.com/mod/fabric-api)
or [![Quilted Fabric API](https://img.shields.io/badge/Quilted%20Fabric%20API-000000?logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgcm9sZT0iaW1nIj4gPGc+ICA8dGl0bGU+TGF5ZXIgMTwvdGl0bGU+ICA8cmVjdCB4PSIzIiB5PSIzIiB3aWR0aD0iMyIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMTUxLCAzNCwgMjU1KSIvPiAgPHJlY3QgeD0iNiIgeT0iMyIgd2lkdGg9IjMiIGhlaWdodD0iMyIgZmlsbD0icmdiKDIyMCwgNDEsIDIyMSkiLz4gIDxyZWN0IHg9IjkiIHk9IjMiIHdpZHRoPSIxIiBoZWlnaHQ9IjMiIGZpbGw9InJnYigxMDEsIDQ5LCAxNDEpIi8+ICA8cmVjdCB4PSIxMCIgeT0iMyIgd2lkdGg9IjMiIGhlaWdodD0iMyIgZmlsbD0icmdiKDM5LCAxNjIsIDI1MykiLz4gIDxyZWN0IHg9IjMiIHk9IjYiIHdpZHRoPSIzIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigxNjgsIDM1LCAxNzMpIi8+ICA8cmVjdCB4PSI2IiB5PSI2IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMzMsIDg5LCAxNDcpIi8+ICA8cmVjdCB4PSI3IiB5PSI2IiB3aWR0aD0iMiIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMzYsIDEyNCwgMTk3KSIvPiAgPHJlY3QgeD0iOSIgeT0iNiIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDMzLCA2MywgMTIyKSIvPiAgPHJlY3QgeD0iMTAiIHk9IjYiIHdpZHRoPSIzIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig0NCwgNTUsIDE5OCkiLz4gIDxyZWN0IHg9IjMiIHk9IjciIHdpZHRoPSIzIiBoZWlnaHQ9IjIiIGZpbGw9InJnYigyMjAsIDQxLCAyMjEpIi8+ICA8cmVjdCB4PSI3IiB5PSI3IiB3aWR0aD0iMiIgaGVpZ2h0PSIyIiBmaWxsPSJyZ2IoMzksIDE2MiwgMjUzKSIvPiAgPHJlY3QgeD0iOSIgeT0iNyIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDM1LCA3OSwgMTUzKSIvPiAgPHJlY3QgeD0iMTAiIHk9IjciIHdpZHRoPSIzIiBoZWlnaHQ9IjIiIGZpbGw9InJnYig1MSwgNjgsIDI1NSkiLz4gIDxyZWN0IHg9IjYiIHk9IjciIHdpZHRoPSIxIiBoZWlnaHQ9IjIiIGZpbGw9InJnYigzNSwgMTE3LCAxODkpIi8+ICA8cmVjdCB4PSI5IiB5PSI4IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMzUsIDc5LCAxNTMpIi8+ICA8cmVjdCB4PSIzIiB5PSI5IiB3aWR0aD0iMyIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMTEzLCAyOCwgMTQyKSIvPiAgPHJlY3QgeD0iNiIgeT0iOSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDMzLCA1NywgMTE4KSIvPiAgPHJlY3QgeD0iNyIgeT0iOSIgd2lkdGg9IjIiIGhlaWdodD0iMSIgZmlsbD0icmdiKDM2LCA3NCwgMTUzKSIvPiAgPHJlY3QgeD0iOSIgeT0iOSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDMxLCA0NSwgMTAwKSIvPiAgPHJlY3QgeD0iMTAiIHk9IjkiIHdpZHRoPSIzIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigzNiwgMzcsIDEyOCkiLz4gIDxyZWN0IHg9IjMiIHk9IjEwIiB3aWR0aD0iMyIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMTUxLCAzNCwgMjU1KSIvPiAgPHJlY3QgeD0iNiIgeT0iMTAiIHdpZHRoPSIxIiBoZWlnaHQ9IjMiIGZpbGw9InJnYig0MywgNTIsIDE5MCkiLz4gIDxyZWN0IHg9IjciIHk9IjEwIiB3aWR0aD0iMiIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoNTEsIDY4LCAyNTUpIi8+ICA8cmVjdCB4PSI5IiB5PSIxMCIgd2lkdGg9IjEiIGhlaWdodD0iMyIgZmlsbD0icmdiKDM3LCA0MCwgMTM2KSIvPiAgPHJlY3QgeD0iMTEiIHk9IjEwIiB3aWR0aD0iMiIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMTAzLCAyOCwgMTc0KSIvPiAgPHJlY3QgeD0iMTAiIHk9IjExIiB3aWR0aD0iMSIgaGVpZ2h0PSIyIiBmaWxsPSJyZ2IoOTgsIDI3LCAxNjUpIi8+ICA8cmVjdCB4PSIxMSIgeT0iMTEiIHdpZHRoPSIyIiBoZWlnaHQ9IjIiIGZpbGw9InJnYigxNTEsIDM0LCAyNTUpIi8+ICA8cmVjdCB4PSIxMyIgeT0iMTEiIHdpZHRoPSIxIiBoZWlnaHQ9IjIiIGZpbGw9InJnYigxMTIsIDI4LCAxODgpIi8+ICA8cmVjdCB4PSIxMSIgeT0iMTMiIHdpZHRoPSIyIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigxMDgsIDI4LCAxODApIi8+IDwvZz48L3N2Zz4=)](https://modrinth.com/mod/qsl)for your Minecraft version.
2. Download the mod from [![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [![Modrinth](https://img.shields.io/badge/Modrinth-000000.svg?logo=modrinth)](https://modrinth.com/mod/command-debug-service).
3. Put the mod into your `mods` folder.

### IDE Debug Tool
[![release-page](.doc/s1.svg)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases)
[![doc](.doc/s2.svg)](ide-debug-tool/README.md)
* The tool is a `jar` file, you can just download it from [![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) and you can use it without any installation steps.
* For more information, please see [![IDE Debug Tool](https://img.shields.io/badge/IDE%20Debug%20Tool%20%7c%20README%2Emd-000000.svg?logo=markdown&logoColor=50AAFF)](ide-debug-tool/README.md).

### Idea Plugin
![Version](https://img.shields.io/jetbrains/plugin/v/me.jaffe2718.devkit)
![Downloads](https://img.shields.io/jetbrains/plugin/d/me.jaffe2718.devkit)
![Plugin](https://img.shields.io/jetbrains/plugin/r/rating/me.jaffe2718.devkit)
<br>
[![Marketplace](.doc/s0.svg)](https://plugins.jetbrains.com/plugin/22587-minecraft-command-devkit)

1. Download the tool from [![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [![JetBrains Marketplace](https://img.shields.io/badge/JetBrains_Marketplace-000000?logo=jetbrains&logoColor=black&labelColor=FFFFFF)](https://plugins.jetbrains.com/plugin/22587-minecraft-command-devkit), choose the version suitable for the mod.
2. Install the plugin in your IDEA: click `File` -> `Settings` -> `Plugins` -> `⚙️` -> `Install Plugin from Disk...` -> choose the downloaded file, or install it directly from JetBrains Marketplace inside IDE.


## Usage 📖

> **Note**: The Author's most recommended way to use this mod is to use the plugin.

### Mods

* The mods only create 2 socket servers to provide code completion and code execution service.
* The mods should work with other tools like [IDE Debug Tool](#ide-debug-tool) or [IDEA Plugin](#idea-plugin) or [other tools](DEV.md) you write by yourself.

### IDEA Plugin

#### Create a New Datapack Project

1. Setup a new datapack project in IDEA.
   1. At the welcome page of IDEA, click `New Project`;
   2. Choose `Minecraft Datapack`;
   3. Fill in the metadata of the datapack project;
      <details>
         <summary></summary>
         <img src=".doc/plugin/i1000.png" alt="create new project"/>
         <center>fill in metadata</center>
      </details>
   
   4. Click `Create`, and the project will be automatically created with the default structure, and it will create `data/<namespace>/functions/demo.mcfunction` as example.
      <details>
         <summary></summary>
         <img src=".doc/plugin/i1001.png" alt="fill in metadata"/>
         <center>The new datapack project</center>
      </details>

2. Import an existing datapack as a project.
   1. At the welcome page of IDEA, click `New Project`;
   2. Choose `Import Minecraft Datapack`;
   3. Choose the datapack you want to import and fill the project name and extract path;
      <details>
         <summary></summary>
         <img src=".doc/plugin/i1002.png" alt="import existing project"/>
         <center>Choose the datapack you want to import</center>
      </details>
      <details>
         <summary></summary>
         <img src=".doc/plugin/i1003.png" alt="import existing project"/>
         <center>Filling the project name and extract path</center>
      </details>
   
   > **Note**: The plugin will automatically check if the datapack is valid by these rules:<br>&nbsp;&nbsp;&nbsp;&nbsp;1. The datapack must be a zip file;<br>&nbsp;&nbsp;&nbsp;&nbsp;2. The zip file must contain a `pack.mcmeta` file;<br>&nbsp;&nbsp;&nbsp;&nbsp;3. The zip file must contain a `data` folder.<br>If the datapack is invalid, the plugin will prevent you from importing it.
         <details>
            <summary></summary>
            <img src=".doc/plugin/i1004.png" alt="import existing project"/>
            <center>The plugin will prevent you from importing an invalid datapack</center>
         </details>
   
   4. Click `Create`, and the datapack will be automatically imported as a project.
      <details>
         <summary></summary>
         <img src=".doc/plugin/i1005.png" alt="import existing project"/>
         <center>The new project based on the imported datapack</center>
      </details>

#### Edit & Debug Minecraft Function

1. Open or create a project;
2. Open or create a Minecraft function file;
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1006.png" alt="open or create a function file"/>
      <center>create a Minecraft function file</center>
   </details>

3. Start the Minecraft game with the [mod](#mods) installed, and open the world you want to debug;
4. Start edit the `*.mcfunction` file, connect to the socket servers and start debug;
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1007.png" alt="start debug"/>
      <center>Click the function buttons at the toolbar of the editor to get debug services</center>
   </details>
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1008.png" alt="start debug"/>
      <center>Connect to the code execution service</center>
   </details>
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1009.png" alt="start debug"/>
      <center>Connect to the completion service</center>
   </details>

5. Now you can start edit the `*.mcfunction` file and get code completion and code execution service.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1010.png" alt="import existing project"/>
      <center>Code completion</center>
   </details>

6. You can click `Execute Without Log` to execute the command without logging the result to the console.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1011.png" alt="execute without log"/>
      <center>Execute the command without logging</center>
   </details>

7. You can click `Execute` to execute the command and log the result to the console.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i1012.png" alt="execute"/>
      <center>Execute the command and log the result to the console</center>
   </details>
   
   > **Note**: Remember to configure the project JDK. Click `File` -> `Project Structure` to choose the JDK you want to use.
         <details>
            <summary></summary>
            <img src=".doc/plugin/i1013.png" alt="configure project JDK"/>
            <center>Congigure the project JDK</center>
         </details>
   

#### Custom Syntax Highlighting

* Click `File` -> `Settings` -> `Editor` -> `Color Scheme` -> `Minecraft Function` to customize the syntax highlighting.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i2000.png" alt="custom syntax highlighting"/>
      <center>Click <code>File</code> -> <code>Settings</code> -> <code>Editor</code> -> <code>Color Scheme</code> -> <code>Minecraft Function</code> to customize the syntax highlighting</center>
   </details>

#### Minecraft Command Console

1. Launch the Minecraft game with the [mod](#mods) installed, and open the world you want to debug;

2. Click `Tools` -> `Minecraft Command DecKit` -> `Minecraft Command Console` and fill the host and port of the command execution socket server.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i3000.png" alt="open console"/>
      <center>Click <code>Tools</code> -> <code>Minecraft Command DecKit</code> -> <code>Minecraft Command Console</code> to open the console</center>
   </details>
   <details>
      <summary></summary>
      <img src=".doc/plugin/i3001.png" alt="open console"/>
      <center>Fill the host and port of the command execution socket server</center>
   </details>

3. You can write your commands line by line press `Enter` to execute the command.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i3002.png" alt="open console"/>
      <center>Write your commands line by line press <code>Enter</code> to execute the command</center>
   </details>

#### Generate Minecraft Datapack

1. There are two ways to start the task to generate a datapack:
   - Right click the project view and click `Generate Minecraft Datapack` in the context menu.
      <details>
         <summary></summary>
         <img src=".doc/plugin/i4000.png" alt="generate datapack"/>
         <center>Right click the project view and click <code>Generate Minecraft Datapack</code> in the context menu</center>
      </details>
   
   - Click `Tools` -> `Minecraft Command DecKit` -> `Generate Minecraft Datapack` from the main menu bar:
      <details>
         <summary></summary>
         <img src=".doc/plugin/i4001.png" alt="generate datapack"/>
         <center>Click <code>Generate Minecraft Datapack</code> from the main menu bar</center>
      </details>

2. Click `Generate` to start the task, and the datapack will be generated in the `build` folder of the project named `<project_name>.zip`.
   <details>
      <summary></summary>
      <img src=".doc/plugin/i4002.png" alt="generate datapack"/>
      <center>Click <code>Generate</code> to start the task</center>
   </details>

### Command Line Tool

* You can also use the [IDE Debug Tool](#ide-debug-tool) to connect to the socket server and execute the code.
   <details>
      <summary></summary>
      <img src=".doc/cmd/i5001.png" alt="command line tool"/>
      <center>Command Line Tool</center>
   </details>


## Warning ⚠️

When you use this mod, you should be careful.
Because it can execute any command.
Do not send the host and port to any untrusted program to avoid being attacked.
Do not install this mod on your multiplayer server to avoid being attacked by DDOS or other attacks.

## Development 🔧

### Overview
This is the document for developers. 
We will introduce how to develop external tools to work with this mod.
Programing language is not limited, you can use any language you like,
the only thing you need to do is make your tool has the ability to connect to the socket server and interact with it.

### Concepts
- For 2.x version, the mod will create two socket servers, one for code completion and one for code execution, whatever the mod is for `Fabric` or `Quilt`.

    |     Server      |     Type      |                  Description                  |             Accepted Message              |   Returned Message   |
    |:---------------:|:-------------:|:---------------------------------------------:|:-----------------------------------------:|:--------------------:|
    | Code Completion | Socket Server |        The server for code completion         | single line command or unfinished command | multiple line result |
    | Code Execution  | Socket Server | The server for command execution in Minecraft |            single line command            | execution feedbacks  |
- Tips: the message sent is a single line of text, you should add `\n` at the end of the message or auto flush the buffer to send the message to the server.

### More Information
For more information, please visit [![https://img.shields.io/badge/DEV%20%7c%20README%2Emd-000000.svg?logo=markdown&logoColor=50AAFF](https://img.shields.io/badge/Developer%20Documentation%20%7c%20DEV%2Emd-000000.svg?logo=markdown&logoColor=50AAFF)](DEV.md).
  

## License 📜
[![Licence](https://img.shields.io/github/license/Jaffe2718/Command-Debug-DevKit?style=for-the-badge)](LICENSE)<br>
This project is licensed under the MIT License.
You can use this project for any purpose for free.
See the [![Licence](https://img.shields.io/badge/License-MIT-000000.svg?style=flat&color=B0FF00&logoColor=B0FF00)](LICENSE) file for details.

## Update and Cooperation 🤝
Currently, this project is still in development. 
If you have any idea or suggestion,
please open an issue or pull request.
And the plugin is only for `IntelliJ Platform IDEs`, 
if you want to use other IDEs such as `Eclipse` and `VS Code`,
you can create a plugin for it and pull request to this project.
What's more, the mod is only for `Fabric` and `Quilt` currently,
you can rewrite it for `Forge` or other mod loaders and pull request to this project.
I will be very grateful if you can help me to improve this project.