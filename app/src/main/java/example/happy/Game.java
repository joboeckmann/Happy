package example.happy;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Josephine on 3/19/2016.
 */
public class Game {
    public ArrayList<Integer> stack1;
    public ArrayList<Integer> stack2;
    public ArrayList<Integer> stack3;
    public ArrayList<Integer> stack4;
    public ArrayList<Integer> deck;
    public Move first;
    public Move second;

    public Game(){
        first=new Move();
        second=new Move();
        Random r=new Random();
        deck=new ArrayList<>();
        stack1=new ArrayList<>();
        stack2=new ArrayList<>();
        stack3=new ArrayList<>();
        stack4=new ArrayList<>();
        for (int i=0;i<4;i++){
            for (int j=1;j<14;j++){
                deck.add(j);
            }
        }
        stack1.add(deck.remove(r.nextInt(52)));
        stack2.add(deck.remove(r.nextInt(51)));
        stack3.add(deck.remove(r.nextInt(50)));
        stack4.add(deck.remove(r.nextInt(49)));

    }
    public boolean makeMove(int pos, int id){
        if (!first.inUse){
            first.pos=pos;
            first.id=id;
            first.inUse=true;
            return false;
        }
        second.id=id;
        second.pos=pos;
        


    }




}
