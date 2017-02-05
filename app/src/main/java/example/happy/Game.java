package example.happy;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is the core functionality and logic for the game. It can be confusing so these are the basic
 * rules:
 * 1)The goal is to move all the numbers to the nest
 * 2)Number move to the nest when:
 *          -5 numbers are in a row (since it was difficult to win it also wraps around, so
 *          11,12,13,1,2 also counts)
 *          -pairs are selected
 * 3)Also if you get a stack down to 0, you can tap a number twice to move it to the nest
 * 4)You are able to move a number on to another number when they are in order
 * 5)You can hit the undo button to undo any moves
 * Created by Josephine on 3/19/2016.
 */
public class Game {
    public ArrayList<Number> stack1;
    public ArrayList<Number> stack2;
    public ArrayList<Number> stack3;
    public ArrayList<Number> stack4;
    public ArrayList<Number> deck;
    public Move first;
    public Move second;
    public ArrayList<Number> nest;
    public ArrayList<ArrayList<Number>> sList;
    private Boolean freebie;
    private Undo head;
    private Undo temp;

    public Game(){
        head=new Undo(null,null, null, null, 0, false, false);//create the head of the linked list
        freebie=false;//sometime a free move is allowed
        nest=new ArrayList<>();//A stack of completed numbers
        first=new Move();//the first move a player makes
        second=new Move();//the second
        Random r=new Random();
        deck=new ArrayList<>();
        stack1=new ArrayList<>();
        stack2=new ArrayList<>();
        stack3=new ArrayList<>();
        stack4=new ArrayList<>();
        sList=new ArrayList<>();
        sList.add(stack1);
        sList.add(stack2);
        sList.add(stack3);
        sList.add(stack4);

        //creates the deck
        for (int i=0;i<4;i++){
            for (int j=1;j<14;j++){
                deck.add(new Number(j,false));
            }
        }
        //Takes 4 random numbers out of the deck and deals them to the four stacks
        stack1.add(deck.remove(r.nextInt(52)));
        stack2.add(deck.remove(r.nextInt(51)));
        stack3.add(deck.remove(r.nextInt(50)));
        stack4.add(deck.remove(r.nextInt(49)));

        //shuffle that deck baby!
        Number temp;
        int pos1;
        int p2;
        //basically it randomly chooses two positions and swaps the numbers located there
        for (int i=0;i<30;i++){
            pos1=r.nextInt(48);
            p2=r.nextInt(48);
            temp=deck.get(pos1);
            deck.set(pos1,deck.get(p2));
            deck.set(p2,temp);
        }

    }
    /**
     * Makes a move if valid. To make a move, there must be two clicks form the user. The first is
     * indicated what should be moved. The second indicated where it should move if it is a valid
     * move.
     * int pos: which position in the stack was selected
     * int id: which stack was selected
     * MyAdapter adapter: the adapter for the stack
     */
    public boolean makeMove(int pos, int id, MyAdapter adapter){

        if (!first.inUse){
            first.pos=pos;
            first.id=id;
            first.inUse=true;
            first.adapter=adapter;
            first.num=adapter.mDataset.get(pos).num;
            first.stack=sList.get(id-1);
            //we need two moves, so if only one was made we can quit
            return false;

        }

        ArrayList<Number> nums=new ArrayList<>();
        second.id=id;
        second.pos=pos;
        second.adapter=adapter;
        second.num=adapter.mDataset.get(pos).num;
        second.inUse=true;
        second.stack=sList.get(id-1);

        makeSmall();

       if (validMove(first.id,second.id,first.num,second.num,first.pos)) {

            //Pairs of the same number are a valid move but we must check that they are in two
           //different stacks and they are both at position 0
           if (first.id!=second.id&&first.num==second.num&&first.pos==0&&second.pos==0){
               moveToNest(first.id,first.adapter);
               moveToNest(second.id, second.adapter);
               //Add the two number to the undo linked list
               temp=new Undo(first.stack, nest, first.adapter, null, 1, true, false);
               temp.next=head.next;
               head.next=temp;
               temp=new Undo(second.stack, nest, second.adapter, null, 1, true, false);
               temp.next=head.next;
               head.next=temp;

           }
           //Sometime a free move is allowed if the user taps the same number twice
          else if (freebie&&first.id==second.id&&first.pos==second.pos){
               moveToNest(first.id, first.adapter);
               freebie=false;
               temp=new Undo(first.stack, nest, first.adapter, null, 1, false, false);
               temp.next=head.next;
               head.next=temp;

           }
            //Otherwise we remove the correct number from the first stack and add it to the second stack
           else{
               if (second.num==0){
                   second.stack.remove(0);
                   second.adapter.notifyItemRemoved(0);
               }
               for (int i = first.pos; i > -1; i--) {
                    nums.add(first.stack.remove(i));
                   //first.adapter.vList.remove(i);//This is something I changed hopefully I don't break everything!!
                   first.adapter.notifyItemRemoved(0);
               }
               int size=nums.size();
               for (int i = 0; i < size; i++) {
                   second.stack.add(0, nums.remove(0));
                   //second.adapter.vList.add(0, views.remove(0));//This is something I changed hopefully I don't break everything!!
                   second.adapter.notifyItemInserted(0);
               }
               temp=new Undo(first.stack, second.stack, first.adapter, second.adapter, size, false, false);
               temp.next=head.next;
               head.next=temp;
           }
           //Check if there are five in a row
           checkFive(second.stack,second.adapter);
       }
        //Then reset the moves
        first.inUse=false;
        second.inUse=false;

        //If either stack is empty, you get a free move (freebie) and add an invisible number 0
        if (first.stack.size()==0) {
            freebie = true;
            first.stack.add(new Number(0,false));
            first.adapter.notifyItemInserted(0);
        }
        if (second.stack.size()==0){
            second.stack.add(new Number(0,false));
            second.adapter.notifyItemInserted(0);
        }
        return true;
    }
    /**
     * Checks if there are five in a row
     */
    private void checkFive(ArrayList<Number> stack, MyAdapter a) {
        if (stack.size()<5)return;
        //Direction decides if it is going up or down, 5,4,3,2,1 or 1,2,3,4,5
        int direction=stack.get(0).num-stack.get(1).num;
        if (Math.abs(direction)!=1&&Math.abs(direction)!=12)return;
        int count=1;
        int i=1;
       while (i+1<stack.size()){
           if (stack.get(i).num-stack.get(i+1).num==direction) {
               count++;
               i++;
           }
           //Remember it wraps around so 12,13,1,2,3 counts as 5 in row
           else if(stack.get(i).num==13&&stack.get(i+1).num==1){
               count++;
               i++;
               }
           //Also 2,1,13,12,11 is good
           else if(stack.get(i).num==1&&stack.get(i+1).num==13){
               count++;
               i++;
           }
           else{
               return;
           }
       }
        if (count<4) return;

        for (i=0;i<count+1;i++) {
            nest.add(0,stack.remove(0));
            a.notifyItemRemoved(0);
        }
        //Adds an invisible 0 number which makes checking easier later
        if (stack.size()==0){
            stack.add(new Number(0,false));
            a.notifyItemInserted(0);
        }
        temp=new Undo(stack, nest, a, null, count+1, false, false);
        temp.next=head.next;
        head.next=temp;

    }
    /**
     *Checks if the users move is valid
     */
    private boolean validMove(int id, int id2, int n1, int n2, int pos) {
        //You are only abe to add to the top of the stack, not to the middle
        if (second.pos!=0) return false;

        //If the id's match it means they are trying to put it on the same stack
        if (!freebie&&id==id2){
            return false;
        }
        //If the number equals 0, it means the stack is empty
        if (second.num==0){
            return true;
        }
        //tricky because it wraps around
        if (n1==1){
            if (n2!=13){
                if (n2!=2) {
                    if (n2!=1)
                    return false;
                }
            }
            return true;
        }
        if (n1==13){
            if (n2!=1){
                if (n2!=12) {
                    if (n2 != 13)
                        return false;
                }
            }
            return true;
        }

        if (freebie&&id==id2){
            return true;
        }
       else if (n1!=n2&&Math.abs(n1-n2)!=1){
            return false;
        }

      else if (n1==n2&&pos==0){
            return true;
        }

        int direction=n1-n2;
        if (pos==0)return true;

        //if you want to move move than one number you have to check that all are valid
        for (int j = 0; j <= pos; j++) {
            if (j!=pos&&first.stack.get(j).num-first.stack.get(j+1).num!=direction)return false;
        }

        return true;
    }

