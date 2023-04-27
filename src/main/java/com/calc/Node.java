package com.calc;

public class Node {
    public Type tp;
    public Node l, r;
    public float val;
    public void print(Node n, int k){
        System.out.println(k + " : " + n.tp.toString() + ", val = " + n.val);
        if (n.l != null) {
            print(n.l, 2*k);
        }
        if (n.r != null){
            print(n.r, 2*k+1);
        }
    }
    public Node (Type tp, Node l, Node r, float val){
        this.tp = tp;
        this.l = l;
        this.r = r;
        this.val = val;
    }
    public Node (Type tp, Node l, Node r){
        this.tp = tp;
        this.l = l;
        this.r = r;
        this.val = 0;
    }
}
