import java.io.*;
import java.util.HashMap;

public class Encode {
    static void sort(Object[] chars, int[] len){
        for(int i = 1 ; i<chars.length ; i++){
            int curLen = len[i];
            char curChar =(char)chars[i];
            for(int j = i; j > 0 && (1000 * len[j-1] + (char) chars[j-1]) > (1000 * curLen + curChar) ;j--){
               chars[j] = chars[j-1];
               len[j] = len[j-1];
               chars[j-1] = curChar;
               len[j-1] = curLen;
            }
        }
    }
    static HashMap<Character, String> createCanonicalCodeBook(Object[] chars , int[] len) {
        HashMap<Character, String> hashMap = new HashMap<>();

        String code = "";
        for(int i=0; i<len[0]; i++) code += "0";

        for (int i=0; i<chars.length; i++) {
            hashMap.put((char) chars[i], code);
            if (i+1 < chars.length) {
                int length = code.length();
                code = Integer.toBinaryString( Integer.parseInt(code, 2) + 1 );
                while (code.length() < length) code = "0" + code;
                while (code.length() < len[i+1]) code = code + "0";
            }
        }

        return hashMap;
    }

    public static void main(String[] arg) {
        Heap minHeap = new Heap(128);
        int[] array = new int[128];

        BufferedReader input = null;
        try{
            input = new BufferedReader(
                    new FileReader(
                            new File("data.txt")));
            while(true){
                String line = input.readLine();
                if(line == null) break;
                for(int i = 0;i<line.length();i++){
                    array[line.charAt(i)]++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                input.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        for( int i = 0 ; i<array.length ; i++){
            if(array[i]!=0) {
                Node node = new Node();
                node.ch = (char) i;
                node.freq = array[i];
                minHeap.insert(node);
            }
        }

        while(minHeap.getSize()!=1){
            Node parent = new Node();
            Node n1 = minHeap.pop();
            Node n2 = minHeap.pop();
            parent.freq = n1.freq + n2.freq;
            parent.left = n1;
            parent.right = n2;
            minHeap.insert(parent);
        }

        HashMap<Character,String> codeBook = new HashMap();
        Node huffman = minHeap.pop();
        Node.preOrder(huffman,"",codeBook);

        Object[] characters = codeBook.keySet().toArray();
        Object[] codes = codeBook.values().toArray();
        int[] len = new int[codes.length];

        for(int i = 0; i<codes.length;i++){
            String code = (String) codes[i];
            len[i] = code.length();
        }

        sort(characters,len);
        HashMap<Character, String> canonicalCodeBook = createCanonicalCodeBook(characters,len);

        DataOutputStream output = null;
        try{
            output = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("EncodedFile.bin"),true)));
            output.writeInt(characters.length);
            for(int i = 0 ;i<characters.length;i++){
                output.writeChar((char)characters[i]);
                output.writeInt(len[i]);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        BufferedReader input2 = null;
        try{
            input2 = new BufferedReader(new FileReader(new File("data.txt")));
            while (true) {
                String line = input2.readLine();
                if(line == null) break;
                for(int i = 0;i<line.length();i++){
                    if (output != null) {
                        output.writeChars(canonicalCodeBook.get(line.charAt(i)));
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                input2.close();
                output.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("Encoding finished successfully");
    }
}