    /**
     * Move numbers from the stacks to the nest
     */
    private void moveToNest(int id, MyAdapter a) {
        switch (id){
            case 1:nest.add(0,stack1.remove(0));
                if (stack1.size()==0) freebie=true;
                break;
            case 2:nest.add(0,stack2.remove(0));
                if (stack2.size()==0) freebie=true;
                break;
            case 3:nest.add(0,stack3.remove(0));
                if (stack3.size()==0) freebie=true;
                break;
            case 4:nest.add(0,stack4.remove(0));
                if (stack4.size()==0) freebie=true;
                break;
        }
        a.notifyItemRemoved(0);

    }
    /**
     * Loops through and sets the size of all numbers back to small
     */
    public void makeSmall() {
        for (int i=0;i<first.adapter.mDataset.size();i++){
            first.adapter.mDataset.get(i).big=false;
            first.adapter.notifyItemChanged(i);
        }
    }
    /**
     * This increases the size of the chosen numbers
     */
    public void PleaseJustMakeItBig(int pos){//thats what she said
        int direction=0;
        for (int j = 0; j <= pos; j++) {
            if (j==0&&first.stack.size()>1){
                direction=first.stack.get(0).num-first.stack.get(1).num;
                first.adapter.mDataset.get(0).big=true;
                first.adapter.notifyItemChanged(0);
                if (Math.abs(direction)!=1) return;
            }
            if (j!=pos&&first.stack.get(j).num-first.stack.get(j+1).num!=direction)return;
            first.adapter.mDataset.get(j).big=true;
            first.adapter.notifyItemChanged(j);
        }
    }

