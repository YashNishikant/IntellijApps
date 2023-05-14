
public class TreeNode<E extends Comparable<E>>{

    private TreeNode left;
    private TreeNode right;
    private E value;

    public TreeNode(E v){
        value = v;
        right=null;
        left=null;
    }

    public TreeNode<E> getRight(){
        return right;
    }

    public TreeNode<E> getLeft(){
        return left;
    }

    public void setRight(TreeNode<E> val){
        right = val;
    }

    public void setLeft(TreeNode<E> val){
        left = val;
    }

    public E getValue(){
        return value;
    }

    public void setValue(E v){
        value = v;
    }

    public String toString(){
        return "Value: " + value + " | Left: " + getLeft().getValue() + " | Right: " + getRight().getValue() ;
    }
}
