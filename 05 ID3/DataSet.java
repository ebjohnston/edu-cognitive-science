import java.util.ArrayList;

// ID3 Algorithm - Tree Data Container
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

public class DataSet {
    public class Datum {
        public String name;
        public int quantity;

        public Datum(String name) {
            this.name = name;
            quantity = 1;
        }

        @Override
        public String toString() {
            return name + ":" + quantity;
        }
    }

    public ArrayList<Datum> data;

    public DataSet() {
        data = new ArrayList<Datum>();
    }

    public void add(String name) {
        for (Datum iterator : data) {
            if (iterator.name.equals(name)) {
                iterator.quantity++;
                return;
            }
        }

        data.add(new Datum(name));
    }


    public int size() {
        return data.size();
    }

    @Override
    public String toString() {
        String buffer = "";

        for (Datum iterator : data) {
            buffer += iterator.name + "  ";
        }

        return buffer;
    }

    public String[] getStringArray() {
        String[] buffer = new String[data.size()];

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = data.get(i).name;
        }

        return buffer;
    }

    public int[] getHistogram() {
        int[] buffer = new int[data.size()];

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = data.get(i).quantity;
        }

        return buffer;
    }
}
