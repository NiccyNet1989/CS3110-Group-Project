import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
<<<<<<< Updated upstream
        /*
        =====================================================
        Step 1

        To implement an NFA using the provided class, first start by initializing all states in the NFA
        Use constructor for State()       *Name for state is optional but recommended
            Example:      State q0 = new State("q0");
            Example:      State q1 = new State(), q2 = new State();

        You must define every state in the NFA with a constructor or the code will not work
         =====================================================
         */





        /*
        =====================================================
        Step 2

        Next, use the State class's addTransitions method to add all of a state's transitions
        The method takes in a string of inputs and a state that said inputs will transition to
        If a state transitions to more than 1 state, you will need to use this method more than once

        Example:    q0.addTransitions("01", q1);
            In the above example, q0 will now transition to q1 if the user inputs a 0 or a 1

        Example:    q0.addTransitions("0123456789", q2);
            Now, q0 will also transition to q2 if the user inputs any character from 0 to 9
            q0 still transitions to q1 upon inputting a 0 or a 1
        =====================================================
         */





        /*
        =====================================================
        Step 3 (Can skip)

        Create an array of the all the states in the NFA, to be passed to the NFA constructor
            Example:    State[] states = {q0, q1};

        Additionally, create an array of states for whichever states the NFA will consider as "accept states"
            Example: State[] acceptStates = {q1};
        Even if it is just one state, it must still be passed as an array
        =====================================================
         */





        /*
        =====================================================
        Step 4

        Use the NFA constructor to create the NFA
            Parameters are; NFA(State[] states, State startState, State[] acceptStates)
        Example:    NFA nfa = new NFA(states, q0, acceptStates);



        **If desired, you can skip Step 3 and create the arrays directly in the new NFA declaration
        Example:    NFA nfa = new NFA(new State[] {q0, q1, q2}, q0, new State[] {q1});
        =====================================================
         */





        /*
        =====================================================
        Once the NFA has been created, you can now use its various methods

        NFA.isValidInput(String);
            Will take any string and test if is a valid input (Tests if the input lands on an accept state)

        NFA.printTransitions();
            Will print each state of the NFA and their respective transitions


        =====================================================
         */


        //===========================================================================
        //===========================================================================
        //===========================================================================
        //Here is an example of a simple NFA created using the provided classes, which simply reads the input "hello" or "hi"   *Case sensitive
        //Step 1
        State q0 = new State(), q1 = new State(), q2 = new State(), q3 = new State(), q4 = new State(), q5 = new State(), q6 = new State(), q7 = new State();


        //Step 2
        q0.addTransitions("h", q1);
        q0.addTransitions("h", q6);
        q1.addTransitions("e",q2);
        q2.addTransitions("l",q3);
        q3.addTransitions("l",q4);
        q4.addTransitions("o",q5);
        //q5 has no transitions
        q6.addTransitions("i",q7);
        //q7 also has no transitions


        //Step 3
        State[] states = {q0, q1, q2, q3, q4, q5, q6, q7};
        State[] acceptStates = {q5, q7};


        //Step 4
        NFA nfa = new NFA(states, q0, acceptStates);

        //Now we can use the NFA
        nfa.isValidInput("hello");
        System.out.print("\n\n\n\n");

        nfa.isValidInput("hi");
        System.out.print("\n\n\n\n");

        nfa.isValidInput("howdy");
        System.out.print("\n\n\n\n");


=======
        try {
            // Load the NFA from the JFLAP (.jff) file using the relative path.
            NFA nfa = loadNFAFromJFF("Nfa.jff");
            
            // Use file-based testing: read test cases from "in_ans.txt" and write results to "out.txt"
            File inputFile = new File("in_ans.txt");
            File outputFile = new File("NFA to Java tools/out.txt");
            
            // Debug output: show absolute paths used
            System.out.println("Using input file: " + inputFile.getAbsolutePath());
            System.out.println("Using output file: " + outputFile.getAbsolutePath());
            
            try (Scanner fileScanner = new Scanner(inputFile);
                 PrintWriter writer = new PrintWriter(outputFile)) {
                 
                // Check if file has content
                if (!fileScanner.hasNextLine()) {
                    System.out.println("Test file in_ans.txt appears to be empty.");
                }
                 
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.isEmpty()) continue;
                    
                    // Each line should be in the format: <input> <expectedResult>
                    String[] parts = line.split("\\s+");
                    if (parts.length < 2) {
                        System.out.println("Ignoring improperly formatted line: " + line);
                        continue;
                    }
                    
                    String testInput = parts[0];
                    String expected = parts[1];
                    
                    // Evaluate the input using your custom NFA
                    boolean actual = nfa.isValidInput(testInput);
                    System.out.println(actual);
                    String passFail = (String.valueOf(actual).equalsIgnoreCase(expected)) ? "pass" : "fail";
                    
                    String outputLine = String.format("Input: %-10s Actual: %-5s Expected: %-5s %s", 
                                                      testInput, actual, expected, passFail);
                    writer.println(outputLine);
                    
                    System.out.println(outputLine);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Test file NFA to Java tools/in_ans.txt not found: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error loading NFA from Nfa.jff: " + e.getMessage());
            e.printStackTrace();
        }
>>>>>>> Stashed changes
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