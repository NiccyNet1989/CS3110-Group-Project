public class Main {
    public static void main(String[] args) {
        //Initialize states to generate completed state definitions later
        State q0 = new State("q0"), q1 = new State("q1"), q2 = new State("q2"), q3 = new State("q3"), q4 = new State("q4"), q5 = new State("q5");

        //First we'll need to generate each state's transitions
        //This will be done with the Transition class and its one and only constructor
        Transition[] q0Transitions = {new Transition('0', q1), new Transition('1', q1), new Transition('2', q1), new Transition('3', q1), new Transition('4', q1), new Transition('5', q1), new Transition('6', q1), new Transition('7', q1), new Transition('8', q1), new Transition('9', q1)};
        Transition[] q1Transitions = {new Transition('0', q1), new Transition('1', q1), new Transition('2', q1), new Transition('3', q1), new Transition('4', q1), new Transition('5', q1), new Transition('6', q1), new Transition('7', q1), new Transition('8', q1), new Transition('9', q1), new Transition('.', q2)};
        Transition[] q2Transitions = {new Transition('0', q3), new Transition('1', q3), new Transition('2', q3), new Transition('3', q3), new Transition('4', q3), new Transition('5', q3), new Transition('6', q3), new Transition('7', q3), new Transition('8', q3), new Transition('9', q3)};
        Transition[] q3Transitions = {new Transition('0', q3), new Transition('1', q3), new Transition('2', q3), new Transition('3', q3), new Transition('4', q3), new Transition('5', q3), new Transition('6', q3), new Transition('7', q3), new Transition('8', q3), new Transition('9', q3)};
        Transition[] q4Transitions = {new Transition('λ', q0), new Transition('-', q0), new Transition('λ', q5), new Transition('-', q5)};
        Transition[] q5Transitions = {new Transition('0', q5), new Transition('1', q5), new Transition('2', q5), new Transition('3', q5), new Transition('4', q5), new Transition('5', q5), new Transition('6', q5), new Transition('7', q5), new Transition('8', q5), new Transition('9', q5)};

        //Now, using those transitions, we can redefine each state's transitions
        q0.setTransitions(q0Transitions);
        q1.setTransitions(q1Transitions);
        q2.setTransitions(q2Transitions);
        q3.setTransitions(q3Transitions);
        q4.setTransitions(q4Transitions);
        q5.setTransitions(q5Transitions);

        State[] decimalReaderNFAStates = {q0, q1, q2, q3, q4, q5};
        NFA decimalReaderNFA = new NFA(decimalReaderNFAStates, q4, new State[]{q3, q5});

//        decimalReaderNFA.printTransitions();
//        decimalReaderNFA.printLiveStates();
    }
}