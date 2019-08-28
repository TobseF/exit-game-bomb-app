# 💣 ExitGame Bomb - Android App
 
This bomb is part of an **ExitGame** where payers have to deactivate a bomb by coding challenges.

The Bomb runs on an Android Device. It starts with a countdown before it explodes.
It's not accessible for the players. Players have to deactivate the bomb with coding challenges.
The deactivation commands are sent to an REST interface of the Bomb app.

## [📚 Bomb Instruction Manual](https://github.com/TobseF/its-exit-game-bomb/releases/download/v0.0.1/Bomb.Instruction.Manual.pdf)
The players read the [Bomb Instruction Manual](https://github.com/TobseF/its-exit-game-bomb/releases/download/v0.0.1/Bomb.Instruction.Manual.pdf) which helps
them to stop the bomb. Print it for them.

## 📡 Remote Control
The players solve the puzzle by trigger unit tests which access the bombs REST API. There are two projects which can control the bomb:
1. **[Bomb-Client](https://github.com/TobseF/bomb-client).**  
  The client code with empty unit tests and access the the puzzle endpoints. The players try to disable the bomb remotely with this tests.
2. **[Bomb-Client-App](https://github.com/TobseF/bomb-client-app)**  
  The admin UI which can change settings of the bomb an check the bombs state. This is of course not available for the players.

## 🚀 Start
The App runs on Android and Desktop. So you can run, test and develop it without an Android device or emulator.
To start the app run the Gradle task:

* **Run Desktop:** _desktop > Tasks > other > run_
* **Run Android:** _android > Tasks > other > run_
* **Build Android:** _android > Tasks > build > build_

You can also run the `DesktopLauncher` class direct and skip the gradle build - which can be faster. 
Then you have to set the _Working directory_ of the _Run Configuration_ to  `${your-path}\bomb-app\android\assets`.

After an android build, the apps apks are present in `bomb-app\android\build\outputs\apk\debug` and `...\release`. You can install them with:  
`adb install -r "${full-path}\bomb-app\android\build\outputs\apk\debug\android-debug.apk"`

Run Gradle tasks with at least **[Gradle 5.5](https://gradle.org/install/)** and ensure it runs with a 
**[Java 1.8 JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)**.
You can check this here:  
_IntelliJ > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JVM_

### 💡 External Hardware
💡 The comb can be paired with external Hardware. It connects to a Phillips Hue bridge and controls lights.
So lights switch to ![RED](https://placehold.it/15/f03c15/000000?text=+) if the bomb gets activated and to 
![GREEN](https://placehold.it/15/c5f015/000000?text=+) if it gets disarmed. See `HueService` for details.

🔊 To bomb also plays audio files, so it may be a good idea to connect it with an external Bluetooth speaker.

⏰ The bomb can connect to an external 7-segment hardware timer. See `TimerService` for details.

### ⌨ Controls
Available if _debug_ switch is on:
* Press <kbd>Space</kbd> to switch to the next screen.
* Press <kbd>Enter</kbd> to trigger an action on a screen.

|  Button   |                         Description                                |
|-----------|:-------------------------------------------------------------------|
| Reset Hue | Clear the Hue API key, so you have to pair the  Hue again.         |
| Pair      | Pair the bomb with the Phillips Hue bridge. Creates a new API key. |
| Start     | Start the bomb in inactive mode.                                   |


## 🔧 Config
The settings screen allows to change the bombs configuration.

|  Setting    |                         Description                                     |
|-------------|:------------------------------------------------------------------------|
| Bomb time * | The time before the bomb explodes in minutes.                           |
| Timer IP    | IP Address of the external timer                                        |
| Hue IP      | IP Address of Phillips Hue bridge                                       |
| Hue Room    | Name of the Room with the Hue Lights. Change it in the Phillips Hue App |
| Hue Key     | API Key from a paired HUE. Pair the Hue to retrieve it.                 |
| Debug       | If selected, you can switch the screens by <kbd>Space</kbd> or touch.   |
|  \* The _Bomb time_ can be also changed by the REST interface.                        | 
 
## 🔌 Endpoints
The bomb can be controlled on with a REST endpoint on port `5000`.

|  Endpoint | Type |                       Description                                  |
|-----------|:----:|:-------------------------------------------------------------------|
| `start`   | PUT  | Start the bomb with a specific time in minutes.                    |
| `stop`    | PUT  | Stops the bomb the bomb with the time.                             |   
| `reset`   | PUT  | Start the bomb with a specific time in minutes.                    |
| `info`    | GET  | Reads the bomb state: `screen:LoginPuzzle;finish:1566910090308`.   |
| `connect` | PUT  | Try to solve the the `ConnectPuzzle`.                              |
| `numbers` | PUT  | Try to solve the the `NumbersPuzzle`.                              |

For endpoint details check the `com.libktx.game.endpoint` and `com.libktx.game.puzzle` packages.
You can find a sample implementation to control the bomb in the [Bomb-Client-App](https://github.com/TobseF/bomb-client-app).
If you want to solve the puzzles, check out the [Bomb-Client](https://github.com/TobseF/bomb-client).
  
  
## 🐞 Troubleshooting
```
Exception in thread "LWJGL Application" com.badlogic.gdx.utils.GdxRuntimeException: 
Couldn't load file: icons/icon_16.png
```
You forgot to to set the _Working directory_ of the _Run Configuration_ to  `${your-path}\bomb-app\android\assets`. 

---

```
android:mergeDexDebug FAILED
DexArchiveMergerException: Error while merging dex archives
Program type already present: com.libktx.game.AndroidLauncher
```
Run a _android > Tasks > build > clean_ with gradle, and try it again. 

---

```
Error running 'Run Desktop': Class 'com.libktx.game.desktop.DesktopLauncher' not found in module 'desktop'
```
Just launch it again.

---

```
Exception in thread "main" java.lang.UnsupportedClassVersionError: com/intellij/junit5/JUnit5IdeaTestRunner : 
Unsupported major.minor version 52.0
```
Ensure you run unit tests with an Java 1.8 JDK.