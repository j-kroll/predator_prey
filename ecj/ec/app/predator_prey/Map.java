/**
*Map class for predator_prey
*Creates a map for each trial
*Prints the map
*Moves individuals
*The map stores individuals and food, and does not have edges
*The size of the map is defined by the parameter file
*
*For Evolutionary Computation Final Project
*Spring 2015
*Authors:Julia Kroll, Valerie Lambert, Caitlin Donahue
*/

package ec.app.predator_prey;
import java.util.*;

public class Map {
    public Plot[][] grid;
    public int x_dim, y_dim;
    public Sheep[] sheep_pop;
    public Wolf[] wolf_pop;
    public Random rgen;
    public int sheepPopSize;
    public int wolfPopSize;
    public int livingSheep;
    
    /**
    Plots are the spaces on the map. They hold an agent and food
    */
    private class Plot {
        public int food;
        public Agent agent;
        
        public Plot(int food) {
            this.food = food;
            this.agent = null;
        }
        
        /**
        Returns the amount of food in this plot
        */
        public int getFood() {
            return food;
        }
        
        /**
        Returns true if there is food in this plot
        */
        public boolean isFood() {
            return (food > 0);
        }
        
        /**
        Returns true if this plot contains an agent
        */
        public boolean hasAgent() {
            if (agent == null) {
                return false;
            }
            return true;
        }
        
        /**
        Returns the type of agent in this plot represented by an int
        0--> Sheep, 1-->wolf
        If there is no agent in this plot returns 2
        */
        public int getAgentType() {
            if (agent == null) {
                return 2;
            }    
            return agent.agentType;
        }
        
        /**
        Kills the agent in this plot and sets the agent variabe to null
        */
        public void killAgent() {
            //System.out.println("(kill agent) Agent type: " + agent.agentType);
            agent.death();
            if (agent.agentType == 0) {
                livingSheep--;
            }
            agent = null;
        }
        
        /**
        Returns true of there is a sheep n this plot
        */
        public boolean hasSheep() {
            if (agent != null && agent.agentType == 0) {
                return true;
            }
            return false;
        } 
        
        
        /**
        Returns true if there is a wolf in this plot
        */
        public boolean hasWolf() {
            if (agent != null && agent.agentType == 1) {
                return true;
            }
            return false;
        } 
    }
    
    public Map(int x_dim, int y_dim, int sheepPopSize, int wolfPopSize) {
        this.x_dim = x_dim;
        this.y_dim = y_dim;
        this.sheepPopSize = sheepPopSize;
        this.wolfPopSize = wolfPopSize;
        this.rgen = new Random();
        init();
    }

    /**
    Initializes the grid and randomly places food, sheep, and wolves
    */
    public void init() {
        livingSheep = sheepPopSize;
        grid = new Plot[x_dim][y_dim];
        //Initializes all of the plots on the map, with a random food amount from 0-3
        for (int i=0; i<x_dim; i++) {
            for (int j=0; j<y_dim; j++) {
                grid[i][j] = new Plot(rgen.nextInt(4));
            }
        }
        //Initializes the sheep population with a random position and facing
        //Searches randomly for a plot until it finds one that is not already occupied//Stores each individual in an array
        sheep_pop = new Sheep[sheepPopSize];
        for (int i=0; i<sheepPopSize; i++) {
            int x = 0;
            int y = 0;
            do {
                x = rgen.nextInt(x_dim);
                y = rgen.nextInt(y_dim);
            } while (grid[x][y].hasAgent());
            int facing = rgen.nextInt(4);
            sheep_pop[i] = new Sheep(x, y, facing);
            grid[x][y].agent = sheep_pop[i];
        }
        //Initializes the wolf population in the same way as the sheep population
        wolf_pop = new Wolf[wolfPopSize];
        for (int i=0; i<wolfPopSize; i++) {
            int x = 0;
            int y = 0;
            do {
                x = rgen.nextInt(x_dim);
                y = rgen.nextInt(y_dim);
            } while (grid[x][y].hasAgent());
            int facing = rgen.nextInt(4);
            wolf_pop[i] = new Wolf(x, y, facing);
            grid[x][y].agent = wolf_pop[i];
        }
    }
    
    /**
    Prints the map to the terminal.
    Need to press 'enter' to continue on to the next move
    */
    public void printGrid() {
         Scanner in = new Scanner(System.in);
         String map = "\n";
         for (int j=0; j<x_dim; j++) {
            for (int n=0; n<x_dim; n++) {
                map += "--------";
            }
            map += "\n|";
            for (int i=0; i<y_dim; i++) {
                if (grid[i][j].hasWolf()) {
                    map += " ]nVn[ ";
                } else if (grid[i][j].hasSheep()) {
                    map += "***n_n ";
                } else if (grid[i][j].food > 0) {
                    map += "       ";
                } else {
                    map += "       ";
                }
                map += "|";
            }
            map += "\n|";
            for (int i=0; i<y_dim; i++) {
                if (grid[i][j].hasSheep()) {
                    map += "**(o o)";
                } else if (grid[i][j].hasWolf()) {
                    map += " (o o) ";
                } else if (grid[i][j].food > 0) {
                    map += "   *   ";
                } else {
                    map += "       ";
                }
                map += "|";
            }
            map += "\n|";
            for (int i=0; i<y_dim; i++) {
                if (grid[i][j].hasWolf()) {
                    map += "  \\./  ";
                } else if (grid[i][j].hasSheep()) {
                    map += "(**)*) ";
                } else if (grid[i][j].food > 0) {
                    map += "       ";
                } else {
                    map += "       ";
                }
                map += "|";
            }
            map+= "\n";
        }
        for (int n=0; n<x_dim; n++) {
                map += "--------";
            }
        map += "\n";
        System.out.println(map);
        
        //Some helper information to show information about hte current populations
        for (int i=0; i<sheepPopSize; i++) {
            //System.out.println("Sheep " + i + ": " + sheep_pop[i].x_pos + ", " + sheep_pop[i].y_pos + " has eaten " + sheep_pop[i].foodEaten + " foods.");
        }
        for (int i=0; i<wolfPopSize; i++) {
            //System.out.println("Wolf " + i + ": " + wolf_pop[i].x_pos + ", " + wolf_pop[i].y_pos + " has eaten " + wolf_pop[i].foodEaten + " foods.");
        }
        in.nextLine();
    }
    
