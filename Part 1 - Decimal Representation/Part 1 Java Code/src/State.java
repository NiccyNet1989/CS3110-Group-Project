public class State {
    public boolean isLive = false;
    public Transition[] transitions;

    private String name;
    public boolean isAcceptState = false;


    State(Transition[] inputtedTransitions, boolean startState, boolean defineAcceptState) {
        this.transitions = new Transition[inputtedTransitions.length];

        for (int i = 0; i < inputtedTransitions.length; i++) {
            this.transitions[i] = inputtedTransitions[i];
        }

        if (startState) this.isLive = true;
        if (defineAcceptState) this.isAcceptState = true;
    }

    State(Transition[] inputtedTransitions, boolean startState, String InputtedName, boolean defineAcceptState) {
        this.transitions = new Transition[inputtedTransitions.length];

        for (int i = 0; i < inputtedTransitions.length; i++) {
            this.transitions[i] = inputtedTransitions[i];
        }

        if (startState) this.isLive = true;
        this.name = InputtedName;
        if (defineAcceptState) this.isAcceptState = true;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public String getName() {
        return name;
    }

    public boolean isAcceptState() {
        return isAcceptState;
    }
}
