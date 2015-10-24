/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function that does the following:
*If the first child is 1,2, or 3 evaluate the second child. Else, evaluate the third child.
*/
package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class ite extends GPNode
    {
    public String toString() { return "ite"; }

    public int expectedChildren() { return 3; }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        PredatorPreyData rd = ((PredatorPreyData)(input));

        children[0].eval(state,thread,input,stack,individual,problem);
        int evalSecondChild = rd.val;
        //If the first child is 1,2, or 3 evaluate the second child. Else, evaluate the third child.
    	if (evalSecondChild>0) {
    		children[1].eval(state,thread,input,stack,individual,problem);
    	}
        //Evaluate the third child if the first child is 0
    	else {
    		children[2].eval(state,thread,input,stack,individual,problem);
    	}
        
        rd.val = rd.val % 4;
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }   
}