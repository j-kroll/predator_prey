/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function for predator prey GP trees
*Multiply the two children together
*/
package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class mult extends GPNode
    {
    public String toString() { return "*"; }

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
        children[1].eval(state,thread,input,stack,individual,problem);
        //multiply the children
        rd.val = (result * rd.val) % 4;
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }
}
