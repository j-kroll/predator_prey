/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function for predator prey GP trees
*/

package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class add extends GPNode
    {
    //For printing the GPs
    public String toString() { return "+"; }
    //the expected number of children this function has
    public int expectedChildren() { return 2; }
    
    //Evaluates this nodes children
    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        int result;
        PredatorPreyData rd = ((PredatorPreyData)(input));
        //evaluates the first child
        children[0].eval(state,thread,input,stack,individual,problem);
        result = rd.val;
        //evaluates the second child
        children[1].eval(state,thread,input,stack,individual,problem);
        //adds the children together
        //can return 0-3
        rd.val = (result + rd.val) % 4;
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
    }
        }
    
}
