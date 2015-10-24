/**
*Made for Evolutionary Computing Final Project
*Spring Term 2015
*Authors: Julia Kroll, Valerie Lambert, Caitlin Donahue
*
*Stores the data that is passed between nodes of the GP tree
*/


package ec.app.predator_prey;
import ec.gp.*;

public class PredatorPreyData extends GPData
    {
    public int val;    // return value
    
    /**
    Copy
    */
    public void copyTo(final GPData gpd){   
         ((PredatorPreyData)gpd).val = val;
    }
}


