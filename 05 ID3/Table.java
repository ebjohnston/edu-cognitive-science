import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// ID3 Algorithm - Tree Utilities
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

public class Table {
    private int addIndex;
    private int categoryIndex;
    private int elementCount;
    private int featureCount;

    private String[][] contents;
    public String selfEdge;
    public ArrayList<String> featureLabels;

    public Table(int elementCount, int featureCount) {
        this.featureCount = featureCount;
        this.elementCount = elementCount;

        categoryIndex = featureCount + 1;
        featureLabels = new ArrayList<String>();

        contents = new String[elementCount][featureCount + 1];
        for (String[] row : contents) {
            for (String cell : row) {
                cell = "";
            }
        }
    }

    public void add(String[] row) {
        if (row.length != categoryIndex) {
            System.out.println("the str[] was not the right size to be added.");
            System.out.println("this is the str[]:");

            for (String cell : row) {
                System.out.print(cell + " ");
            }

            System.out.println("this is the table:\n" + this);
        }
        if (addIndex >= contents.length) {
            System.out.println("this table isnt large enough to hold another element");
        }
        contents[addIndex++] = row;
    }

    public Table[] makeSubtables(int featureIndex) {
        if (featureIndex >= featureCount) {
            System.out.println("error! not enough features. feat#: " +
                               featureIndex + "\n" + this);
            System.out.println("returning null");
            return null;
        }

        //figure out how many subtables we need.
        DataSet bufferSet = new DataSet();
        for (String[] row : contents) {
            bufferSet.add(row[featureIndex]);
        }

        int size = bufferSet.size();
        Table[] output = new Table[size];
        HashMap<String, Table> subtables = new HashMap<String, Table>();

        for (int i = 0; i < output.length; i++) {
            output[i] = new Table(bufferSet.data.get(i).quantity, featureCount - 1);

            output[i].selfEdge = bufferSet.data.get(i).name;

            output[i].featureLabels = new ArrayList<String>(featureLabels);
            output[i].featureLabels.remove(featureIndex);

            subtables.put(bufferSet.data.get(i).name, output[i]);
        }

        for (String[] row : contents) {
            String[] reduced = new String[categoryIndex - 1];

            for (int i = 0, r = 0; i < row.length; i++, r++) {
                if (i == featureIndex) { //skip the String at feat#
                    i++;
                }
                reduced[r] = row[i]; //fill em up.
            }

            String tabName = row[featureIndex];
            subtables.get(tabName).add(reduced); //add each element to its subtable
        }
        return output;
    }

    public boolean isLeaf() {
        return featureCount == 0 ||                 // out of features
               getGain(getHighestGain()) == 0.0 ||  // unable to gain info
               isSameClass();                       // unable to split further
    }

    private boolean isSameClass() {
        String[] categories = getCategories();

        if (categories.length == 0) { // this should never happen
            System.out.println("ERROR: isSameClass() called on a table with " +
                               "0 elements");
            return true;
        }

        // ensure all elements match first
        String first = categories[0];
        for (String category : categories) {
            if (!category.equals(first)) {
                return false;
            }
        }
        return true;
    }

    public int getHighestGain() {
        int bestIndex = -1;
        double bestValue = -1;

        for (int i = 0; i < featureCount; i++) {
            if (getGain(i) > bestValue) {
                bestIndex = i;
                bestValue = getGain(i);
            }
        }

        return bestIndex;
    }

    public double getGain(int featureIndex) {
        double output = this.getEntropy();
        Table[] subtables = this.makeSubtables(featureIndex);

        for (Table subtable : subtables) {
            output -= subtable.getEntropy() * subtable.elementCount / this.elementCount;
        }

        return output;
    }

    public String[] getFeatureLabels(int featureIndex) {
        DataSet output = new DataSet();

        for (int row = 0; row < contents.length; row++) {
            output.add(contents[row][featureIndex]);
        }

        return output.getStringArray();
    }

    public ArrayList<String> getCategorySet() {
        ArrayList<String> output = new ArrayList<>();

        String[] categories = getCategories();
        boolean inSet;

        for (String category : categories) {
            inSet = false;
            for (String b : output) { // iterates through out
                if (b.equals(category)) {
                    inSet = true;
                }
            }

            if (!inSet) {
                output.add(category);
            }
        }

        return output;
    }

    public String[] getCategories() {
        String[] output = new String[elementCount];

        for (int row = 0; row < contents.length; row++) {
            int last = contents[row].length - 1;
            output[row] = contents[row][last];
        }

        return output;
    }

    public double getEntropy() {
        DataSet bufferSet = new DataSet();

        for (int row = 0; row < contents.length; row++) {
            int last = contents[row].length - 1;
            bufferSet.add(contents[row][last]);
        }

        // histogram of the possible weighted outcomes.
        int[] outcomes = bufferSet.getHistogram();

        double sum = 0;
        for (int i = 0; i < outcomes.length; i++) {
            double temp = outcomes[i] / (double)elementCount;
            sum -= temp * (Math.log(temp) / Math.log(2.0));
        }

        return sum;
    }

    @Override
    public String toString() {
        String output = "";

        if (selfEdge != null) {
            output += selfEdge.toUpperCase() + "   LEAF:" + isLeaf() + "\n";
        }

        int[] columnWidths = new int[contents[0].length];

        // get column widths
        for (int row = 0; row < contents.length; row++) {
            for (int column = 0; column < contents[0].length; column++) {
                columnWidths[column] = Math.max(columnWidths[column], contents[row][column].length());
            }
        }

        // add space on either side
        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] += 2;
        }

        // add padding as necessary, process table
        for (int row = 0; row < contents.length; row++) {
            for (int column = 0; column < contents[0].length; column++) {
                String padding = new String(new char
                        [columnWidths[column]-contents[row][column].length()])
                        .replace("\0"," ");

                output += contents[row][column] + padding;
            }
            output += "\n";
        }

        output += "\ndata categories: ";

        output += getCategorySet();

        return output;
    }
}
