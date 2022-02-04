import java.util.HashMap;

public class Node {
    int freq;
    char ch;
    Node left;
    Node right;
    boolean isLeaf(){
        return left == null;
    }
    static void preOrder(Node node, String code, HashMap<Character,String> codeBook){
        if(node!=null){
            if(node.isLeaf()){
                codeBook.put(node.ch , code);
            }
            preOrder(node.left,code+"0",codeBook);
            preOrder(node.right,code+"1",codeBook);
        }
    }
}
