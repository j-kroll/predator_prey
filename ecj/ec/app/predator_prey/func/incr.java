/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function for predator prey GP trees
*increments the child's value by 1
*/
package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class incr extends GPNode
    {
    public String toString() { return "incr"; }

    public int expectedChildren() { return 1; }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        int result;
        PredatorPreyData rd = ((PredatorPreyData)(input));
        //gets the value of the child
        children[0].eval(state,thread,input,stack,individual,problem);
        //increments the value
        rd.val = (rd.val ++) % 4;
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }  
}