public class NFA {
    State[] states;
    State startState;
    State[] acceptStates;
    int[] liveStates;
    int[] lambdaStates;

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

        this.liveStates = new int[this.states.length];
        for (int i = 0; i < this.liveStates.length; i++) {
            if (this.states[i].isLive) {
                this.liveStates[i] = 1;
            } else {
                this.liveStates[i] = 0;
            }
        }


        //Here the constructor indexes each state's index field, so we can more easily reference their index in the states[] and liveStates[] arrays
        for (int i = 0; i < this.states.length; i++) {
            this.states[i].setIndex(i);
        }


        //The constructor will also identify which states have a lambda transition
        //This is because we need to update states[] and liveStates[] if any live states have a lambda transition
        //Instead of finding each state with a lambda transition each time we update according to lambda transitions, we can just list which states have a lambda transition and store their indexes in an array
        int[] copyArray = new int[this.states.length];
        int copyIndex = 0;
        lambdaStates = new int[this.states.length];
        for (int i = 0; i < this.states.length; i++) {
            for (Transition transition : this.states[i].getTransitions()) {
                if (transition.getInput() == 'λ') {
                    lambdaStates[i] = i + 1;
                }
            }
        }
        for (int i = 0; i < this.lambdaStates.length; i++) {
            if (lambdaStates[i] != 0) {
                copyArray[copyIndex] = lambdaStates[i] - 1;
                copyIndex++;
            }
        }
        lambdaStates = new int[copyIndex];
        for (int i = 0; i < lambdaStates.length; i++) {
            lambdaStates[i] = copyArray[i];
        }
    }

    public void printTransitions() {
        for (State state : states) {
            state.printTransitions();
            System.out.print("\n\n");
        }
    }

    public boolean isValidInput(String input) {
        System.out.print("Checking if \"" + input + "\" is a valid input...\n");
        boolean finishedOnLiveState = false;
        char[] stringToCharArray = input.toCharArray();
        boolean firstIteration = true;
        int iteration = 0;

        for (char character : stringToCharArray) {
            //The following is just to display the starting point after lambda functions have been considered
            if (firstIteration) {
                System.out.print("Starting point (After considering 'λ' transitions)\n");

                considerLambdas();

                printLiveStates();
                firstIteration = false;
                System.out.print("\n");
            }
            System.out.print("Reading Character: " + character + "\n");

            //Before we consider anything, we must update the liveStates so that all states with a 'λ' transition activate their corresponding transitions
            //For this, we use the considerLambdas method
            if (!firstIteration) {
                considerLambdas();
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
                        tempStateArray[transition.getTransitionState().getIndex()] = 1;
                    }
                }
            }


            //At this point we've properly set up the tempStateArray with 1's at the indexes of states that become live after this iteration and 0's at the indexes of states that should be dead at this iteration
            //So now, we must update which states on the NFA are live using the State Class's setLive method
            //First, we'll set all states to be dead
            for (int i = 0; i < this.states.length; i++) {
                activateState(false, this.states[i]);
            }


