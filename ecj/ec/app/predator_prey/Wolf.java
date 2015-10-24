/**
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Holds the information for a wolf
*Sets wolf agentType to 1
*/
package ec.app.predator_prey;

public class Wolf extends Agent {
    
    public Wolf(int x_pos,int y_pos, int facing) {
        super(x_pos,y_pos,facing);
        this.agentType = 1;
    }
    
}