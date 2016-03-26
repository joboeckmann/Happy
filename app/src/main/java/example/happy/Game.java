package example.happy;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

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

        int t;
        int p1;
        int p2;
        for (int i=0;i<30;i++){
            p1=r.nextInt(49);
            p2=r.nextInt(49);
            t=deck.get(p1);
            deck.set(p1,deck.get(p2));
            deck.set(p2,t);
        }

    }
    public boolean makeMove(int pos, int id, MyAdapter adapter){

        if (!first.inUse){
            first.pos=pos;
            first.id=id;
            first.inUse=true;
            first.adapter=adapter;
            return false;

        }
        ArrayList<Integer> nums=new ArrayList<>();
        second.id=id;
        second.pos=pos;
        second.adapter=adapter;

       if (validMove(first.id,second.id,first.adapter.mDataset.get(first.pos),second.adapter.mDataset.get(second.pos))) {
           if (first.id==1) {
               for (int i = first.pos; i > -1; i--) {
                    nums.add(stack1.remove(0));
                   first.adapter.notifyItemRemoved(0);
               }
           }
           else if (first.id==2) {
               for (int i = first.pos; i > -1; i--) {
                   nums.add(stack2.remove(0));
                   first.adapter.notifyItemRemoved(0);
               }
           }           else if (first.id==3) {
               for (int i = first.pos; i > -1; i--) {
                   nums.add(stack3.remove(0));
                   first.adapter.notifyItemRemoved(0);
               }
           }
           else if (first.id==4) {
               for (int i = first.pos; i > -1; i--) {
                   nums.add(stack4.remove(0));
                   first.adapter.notifyItemRemoved(0);
               }
           }
           int size=nums.size();
           if (second.id==1) {
               for (int i = 0; i < size; i++) {
                   stack1.add(0, nums.remove(0));
                   second.adapter.notifyItemInserted(0);
               }
           }
          else if (second.id==2) {
               for (int i = 0; i < size; i++) {
                   stack2.add(0, nums.remove(0));
                   second.adapter.notifyItemInserted(0);
               }
           }
           else if (second.id==3) {
               for (int i = 0; i < size; i++) {
                   stack3.add(0, nums.remove(0));
                   second.adapter.notifyItemInserted(0);
               }
           }
           else if (second.id==4) {
               for (int i = 0; i < size; i++) {
                   stack4.add(0, nums.remove(0));
                   second.adapter.notifyItemInserted(0);
               }
           }



       }
        first.inUse=false;
        return true;
    }

    private boolean validMove(int id, int id2, int n1, int n2) {
        if (id==id2){
            makeSmall();
            return false;
        }
        if (n1!=n2&&Math.abs(n1-n2)!=1){
            makeSmall();
            return false;
        }
        return true;
    }

    private void makeSmall() {
        for (int i=0;i<first.adapter.vList.size();i++){
            ((TextView) first.adapter.vList.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
        }

    }

    public void makeBig(int pos, ArrayList<View> stack) {
        for (int j = 0; j <= pos; j++) {
            // v=rViews[i].adapter.vList.get(j);
            ((TextView) stack.get(j)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
        }
    }


}
