# predator_prey

PredatorPrey is a program that evolves predator prey behavior on a grid
It uses wolf and sheep, and prints to the terminal.
 
This program was written for the Evolutionary Computation Final project, Spring term 2015
Written by Julia Kroll, Valerie Lambert, and Caitlin Donahue

This uses the ECJ Package (A Java-based Evolutionary Computation Research System)
https://cs.gmu.edu/~eclab/projects/ecj/

Our code is in the directory ecj/ec/app/predator_prey

----------------------------------------------------------------------------------------------------------

To run the the program:

-First Navigate to the ecj folder
-Then run the make file (by typing 'make' in the terminal)
-then, from the ecj folder, run the command:

java -classpath ..:. ec.Evolve -file ec/app/predator_prey/predator_prey.params

The program will then print the best fitnesses for each generation 
Every 100 generations, for the first trial it will print the map for every move in that trial. 
To move on to the next step of the map, and on to the rest of the run, hit 'enter'

-The program will also create an 'out.stat' file in the ecj folder, which contains stats on the run, including the GP individual trees


---------------------------------------------------------------------------------------------------------

Modifications to the run can be made by modifying the predator_prey.params file in ec/app/predator_prey
In the parameter file you can modify:
-The sheep intelligence
-The wolf intelligence
-The sheep population size
-the wolf population size
-the board size
-the number of generations
-the complexity of the trees
-The number of moves in each trial

