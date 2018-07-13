package com.terrarium.utils;

public class Pair
{

    public int x;
    public int y;
    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Pair pair)
    {
        return x == pair.x && y == pair.y;
    }

}
