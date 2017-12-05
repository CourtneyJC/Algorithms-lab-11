import java.util.ArrayList;
import java.util.PriorityQueue;

public class Huffman {
    private Node root;
    private ArrayList<Code> charCodes = new ArrayList<>();

    public Huffman(ArrayList<CharFreqPair> charFreqs){
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        
        // Construct a priority queue of nodes out of the given frequencies and characters
        for(CharFreqPair charFreq : charFreqs){
            queue.add(new Node(charFreq.freq, charFreq.chr));
        }
        
        // Collapse the queue until there is only the root of the tree left
        while(queue.size() > 1){
            Node first = queue.poll();
            Node second = queue.poll();
            byte total = (byte)(first.freq + second.freq);

            if (first.freq < second.freq){
                queue.add(new Node(total, first, second));
            } else {
                queue.add(new Node(total, second, first));
            }
        }
        
        this.root = queue.poll();
        makeCodes("",root);


    }

    // Recursively travel Huffman tree to find the encodings
    public void makeCodes(String codeSoFar, Node node){
        if(node.leaf){
            charCodes.add(new Code(node.chr, codeSoFar));
        } else {
            makeCodes(codeSoFar + "1", node.one);
            makeCodes(codeSoFar + "0", node.zero);
        }
        //TODO I think this will work correctly
    }

    public String encodeString(String toEnc){
        String code = "";

        for (char chr : toEnc.toCharArray()){
            code = code + encodeChr(chr);
        }

        return code;
    }

    //Uses arraylist of codes to find the code for this character
    public String encodeChr(char chr){
        String enCode = "";

        for (int i = 0; i < charCodes.size(); i++){
            Code code = charCodes.get(i);
            if (code.chr == chr){
                enCode = code.code;
                break;
            }
        }

        return enCode;
    }

    public class Node implements Comparable<Node>{
        Node zero;
        Node one;
        char chr;
        boolean leaf;
        byte freq;

        public Node(byte freq, char chr){
            this.freq = freq;
            this.chr = chr;
            this.leaf = true;
        }
        public Node(byte freq, Node zero, Node one){
            this.freq = freq;
            this.leaf = false;
            this.zero = zero;
            this.one = one;
        }

        public int compareTo(Node node){
            return node.freq - this.freq;
        }
    }

    public class Code {
        char chr;
        String code;

        public Code(char chr, String code){
            this.chr = chr;
            this.code = code;
        }
    }

}