    //Used when randomly moving the sheep and wolves
    //Not currently used in the program
    //moves all individuals randomly
    public void step() {
        for (int i=0; i<sheepPopSize; i++) {
            int n = rgen.nextInt(4);
            if (n == 0) {
                right(sheep_pop[i]);
            } else if (n == 1) {
                left(sheep_pop[i]);
            } else if (n == 2) {
                forward(sheep_pop[i]);
            } else {
                backward(sheep_pop[i]);
            }
        }
        for (int i=0; i<wolfPopSize; i++) {
            int n = rgen.nextInt(4);
            if (n == 0) {
                right(wolf_pop[i]);
            } else if (n == 1) {
                left(wolf_pop[i]);
            } else if (n == 2) {
                forward(wolf_pop[i]);
            } else {
                backward(wolf_pop[i]);
            }
        }
    }
    
    /**
    Is passed an agent, moves the agent one space in the direction it is currently facing
    If the space is currently occupied, 1 of two things happen:
    If the agent in the occupied space is the same type as the current agent, the current agent does not move. 
    If the current agent is a sheep and the agent in the occupied space is a wolf the current agent does not move.
    If the current agent is a wolf and the agent in the occupied space is a sheep the wolf eats the sheep.
    */
    public void forward(Agent agent) {
        int x = agent.x_pos;
        int y = agent.y_pos;
        int facing = agent.facing;
        
        // 0 is N, 1 is E, 2 is S, 3 is W
        if (facing == 0) {
            y--;
            if (y<0) {
                y += y_dim;
            }
        } else if (facing == 1) {
            x++;
            if (x<0) {
                x += x_dim;
            }
        } else if (facing == 2) {
            y++;
            if (y<0) {
                y += y_dim;
            }
        } else {
            x--;
            if (x<0) {
                x += x_dim;
            }
        }
        //This allows the map to not have walls
        x = x % x_dim;
        y = y % y_dim;
        //If the space to move is not accupied move the current agent
        if (!grid[x][y].hasAgent()) {
            grid[agent.x_pos][agent.y_pos].agent = null;
            agent.x_pos = x;
            agent.y_pos = y;
            grid[x][y].agent = agent;
        } else {
            //If the current agent is a wolf and the other agetn is a sheep
            //kill the sheep
            //have the wolf eat
            //move the wolf forward
            if (agent.agentType == 1 && grid[x][y].hasSheep()) {
                grid[x][y].killAgent();
                agent.eat();
                grid[agent.x_pos][agent.y_pos].agent = null;
                agent.x_pos = x;
                agent.y_pos = y;
                grid[x][y].agent = agent;
            }    
        }    
        //If there is food on the space that an agent ends the turn on
        //and that agent is a sheep
        //have the sheep eat the food
        if (grid[x][y].food > 0 && agent.agentType == 0) {
            grid[x][y].food--;
            agent.eat();
        }
    }
    
    /**
    Turn the agent to it's right and then move forward
    */
    public void right(Agent agent) {
        agent.facing = (agent.facing+1)%4;
        forward(agent);
    }
    
    /**
    Turn the agent to it's left and then move forward
    */
    public void left(Agent agent) {
        agent.facing = (agent.facing+3)%4;
        forward(agent);
    }
    
    /**
    Turn the agent backwards then have it move forward
    */
    public void backward(Agent agent) {
        agent.facing = (agent.facing+2)%4;
        forward(agent);
    }
    
    /**
    Get how much food is currenly in the plot at x,y
    */
    public int getFood(int x, int y) {
        x = x % x_dim;
        y = y % y_dim;
        while (x<0) {
            x += x_dim;
        }
        while (y<0) {
            y += y_dim;
        }
        return grid[x][y].getFood();
    }
    
    /**
    return the type of the agent that is currently in x,y
    */
    public int getAgentType(int x, int y) {
        x = x % x_dim;
        y = y % y_dim;
        while (x<0) {
            x += x_dim;
        }
        while (y<0) {
            y += y_dim;
        }
        return grid[x][y].getAgentType();
    }
    
    //If running the program for random moves. 
    //Runs for 3 steps
    public static void main(String[] args) {
        Map m = new Map(10,10,5,2);
        m.printGrid();
        m.step();
        m.printGrid();
        m.step();
        m.printGrid();
        m.step();
        m.printGrid();
    }
}