import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// ID3 Algorithm - Tree Node
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

public class Node {
    private String name;
    private HashMap<String, Node> children;

    public Table table;

    public Node(Table table) {
        this.table = table;
        name = null;
        children = new HashMap<>();
    }

    public Node categorize(ArrayList<String> datum, ArrayList<String> featureLabels) {
        if (datum.size() != featureLabels.size()) {
            System.out.println("SOMETHING HAS GONE TERRIBLY WRONG!!!!");
        }

        int featureIndex = -1;
        for (int i = 0; i < featureLabels.size(); i++) {
            if (featureLabels.get(i).equals(name)) {
                featureIndex = i;
            }
        }
        if (featureIndex == -1 || table.isLeaf()) {
            return this;
        }

        String feature = datum.get(featureIndex);

        if (hasOutEdge(feature)) {
            Node child = children.get(feature);

            ArrayList<String> childDatum = new ArrayList<>(datum);
            childDatum.remove(featureIndex);

            ArrayList<String> childFeatureLabels = new ArrayList<>(featureLabels);
            childFeatureLabels.remove(featureIndex);

            return child.categorize(childDatum, childFeatureLabels);
        }
        else {
            return this;
        }
    }

    private boolean hasOutEdge(String edge) {
        for (String key : children.keySet()) {
            if (key.equals(edge)) {
                return true;
            }
        }
        return false;
    }

    public void split() {
        if (table.isLeaf()) {
            name = table.getCategorySet().toString();
        }
        else {
            int bestFeature = table.getHighestGain();
            name = table.featureLabels.get(bestFeature);
            String[] outEdges = table.getFeatureLabels(bestFeature);
            Table[] subtables = table.makeSubtables(bestFeature);

            for (String s : outEdges) {
                Table t = findTableByName(subtables, s); //gets the CORRECT table
                Node n = new Node(t);
                children.put(s, n);
                children.get(s).split(); //ask it if it wants to breed
            }
        }
    }

    @Override
    public String toString() {
        String out = name.toUpperCase() + "\n";
        int tab = 2;
        String s = new ArrayList<>(Arrays.asList(table.getCategories())).toString();

        for (String key : children.keySet()) {
            String temp = key + "-->" + children.get(key).name + "\n";

            if(!children.get(key).children.isEmpty()) {
                temp += indent(tab, children.get(key).toString());
            }

            out += indent(tab,temp);
        }

        return out;
    }

    private Table findTableByName(Table[] tables, String edge) {
        Table output = null;

        for (Table t : tables) {
            if (t.selfEdge.equals(edge)) {
                return t;
            }
        }

        return output;
    }

    public static String indent(int length, String input) {
        String out = input;
        String pad = new String(new char[length]).replace("\0"," ");

        for (int i = out.length()-1; i > 0; i--) {
            if (out.substring(i-1, i).equals("\n")) {
                out = out.substring(0, i) + pad + out.substring(i);
            }
        }

        out = pad + out;
        return out;
    }
}
