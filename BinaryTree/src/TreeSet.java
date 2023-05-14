public class TreeSet<E extends Comparable<E>>{

    TreeNode root;
    int size;
    String toStringResult;

    public TreeSet(){

    }

    public int size(){
        return size;
    }

    public boolean contains(TreeNode<E> temp, E value){

        if(temp==null){
            return false;
        }
        else if (value.compareTo(temp.getValue())==0){
            return true;
        }

        if(value.compareTo(temp.getValue()) < 0){
            return contains(temp.getLeft(), value);
        }
        else{
            return contains(temp.getRight(), value);
        }

    }


    public void add(TreeNode<E> node, E value){

        if(node.getValue()==value){
            return;
        }
        else if(value.compareTo(node.getValue()) < 0){
            if(node.getLeft()==null){
                node.setLeft(new TreeNode<E>(value));
                size++;
                return;
            }
            else{
                add(node.getLeft(), value);
            }
        }
        else if(value.compareTo(node.getValue()) > 0){
            if(node.getRight()==null){
                node.setRight(new TreeNode<E>(value));
                size++;
                return;
            }
            else{
                add(node.getRight(), value);
            }
        }
        else{

        }
    }
    public void add(E value){

        if(root==null){
            root = new TreeNode<E>(value);
            size++;
        }
        else{
            add(root, value);
        }
    }

    public E minValue(TreeNode<E> temp){
        if(temp.getLeft()==null){
            return temp.getValue();
        }
        else{
            return minValue(temp.getLeft());
        }
    }

    public TreeNode<E> remove(TreeNode<E> node, E value){
        if(node == null){
            return null;
        }

        if(value.compareTo(node.getValue())<0){
            node.setLeft(remove(node.getLeft(),value));
        }
        else if(value.compareTo(node.getValue())>0){
            node.setRight(remove(node.getRight(),value));
        }
        else{
            if(node.getLeft() == null && node.getRight() == null){
                node=null;
            }
            else if(node.getLeft()==null){
                return node.getRight();
            }
            else if(node.getRight()==null){
                return node.getLeft();
            }
            else{
                E minValue = minValue(node.getRight());
                node.setValue(minValue);
                node.setRight(remove(node.getRight(), minValue));
            }
        }
        return node;
    }

    public void remove(E value){
        if(root == null){
            return;
        }
        if(contains(root, value)){
            if(root.getLeft()==null && root.getRight()==null){
                root=null;
                size=0;
                return;
            }
            else{
                size--;
                root = remove(root, value);
            }
        }

    }

    public TreeNode getRoot(){
        return root;
    }

    public void preOrder(TreeNode<E> node){
        if(node!=null){
            toStringResult += node.getValue() + " ";
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }

    }

    public String preOrder(){
        if(size==0){
            return "[]";
        }
        else{
            toStringResult = "[";
            preOrder(root);
            toStringResult = toStringResult.substring(0,toStringResult.length()-2);
            toStringResult += "]";
        }
        return toStringResult;
    }

    public void postOrder(TreeNode<E> node){
        if(node!=null){
            postOrder(node.getLeft());
            postOrder(node.getRight());
            toStringResult += node.getValue() + " ";
        }
    }

    public String postOrder(){
        if(size==0){
            return "[]";
        }
        else{
            toStringResult = "[";
            postOrder(root);
            toStringResult = toStringResult.substring(0,toStringResult.length()-2);
            toStringResult += "]";
        }
        return toStringResult;
    }

    public void inOrder(TreeNode<E> node){
        if(node!=null){
            inOrder(node.getLeft());
            toStringResult += node.getValue() + " ";
            inOrder(node.getRight());
        }
    }

    public String inOrder(){
        toStringResult="";
        if(size==0){
            return "[]";
        }
        else{
            toStringResult += "[";
            inOrder(root);
            toStringResult = toStringResult.substring(0,toStringResult.length()-2);
            toStringResult += "]";
        }
        return toStringResult;
    }

    public TreeNode<E> rotateLeft(TreeNode<E> node){
        TreeNode<E> t = node.getRight();
        node.setRight(t.getLeft());
        t.setLeft(node);
        return t;
    }

    public void rotateLeft(){
        if(root==null || root.getRight()==null){
            return;
        }
        else{
            root=rotateLeft(root);
        }
    }

    public TreeNode<E> rotateRight(TreeNode<E> node){
        TreeNode<E> t = node.getLeft();
        node.setLeft(t.getRight());
        t.setRight(node);
        return t;
    }

    public void rotateRight(){
        if(root==null || root.getLeft()==null){
            return;
        }
        else{
            root=rotateRight(root);
        }
    }

}