/**
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Holds the information for a sheep
*Sets sheep agentType to 0
*/

package ec.app.predator_prey;

public class Sheep extends Agent {
    
    public Sheep(int x_pos,int y_pos, int facing) {
        super(x_pos,y_pos,facing);
        this.agentType = 0;
    }
    
}