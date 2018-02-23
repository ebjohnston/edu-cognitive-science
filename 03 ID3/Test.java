import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// ID3 Algorithm - Sample Test
// ========================================
// Ethan Johnston
// CS 390L - Cognitive Science and AI
// Dr. Jody Paul
// 9 December 2017
// ========================================
// Collaborated with:
//   -Daniel Dews
//   -Harley Dutton
//   -Heather DeMarco
//   -Joseph Brennan

public class Test {
    private static String separator = "========================================";

    private static Table table;
    private static Node root;

    // test data
    private static String[] features = {"quantity", "color", "shape"};

    private static String[][] contents = {
        {"1", "red", "square", "A"},
        {"1", "blue", "square", "B"},
        {"1", "red", "circle", "C"},
        {"1", "blue", "circle", "D"},
        {"2", "red", "triangle", "A"},
        {"2", "blue", "triangle", "B"},
        {"2", "green", "triangle", "C"},
        {"1", "green", "square", "A"},
        {"2", "red", "circle", "B"},
        {"1", "red", "triangle", "C"},
    };

    private static String[][] tests = {
        {"1", "red", "square"},
        {"3", "blue", "triangle"},
        {"1", "green", "triangle"},
        {"1", "yellow", "square"},
        {"2", "green", "oval"},
        {"2", "blue", "circle"},
    };

    public static void main(String[] args){
        printHeader();

        setTable();
        printTable();

        processTree();

        processNewData();

        processInteraction();
    }

    private static void printHeader() {
        System.out.println("");
        System.out.println(separator);
        System.out.println("ID3 Program Test");
        System.out.println(separator);
        System.out.println("");
    }

    private static void setTable() {
        final int ROWS = contents.length;
        final int COLUMNS = contents[0].length - 1;

        table = new Table(ROWS, COLUMNS);

        table.featureLabels = new ArrayList<String>(Arrays.asList(features));

        for(int i = 0; i < contents.length; i++) {
            table.add(contents[i]);
        }
    }

    private static void printTable() {
        System.out.println("Data Table");
        System.out.println(separator);
        System.out.println(table);
        System.out.println("");

        int gainFeatureIndex = table.getHighestGain();
        String gainFeature = table.featureLabels.get(gainFeatureIndex);

        System.out.println("subtables split on feature: " + gainFeature.toUpperCase());
        System.out.println("");

        System.out.println("Subtables");
        System.out.println(separator);

        for (Table subtable : table.makeSubtables(table.getHighestGain())) {
            System.out.println(subtable);
            System.out.println(separator);
        }

        System.out.println("");
    }

    private static void processTree() {
        root = new Node(table);
        root.split();
        System.out.println(root);
    }

    private static void processNewData() {
        ArrayList<ArrayList<String>> testList = new ArrayList<ArrayList<String>>();

        final int NEW_TESTS = tests.length;
        Node[] results = new Node[NEW_TESTS];

        ArrayList<String> rootFeatureLabels = new ArrayList<String>(root.table.featureLabels);

        for (int i = 0; i < NEW_TESTS; i++) {
            testList.add(new ArrayList<String>(Arrays.asList(tests[i])));
            results[i] = root.categorize(testList.get(i), rootFeatureLabels);
        }

        // print results
        System.out.println(separator);
        for (int i = 0; i < NEW_TESTS; i++) {
            String category = results[i].table.getCategorySet().toString();
            System.out.println("the category for " + testList.get(i) + " is " + category);
        }
    }

    private static void processInteraction() {
        System.out.println("");
        System.out.println("Interactive Sorting");
        System.out.println(separator);

        Scanner console = new Scanner(System.in);
        String[] input = new String[features.length];

        do {
            System.out.println("");

            for(int i = 0; i < features.length; i++) {
                System.out.print("What is the " + features[i] + "?: ");
                input[i] = console.nextLine();
            }

            ArrayList<String> inputList = new ArrayList<String>(Arrays.asList(input));
            ArrayList<String> rootFeatureLabels = new ArrayList<String>(root.table.featureLabels);
            Node result = root.categorize(inputList, rootFeatureLabels);
            String category = result.table.getCategorySet().toString();

            System.out.println("");
            System.out.println("the category for " + inputList + " is " + category);
            System.out.println("");

            System.out.print("Continue? (y/n): ");
            String cont = console.nextLine();

            if (cont.equalsIgnoreCase("n")) {
                break;
            }
        } while (true);
    }
}
