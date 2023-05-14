import java.util.ArrayList;

public class binarytree {

    public binarytree(){

        TreeSet<Integer> tset = new TreeSet<Integer>();

        for(int i = 0; i < 30; i++){
            int r = (int)(Math.random()*100)+1;
            tset.add(r);
            System.out.print(r + " ");
        }

        System.out.println();
        System.out.println("Size: " + tset.size());
        System.out.println();

        TreeSet<Integer> tset2 = new TreeSet<Integer>();
        String preorder = tset.preOrder();
        String[] pieces = (preorder.substring(1, preorder.length()-1)).split(" ");
        for(int i = 0; i < pieces.length; i++){
            tset2.add(Integer.parseInt(pieces[i]));
        }

        output(tset2);

        System.out.println();

        TreeSet<Integer> tset3 = new TreeSet<Integer>();
        String inorder = tset.inOrder();
        String[] pieces2 = (inorder.substring(1, inorder.length()-1)).split(" ");
        for(int i = 0; i < pieces2.length; i++){
            tset3.add(Integer.parseInt(pieces2[i]));
        }

        output(tset3);

        System.out.println();

        System.out.println("PreOrder, InOrder, and the previous InOrder are the same");

        System.out.println();

        TreeSet<Integer> tset4 = new TreeSet<Integer>();
        String postorder = tset.postOrder();
        String[] pieces3 = (postorder.substring(1, postorder.length()-1)).split(" ");

        for(int i = 0; i < pieces3.length; i++){
            tset4.add(Integer.parseInt(pieces3[i]));
        }

        output(tset4);

        System.out.println();
        System.out.println("PreOrder was the same as the previous PreOrder and previous InOrder");
        System.out.println();

        TreeSet<String> tset5 = new TreeSet<String>();
        for(int i = 0; i < 20; i++){
            char r = (char)((Math.random()*26)+65);
            tset5.add(r+"");
            System.out.print(r + " ");
        }

        System.out.println();

        tset5.rotateRight();
        output(tset5);
        tset5.rotateRight();
        output(tset5);
        tset5.rotateRight();
        output(tset5);


        ArrayList<Integer> randomlist = new ArrayList<Integer>();
        TreeSet<Integer> randomset = new TreeSet<Integer>();

        for(int i = 0; i < 10; i++){
            int r = (int)(Math.random()*500)+1;
            randomlist.add(r);
            randomset.add(r);
        }

        int index = (int)(Math.random()*(randomlist.size()))+1;
        randomset.remove(randomlist.remove(index));
        System.out.println("\n");
        output(randomset);

    }

    void output(TreeSet t){

        System.out.println("PreOrder: " + t.preOrder());
        System.out.println("PostOrder: " + t.postOrder());
        System.out.println("InOrder: " + t.inOrder());

    }

    public static void main(String[] args) {
        new binarytree();
    }

}
