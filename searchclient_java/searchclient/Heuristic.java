package searchclient;

import jdk.security.jarsigner.JarSigner;

import java.lang.reflect.Array;
import java.sql.ClientInfoStatus;
import java.util.*;


public abstract class Heuristic
        implements Comparator<State>
{
    public Heuristic(State initialState)
    {
        // Here's a chance to pre-process the static parts of the level.

    }

    public int h(State s)
    {
        int distance = 0;
        int manhattan_distance = 0;

        HashMap<Character, ArrayList<Integer>> goalRow = new HashMap<>();
        HashMap<Character, ArrayList<Integer>> goalCol = new HashMap<>();

        int closest_goal = 100000;
        for (int row = 0; row < s.goals.length; row++){
            for(int col = 0; col <s.goals[row].length; col++){
                char goal = s.goals[row][col];
                char box = s.boxes[row][col];
                if('A' <= goal && goal <= 'Z' )
                {
                    distance = Math.abs(s.agentRows[0] - row) + Math.abs(s.agentCols[0] - col);

                    if (distance < closest_goal)
                    {
                        closest_goal = distance;
                    }
                    if (!goalRow.containsKey(goal))
                    {
                        goalRow.put(goal, new ArrayList<>());
                        goalCol.put(goal, new ArrayList<>());
                    }
                    goalRow.get(goal).add(row);
                    goalCol.get(goal).add(col);
                }
            }
        }

        for (int row = 0; row < s.boxes.length; row++){
            for(int col = 0; col <s.boxes[row].length; col++){
                char box = s.boxes[row][col];
                if('A' <= box && box <= 'Z' ) {
                    int minRow = 0;
                    int minCol = 0;
                    int minMan = 1000000;
                    int ind = 0;

                    for (int y = 0; y < goalRow.get(box).size(); ++y)
                    {
                        minRow = Math.abs(goalRow.get(box).get(y) - row);
                        minCol = Math.abs(goalCol.get(box).get(y) - col);
                        if(minRow + minCol < minMan)
                        {
                            minMan = minRow + minCol;
                            ind = y;
                        }

                    }
                    goalRow.get(box).remove(ind);
                    goalCol.get(box).remove(ind);
                    manhattan_distance += minMan;
                }
            }
        }
        return manhattan_distance;
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
