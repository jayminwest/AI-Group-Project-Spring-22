package searchclient;

import java.util.Comparator;
import java.util.HashMap;


public abstract class Heuristic
        implements Comparator<State>
{
    public Heuristic(State initialState)
    {
        // Here's a chance to pre-process the static parts of the level.

    }

    public int h(State s)
    {

        /*
            Search the state boxes
            Search the state goals
            see if there are any boxes on any goals
            check that the colors match
            count how many are covered with the right color boxes
            subtract that count from the total number of goals
            return the amount of UNCOVERED goals
         */

        // count all goals
        int count_uncovered = 0;
        int manhattan_distance = 0;
        /*for (int row = 1; row <s.goals.length-1; row++){
            for(int col = 1; col <s.goals[row].length-1; col++){
                char goal = s.goals[row][col];
                if('A' <= goal && goal <= 'Z' ){
                    count_uncovered ++;
                }
                if('A' <= goal && goal <= 'Z' && s.boxes[row][col] == goal){
                    count_uncovered --;
                }
            }
        }*/
        HashMap<Character, Integer> goalRow = new HashMap<>();
        HashMap<Character, Integer> goalCol = new HashMap<>();
        int closest_goal = 100000;
        for (int row = 1; row < s.goals.length-1; row++){
            for(int col = 1; col <s.goals[row].length-1; col++){
                char goal = s.goals[row][col];
                char box = s.boxes[row][col];
                if('A' <= goal && goal <= 'Z' ){
                    if(Math.abs(s.agentRows[0]-row) + Math.abs(s.agentCols[0]-col) < closest_goal){
                        closest_goal = Math.abs(s.agentRows[0]-row) + Math.abs(s.agentCols[0]-col);
                    }
                    if(!goalRow.containsKey(goal)){
                        goalRow.put(goal, row);
                        goalCol.put(goal, col);
                    }
                    else{
                        manhattan_distance += Math.abs(goalRow.get(goal) - row);
                        manhattan_distance += Math.abs(goalCol.get(goal) - col);
                    }
                }
                if('A' <= box && box <= 'Z' ){
                    if(!goalRow.containsKey(box)){
                        goalRow.put(box, row);
                        goalCol.put(box, col);
                    }
                    else{
                        manhattan_distance += Math.abs(goalRow.get(box) - row);
                        manhattan_distance += Math.abs(goalCol.get(box) - col);

                    }
                }
            }
        }
        return manhattan_distance+closest_goal;
    }

    public abstract int f(State s);

    @Override
    public int compare(State s1, State s2)
    {
        return this.f(s1) - this.f(s2);
    }
}

class HeuristicAStar
        extends Heuristic
{
    public HeuristicAStar(State initialState)
    {
        super(initialState);
    }

    @Override
    public int f(State s)
    {
        return s.g() + this.h(s);
    }

    @Override
    public String toString()
    {
        return "A* evaluation";
    }
}

class HeuristicWeightedAStar
        extends Heuristic
{
    private int w;

    public HeuristicWeightedAStar(State initialState, int w)
    {
        super(initialState);
        this.w = w;
    }

    @Override
    public int f(State s)
    {
        return s.g() + this.w * this.h(s);
    }

    @Override
    public String toString()
    {
        return String.format("WA*(%d) evaluation", this.w);
    }
}

class HeuristicGreedy
        extends Heuristic
{
    public HeuristicGreedy(State initialState)
    {
        super(initialState);
    }

    @Override
    public int f(State s)
    {
        return this.h(s);
    }

    @Override
    public String toString()
    {
        return "greedy evaluation";
    }
}
