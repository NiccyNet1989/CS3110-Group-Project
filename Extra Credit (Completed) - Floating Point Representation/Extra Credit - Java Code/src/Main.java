import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Initialize states to generate completed state definitions later
        State q0 = new State("q0"), q1 = new State("q1"), q2 = new State("q2"), q3 = new State("q3"), q4 = new State("q4");
        State q5 = new State("q5"), q6 = new State("q6"), q7 = new State("q7"), q8 = new State("q8");
        State q9 = new State("q9"), q10 = new State("q10"), q11 = new State("q11");

        //Now add transitions to states
        q0.addTransitions("λ-", q1);
        q1.addTransitions("0123456789", q1);
        q1.addTransitions("0123456789", q2);
        q1.addTransitions(".", q4);
        q1.addTransitions("_", q11);
        q2.addTransitions(".", q3);
        q2.addTransitions("eE", q7);
        q4.addTransitions("0123456789", q5);
        q5.addTransitions("0123456789", q5);
        q5.addTransitions("_", q6);
        q5.addTransitions("eE", q7);
        q6.addTransitions("0123456789", q5);
        q7.addTransitions("λ-", q8);
        q8.addTransitions("0123456789", q9);
        q9.addTransitions("0123456789", q9);
        q9.addTransitions("_", q10);
        q10.addTransitions("0123456789", q9);
        q11.addTransitions("0123456789", q1);

        NFA decimalReaderNFA = new NFA(new State[]{q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10}, q0, new State[]{q3, q5, q9});

//        decimalReaderNFA.printTransitions();
//        decimalReaderNFA.printLiveStates();

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("Please input any string to test Floating Point Literal NFA\n*Input the string 'exit' to end the program\n\n");
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