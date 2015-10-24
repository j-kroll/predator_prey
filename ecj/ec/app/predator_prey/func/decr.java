/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function for predator prey GP trees
*decrements the child's value by 1
*/

package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class decr extends GPNode
    {
    //for printing the trees
    public String toString() { return "decr"; }
    //expected # of children
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

        children[0].eval(state,thread,input,stack,individual,problem);
        //decrements the value
        rd.val = (rd.val --) % 4;  
        //makes sure the value is not negative
        while (rd.val < 0) {
            rd.val += 4;
        }
        if (rd.val < 0) {
            System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }    
}