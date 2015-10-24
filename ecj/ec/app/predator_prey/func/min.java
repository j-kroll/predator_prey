/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function for predator prey GP trees
*Returns the value of the minimun child
*/
package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class min extends GPNode
    {
    public String toString() { return "min"; }

    public int expectedChildren() { return 2; }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        int result;
        PredatorPreyData rd = ((PredatorPreyData)(input));

        children[0].eval(state,thread,input,stack,individual,problem);
        result = rd.val;
        //If the first child was less than or equal to the second child, set the value
        //back to that of the first child
        children[1].eval(state,thread,input,stack,individual,problem);
        if (result <= rd.val) {
        	rd.val = result;
        }   
        rd.val = rd.val % 4;
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }
}