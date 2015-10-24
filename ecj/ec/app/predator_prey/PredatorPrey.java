/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Runs a simulation of predator prey
*Evolves Wolves and can also evolve sheep
*/

package ec.app.predator_prey;
import ec.*;
import ec.coevolve.*;
import ec.util.*;
import java.util.*;
import ec.gp.*;
import ec.gp.koza.*;
import java.io.*;
import ec.app.predator_prey.func.*;
import ec.simple.*;

public class PredatorPrey extends GPProblem implements GroupedProblemForm {
    
    //for printing purposes
    public static final String P_MOVES = "moves";
    public static final String P_SHEEPSIZE = "sheepsize";
    public static final String P_WOLFSIZE = "wolfsize";
    public static final String P_POP_SUBPOP = "pop.subpops";
    public static final String P_XDIM = "xdim";
    public static final String P_YDIM = "ydim";
    public static final String P_SHEEPINTEL = "sheepintel";
    public static final String P_WOLFINTEL = "wolfintel";

    
    // maximum number of moves
    public int maxMoves;
    public Map map;
    public int sheepPopSize;
    public int wolfPopSize;
    public int sheepIntel;
    public int wolfIntel;
    public int x_dim;
    public int y_dim;
    public Agent cur_agent;
    public Random rgen;
    public int generationCount;
    public int trialCount;
    
    
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);
        generationCount = 0;
        trialCount = 0;
        // how many maxMoves?
        maxMoves = state.parameters.getInt(base.push(P_MOVES),null,1);
        if (maxMoves==0) {
            state.output.error("The number of moves a wolf has to make must be >0");
        }
        //Gets information from the parameter file
        sheepPopSize = state.parameters.getInt(base.push(P_SHEEPSIZE),null);
        sheepIntel = state.parameters.getInt(base.push(P_SHEEPINTEL),null);
        wolfIntel = state.parameters.getInt(base.push(P_WOLFINTEL),null);
        wolfPopSize = state.parameters.getInt(base.push(P_WOLFSIZE),null);
        x_dim = state.parameters.getInt(base.push(P_XDIM),null);
        y_dim = state.parameters.getInt(base.push(P_YDIM),null);
        
        //Creates the map
        map = new Map(x_dim, y_dim, sheepPopSize, wolfPopSize);
        rgen = new Random();
    }
    
    /**
    Returns the amount of food at plot x,y on the map
    */
    public int getFoodAmount(int x, int y) {
        return map.getFood(x,y);
    }
    
     /**
     Gets the facing of the current wolf. 0-->N, 1-->E, 2-->S, 3-->W
     */
    public int getIndOrientation() {
        return cur_agent.getOrientation();
    }
    
    /**
    Gets the x position of the current wolf
    */
    public int getIndX() {
        return cur_agent.getX();
    }
    
    /**
    Gets the Y position of the current wolf
    */
    public int getIndY() {
        return cur_agent.getY();
    }
    
    /**
    Gets the Agent type of the individual at x,y. 0--> Sheep, 1--> Wolf, 2--> None
    */
    public int getAgentType(int x, int y) {
        return map.getAgentType(x,y);
    }
    
    /**
    Prepares the population to run
    */
    public void preprocessPopulation(final EvolutionState state, Population pop, final boolean[] prepareForFitnessAssessment, final boolean countVictoriesOnly) {
        generationCount ++;
        
    }
    
    /**
    Sets the trial count to 0 (Allows it to print only one trial every 100 generations)
    */
    public void postprocessPopulation(final EvolutionState state, Population pop, final boolean[] assessFitness, final boolean countVictoriesOnly) {  
        trialCount = 0;
    }
    
    /**
    Takes a sheep as an argument, checks the adjacent squares for wolves and moves the sheep in the opposite direction of the wolf. If surrounded by more than one wold the sheep will run from the first wolf it sees.
    If there is no wolf adjacent to the sheep this returns false
    If the sheep moves this returns true
    */
    public boolean checkAdjacentSquares(int sheep_i) {
        int sheepOrientation = map.sheep_pop[sheep_i].facing;
        int sheepX =map.sheep_pop[sheep_i].x_pos;
        int sheepY = map.sheep_pop[sheep_i].y_pos;
        int northAgent = getAgentType(sheepX,sheepY - 1);
        int southAgent = getAgentType(sheepX,sheepY + 1);
        int westAgent = getAgentType(sheepX - 1,sheepY);
        int eastAgent = getAgentType(sheepX + 1,sheepY);
        // If there is a wolf to the north of the sheep
        if (northAgent == 1) {
            if (sheepOrientation == 0) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 1) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 2) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
            //If tehre is a wolf to the east of the sheep
        } else if (eastAgent == 1) {
            if (sheepOrientation == 1) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 2) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 3) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
            //If there is a wolf south of the sheep
        } else if ( southAgent == 1) {
            if (sheepOrientation == 2) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 3) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 0) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
            //If there is a wolf east of the sheep
        } else if (westAgent == 1) {
            if (sheepOrientation == 3) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 0) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 1) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        }
        int newSheepX =map.sheep_pop[sheep_i].x_pos;
        int newSheepY = map.sheep_pop[sheep_i].y_pos;
        
        if (newSheepX == sheepX && newSheepY == sheepY) {
            return false;
        }
        return true;
    }
    
    /**
    Checks the squares two spaces away from the sheep in the cardinal directions.
    If there is a wolf, this moves the sheep in the opposite direction of the first wolf it sees and this returns true.
    If there is not a wolf this returns false
    */
    public boolean checkTwoSquaresAway(int sheep_i) {
        int sheepOrientation = map.sheep_pop[sheep_i].facing;
        int sheepX =map.sheep_pop[sheep_i].x_pos;
        int sheepY = map.sheep_pop[sheep_i].y_pos;
        int northAgent = getAgentType(sheepX,sheepY - 2);
        int southAgent = getAgentType(sheepX,sheepY + 2);
        int westAgent = getAgentType(sheepX - 2,sheepY);
        int eastAgent = getAgentType(sheepX + 2,sheepY);
        if (northAgent == 1) {
            if (sheepOrientation == 0) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 1) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 2) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        } else if (eastAgent == 1) {
            if (sheepOrientation == 1) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 2) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 3) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        } else if ( southAgent == 1) {
            if (sheepOrientation == 2) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 3) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 0) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        } else if (westAgent == 1) {
            if (sheepOrientation == 3) {
                map.backward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 0) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 1) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        }
        int newSheepX =map.sheep_pop[sheep_i].x_pos;
        int newSheepY = map.sheep_pop[sheep_i].y_pos;
        
        if (newSheepX == sheepX && newSheepY == sheepY) {
            return false;
        }
        return true;
    }
    
    /**
    Moves the sheep in a random direction
    */
    public void randomMove(int sheep_i) {
        int n = rgen.nextInt(4);
        if (n == 0) {
            map.right(map.sheep_pop[sheep_i]);
        } else if (n == 1) {
            map.left(map.sheep_pop[sheep_i]);
        } else if (n == 2) {
            map.forward(map.sheep_pop[sheep_i]);
        } else {
            map.backward(map.sheep_pop[sheep_i]);
        }
    }
    
    /**
    Checks the squares diagonal from the sheep (NW, NE, SE, SW)
    If there is a wolf, the sheep moves away from the first wolf that it sees, and this returns true.
    If there is no wolf this returns false.
    */
    public boolean checkDiagonalSquares (int sheep_i) {
        int sheepOrientation = map.sheep_pop[sheep_i].facing;
        int sheepX =map.sheep_pop[sheep_i].x_pos;
        int sheepY = map.sheep_pop[sheep_i].y_pos;
        int northWestAgent = getAgentType(sheepX - 1,sheepY - 1);
        int northEastAgent = getAgentType(sheepX + 1,sheepY - 1);
        int southEastAgent = getAgentType(sheepX + 1,sheepY + 1);
        int southWestAgent = getAgentType(sheepX - 1,sheepY + 1);
        if (northWestAgent == 1) {
            if (sheepOrientation == 0) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 1) {
                map.forward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 2) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        } else if (northEastAgent == 1) {
            if (sheepOrientation == 1) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 2) {
                map.forward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 3) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        } else if ( southEastAgent == 1) {
            if (sheepOrientation == 2) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 3) {
                map.forward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 0) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        } else if (southWestAgent == 1) {
            if (sheepOrientation == 3) {
                map.right(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 0) {
                map.forward(map.sheep_pop[sheep_i]);
            } else if (sheepOrientation == 1) {
                map.forward(map.sheep_pop[sheep_i]);
            } else {
                map.left(map.sheep_pop[sheep_i]);
            }
        }
        int newSheepX =map.sheep_pop[sheep_i].x_pos;
        int newSheepY = map.sheep_pop[sheep_i].y_pos;
        
        if (newSheepX == sheepX && newSheepY == sheepY) {
            return false;
        }
        return true;
    }
    
    /**
    evaluates the Wolf and sheep's behaviour on the map.
    */
    public void evaluate(final EvolutionState state,
        final Individual[] ind,  // the individuals to evaluate together
        final boolean[] updateFitness,  // should this individuals' fitness be updated?
        final boolean countVictoriesOnly, // don't bother updating Fitness with socres, just victories
        final int[] subpops,
        final int threadnum) {
            
        PredatorPreyData input = (PredatorPreyData)(this.input);

        //This moves the wolves according to the intelligence defined in the parameter file
        //0 means random, 1 means evolving
        for(int moves=0;moves<maxMoves; ) {
            //Wolves move first
            for (int wolf_i = 0; wolf_i < wolfPopSize; wolf_i++) {
                cur_agent = map.wolf_pop[wolf_i];
                if (wolfIntel == 0) {
                    //Random wolves
                    if (map.wolf_pop[wolf_i].isAlive) {
                        int n = rgen.nextInt(4);
                        if (n == 0) {
                            map.forward(map.wolf_pop[wolf_i]);
                        } else if (n == 1) {
                            map.right(map.wolf_pop[wolf_i]);
                        } else if (n == 2) {
                            map.backward(map.wolf_pop[wolf_i]);
                        } else {
                            map.left(map.wolf_pop[wolf_i]);
                        }
                    }
                } else {
                    //Evaluate the tree of the wolf. 0-->Forward, 1-->right, 2-->backward, 3-->left
                    ((GPIndividual)ind[wolf_i]).trees[0].child.eval(state,threadnum,input,stack,((GPIndividual)ind[wolf_i]),this);
                    int eval = input.val;
                    if (eval == 0) {
                        map.forward(cur_agent);
                    } else if (eval == 1) {
                        map.right(cur_agent);
                    } else if (eval == 2) {
                        map.backward(cur_agent);
                    } else if (eval == 3) {
                        map.left(cur_agent);
                    } else {
                        System.out.println("eval non-4mod " + eval);
                    }
                }   
            }
            //Next move the sheep population
            //Moves according to intelligence defined in the parameter file
            // 0 --> Random move
            //1 --> sees adjacent squares
            //2 --> sees adjacent squares and diagonals
            //3 --> sees adjacent squares, diagonals, and two squares away
            //4 --> Sheep tries to evolve
            for (int sheep_i = 0; sheep_i < sheepPopSize; sheep_i++) {
                cur_agent = map.sheep_pop[sheep_i];
                if (map.sheep_pop[sheep_i].isAlive) {
                    if (sheepIntel == 0) {
                        //Random sheep
                        randomMove(sheep_i); 
                    } else if (sheepIntel == 1) {
                        //Smart sheep. If a sheep sees a wolf in a spot next to it
                        //it moves in the opposite direction of the wolf.
                        //can't see diagonals.
                        boolean moved = false;
                        moved = checkAdjacentSquares(sheep_i);
                        if (moved == false) {
                            randomMove(sheep_i);    
                        }    
                    } else if (sheepIntel == 2) {
                        boolean moved = false;
                        moved = checkAdjacentSquares(sheep_i);
                        if (moved == false) {
                            moved = checkDiagonalSquares(sheep_i);    
                        }
                        if (moved == false) {
                            randomMove(sheep_i);
                        }
                    } else if (sheepIntel == 3) {
                        boolean moved = false;
                        moved = checkAdjacentSquares(sheep_i);
                        if (moved == false) {
                            moved = checkDiagonalSquares(sheep_i);    
                        }
                        if (moved == false) {
                            moved = checkTwoSquaresAway(sheep_i);
                        }
                        if (moved == false) {
                            randomMove(sheep_i);
                        }
                        //evaluate the tree of the current wolf
                    } else {                ((GPIndividual)ind[sheep_i+wolfPopSize]).trees[0].child.eval(state,threadnum,input,stack,((GPIndividual)ind[sheep_i+wolfPopSize]),this);
                        int eval = input.val;
                        if (eval == 0) {
                            map.forward(cur_agent);
                        } else if (eval == 1) {
                            map.right(cur_agent);
                        } else if (eval == 2) {
                            map.backward(cur_agent);
                        } else if (eval == 3) {
                            map.left(cur_agent);
                        } else {
                            System.out.println("eval non-4mod " + eval);
                        }
                    }
                } 
            }
            moves++;
            //Prints the map of the first trial every 100 generations
            //The map will print for every move, you must press 'enter' to 
            //see the next move
            if ((generationCount % 100 ==0 || generationCount == 1) && trialCount == 1) {
                map.printGrid();    
            } 
        }
        //Fitness
        int food_sum = 0;
        for(int i = 0 ; i < wolfPopSize; i++) {
            food_sum += map.wolf_pop[i].foodEaten;
        }
        //assign fitness to each individual
        for(int i = 0 ; i < ind.length; i++) {        
        // Koza Fitness -- we decided not to use this
          //  KozaFitness f = ((KozaFitness)ind[i].fitness);
            //f.setStandardizedFitness(state,((int)Math.pow(2, sheepPopSize)-(int)Math.pow(2, map.wolf_pop[i].foodEaten)));
            //f.hits = (int)Math.pow(2, map.wolf_pop[i].foodEaten);
            
            //If the individual is a sheep
            if (i < wolfPopSize) {
                //The fitness for wolves is equal to the total amount of sheep 
                //eaten during the trial, divided by the number of wolves
                SimpleFitness f = ((SimpleFitness) ind[i].fitness);

                //ONLY HAVE ONE OF THESE 
                //the first is shared fitness
                //the second is having each wolf's fitness defined by how many sheep it ate
                f.setFitness(state, (float)food_sum / (float)wolfPopSize, false);
                //f.setFitness(state, map.wolf_pop[i].foodEaten , false);
                ind[i].evaluated = true;
                
            //If the individual is a sheep
            //It's fitness is defined by how much food it ate
            //If the sheep died it's fitness is divided by 2
            } else {
                SimpleFitness f = ((SimpleFitness) ind[i].fitness);
                int food_count = map.sheep_pop[i-wolfPopSize].foodEaten;
                if (map.sheep_pop[i-wolfPopSize].isAlive) {
                    f.setFitness(state, food_count, false);
                } else {
                    f.setFitness(state, food_count/2.0, false);
                }
                
                ind[i].evaluated = true;
            }
        }
        
        //need a new map for a new trial
        map.init();
        
        //System.out.println();
        //System.out.println("-----------New Generation!--------------");
        //System.out.println();
        
        trialCount++;
    }
    
    public void evaluate(EvolutionState a, Individual b, int c, int d) {
        //We shouldn't need this but it's getting mad at me :(
    }
    
}