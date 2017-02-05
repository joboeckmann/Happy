package example.happy;

/**
 * A simple object that stores the number and whether or not it is big
 * Created by Josephine on 4/23/2016.
 */
public class Number {
    public int num;
    public boolean big;

    public Number(int num, boolean big){
        this.num=num;
        this.big=big;
    }
    public String toString(){
        return ""+num;
    }

}
