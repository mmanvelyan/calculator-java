enum nType{
    num,
    add,
    sub,
    mul,
    div
}
public class Node {
    public nType tp;
    public Node l, r;
    public float val;
    public Node (nType tp, Node l, Node r, float val){
        this.tp = tp;
        this.l = l;
        this.r = r;
        this.val = val;
    }
    public Node (nType tp, Node l, Node r){
        this.tp = tp;
        this.l = l;
        this.r = r;
        this.val = 0;
    }
}
