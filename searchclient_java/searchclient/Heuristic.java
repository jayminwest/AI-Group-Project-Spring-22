package searchclient;

import java.util.Comparator;

public abstract class Heuristic
        implements Comparator<State>
{
    public Heuristic(State initialState)
    {
        // Here's a chance to pre-process the static parts of the level.
        h(initialState);
    }

    public int h(State s)
    {
        /*
            count all goals in level
                make this the count_uncovered
            loop through the boxes
            check the coordinates for the box against the goals
            if they match, subtract from count uncovered
         */

        int count_uncovered = 0;

        for (int row = 1; row < s.goals.length; row++)
        {
            for (int col = 1; col < s.goals[row].length; col++)
            {
                char goal = s.goals[row][col];
                if ('A' <= goal && goal <= 'Z')
                {
                    count_uncovered++;
                }
            }
        }

        for (int row = 1; row < s.boxes.length; row++)
        {
            for (int col = 1; col < s.boxes[row].length; col++)
            {
                char box = s.boxes[row][col];
                if ('A' <= goal && goal <= 'Z' != 0)
                {
                    count_uncovered--;
                }
            }
        }

//        // For each goal in the state
//        for (int row = 0; row < s.goals.length; row++)
//        {
//            for (int col = 0; col < s.goals[row].length; col++)
//            {
//                char goal = s.goals[row][col];
//
//                //If the goal is covered, subtract from the count_uncovered
//                if ('A' <= goal && goal <= 'Z' && s.boxes[row][col] != goal)
//                {
//                    count_uncovered--;
//                }
//                // Else if it is covered, add to the count_uncovered
//                else {
//                    count_uncovered++;
//                }
//            }
//        }
//
//        if (count_uncovered < 0) {
//            count_uncovered = 0;
//        }

        return count_uncovered;
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
