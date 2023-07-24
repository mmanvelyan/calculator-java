package com.calc;

import java.util.ArrayList;

public class Node {
    private final Node l, r;
    private final Token token;

    public Token getToken(){
        return token;
    }

    public Node getL(){
        return l;
    }

    public Node getR(){
        return r;
    }

    public Node (ArrayList<Node> a, Token t){
        if (a.size() == 1){
            this.token = a.get(0).token;
            this.l = a.get(0).l;
            this.r = a.get(0).r;
            return;
        }
        int idx = a.size()-1;
        Node x = new Node(t, a.get(idx-1), a.get(idx));
        idx -= 2;
        while (idx >= 0){
            x = new Node(t, a.get(idx), x);
            idx--;
        }
        this.token = x.token;
        this.l = x.l;
        this.r = x.r;
    }

    public Node (Token token, Node l, Node r){
        this.token = new Token(token);
        this.l = l;
        this.r = r;
    }
}

