package com.example.shubzz99.prototype;

/**
 * Created by shubzz99 on 12/7/17.
 */

public class brandData
{
    public int Count;
    public String Date;
    public int Female;
    public int Male;
    public int Negative;
    public int Neutral;
    public int Positive;

    public brandData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public brandData(int Count,String date, int Female, int Male, int Negative, int Neutral, int Positive) {
        this.Count=Count;
        this.Date = date;
        this.Female = Female;
        this.Male = Male;
        this.Negative = Negative;
        this.Neutral = Neutral;
        this.Positive = Positive;

    }

//    public int getPositive()
//    {
//        return Positive;
//    }
}
