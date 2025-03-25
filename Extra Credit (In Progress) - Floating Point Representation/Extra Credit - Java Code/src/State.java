public class State {
    public boolean isLive = false;
    public Transition[] transitions;

    private String name;
    private int index;

    State(String InputtedName) {
        this.transitions = new Transition[0];
        this.name = InputtedName;
    }

    State() {
        this.transitions = new Transition[0];
    }

    public void setTransitions(Transition[] newTransitions) {
        this.transitions = newTransitions;
    }

    public void addTransitions(String newTransitions, State transitionState) {
        char[] newTransitionArray = newTransitions.toCharArray();

        Transition[] placeholder = new Transition[this.transitions.length];
        for (int i = 0; i < this.transitions.length; i++) {
            placeholder[i] = this.transitions[i];
        }

        this.transitions = new Transition[this.transitions.length + newTransitions.length()];

        int newTransitionsIndex = 0;
        for (int i = 0; i < this.transitions.length; i++) {
            if (i < placeholder.length) {
                this.transitions[i] = placeholder[i];
            } else {
                this.transitions[i] = new Transition(newTransitionArray[newTransitionsIndex], transitionState);
                newTransitionsIndex++;
            }
        }
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


    public void printTransitions() {
        System.out.println(this.getName() + "'s Transitions\n========================");

        for (int i = 0; i < this.transitions.length; i++) {
            System.out.println(transitions[i].getInput() + " goes to " + transitions[i].getTransitionState().getName());
        }
    }

    public Transition[] getTransitions() {
        return transitions;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