    /**
     * Deal numbers from the deck to the stacks
     */
    public void dealNumbers(MyAdapter a1, MyAdapter a2, MyAdapter a3, MyAdapter a4) {

        if (first.inUse) {
            makeSmall();
        }

        first.inUse=false;
        second.inUse=false;

        //remove any invisible numbers
        if(stack1.size()>0&&stack1.get(0).num==0){
            stack1.remove(0);
            a1.notifyItemRemoved(0);
        }

        //add to the first deck and add a link to the undo list
        stack1.add(0,deck.remove(0));
        a1.notifyItemInserted(0);
        temp=new Undo(deck, stack1, null, a1, 1, false, true);
        temp.next=head.next;
        head.next=temp;

        //Repeat for all the stacks
        //todo: repetitive code, clean up when testing device is fixed
        if(stack2.size()>0&&stack2.get(0).num==0){
            stack2.remove(0);
            a2.notifyItemRemoved(0);
        }

        stack2.add(0,deck.remove(0));
        a2.notifyItemInserted(0);
        temp=new Undo(deck, stack2, null, a2, 1, false, true);
        temp.next=head.next;
        head.next=temp;


        if(stack3.size()>0&&stack3.get(0).num==0){
            stack3.remove(0);
            a3.notifyItemRemoved(0);
        }
        stack3.add(0,deck.remove(0));
        a3.notifyItemInserted(0);
        temp=new Undo(deck, stack3, null, a3, 1, false, true);
        temp.next=head.next;
        head.next=temp;

        if(stack4.size()>0&&stack4.get(0).num==0){
            stack4.remove(0);
            a4.notifyItemRemoved(0);
        }
        stack4.add(0,deck.remove(0));
        a4.notifyItemInserted(0);
        temp=new Undo(deck, stack4, null, a4, 1, false, true);
        temp.next=head.next;
        head.next=temp;

        ArrayList<MyAdapter> aList=new ArrayList<MyAdapter>();
        aList.add(a1);
        aList.add(a2);
        aList.add(a3);
        aList.add(a4);
        for (int i=0; i<4; i++){
            checkFive(sList.get(i),aList.get(i));
            checkDouble(i+1, aList.get(i));
        }
    }

    /**
     * When any number is placed on another number, we have to check if they are the same
     */
    private void checkDouble(int id, MyAdapter a) {
        if (sList.get(id-1).size()>1&&sList.get(id-1).get(0).num==sList.get(id-1).get(1).num){
            moveToNest(id, a);
            moveToNest(id, a);
            temp=new Undo(sList.get(id-1), nest, a, null, 1, false, false);
            temp.next=head.next;
            head.next=temp;
            temp=new Undo(sList.get(id-1), nest, a, null, 1, false, false);
            temp.next=head.next;
            head.next=temp;

            if(sList.get(id-1).size()==0) {
                sList.get(id-1).add(new Number(0,false));
            }
        }
    }

    /**
     * Undo the last move that the player made
     */
    public int undoThatSheet() {
        int repeats;
        if (head.next==null)return 0;
        if (head.next.from.get(0).num==0){
            head.next.from.remove(0);
            head.next.fromAdaper.notifyItemRemoved(0);
        }

        for (int i=0; i<head.next.count;i++){

            head.next.from.add(0,head.next.to.remove(0));
            if (head.next.toAdapter !=null)head.next.toAdapter.notifyItemRemoved(0);
           if (head.next.fromAdaper !=null) head.next.fromAdaper.notifyItemInserted(0);

        }
        if (head.next.pair) repeats=2;
        else if (head.next.deckDealed){
            repeats=4;
        }
        else{
            repeats=1;
        }
          head.next=head.next.next;
        return repeats;

   }
    /**
     * A debug tool to view the linked list
     */
    public void printUndoTrain(){
        Undo pointer=head;
        int train=0;
        while (pointer.next!=null){
            train=train+pointer.count;
            pointer=pointer.next;
        }
   }
    /**
     * Returns the count of all the numbers not in the stack
     */
    public int getScore(){
        int score=0;
        if (stack1.get(0).num!=0){
            score=score+stack1.size();
        }
        if (stack2.get(0).num!=0){
            score=score+stack2.size();
        }
        if (stack3.get(0).num!=0){
            score=score+stack3.size();
        }
        if (stack4.get(0).num!=0){
            score=score+stack4.size();
        }
        score=score+deck.size();
        Log.i("score",""+score);
        return score;
    }



}
