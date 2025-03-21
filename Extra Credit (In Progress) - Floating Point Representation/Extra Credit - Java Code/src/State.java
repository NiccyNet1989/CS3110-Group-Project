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
