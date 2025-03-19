public class Transition {
    char input;
    State transitionState;

    Transition(char givenInput, State resultantTransition) {
        this.input = givenInput;
        this.transitionState = resultantTransition;
    }

    public char getInput() {
        return input;
    }

    public State getTransitionState() {
        return transitionState;
    }
}
