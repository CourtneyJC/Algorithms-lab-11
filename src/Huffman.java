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

        if (charFreqs.size() == 1){
            Node alone = new Node ((byte) 100, queue.poll().chr);
            this.root = new Node((byte) 100, null, alone);
        } else {
            this.root = queue.poll();
        }

        makeCodes("", root);
        System.out.println();

    }

    // Recursively travel Huffman tree to find the encodings
    public void makeCodes(String codeSoFar, Node node){
        if (node == null){
            return;
        } else if(node.leaf){
            Code code = new Code(node.chr, codeSoFar);
            charCodes.add(code);
            System.out.print(code);
        } else {
            makeCodes(codeSoFar + "1", node.one);
            makeCodes(codeSoFar + "0", node.zero);
        }
    }

    public String encodeString(String toEnc){
        try {
            String code = "";

            for (char chr : toEnc.toCharArray()) {
                code = code + encodeChr(chr);
            }

            return code;
        } catch (UnsupportedOperationException e){
            return "Unsupported character used";
        }
    }

    //Uses arraylist of codes to find the code for this character
    private String encodeChr(char chr) throws UnsupportedOperationException{
        String enCode = "";

        for (int i = 0; i < charCodes.size(); i++){
            Code code = charCodes.get(i);
            if (code.chr == chr){
                return code.code;
            }
        }

        throw new UnsupportedOperationException();
    }

    // Traverses the code tree to decode the String
    public String decodeString(String code){
        String str = "";
        Node current = root;

        for (int i = 0; i < code.length(); i++){
            // If the node is a leaf, we've found the next character
            if (current == null){
                return "Invalid encoding";
            }
            if (current.leaf){
                str = str + current.chr;
                current = root;
            }

            // Traverse the tree until we find the right character
            if (code.charAt(i) == '0'){
                current = current.zero;
            } else if (code.charAt(i) == '1'){
                current = current.one;
            } else {
                return "Invalid encoding";
            }
        }

        if(current.leaf) {
            str = str + current.chr;
        } else {
            return "Invalid encoding";
        }

        return str;
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
            return this.freq - node.freq;
        }
    }

    public class Code {
        char chr;
        String code;

        public Code(char chr, String code){
            this.chr = chr;
            this.code = code;
        }

        public String toString(){
            return chr + ": " + code + "  ";
        }
    }

}
