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
            if (states[i].isLive) {
                liveStates[i] = 1;
            } else {
                liveStates[i] = 0;
            }
        }
    }

    public void printTransitions() {
        for (State state : states) {
            state.printTransitions();
            System.out.print("\n\n");
        }
    }

    public boolean isValidInput(String input) {
        boolean finishedOnLiveState = false;
        char[] stringToCharArray = input.toCharArray();
        boolean firstIteration = true;

        for (char character : stringToCharArray) {
            //The following is just to display the starting point after lambda functions have been considered
            if (firstIteration) {
                System.out.print("Starting point (After considering λ transitions)\n");

                for (int i = 0; i < this.states.length; i++) {
                    if (liveStates[i] == 1) {
                        for (Transition transition : states[i].getTransitions()) {
                            if (transition.getInput() == 'λ') {
                                transition.getTransitionState().setLive(true);
                            }
                        }
                        for (int j = 0; j < this.states.length; j++) {
                            if (states[j].isLive) liveStates[j] = 1;
                        }
                    }
                }
                printLiveStates();
                firstIteration = false;
                System.out.print("\n");
            }
            System.out.print("Reading Character: " + character + "\n");

            //Before we consider anything, we must update the liveStates so that all states with a 'λ' transition activate their corresponding transitions
            //Note that we run the process n^2 times, where n is the number of states, to account for instances where a 'λ' activates a state with another 'λ' and so on
            //By running n^2 times, we run this process as many times as needed to capture the highest possible amount of chains where a 'λ' activates another 'λ'
            if (!firstIteration) {
                for (int i = 0; i < this.states.length; i++) {
                    if (liveStates[i] == 1) {
                        for (Transition transition : states[i].getTransitions()) {
                            if (transition.getInput() == 'λ') {
                                transition.getTransitionState().setLive(true);
                            }
                        }
                        for (int j = 0; j < this.states.length; j++) {
                            if (states[j].isLive) liveStates[j] = 1;
                        }
                    }
                }
            }

            //Now we generate a temporary array which will have all states to be "dead" states
            //This is necessary because for each iteration the NFA reads in a character, it cannot update the current "live" states on the same iteration.
            //Otherwise, the NFA may read states which should be dead on the current iteration as "live" just because the state before them set them to live
            int[] tempStateArray = new int[this.liveStates.length];
            for (int index : tempStateArray) {
                tempStateArray[index] = 0;
            }

            //Now we need to run through each of the live states
            for (int i = 0; i < liveStates.length; i++) {
                //Of course, if a state is not live, we don't consider it or any of its transitions at all during the current iteration
                //For example, if state q0 has a transition to state q1 upon an input of '1' but state q0 is dead, state q1 should not become live due to the input of '1'
                if (this.liveStates[i] == 0) continue;

                //When we do come across a live state, we now need to look at each of its transitions and see if they match the character in the inputted string or is a 'λ' type transition
                for (Transition transition : this.states[i].getTransitions()) {
                    //If a transition does match an inputted character or is a 'λ' transition, we then look at its corresponding transitionState
                    if (character == transition.getInput()) {
                        //To find the matching state for the Transition at hand, we run through the NFA's states again
                        for (int j = 0; j < this.states.length; j++) {
                            //And if the state matches, we set it to be equal to 1 in the tempStateArray
                            //Note that this does not change which states are live in the states array, preserving the nature of the given iteration
                            //Instead, we will update the actual live states after we are done reading the character at this iteration
                            if (this.states[j].equals(transition.getTransitionState())) tempStateArray[j] = 1;
                        }
                    }
                }
            }


            //At this point we've properly set up the tempStateArray with 1's at the indexes of states that become live after this iteration and 0's at the indexes of states that should be dead at this iteration
            //So now, we must update which states on the NFA are live using the State Class's setLive method
            //First, we'll set all states to be dead
            for (int i = 0; i < this.states.length; i++) {
                this.states[i].setLive(false);
                this.liveStates[i] = 0;
            }

            //Then, we use tempStateArray to set each state at their corresponding index to be live, if the element at the given state's index is a 1 and not a 0
            for (int i = 0; i < this.states.length; i++) {
                if (tempStateArray[i] == 1) {
                    this.states[i].setLive(true);
                    this.liveStates[i] = 1;
                }
            }

            printLiveStates();
            System.out.print("\n");
        }

        //Once we've run through the character inputted by the method call, the final step is to check if any of the accept states are live
        for (State state : this.acceptStates) {
            if (state.isLive) finishedOnLiveState = true;
        }

        //Additionally, we also need to reset the NFA back to its original state
        //In other words, all states should be dead except the start state

        for (State state : this.states) {
            if (this.startState.equals(state)) state.setLive(true);
            else state.setLive(false);
        }
        return finishedOnLiveState;
    }

    public void printLiveStates() {
        for (int index : liveStates) {
            System.out.print(index + "\t");
        }
    }
}
