package searchclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GraphSearch {

    public static Action[][] search(State initialState, Frontier frontier)
    {
        //System.out.println("a7a");

        boolean outputFixedSolution = false;

        if (outputFixedSolution) {
            //Part 1:
            //The agents will perform the sequence of actions returned by this method.
            //Try to solve a few levels by hand, enter the found solutions below, and run them:

            return new Action[][] {
                    {Action.MoveE},
                    {Action.MoveN},
                    {Action.PullWS},
                    {Action.PullNW},
                    {Action.PushSS}
            };
        } 
        else {

            //Part 2:
            //Now try to implement the Graph-Search algorithm from R&N figure 3.7
            //In the case of "failure to find a solution" you should return null.
            //Some useful methods on the state class which you will need to use are:
            //state.isGoalState() - Returns true if the state is a goal state.
            //state.extractPlan() - Returns the Array of actions used to reach this state.
            //state.getExpandedStates() - Returns an ArrayList<State> containing the states reachable from the current state.
            //You should also take a look at Frontier.java to see which methods the Frontier interface exposes
            //
            //printSearchStates(explored, frontier): As you can see below, the code will print out status 
            //(#explored states, size of the frontier, #generated states, total time used) for every 10000th node generated.
            //You might also find it helpful to print out these stats when a solution has been found, so you can keep 
            //track of the exact total number of states generated.


            int iterations = 0;

            frontier.add(initialState);
            HashSet<State> explored = new HashSet<>();

            while (true) {
                //Print a status message every 10000 iteration
                if (++iterations % 10 == 0) {
                    printSearchStatus(explored, frontier);
                }
                // If there is nothing more to search:
                if(frontier.isEmpty()){
                    return null;
                }
                State curr = frontier.pop();
                // If the current state from the frontier is the goal state:
                if(curr.isGoalState()){
                    // Return the plan to reach the goal state:
                    return curr.extractPlan();
                }
                // If it is not the goal state, add it to the explored states:
                explored.add(curr);
                // For each of the possible states:
                for (State child : curr.getExpandedStates()){
                    // If the state has not been seen before, add it to the frontier:
                    if(!frontier.contains(child) && !explored.contains(child)){
                        frontier.add(child);
                    }
                }
            }
        }
    }

    private static long startTime = System.nanoTime();

    private static void printSearchStatus(HashSet<State> explored, Frontier frontier)
    {
        String statusTemplate = "#Expanded: %,8d, #Frontier: %,8d, #Generated: %,8d, Time: %3.3f s\n%s\n";
        double elapsedTime = (System.nanoTime() - startTime) / 1_000_000_000d;
        System.err.format(statusTemplate, explored.size(), frontier.size(), explored.size() + frontier.size(),
                          elapsedTime, Memory.stringRep());
    }
}
