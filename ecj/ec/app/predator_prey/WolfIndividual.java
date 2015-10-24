/**
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Holds the information for a wolf
*includes the GP information
*/

package ec.app.predator_prey;
import ec.*;
import ec.gp.*;
import ec.util.*;
import java.io.*;

public class WolfIndividual extends GPIndividual {
 
    public int x_pos, y_pos;
    public int facing; // 0 -> N, 1 -> E, 2 -> S, 3 -> W
    public int foodEaten;
    public int agentType; // 0 -> sheep, 1 -> wolf
    public boolean isAlive;

    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state,base);
    }
    
    public WolfIndividual(int x_pos, int y_pos, int facing) {
        
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.facing = facing;
        this.foodEaten = 0;
        this.isAlive = true;
        this.agentType = 1;
    }
    
    /**
    Increments the amount of food eaten
    */
    public void eat() {
        foodEaten++;
    }
    
    /**
    'Kills' the agent
    If the agent is killed it will not be printed on the map or move
    it's fitness will still be evaluated
    */
    public void death() {
        isAlive = false;
    }  
    
    public int getX() {
        return x_pos;
    }
    
    public int getY() {
        return y_pos;
    }
    
    public int getOrientation() {
        return facing;
    } 
}