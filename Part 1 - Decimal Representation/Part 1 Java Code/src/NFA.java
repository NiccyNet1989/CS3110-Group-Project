public class NFA {
    State[] states;
    State startState;
    State[] acceptStates;
    int[] liveStates;

    NFA(State[] givenStates, State start, State[] finishes) {
        this.states = new State[givenStates.length];
        for (int i = 0; i < givenStates.length; i++) {
            this.states[i] = givenStates[i];
        }

        this.startState = start;
        start.setLive(true);

        this.acceptStates = new State[finishes.length];
        for (int i = 0; i < finishes.length; i++) {
            this.acceptStates[i] = finishes[i];
        }

        liveStates = new int[states.length];
        for (int i = 0; i < liveStates.length; i++) {
            if (states[i].isLive) liveStates[i] = 1;
            else liveStates[i] = 0;
        }
    }

    public void printTransitions() {
        for (State state : states) {
            state.printTransitions();
            System.out.print("\n\n");
        }
    }

    public boolean isValidInput(String input) {
        char[] stringToCharArray = input.toCharArray();

        for (char character : stringToCharArray) {
            //First we generate a temporary array which will have all states to be "dead" states
            //This is necessary because for each iteration the NFA reads in a character, it cannot update the current "live" states on the same iteration.
            //Otherwise, the NFA may read states which should be dead on the current iteration as "live" just because the state before them set them to live
            int[] tempStateArray = new int[liveStates.length];
            for (int index : tempStateArray) {
                tempStateArray[index] = 0;
            }

            //Now we need to run through each of the live states
            for (int index : liveStates) {
                //Of course, if a state is not live, we don't consider it or any of its transitions at all during the current iteration
                //For example, if state q0 has a transition to state q1 upon an input of '1' but state q0 is dead, state q1 should not become live due to the input of '1'
                if (!states[index].isLive) continue;

                for (Transition transition : states[index].getTransitions()) {
                    if (character == transition.getInput() || transition.getInput() == 'λ') {
                        for (int i = 0; i < states.length; i++) {
                            if (states[i].equals(transition.getTransitionState())) tempStateArray[i] = 1;
                        }
                    }
                }
            }

            for (State state : states) {
                state.setLive(false);
            }

            for (int index : tempStateArray) {
                if (tempStateArray[index] == 1) states[index].setLive(true);

            }
        }

        return false;
    }

    public void printLiveStates() {
        for (int index : liveStates) {
            System.out.print(index + "\t");
        }
    }
}
