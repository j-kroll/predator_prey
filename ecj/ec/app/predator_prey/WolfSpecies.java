/**
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Species type for the GPs
*/

package ec.app.predator_prey;
import ec.*;
import ec.gp.*;
import ec.util.*;
import java.io.*;

public class WolfSpecies extends GPSpecies {

    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state,base);
    }
    
    public Individual newIndividual(EvolutionState state, int thread) 
        {
        GPIndividual newind = ((WolfIndividual)(i_prototype)).lightClone();
        
        // Initialize the trees
        for (int x=0;x<newind.trees.length;x++)
            newind.trees[x].buildTree(state, thread);

        // Set the fitness
        newind.fitness = (Fitness)(f_prototype.clone());
        newind.evaluated = false;

        // Set the species to this
        newind.species = this;

        return newind;
    }   
}