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
        head=new Undo(null,null, null, null, 0, false, false);
        freebie=false;
        nest=new ArrayList<>();
        first=new Move();
        second=new Move();
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

        for (int i=0;i<4;i++){
            for (int j=1;j<14;j++){
                deck.add(new Number(j,false));
            }
        }
        stack1.add(deck.remove(r.nextInt(52)));
        stack2.add(deck.remove(r.nextInt(51)));
        stack3.add(deck.remove(r.nextInt(50)));
        stack4.add(deck.remove(r.nextInt(49)));

        Number t;
        int p1;
        int p2;
        for (int i=0;i<30;i++){
            p1=r.nextInt(48);
            p2=r.nextInt(48);
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
            first.num=adapter.mDataset.get(pos).num;
            first.stack=sList.get(id-1);
           // makeBig(first.pos, first.adapter);

            return false;

        }
        ArrayList<Number> nums=new ArrayList<>();
       // ArrayList<View>views=new ArrayList<>();
        second.id=id;
        second.pos=pos;
        second.adapter=adapter;
        second.num=adapter.mDataset.get(pos).num;
        second.inUse=true;
        second.stack=sList.get(id-1);

        makeSmall();
       if (validMove(first.id,second.id,first.num,second.num,first.pos)) {

           if (first.id!=second.id&&first.num==second.num&&first.pos==0&&second.pos==0){
               moveToNest(first.id,first.adapter);
               moveToNest(second.id, second.adapter);
               temp=new Undo(first.stack, nest, first.adapter, null, 1, true, false);
               temp.next=head.next;
               head.next=temp;
               temp=new Undo(second.stack, nest, second.adapter, null, 1, true, false);
               temp.next=head.next;
               head.next=temp;
//              printUndoTrain();

           }
          else if (freebie&&first.id==second.id&&first.pos==second.pos){
               moveToNest(first.id, first.adapter);
               freebie=false;
               temp=new Undo(first.stack, nest, first.adapter, null, 1, false, false);
               temp.next=head.next;
               head.next=temp;

           }
//               }
           else{
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
               //printUndoTrain();
           }
           checkFive(second.stack,second.adapter);
           Log.i("nest", nest.toString());
       }

        first.inUse=false;
        second.inUse=false;
        if (first.stack.size()==0) freebie=true;
       // Log.i("butt", " "+freebie);
       // Log.i(""+first.id,first.adapter.vListString());
       // Log.i(""+second.id,second.adapter.vListString());
        return true;
    }

    private void checkFive(ArrayList<Number> stack, MyAdapter a) {
        if (stack.size()<5)return;
        int direction=stack.get(0).num-stack.get(1).num;
        if (Math.abs(direction)!=1)return;
        int count=1;
        int i=1;
       while (i+1<stack.size()&&stack.get(i).num-stack.get(i+1).num==direction){
           count++;
           i++;
       }
        if (count<4) return;
        for (i=0;i<count+1;i++) {
            nest.add(0,stack.remove(0));
            a.notifyItemRemoved(0);

        }
        temp=new Undo(stack, nest, a, null, count+1, false, false);
        temp.next=head.next;
        head.next=temp;
//        temp=new Undo(stack, nest, a, null, count+1, false, false);
//        temp.next=head.next;
//        head.next=temp;
 //       Log.i("nest", nest.toString());
       // printUndoTrain();

//        for (int i=1;i<4;i++){//check for five
//            if (stack.get(i).num-stack.get(i+1).num!=direction)return;
//        }
//        for (int i=0;i<5;i++){
//            nest.add(stack.remove(0));
//            //a.vList.remove(0);///This is something I changed hopefully I don't break everything!!
//            a.notifyItemRemoved(0);
//        }
    }

    private boolean validMove(int id, int id2, int n1, int n2, int pos) {
        if (second.pos!=0) return false;
        if (!freebie&&id==id2){
            //makeSmall();
            return false;
        }
        if (freebie&&id==id2){
            //freebie=false;
            return true;
        }
       else if (n1!=n2&&Math.abs(n1-n2)!=1&&(n1!=1&&n2!=13)&&(n1!=13&&n2!=1)){
           // makeSmall();
            return false;
        }
      else   if (n1==n2&&pos==0){
            return true;
        }
        int direction=n1-n2;
        if (pos==0)return true;

        for (int j = 0; j <= pos; j++) {
            if (j!=pos&&first.stack.get(j).num-first.stack.get(j+1).num!=direction)return false;
            //((TextView) first.adapter.vList.get(first.adapter.vList.size()-j-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
        }




        return true;
    }

    private void moveToNest(int id, MyAdapter a) {
        //Log.i("before", nest.toString());
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
       // a.vList.remove(0);
        //Log.i("Butt", " "+freebie);
        //Log.i("here", "movetonest");
        a.notifyItemRemoved(0);


    }

    public void makeSmall() {
        for (int i=0;i<first.adapter.mDataset.size();i++){
           // ((TextView) first.adapter.vList.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
            first.adapter.mDataset.get(i).big=false;
            first.adapter.notifyItemChanged(i);
        }

    }
    //this whole method is screwy!
