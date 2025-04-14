import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Load the NFA from the JFLAP (.jff) file using the relative path.
            NFA nfa = loadNFAFromJFF("Combined NFA.jff");

            // Use file-based testing: read test cases from "in_ans.txt" and write results to "out.txt"
            File inputFile = new File("in_ans.txt");
            File outputFile = new File("out.txt");

            // Debug output: show absolute paths used
            System.out.println("Using input file: " + inputFile.getAbsolutePath());
            System.out.println("Using output file: " + outputFile.getAbsolutePath());

            try (Scanner fileScanner = new Scanner(inputFile);
                 PrintWriter writer = new PrintWriter(outputFile)) {

                // Check if file has content
                if (!fileScanner.hasNextLine()) {
                    System.out.println("Test file in_ans.txt appears to be empty.");
                }

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.isEmpty()) continue;

                    // Each line should be in the format: <input> <expectedResult>
                    String[] parts = line.split("\\s+");
                    if (parts.length < 2) {
                        System.out.println("Ignoring improperly formatted line: " + line);
                        continue;
                    }

                    String testInput = parts[0];
                    String expected = parts[1];

                    // Evaluate the input using your custom NFA
                    boolean actual = nfa.isValidInput(testInput);
                    System.out.println(actual);
                    String passFail = (String.valueOf(actual).equalsIgnoreCase(expected)) ? "pass" : "fail";

                    String outputLine = String.format("Input: %-10s Actual: %-5s Expected: %-5s %s",
                            testInput, actual, expected, passFail);
                    writer.println(outputLine);

                    System.out.println(outputLine);
                }

                validateUntilExit(nfa);
            } catch (FileNotFoundException e) {
                System.out.println("Test file NFA to Java tools/in_ans.txt not found: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error loading NFA from Nfa.jff: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Loads an NFA from a JFLAP (.jff) file.
    public static NFA loadNFAFromJFF(String fileName) throws Exception {
        // Parse the JFLAP XML file
        File xmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Create a mapping from state id to State object
        HashMap<String, State> stateMap = new HashMap<>();
        NodeList stateList = doc.getElementsByTagName("state");
        for (int i = 0; i < stateList.getLength(); i++) {
            Element stateElem = (Element) stateList.item(i);
            String id = stateElem.getAttribute("id");
            String name = stateElem.getAttribute("name");
            if (name == null || name.isEmpty()) {
                name = "q" + id;
            }
            State state = new State(name);
            stateMap.put(id, state);
        }

        // Identify the start state and final states from the XML
        State startState = null;
        HashMap<String, Boolean> finalIDs = new HashMap<>();
        for (int i = 0; i < stateList.getLength(); i++) {
            Element stateElem = (Element) stateList.item(i);
            String id = stateElem.getAttribute("id");

            // Check for the <initial/> tag
            NodeList initialNodes = stateElem.getElementsByTagName("initial");
            if (initialNodes.getLength() > 0) {
                startState = stateMap.get(id);
            }
            // Check for the <final/> tag
            NodeList finalNodes = stateElem.getElementsByTagName("final");
            if (finalNodes.getLength() > 0) {
                finalIDs.put(id, true);
            }
        }

        // Add transitions based on the XML <transition> elements
        NodeList transList = doc.getElementsByTagName("transition");
        for (int i = 0; i < transList.getLength(); i++) {
            Element transElem = (Element) transList.item(i);
            String fromId = transElem.getElementsByTagName("from").item(0).getTextContent();
            String toId = transElem.getElementsByTagName("to").item(0).getTextContent();
            String read = transElem.getElementsByTagName("read").item(0).getTextContent();
            // In JFLAP, an empty transition is represented by an empty string.
            if (read.equals("")) {
                read = "λ";
            }
            State fromState = stateMap.get(fromId);
            State toState = stateMap.get(toId);
            // Add each character as a separate transition using your addTransitions method
            fromState.addTransitions(read, toState);
        }

        // Create collections of all states and of the final (accept) states
        State[] states = stateMap.values().toArray(new State[stateMap.size()]);
        java.util.List<State> acceptList = new java.util.ArrayList<>();
        for (String id : stateMap.keySet()) {
            if (finalIDs.containsKey(id)) {
                acceptList.add(stateMap.get(id));
            }
        }
        State[] acceptStates = acceptList.toArray(new State[acceptList.size()]);

        return new NFA(states, startState, acceptStates);
    }

    public static void validateUntilExit(NFA nfa) {
        Scanner scanner = new Scanner(System.in);
        String string = "";

        System.out.println("\n");

        while (true) {
            System.out.println("To test further inputs, please input a string (Type \"exit\" to end the program):");
            string = scanner.nextLine();

            if (string.toLowerCase().equals("exit")) break;

            nfa.isValidInput(string);

            System.out.println("\n\n");
        }
    }
}