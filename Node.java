package LabProject;

public class Node {
    Transaction data;
    Node left, right;
    int height;
    public Node(Transaction data){
        this.data=data;
        this.left=this.right=null;
        this.height=1;
    }
}
