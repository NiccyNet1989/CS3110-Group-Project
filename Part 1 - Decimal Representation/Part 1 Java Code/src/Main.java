public class Main {
    public static void main(String[] args) {
        //Initialize states to generate completed state definitions later
        State q0 = null, q1 = null, q2 = null, q3 = null, q4 = null, q5 = null;

        //First we'll need to generate each state's transitions
        //This will be done with the Transition class and its one and only constructor
        Transition[] q0Transitions = {new Transition('0', q1), new Transition('1', q1), new Transition('2', q1), new Transition('3', q1), new Transition('4', q1), new Transition('5', q1), new Transition('6', q1), new Transition('7', q1), new Transition('8', q1), new Transition('9', q1)};
        Transition[] q1Transitions = {new Transition('0', q1), new Transition('1', q1), new Transition('2', q1), new Transition('3', q1), new Transition('4', q1), new Transition('5', q1), new Transition('6', q1), new Transition('7', q1), new Transition('8', q1), new Transition('9', q1), new Transition('.', q2)};
        Transition[] q2Transitions = {new Transition('0', q3), new Transition('1', q3), new Transition('2', q3), new Transition('3', q3), new Transition('4', q3), new Transition('5', q3), new Transition('6', q3), new Transition('7', q3), new Transition('8', q3), new Transition('9', q3)};
        Transition[] q3Transitions = {new Transition('0', q3), new Transition('1', q3), new Transition('2', q3), new Transition('3', q3), new Transition('4', q3), new Transition('5', q3), new Transition('6', q3), new Transition('7', q3), new Transition('8', q3), new Transition('9', q3)};
        Transition[] q4Transitions = {new Transition('λ', q0),new Transition('-',q0),new Transition('λ', q5),new Transition('-',q5)};
        Transition[] q5Transitions = {new Transition('0', q5), new Transition('1', q5), new Transition('2', q5), new Transition('3', q5), new Transition('4', q5), new Transition('5', q5), new Transition('6', q5), new Transition('7', q5), new Transition('8', q5), new Transition('9', q5)};

        //Now, using those transitions, we can create our state objects
        q0 = new State(q0Transitions, false, "q0",false);
        q1 = new State(q1Transitions, false,"q1",false);
        q2 = new State(q2Transitions, false,"q2",false);
        q3 = new State(q3Transitions, false,"q3", true);
        q4 = new State(q4Transitions, true,"q4",false);
        q5 = new State(q5Transitions, false,"q5", true);


    }
}