/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Holds the information about the sheep or wolves
*/

package ec.app.predator_prey;


public class Agent {
 
    public int x_pos, y_pos;
    public int facing; // 0 -> N, 1 -> E, 2 -> S, 3 -> W
    public int foodEaten;
    public int agentType; // 0 -> sheep, 1 ->wolf
    public boolean isAlive;

    public Agent(int x_pos, int y_pos, int facing) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.facing = facing;
        this.foodEaten = 0;
        this.isAlive = true;
    }
    
    /**
    increments the amount of food the agent has eaten
    */
    public void eat() {
        foodEaten++;
    }
    
    /**
    'kills' the agent. This will prevent it from showing up on the map or moving. 
    The agent's fitness will still be evaluated at the end of the run
    */
    public void death() {
        isAlive = false;
    }
    
    /**
    Get the current x position of the agent
    */
    public int getX() {
        return x_pos;
    }
    
    /**
    Get the current y position of the agent
    */
    public int getY() {
        return y_pos;
    }
    
    /**
    Get the current orientation of the agent
    */
    public int getOrientation() {
        return facing;
    }  
}