# PR_Game
PR Game version 1.0

The point of this project is o create a 2D asteroid mining simulator game that is as realistic as possible while still being fun
Currently the game consist of four sprites. The ship, the earth, the astgeroid, and the sun. The user controls the ship sprite 
with the arrow keys on the keyboard. EAch time a key is pressed it enacts "thrust" on the ship going in that direction.
There is a percent metals mined meter and amount of rocket fuel meter on the top of the screen. At the games start 
there is 100% rocket fuel and 0% metals. The goal of the game is to navigate the ship to the asteroid, mine for metals,
and deliver those metals to earth as many times as you can before the rocket fuel amount reaches 0 or
the asteroid is depleted of ore.  

The orbits of the asteroid and the earth around the sun are preset in the OrbitCalculation method as parameterized equations.
The movement of the ship is calculated in that same method but uses the thrust exerced by the user and acceliration due to 
"gravity" from the sun and (to a less extent) from the earth. 
