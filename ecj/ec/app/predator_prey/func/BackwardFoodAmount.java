/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*terminal that returns the amount of food in the square behind the agent
*/
package ec.app.predator_prey.func;
import ec.*;
import ec.app.predator_prey.*;
import ec.gp.*;
import ec.util.*;

public class BackwardFoodAmount extends GPNode
    {
    public String toString() { return "backward-food-sensor"; }
    
    public int expectedChildren() { return 0; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        PredatorPrey p = (PredatorPrey)problem;
        PredatorPreyData rd = ((PredatorPreyData)(input));
        int x = p.getIndX();
        int y = p.getIndY();
        int orientation = p.getIndOrientation();
        //finds the square to the right of the agent depending on it's facing
        switch (orientation) {
            case 0:
                rd.val = p.getFoodAmount(x,y+1);
                break;
            case 1:
                rd.val = p.getFoodAmount(x-1,y);
                break;
            case 2:
                rd.val = p.getFoodAmount(x,y-1);
                break;
            case 3:
                rd.val = p.getFoodAmount(x+1,y);
                break;
        }
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }
}


