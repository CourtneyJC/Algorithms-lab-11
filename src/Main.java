
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println("Enter your letters and frequencies (e.g. a 51 b 22 c 15 d 7 e 5):");
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();

        Huffman huff = makeHuffman(line, scan);


        while(scan.hasNextLine()){
            line = scan.nextLine();

            // Terminate the program if x is entered
            if (line.equals("x")){
                return;
            }
            // if the first character is an e, encode the next word.
            else if (line.charAt(0) == 'e'){
                String toDecode = line.split(" ")[1];
                System.out.println(huff.encodeString(toDecode));
            }
            // if the first character is an d, decode the next word.
            else if (line.charAt(0) == 'd'){
                String toDecode = line.split(" ")[1];
                System.out.println(huff.decodeString(toDecode));
            } else {
                System.out.println("what?");
            }

        }
    }

    // Parses the line given into CharFreqPairs and makes a Huffman with them.
    // If invalid data is given, prompts you to try again
    public static Huffman makeHuffman(String line, Scanner scan){
        String[] arr = line.split(" ");
        ArrayList<CharFreqPair> pairs = new ArrayList<CharFreqPair>();
        byte total = 0;

        for (int i = 0; i < arr.length; i+=2){
            byte freq;

            try {
                freq = Byte.parseByte(arr[i + 1]);
            } catch (Exception e){
                System.out.println("Invalid format. Try again");
                return makeHuffman(scan.nextLine(), scan);
            }
            total += freq;

            if (arr[i].length() != 1){
                System.out.println("Invalid character used. Try again");
                return makeHuffman(scan.nextLine(), scan);
            }
            CharFreqPair pair = new CharFreqPair(arr[i].charAt(0), freq);
            pairs.add(pair);
        }

        if (total > 101 || total < 99){
            System.out.println("Invalid total frequency of letters, try again");
            return makeHuffman(scan.nextLine(), scan);
        }

        return new Huffman(pairs);
    }
}