//    public void makeBig(int pos,MyAdapter a){//, ArrayList<View> stack) {
//        int direction=0;
//        for (int j = 0; j <= pos; j++) {
//            // v=rViews[i].adapter.vList.get(j);
////            ((TextView) stack.get(stack.size()-j-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
////            Log.i("Big","pos"+pos+" j"+j);
////            int t=first.adapter.vList.size();//-j-1;
////            ((TextView) first.adapter.vList.get(first.adapter.vList.size()-j-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
//            if (j==0&&first.stack.size()>1){
//                direction=first.stack.get(0)-first.stack.get(1);
//                ((TextView) first.adapter.vList.get(first.adapter.vList.size()-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
//
//                if (Math.abs(direction)!=1) return;
//            }
//            if (j!=pos&&first.stack.get(j)-first.stack.get(j+1)!=direction)return;
//            ((TextView) first.adapter.vList.get(first.adapter.vList.size()-j-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
//            //a.notify(first.adapter.vList.size()-j-1);
//
//        }
    public void PleaseJustMakeItBig(int pos){//thats what she said
        int direction=0;
        for (int j = 0; j <= pos; j++) {
            if (j==0&&first.stack.size()>1){
                direction=first.stack.get(0).num-first.stack.get(1).num;
//                ((TextView) first.adapter.vList.get(first.adapter.vList.size()-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
                //first.adapter.mDataset.get(first.adapter.mDataset.size()-1).big=true;
                first.adapter.mDataset.get(0).big=true;
                first.adapter.notifyItemChanged(0);
                if (Math.abs(direction)!=1) return;
            }
            if (j!=pos&&first.stack.get(j).num-first.stack.get(j+1).num!=direction)return;
            //((TextView) first.adapter.vList.get(first.adapter.vList.size()-j-1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
           // first.adapter.mDataset.get(first.adapter.mDataset.size()-j-1).big=true;
            first.adapter.mDataset.get(j).big=true;
            first.adapter.notifyItemChanged(j);
        }
    }

//        Log.i("Big", first.adapter.vList.toString());
//    }


    public void doDeckStuff(MyAdapter a1,MyAdapter a2, MyAdapter a3, MyAdapter a4) {

        stack1.add(0,deck.remove(0));
        a1.notifyItemInserted(0);
        temp=new Undo(deck, stack1, null, a1, 1, false, true);
        temp.next=head.next;
        head.next=temp;
//        Log.i("Butt", "added");
//        for (int i=0;i<a1.vList.size();i++){///had to add these stupid lines of code because it was getting big for no reason!
//            ((TextView) a1.vList.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
//        }
        stack2.add(0,deck.remove(0));
        a2.notifyItemInserted(0);
        temp=new Undo(deck, stack2, null, a2, 1, false, true);
        temp.next=head.next;
        head.next=temp;
 //       Log.i("Butt", "added");
//        for (int i=0;i<a2.vList.size();i++){
//            ((TextView) a2.vList.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
//        }
        stack3.add(0,deck.remove(0));
        a3.notifyItemInserted(0);
        temp=new Undo(deck, stack3, null, a3, 1, false, true);
        temp.next=head.next;
        head.next=temp;
//        Log.i("Butt", "added");
//        for (int i=0;i<a3.vList.size();i++){
//            ((TextView) a3.vList.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
//        }
        stack4.add(0,deck.remove(0));
        a4.notifyItemInserted(0);
        temp=new Undo(deck, stack4, null, a4, 1, false, true);
        temp.next=head.next;
        head.next=temp;
//        printUndoTrain();
//        Log.i("Butt", "added");

//        for (int i=0;i<a4.vList.size();i++){
//            ((TextView) a4.vList.get(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
//        }
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

    private void checkDouble(int id, MyAdapter a) {
        if (sList.get(id-1).size()>1&&sList.get(id-1).get(0).num==sList.get(id-1).get(1).num){
            moveToNest(id, a);
            moveToNest(id, a);
            temp=new Undo(sList.get(id-1), nest, a, null, 1, true, false);
            temp.next=head.next;
            head.next=temp;
            temp=new Undo(sList.get(id-1), nest, a, null, 1, false, false);
            temp.next=head.next;
            head.next=temp;
            //printUndoTrain();
        }
    }

    public int undoThatSheet() {
        //Log.i("undo", nest.toString());
        int repeats;
        if (head.next==null)return 0;
       // Log.i("Butt", ""+head.next.count);
        for (int i=0; i<head.next.count;i++){

            head.next.from.add(0,head.next.to.remove(0));
            if (head.next.ta!=null)head.next.ta.notifyItemRemoved(0);
           if (head.next.fa!=null) head.next.fa.notifyItemInserted(0);

        }
        if (head.next.pair) repeats=2;
        else if (head.next.dirtyDeckStuff)repeats=4;
        else{
            repeats=1;
        }
          head.next=head.next.next;
//        Log.i("", nest.toString());
        return repeats;
//
   }
    public void printUndoTrain(){
        Undo pointer=head;
        int train=0;
        while (pointer.next!=null){
           // Log.i("h", "here");
            train=train+pointer.count;
            pointer=pointer.next;
        }
//        Log.i("Train", ""+train);
   }
}
