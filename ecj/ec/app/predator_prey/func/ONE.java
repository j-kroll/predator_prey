/**
*PredatorPrey.java
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Terminal that returns 1
*/
package ec.app.predator_prey.func;
import ec.*;
import ec.app.predator_prey.*;
import ec.gp.*;
import ec.util.*;

public class ONE extends GPNode
    {
    public String toString() { return "1"; }
    
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
        rd.val = 1;
        }
    
}



