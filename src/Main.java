
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();


        while(scan.hasNextLine()){
            line = scan.nextLine();

            // Terminate the program if x is entered
            if (line.equals("x")){
                return;
            }

        }
    }
}
