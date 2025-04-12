# NeatFX
NeatFX is a library that makes working with visual interfaces on java easier, by removing the
use of java's default library **_swing_** and having to prepare your applications for different
window resolutions and input handling or crash handling.

> [!NOTE]
> This project is still in development and will probably contain many errors and issues.
> Please report any problems or give feedback on [the issues tab](https://github.com/dokko-games/WinForJUI/issues).
> It will also probably go under huge changes in the future like changing the graphics API, so it's better to wait for full releases before using it.

![License](https://img.shields.io/github/license/dokko-games/WinForJUI)

## Quick Access
- [Why Use NeatFX](#why)
- [Installation](#installation)
- - [Requirements](#requirements)
- [Features](#features)
- [Usage](#usage)
- [Contributing](#-contributing-to-the-project)
- [License](#license)

## Why?
Java's **_Swing library_** is outdated and confusing for beginners. Other libraries like JavaFX are hard to install
and finally, **using** more low-level libraries like OpenGL or Vulkan is extremely complicated,
especially for a programmer that is just getting started with java.<br>
<br>For that reason, I made **NeatFX**, a library that takes the good stuff from OpenGL and 
mixes it with custom features to create the best framework possible.
## Installation
### For Users
Go into [Releases](https://github.com/dokko-games/WinForJUI/releases) and select the version you want.
After you choose a version, go under assets and select <i>NeatFX.jar</i>
### For developers
1. **Clone the repository**
```bash
git clone https://github.com/dokko-games/WinForJUI.git
```
2. **Navigate to the generated folder**
```bash 
cd WinForJUI
```

After doing this, open your IDE **from your terminal,** and it will automatically open the repository as a workspace.
If you don't want to open it from your terminal just open the created folder WinForJUI on your code editor.
It is recommended to use **IntelliJ Idea** when working **on** the project.
## Requirements:
##### Already installed:
- LWJGL
- Lombok
##### Requires installation:
- Intellij Lombok plugin
## Features
- Easy to use✅
- Useful for games or other types of software too ✅
- Good-looking and customizable design ✅
- Easy to install ✅
## Usage
To make a basic app, you must first make a simple Main class, like in any java program, and add the following code to the main method:
```java
int monitorWidth = ; // your screen's width
int monitorHeight = ; // your screen's height
NeatFX.setDeveloperScreenSize(monitorWidth, monitorHeight);
// If you want to use dark mode add:

//NeatFX.setDesign(new DefaultDarkDesign());

// Needed for NeatFX to work
NeatFX.initialize();

String windowTitle = ; // the window's title
int windowWidth = ; // the window's width
int windowHeight = ; // the window's height
Window window = new Window(windowTitle, windowWidth, windowHeight);

// To add any elements:
Panel panel = new Panel(0, 0, 100, 100, Anchors.CENTERED_SCALE);
window.add(panel);

window.renderLoop(); // Start rendering the window
window.cleanUp(); // After closing it, free all memory allocated by OpenGL
NeatFX.exit(0); // This line is very important, ALWAYS include it in your program
```
## 🤝 Contributing to the project
Contributions are welcome! If you have any suggestions or bug reports feel free to create a new [Issue](https://github.com/dokko-games/WinForJUI/issues)
or [Pull Request](https://github.com/dokko-games/WinForJUI/pulls)
## License
WinForJUI is licensed under the license <i>LGPL-2.1</i>. See the [LICENSE](LICENSE) file for details.