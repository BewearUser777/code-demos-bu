# Code Demos
These are practice programs I made to either improve my programming skills in a specific language or apply my math or physics knowledge to programming.
Most of these are just demos and are not intended to be officially used.



## Languages
- C++
- Python
- Java



## Standalone Scripts
- "matrixproduct.cpp" - a command-line matrix calculator program that performs matrix addition, multiplication, and determinant calculation of square matrices
- "project_game_menu.cpp" - a simple game menu prototype ("OPTIONS" button currently does not have any function and has yet to be finished)
- "project_ball_saver_1.cpp" - a simple ball screensaver (DISCLAIMER: uses std::thread instead of delta time)
- NOTE: in the game menu program, the line containing sf::Font at the very top uses the system font of Mac. For Windows and other devices, refer to [this video](https://www.youtube.com/watch?v=rWwpsw_CR1E&list=PLkX_-fCkj2di5WrSIBE66j5Yq0xmHvpAv&index=17) for help.



## How to run C++ SFML
Running an SFML program in any device is very difficult as there are only a small amount of online tutorials and those that exist do not really explain the setup too well. I do not know how to properly set up SFML on Mac or Linux, but [this SFML 3.0 tutorial for Windows](https://www.youtube.com/watch?v=RHrU3I1nsEI&list=PLkX_-fCkj2di5WrSIBE66j5Yq0xmHvpAv&index=1) may be useful.



## UnknownGameName
"UnknownGameName" is a text-based Java game I created for my final project for my AP Computer Science A class back in 12th grade, though with a SaveState class that no longer exists as of 01/23/2026.



### Project Structure
- "Main.java" - calls the runGame() function from the Commands class to start the game
- "Commands.java" - all of the game logic, progression, and player commands is here
- "Room.java" - handles the explorable areas of the game and their contents
- "Chara.java" - character interactions with the player (boss fights, enemy encounters, etc.)
- "Item.java" - items the player can hold and the logic behind them



### How to Compile & Run
- Download the UnknownGameName folder.
- Download [BlueJ](https://www.bluej.org).
- Open BlueJ and click "Project" on the top left.
- Select "Open Project."
- Locate the UnknownGameName folder and click "Open Project."
- Right click the Main class and click "Compile."
- Right click Main class again and click "void main(String[] args)."



## MAINE
Maine is an AI (intended to be a chatbot) that I started around October-December 2021, although I did not have the time and resources to build a full-scale language model at the time and only started building Maine with Keras transformer architecture around July 2026. As I do not currently have enough knowledge on low-level Computer Science concepts and AI/ML methods, I mostly used YouTube tutorials, AI help, and internet research to build the pre-training programs of Maine, though I am fully aware of how each line of code functions. Currently, Maine is a 250M parameter model at its pre-training stage and may be expanded in the future depending on hardware/software advancements.