//            System.out.print("\n============");
//            for (int i = 0; i < tempStateArray.length; i++) {
//                System.out.print(tempStateArray[i] + "\t");
//            }
//            System.out.print("\n============");


            //Then, we use tempStateArray to set each state at their corresponding index to be live, if the element at the given state's index is a 1 and not a 0
            for (int i = 0; i < this.states.length; i++) {
                if (tempStateArray[i] == 1) {
                    activateState(true, this.states[i]);
                }
            }

            iteration++;
            printLiveStates();
            if (stringToCharArray.length != iteration) System.out.print("\n");
            if (stringToCharArray.length == iteration) {
                System.out.print("\n");
                for (int i = 0; i < states.length; i++) {
                    if (i != 0) System.out.print("\t");
                    for (int j = 0; j < acceptStates.length; j++) {
                        if (acceptStates[j].equals(states[i])) System.out.print("^");
                    }
                }
            }
        }

        //Once we've run through the character inputted by the method call, the final step is to check if any of the accept states are live
        for (State state : this.acceptStates) {
            if (state.isLive) finishedOnLiveState = true;
        }


        //Additionally, we also need to reset the NFA back to its original state once we've tested a string and messed with the states and liveState array
        //In other words, all states should be dead except the start state
        for (int i = 0; i < this.states.length; i++) {
            if (this.startState.equals(states[i])) {
                activateState(true, this.states[i]);
            } else {
                activateState(false, this.states[i]);
            }
        }

        if (finishedOnLiveState) {
            System.out.print("\nThe input " + input + " is a valid input (Ended on an accept state)");
        } else {
            System.out.print("\nThe input " + input + " is an invalid input (Did not end on an accept state)");
        }

        return finishedOnLiveState;
    }


    public boolean isValidInput(String input, boolean simpleAnswer) {
        if (!simpleAnswer) System.out.print("Checking if \"" + input + "\" is a valid input...\n");
        boolean finishedOnLiveState = false;
        char[] stringToCharArray = input.toCharArray();
        boolean firstIteration = true;
        int iteration = 0;

        for (char character : stringToCharArray) {
            //The following is just to display the starting point after lambda functions have been considered
            if (firstIteration) {
                if (!simpleAnswer) System.out.print("Starting point (After considering 'λ' transitions)\n");

                considerLambdas();

                if (!simpleAnswer) printLiveStates();
                firstIteration = false;
                if (!simpleAnswer) System.out.print("\n");
            }
            if (!simpleAnswer) System.out.print("Reading Character: " + character + "\n");

            //Before we consider anything, we must update the liveStates so that all states with a 'λ' transition activate their corresponding transitions
            //For this, we use the considerLambdas method
            if (!firstIteration) {
                considerLambdas();
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
                        tempStateArray[transition.getTransitionState().getIndex()] = 1;
                    }
                }
            }


            //At this point we've properly set up the tempStateArray with 1's at the indexes of states that become live after this iteration and 0's at the indexes of states that should be dead at this iteration
            //So now, we must update which states on the NFA are live using the State Class's setLive method
            //First, we'll set all states to be dead
            for (int i = 0; i < this.states.length; i++) {
                activateState(false, this.states[i]);
            }

            //Then, we use tempStateArray to set each state at their corresponding index to be live, if the element at the given state's index is a 1 and not a 0
            for (int i = 0; i < this.states.length; i++) {
                if (tempStateArray[i] == 1) {
                    activateState(true, this.states[i]);
                }
            }

            iteration++;
            if (!simpleAnswer) {
                printLiveStates();
                if (stringToCharArray.length != iteration) System.out.print("\n");
                if (stringToCharArray.length == iteration) {
                    System.out.print("\n");
                    for (int i = 0; i < states.length; i++) {
                        if (i != 0) System.out.print("\t");
                        for (int j = 0; j < acceptStates.length; j++) {
                            if (acceptStates[j].equals(states[i])) System.out.print("^");
                        }
                    }
                }
            }
        }

        //Once we've run through the character inputted by the method call, the final step is to check if any of the accept states are live
        for (State state : this.acceptStates) {
            if (state.isLive) finishedOnLiveState = true;
        }


        //Additionally, we also need to reset the NFA back to its original state once we've tested a string and messed with the states and liveState array
        //In other words, all states should be dead except the start state
        for (int i = 0; i < this.states.length; i++) {
            if (this.startState.equals(states[i])) {
                activateState(true, this.states[i]);
            } else {
                activateState(false, this.states[i]);
            }
        }

        if (finishedOnLiveState) {
            System.out.print("The input " + input + " is a valid input (Ended on an accept state)");
        } else {
            System.out.print("The input " + input + " is an invalid input (Did not end on an accept state)");
        }

        return finishedOnLiveState;
    }


    private void printLiveStates() {
        for (int index : liveStates) {
            System.out.print(index + "\t");
        }
    }


    //Below is the considerLambdas function
    //Before a character is read at any given iteration of an NFA reading a string, all live states with a 'λ' transition point to another state must activate said state.
    //Thus, the considerLambdas function allows the other methods of the NFA class to set valid states to be live, before the next character in an inputted string is read in
    //To capture all possible chains of lambdas, the method must iterate n^2 times, where n is the amount of states that have a 'λ' transition
    private void considerLambdas() {
        for (int i = 0; i < lambdaStates.length; i++) {
            for (int j = 0; j < lambdaStates.length; j++) {
                for (Transition transition : states[lambdaStates[i]].getTransitions()) {
                    if (states[lambdaStates[i]].isLive && transition.getInput() == 'λ') {
                        activateState(true, transition.getTransitionState());
                    }
                }
            }
        }
    }


    //Throughout the entire process of testing a string, we reference and edit both a State object's isLive field and the NFA's liveStates[] array in parallel
    //Thus, we'll create this activateState to ensure that both are being done in tandem at all times
    private void activateState(boolean turnAlive, State state) {
        if (turnAlive) {
            state.setLive(true);
            liveStates[state.getIndex()] = 1;
        } else {
            state.setLive(false);
            liveStates[state.getIndex()] = 0;
        }
    }
}
