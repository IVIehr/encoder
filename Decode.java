import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Decode {
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
                while (code.length() < len[i+1]) code = code + "0"; //shift
            }
        }

        return hashMap;
    }

    public static Character getKeyByValue(HashMap<Character,String> hashMap,String value) {
        for (Map.Entry<Character, String> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public static void main(String[] arg){
        HashMap<Character, String> canonicalCodeBook;
        PrintWriter output = null;
        try{
            output = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(
                                    new File("decodedFile.txt"))));
        }catch (IOException e){
            e.printStackTrace();
        }
        DataInputStream input = null;
        try{
            input = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("EncodedFile.bin"))));
            int count , maxLen;
            count = input.readInt();
            String line = "";

            Object[] chars = new Object[count];
            int[] len = new int[count];
            for(int i =0 ; i<count ; i++){
                chars[i] = input.readChar();
                len[i] = input.readInt();
            }
            maxLen = len[len.length-1];
            canonicalCodeBook = createCanonicalCodeBook(chars,len);

            String codeLine = input.readLine();
            for(int i=1 ; i<codeLine.length() ; i+=2){
                line = line.concat(String.valueOf(codeLine.charAt(i)));
            }

            int k = 0;
            for(int i = 0 ; i<line.length();i+=k){
                k=0;
                String code = "";
                for( int j = i + 1; j<= i + maxLen ; j++){
                    k++;
                    code = line.substring(i,j);
                    if(canonicalCodeBook.containsValue(code)) {
                        output.print(Decode.getKeyByValue(canonicalCodeBook, code));
                        break;
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                input.close();
                output.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("Decoding finished successfully");
    }
}
