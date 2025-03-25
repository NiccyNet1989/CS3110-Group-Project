import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Initialize states to generate completed state definitions later
        State q0 = new State("q0"), q1 = new State("q1"), q2 = new State("q2"), q3 = new State("q3"), q4 = new State("q4"), q5 = new State("q5"), q6 = new State("q6");

        //Now add transitions to statess
        q0.addTransitions("0123456789", q1);
        q1.addTransitions("0123456789", q1);
        q1.addTransitions(".", q2);
        q2.addTransitions("0123456789", q3);
        q3.addTransitions("0123456789", q3);
        q4.addTransitions("λ-", q0);
        q4.addTransitions("λ-", q5);
        q5.addTransitions("0123456789", q6);
        q6.addTransitions("0123456789", q6);

        NFA decimalReaderNFA = new NFA(new State[]{q0, q1, q2, q3, q4, q5, q6}, q4, new State[]{q3, q6});

//        decimalReaderNFA.printTransitions();
//        decimalReaderNFA.printLiveStates();

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("Please input any string to test Decimal Reader NFA\n*Input the string 'exit' to end the program\n\n");
            input = scanner.nextLine();

            if (input.equals("exit")) break;
            decimalReaderNFA.isValidInput(input);

            System.out.print("\n\nPress enter to continue\n");
            scanner = new Scanner(System.in);
            scanner.nextLine();

            largeIndent();
        }
    }


    public static void largeIndent() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}