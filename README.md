# Command Debug DevKit

![icon](idea-plugin/src/main/resources/META-INF/pluginIcon.svg)
![Stars](https://img.shields.io/github/stars/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Forks](https://img.shields.io/github/forks/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Issues](https://img.shields.io/github/issues/Jaffe2718/Command-Debug-DevKit?style=flat-square)
![Licence](https://img.shields.io/github/license/Jaffe2718/Command-Debug-DevKit?style=flat-square)

by Jaffe2718

## Introduction
This project is written for debugging Minecraft commands. In this project, there are serveral subprojects:
- `fabric`: The mod for fabric, used to be a connection kit for IDE. It can create two socket server to provide code completion and code execution service.
- `ide-debug-tool`: This is a command line tool which can connect to the socket server opened by the mod. It can send code to the server and get the result. It can be a debug tool for IDE or a command line tool to execute *.mcfunction file directly.
- `idea-plugin`: This is a plugin for IntelliJ IDEA. It can connect to the socket server opened by the mod. It provides code completion and code execution service for IDE. The plugin contains a compiled `ide-debug-tool` so you don't need to install it manually.
- `quilt`: The mod for quilt, used to be a connection kit for IDE. It can create two socket server to provide code completion and code execution service.

## Setup

### Mods
![follows](https://img.shields.io/modrinth/followers/command-debug-service)
![latestVersion](https://img.shields.io/modrinth/v/command-debug-service)
![mcVersion](https://img.shields.io/modrinth/game-versions/command-debug-service)
![modDownloads](https://img.shields.io/modrinth/dt/command-debug-service)
<br>
<a href="https://modrinth.com/mod/command-debug-service">
<img src="https://img.shields.io/badge/Command%20Debug%20service-000000.svg?style=for-the-badge&logo=modrinth&label=Modrinth&labelColor=000000&color=000000" alt="Command Debug Service"/>
</a>
#### Fabric/Quilt Mod
1. Install Minecraft with`Fabric/Quilt Loader` and 
[![Fabric-API](https://img.shields.io/badge/Fabric%20API-000000?logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgcm9sZT0iaW1nIj4gPGc%2BICA8cmVjdCB4PSIxMSIgeT0iMiIgd2lkdGg9IjEiIGhlaWdodD0iOCIgZmlsbD0icmdiKDcsMCwwKSIvPiAgPHJlY3QgeD0iNCIgeT0iNSIgd2lkdGg9IjIiIGhlaWdodD0iMiIgZmlsbD0icmdiKDkzLDg1LDcwKSIvPiAgPHJlY3QgeD0iMTIiIHk9IjciIHdpZHRoPSIyIiBoZWlnaHQ9IjMiIGZpbGw9InJnYig0MiwzNiwyNCkiLz4gIDxyZWN0IHg9IjYiIHk9IjMiIHdpZHRoPSI3IiBoZWlnaHQ9IjQiIGZpbGw9InJnYigyMzQsMjE5LDE4OSkiLz4gIDxyZWN0IHg9IjgiIHk9IjAiIHdpZHRoPSIyIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iNyIgeT0iMSIgd2lkdGg9IjIiIGhlaWdodD0iMyIgZmlsbD0icmdiKDE0MiwxMzEsMTEyKSIvPiAgPHJlY3QgeD0iNyIgeT0iMSIgd2lkdGg9IjEiIGhlaWdodD0iMiIgZmlsbD0icmdiKDI0LDE4LDgpIi8%2BICA8cmVjdCB4PSI5IiB5PSIxIiB3aWR0aD0iMiIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjA2LDE5MiwxNjcpIi8%2BICA8cmVjdCB4PSIxMCIgeT0iMSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDksNSwwKSIvPiAgPHJlY3QgeD0iNiIgeT0iMyIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI3LDI0LDE2KSIvPiAgPHJlY3QgeD0iMTIiIHk9IjMiIHdpZHRoPSIyIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iNSIgeT0iNCIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI0LDE4LDgpIi8%2BICA8cmVjdCB4PSI2IiB5PSI0IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoMTI0LDExNCw5OCkiLz4gIDxyZWN0IHg9IjkiIHk9IjQiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigxNDIsMTMxLDExMikiLz4gIDxyZWN0IHg9IjE0IiB5PSI0IiB3aWR0aD0iMSIgaGVpZ2h0PSI1IiBmaWxsPSJyZ2IoMjAsMjAsMTApIi8%2BICA8cmVjdCB4PSI0IiB5PSI1IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoNiwwLDApIi8%2BICA8cmVjdCB4PSIxMCIgeT0iNSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDc5LDcyLDU5KSIvPiAgPHJlY3QgeD0iMTMiIHk9IjQiIHdpZHRoPSIxIiBoZWlnaHQ9IjQiIGZpbGw9InJnYigxMDQsOTUsODApIi8%2BICA8cmVjdCB4PSIzIiB5PSI2IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoNiwwLDApIi8%2BICA8cmVjdCB4PSI0IiB5PSI2IiB3aWR0aD0iOCIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjQwLDIyNCwxOTMpIi8%2BICA8cmVjdCB4PSIxMSIgeT0iNiIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDU2LDUwLDM3KSIvPiAgPHJlY3QgeD0iMTUiIHk9IjUiIHdpZHRoPSIxIiBoZWlnaHQ9IjMiIGZpbGw9InJnYig1MSw0NSwzMykiLz4gIDxyZWN0IHg9IjIiIHk9IjciIHdpZHRoPSIyIiBoZWlnaHQ9IjciIGZpbGw9InJnYig2LDAsMCkiLz4gIDxyZWN0IHg9IjMiIHk9IjciIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig5LDUsMCkiLz4gIDxyZWN0IHg9IjE0IiB5PSI1IiB3aWR0aD0iMSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoNzksNzIsNTkpIi8%2BICA8cmVjdCB4PSIxIiB5PSI4IiB3aWR0aD0iMSIgaGVpZ2h0PSI1IiBmaWxsPSJyZ2IoNiwwLDApIi8%2BICA8cmVjdCB4PSIyIiB5PSI4IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoOTMsODUsNzApIi8%2BICA8cmVjdCB4PSIyIiB5PSI4IiB3aWR0aD0iOSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjQwLDIyNCwxOTMpIi8%2BICA8cmVjdCB4PSIwIiB5PSI5IiB3aWR0aD0iMSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoNTEsNDUsMzMpIi8%2BICA8cmVjdCB4PSIxMCIgeT0iMTAiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig5Myw4NSw3MCkiLz4gIDxyZWN0IHg9IjIiIHk9IjExIiB3aWR0aD0iMiIgaGVpZ2h0PSIyIiBmaWxsPSJyZ2IoODEsNzQsNTkpIi8%2BICA8cmVjdCB4PSIzIiB5PSIxMSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDIwNiwxOTIsMTY3KSIvPiAgPHJlY3QgeD0iNSIgeT0iMTMiIHdpZHRoPSIyIiBoZWlnaHQ9IjMiIGZpbGw9InJnYigyMTYsMjA1LDE3MikiLz4gIDxyZWN0IHg9IjQiIHk9IjExIiB3aWR0aD0iNSIgaGVpZ2h0PSIzIiBmaWxsPSJyZ2IoMjI2LDIxMSwxODIpIi8%2BICA8cmVjdCB4PSI5IiB5PSIxMSIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDEyNCwxMTQsOTgpIi8%2BICA8cmVjdCB4PSIxMCIgeT0iMTEiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iOCIgeT0iMTIiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigxMDQsOTYsODApIi8%2BICA8cmVjdCB4PSI5IiB5PSIxMiIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI3LDI0LDE2KSIvPiAgPHJlY3QgeD0iOCIgeT0iMTMiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigyNCwxOCw4KSIvPiAgPHJlY3QgeD0iNCIgeT0iMTQiIHdpZHRoPSIxIiBoZWlnaHQ9IjEiIGZpbGw9InJnYigzMiwyNSwxMikiLz4gIDxyZWN0IHg9IjciIHk9IjE0IiB3aWR0aD0iMSIgaGVpZ2h0PSIxIiBmaWxsPSJyZ2IoNDQsNDAsMjgpIi8%2BICA8cmVjdCB4PSI4IiB5PSIxMyIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0icmdiKDI3LDI0LDE2KSIvPiAgPHJlY3QgeD0iNSIgeT0iMTUiIHdpZHRoPSIzIiBoZWlnaHQ9IjEiIGZpbGw9InJnYig0MiwzNiwyNCkiLz4gPC9nPjwvc3ZnPg%3D%3D)](https://modrinth.com/mod/fabric-api)
or [![Quilted Fabric API](https://img.shields.io/badge/Quilted%20Fabric%20API-000000?logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGlkPSJMYXllcl8xIiBkYXRhLW5hbWU9IkxheWVyIDEiIHZpZXdCb3g9IjAgMCA5MTMuNTMgOTEzLjUzIj48ZGVmcz48c3R5bGU%2BLmNscy0xe2ZpbGw6Izk3MjJmZn0uY2xzLTJ7ZmlsbDojZGMyOWRkfS5jbHMtM3tmaWxsOiMyN2EyZmR9LmNscy00e2ZpbGw6IzM0Zn08L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMjQ5LjE2IDEyOC41NGExNS4yOCAxNS4yOCAwIDAgMCAxNS4yNiAxNS4yN2gyNnY1OGgtMjZhMTUuMjcgMTUuMjcgMCAwIDAgMCAzMC41M2gyNnY0NC41YTEzLjUyIDEzLjUyIDAgMCAxLTEzLjUyIDEzLjUzaC00NC41di0yNmExNS4yNyAxNS4yNyAwIDAgMC0zMC41NCAwdjI2aC01OHYtMjZhMTUuMjcgMTUuMjcgMCAxIDAtMzAuNTMgMHYyNkg2OC43N2ExMy41NCAxMy41NCAwIDAgMS0xMy41My0xMy41M1Y2OC43OGExMy41MyAxMy41MyAwIDAgMSAxMy41My0xMy41M2gyMDguMDlhMTMuNTIgMTMuNTIgMCAwIDEgMTMuNTIgMTMuNTN2NDQuNDhoLTI2YTE1LjI3IDE1LjI3IDAgMCAwLTE1LjIyIDE1LjI4WiIgY2xhc3M9ImNscy0xIi8%2BPHBhdGggZD0iTTUxNC44NCAxMjguNTRhMTUuMjcgMTUuMjcgMCAwIDAgMTUuMjYgMTUuMjdoMjZ2NThoLTI2YTE1LjI3IDE1LjI3IDAgMCAwIDAgMzAuNTNoMjZ2NDQuNWExMy41MyAxMy41MyAwIDAgMS0xMy41MiAxMy41M0gzMzQuNDVhMTMuNTIgMTMuNTIgMCAwIDEtMTMuNTItMTMuNTN2LTQ0LjVoMjUuOTRhMTUuMjcgMTUuMjcgMCAxIDAgMC0zMC41M2gtMjUuOTR2LTU4aDI1Ljk0YTE1LjI4IDE1LjI4IDAgMSAwIDAtMzAuNTVoLTI1Ljk0VjY4Ljc4YTEzLjUxIDEzLjUxIDAgMCAxIDEzLjUyLTEzLjUzaDIwOC4wOWExMy41MiAxMy41MiAwIDAgMSAxMy41MiAxMy41M3Y0NC40OGgtMjZhMTUuMjcgMTUuMjcgMCAwIDAtMTUuMjIgMTUuMjhaIiBjbGFzcz0iY2xzLTIiLz48cGF0aCBkPSJNODIxLjczIDY4Ljc4djIwOC4wOGExMy41MiAxMy41MiAwIDAgMS0xMy41MiAxMy41M2gtNDQuNXYtMjZhMTUuMjcgMTUuMjcgMCAxIDAtMzAuNTMgMHYyNmgtNTh2LTI2YTE1LjI3IDE1LjI3IDAgMSAwLTMwLjUzIDB2MjZoLTQ0LjVhMTMuNTQgMTMuNTQgMCAwIDEtMTMuNTMtMTMuNTN2LTQ0LjVoMjZhMTUuMjcgMTUuMjcgMCAxIDAgMC0zMC41M2gtMjZ2LTU4aDI2YTE1LjI4IDE1LjI4IDAgMCAwIDAtMzAuNTVoLTI2di00NC41YTEzLjUzIDEzLjUzIDAgMCAxIDEzLjUzLTEzLjUzaDIwOC4wNmExMy41MiAxMy41MiAwIDAgMSAxMy41MiAxMy41M1oiIGNsYXNzPSJjbHMtMyIvPjxwYXRoIGQ9Ik0yOTAuMzggMzM0LjQ0djIwOC4wOWExMy41MiAxMy41MiAwIDAgMS0xMy41MiAxMy41M2gtNDQuNXYtMjZhMTUuMjcgMTUuMjcgMCAwIDAtMzAuNTQgMHYyNmgtNTh2LTI2YTE1LjI3IDE1LjI3IDAgMSAwLTMwLjUzIDB2MjZINjguNzdhMTMuNTMgMTMuNTMgMCAwIDEtMTMuNTMtMTMuNTNWMzM0LjQ0YTEzLjU0IDEzLjU0IDAgMCAxIDEzLjUzLTEzLjUyaDQ0LjV2MjZhMTUuMjcgMTUuMjcgMCAwIDAgMzAuNTMgMHYtMjZoNTh2MjZhMTUuMjcgMTUuMjcgMCAwIDAgMzAuNTQgMHYtMjZoNDQuNWExMy41MiAxMy41MiAwIDAgMSAxMy41NCAxMy41MloiIGNsYXNzPSJjbHMtMiIvPjxwYXRoIGQ9Ik01MTQuODQgMzk0LjIxYTE1LjI1IDE1LjI1IDAgMCAwIDE1LjI2IDE1LjI2aDI2djU4aC0yNmExNS4yNyAxNS4yNyAwIDAgMCAwIDMwLjUzaDI2djQ0LjVhMTMuNTIgMTMuNTIgMCAwIDEtMTMuNTIgMTMuNTNINDk4di0yNmExNS4yNyAxNS4yNyAwIDEgMC0zMC41MyAwdjI2aC01OHYtMjZhMTUuMjcgMTUuMjcgMCAxIDAtMzAuNTQgMHYyNmgtNDQuNDhhMTMuNTEgMTMuNTEgMCAwIDEtMTMuNTItMTMuNTNWMzM0LjQ0YTEzLjUyIDEzLjUyIDAgMCAxIDEzLjUyLTEzLjUyaDIwOC4wOWExMy41MyAxMy41MyAwIDAgMSAxMy41MiAxMy41MnY0NC41aC0yNmExNS4yNiAxNS4yNiAwIDAgMC0xNS4yMiAxNS4yN1oiIGNsYXNzPSJjbHMtMyIvPjxwYXRoIGQ9Ik04MjEuNzMgMzM0LjQ0djIwOC4wOWExMy41MiAxMy41MiAwIDAgMS0xMy41MiAxMy41M0g2MDAuMTJhMTMuNTMgMTMuNTMgMCAwIDEtMTMuNTMtMTMuNTNWNDk4aDI2YTE1LjI3IDE1LjI3IDAgMSAwIDAtMzAuNTNoLTI2di01OGgyNmExNS4yNyAxNS4yNyAwIDEgMCAwLTMwLjUzaC0yNnYtNDQuNWExMy41NCAxMy41NCAwIDAgMSAxMy41My0xMy41Mmg0NC41djI2YTE1LjI3IDE1LjI3IDAgMCAwIDMwLjUzIDB2LTI2aDU4djI2YTE1LjI3IDE1LjI3IDAgMCAwIDMwLjUzIDB2LTI2aDQ0LjVhMTMuNTIgMTMuNTIgMCAwIDEgMTMuNTUgMTMuNTJaIiBjbGFzcz0iY2xzLTQiLz48cGF0aCBkPSJNMjQ5LjE2IDY1OS44OWExNS4yOCAxNS4yOCAwIDAgMCAxNS4yNiAxNS4yN2gyNnY1OGgtMjZhMTUuMjcgMTUuMjcgMCAwIDAgMCAzMC41NGgyNnY0NC41YTEzLjUyIDEzLjUyIDAgMCAxLTEzLjUyIDEzLjUzSDY4Ljc3YTEzLjU0IDEzLjU0IDAgMCAxLTEzLjUzLTEzLjUzVjYwMC4xM2ExMy41MyAxMy41MyAwIDAgMSAxMy41My0xMy41M2g0NC41djI1Ljk1YTE1LjI3IDE1LjI3IDAgMSAwIDMwLjUzIDBWNTg2LjZoNTh2MjUuOTVhMTUuMjcgMTUuMjcgMCAxIDAgMzAuNTQgMFY1ODYuNmg0NC41YTEzLjUyIDEzLjUyIDAgMCAxIDEzLjUyIDEzLjUzdjQ0LjQ4aC0yNmExNS4yOCAxNS4yOCAwIDAgMC0xNS4yIDE1LjI4WiIgY2xhc3M9ImNscy0xIi8%2BPHBhdGggZD0iTTU1Ni4wNiA2MDAuMTN2MjA4LjA4YTEzLjUzIDEzLjUzIDAgMCAxLTEzLjUyIDEzLjUzSDMzNC40NWExMy41MiAxMy41MiAwIDAgMS0xMy41Mi0xMy41M3YtNDQuNWgyNS45NGExNS4yNyAxNS4yNyAwIDEgMCAwLTMwLjU0aC0yNS45NHYtNThoMjUuOTRhMTUuMjggMTUuMjggMCAxIDAgMC0zMC41NWgtMjUuOTR2LTQ0LjQ5YTEzLjUxIDEzLjUxIDAgMCAxIDEzLjUyLTEzLjUzaDQ0LjQ5djI1Ljk1YTE1LjI3IDE1LjI3IDAgMSAwIDMwLjU0IDBWNTg2LjZoNTh2MjUuOTVhMTUuMjcgMTUuMjcgMCAxIDAgMzAuNTMgMFY1ODYuNmg0NC41YTEzLjUyIDEzLjUyIDAgMCAxIDEzLjU1IDEzLjUzWiIgY2xhc3M9ImNscy00Ii8%2BPHJlY3Qgd2lkdGg9IjIzNS4xNCIgaGVpZ2h0PSIyMzUuMTQiIHg9IjYzNS4zIiB5PSI2MzUuMjkiIGNsYXNzPSJjbHMtMSIgcng9IjEzLjUzIiB0cmFuc2Zvcm09InJvdGF0ZSgtNDUgNzUyLjg1NyA3NTIuODY2KSIvPjwvc3ZnPg%3D%3D)](https://modrinth.com/mod/qsl)for your Minecraft version.
2. Download the mod from [![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [![Modrinth](https://img.shields.io/badge/Modrinth-000000.svg?logo=modrinth)](https://modrinth.com/mod/command-debug-service).
3. Put the mod into your `mods` folder.

### IDE Debug Tool
[![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github&style=for-the-badge)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases)
* The tool is a `jar` file, you can just download it from [![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) and you can use it without any installation steps.
* For more information, please see [![IDE Debug Tool](https://img.shields.io/badge/IDE%20Debug%20Tool%20%7c%20README%2Emd-000000.svg?logo=markdown&logoColor=50AAFF)](ide-debug-tool/README.md).

### Idea Plugin
![Version](https://img.shields.io/jetbrains/plugin/v/me.jaffe2718.devkit)
![Downloads](https://img.shields.io/jetbrains/plugin/d/me.jaffe2718.devkit)
![Plugin](https://img.shields.io/jetbrains/plugin/r/rating/me.jaffe2718.devkit)
<br>
<a href="https://plugins.jetbrains.com/plugin/22587-minecraft-command-devkit">
   <img src="https://img.shields.io/badge/Get_from_Marketplace-000000.svg?style=for-the-badge&logo=jetbrains&logoColor=000000&labelColor=orange&color=000000" alt="Minecraft Command DevKit"/>
</a>
1. Download the tool from [![release-page](https://img.shields.io/badge/GitHub%20Releases-000000.svg?logo=github)](https://github.com/Jaffe2718/Command-Debug-DevKit/releases) or [![JetBrains Marketplace](https://img.shields.io/badge/JetBrains_Marketplace-000000?logo=jetbrains&logoColor=EE7010&labelColor=black)](https://plugins.jetbrains.com/plugin/22587-minecraft-command-devkit), choose the version suitable for the mod.
2. Install the plugin in your IDEA: click `File` -> `Settings` -> `Plugins` -> `⚙️` -> `Install Plugin from Disk...` -> choose the downloaded file, or install it directly from JetBrains Marketplace inside IDE.


## Usage
1. Start your Minecraft game and join or create a world. The mod will create two socket server and show the host and port information in the game chat.

   <br>

2. Start your IDE, create or open a project.

   <br>

3. Create or open a file with suffix `.mcfunction`.
   <details>
      <summary>edit *.mcfunction file</summary>
      <img src=".doc/i0.png" alt="edit *.mcfunction file"/>
   </details>
   <br>
4. Connect to these two socket servers by entering the host and port which shown in the game chat. Then you can use the code completion and code execution service.
   <details>
     <summary>connect to socket server</summary>
     <img src=".doc/i1.png" alt="connect to socket server"/>
   </details>
   <details>
     <summary>code completion</summary>
     <img src=".doc/i2.png" alt="code completion"/>
   </details>
   <details>
      <summary>code execution</summary>
      <img src=".doc/i3.png" alt="code execution"/>
   </details>
   <br>
5. You can also start the interactive console by starting the tool from `Tools -> Minecraft Command Console`
   or by pressing `Ctrl + Alt + M` (or `Command + Alt + M` on Mac),
   then you can send single line commands to the game and get the feedback one by one.
6. You can also use the command line tool to connect to the socket server and execute the code.


## Warning
![Static Badge](https://img.shields.io/badge/Warning-000000?logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIHZpZXdCb3g9IjAgMCAxNiAxNiIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGcgaWQ9ImlkZWEvY29tbXVuaXR5L3BsYXRmb3JtL2ljb25zL3NyYy9nZW5lcmFsL2luc3BlY3Rpb25zRXJyb3IiPgo8cGF0aCBpZD0iQ29tYmluZWQgU2hhcGUiIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNOCAxM0MxMC43NjE0IDEzIDEzIDEwLjc2MTQgMTMgOEMxMyA1LjIzODU4IDEwLjc2MTQgMyA4IDNDNS4yMzg1OCAzIDMgNS4yMzg1OCAzIDhDMyAxMC43NjE0IDUuMjM4NTggMTMgOCAxM1pNOSA1VjguNUg3VjVIOVpNOSAxMS41VjkuNUg3VjExLjVIOVoiIGZpbGw9IiNFRTcwMTAiLz4KPC9nPgo8L3N2Zz4%3D&labelColor=black&color=EE7010)
<br>
When you use this mod, you should be careful.
Because it can execute any command.
Do not send the host and port to any untrusted program to avoid being attacked.
Do not install this mod on your multiplayer server to avoid being attacked by DDOS or other attacks.

## License
[![Licence](https://img.shields.io/github/license/Jaffe2718/Command-Debug-DevKit?style=for-the-badge)](LICENSE)<br>
This project is licensed under the MIT License.
You can use this project for any purpose for free.
See the [![Licence](https://img.shields.io/badge/License-MIT-000000.svg?style=flat&color=B0FF00&logoColor=B0FF00)](LICENSE) file for details.

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