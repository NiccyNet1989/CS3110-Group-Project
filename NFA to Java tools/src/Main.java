import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        =====================================================
        Step 1

        To implement an NFA using the provided class, first start by initializing all states in the NFA
        Use constructor for State()       *Name for state is optional but recommended
            Example:      State q0 = new State("q0");
            Example:      State q1 = new State();
        You must define every state in the NFA with a constructor or the code will not work
         =====================================================
         */





        /*
        =====================================================
        Step 2

        Next, define the transitions for each state as a Transition[] array
        Use the one and only constructor for the Transition class; The first parameter is a char for the input the transition accepts, and the second parameter is a State that becomes live after reading the given input

        Example:    Transition[] q0Transitions = {new Transition('0', q1), new Transition('1', q1)};
            In the above example, the q0 state can activate state q1 by reading an input of either 0 or 1
        Example:    Transition[] q1T = {new Transition('0', q1),new Transition('1', q1)}


        Note that you must have defined every transition in step 1 to be able to create the transition's for each state
        You can use any character, and can also use λ to indicate a Lambda transition
        Again, you must do this for every state's transitions or the code will not work
        =====================================================
         */





        /*
        =====================================================
        Step 3

        Now, use the State class's setTransitions(Transitions[] array) method to set each state's transitions using the transitions you generated in step 2

        Example:    q0.setTransitions(q0Transitions)
        Example:    q1.setTransitions(q1T)
            The name of the Transition[] array does not matter, as long as it's contents correspond to the state's transitions

        Repeat this for each state until every state has their own transitions
        States without any transitions are also valid
        =====================================================
         */





        /*
        =====================================================
        Step 4 (Can skip)

        Create an array of the all the states in the NFA, to be passed to the NFA constructor
            Example:    State[] states = {q0, q1};

        Additionally, create an array of states for whichever states the NFA will consider as "accept states"
            Example: State[] acceptStates = {q1};
        Even if it is just one state, it must still be passed as an array
        =====================================================
         */





        /*
        =====================================================
        Step 5

        Use the NFA constructor to create the NFA
            Parameters are; NFA(State[] states, State startState, State[] acceptStates)
        Example:    NFA nfa = new NFA(states, q0, acceptStates);



        **If desired, you can skip Step 4 and create the arrays in the new NFA declaration
        Example:    NFA nfa = new NFA(State[] {q0, q1}, q0, State[] {q1});
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
        Transition[] q0Transitions = {new Transition('h', q1), new Transition('h', q6)};
        Transition[] q1Transitions = {new Transition('e', q2)};
        Transition[] q2Transitions = {new Transition('l', q3)};
        Transition[] q3Transitions = {new Transition('l', q4)};
        Transition[] q4Transitions = {new Transition('o', q5)};
        //Q5 has no transitions
        Transition[] q6Transitions = {new Transition('i', q7)};
        //Q7 also has no transitions


        //Step 3
        q0.setTransitions(q0Transitions);
        q1.setTransitions(q1Transitions);
        q2.setTransitions(q2Transitions);
        q3.setTransitions(q3Transitions);
        q4.setTransitions(q4Transitions);
        q6.setTransitions(q6Transitions);


        //Step 4
        State[] states = {q0, q1, q2, q3, q4, q5, q6, q7};
        State[] acceptStates = {q5, q7};


        //Step 5
        NFA nfa = new NFA(states, q0, acceptStates);

        //Now we can use the NFA
        nfa.isValidInput("hello");
        System.out.print("\n\n\n\n");

        nfa.isValidInput("hi");
        System.out.print("\n\n\n\n");

        nfa.isValidInput("howdy");
        System.out.print("\n\n\n\n");


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