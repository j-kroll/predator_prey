/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Function that returns the value of one of the two children, randomly chosen
*/
package ec.app.predator_prey.func;
import ec.app.predator_prey.*;
import ec.*;
import ec.gp.*;
import ec.util.*;
import java.util.Random;

public class rand extends GPNode
    {
    public String toString() { return "rand"; }

    public int expectedChildren() { return 2; }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        PredatorPreyData rd = ((PredatorPreyData)(input));
        
        Random rgen = new Random();
        int rand = rgen.nextInt(2);
        //If the random number is 0, evaluate the first child
        if (rand == 0) {
        	children[0].eval(state,thread,input,stack,individual,problem);
        } 
        //else evaluate the second child
        else {
        	children[1].eval(state,thread,input,stack,individual,problem);
        }
        rd.val = rd.val % 4;
        if (rd.val < 0) {
         System.out.println(toString() + " is negative, rd.val = " + rd.val);   
        }
    }
}