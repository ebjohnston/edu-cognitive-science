import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Program-Generating Program
// Sample Functionality Test
// ========================================
// Ethan Johnston
// CS 390L - Cognitive Science and AI
// Dr. Jody Paul
// 2 December 2017
// ========================================
// Collaborated with:
//   -Daniel Dews
//   -Harley Dutton
//   -Heather DeMarco
//   -Joseph Brennan

public class MetaTest {

    public static void main(String[] args) throws Exception {
        File[] initialJava = new File(".").listFiles(new JavaFileFilter());
        File[] initialClass = new File(".").listFiles(new ClassFileFilter());

        System.out.println("");
        System.out.println("Number of .java files: " + initialJava.length);
        System.out.println("Number of .class files: " + initialClass.length);

        String[] metaArgs = new String[] {"FUS", "RO", "DAH"};
        Meta.main(metaArgs);

        File[] newJava = new File(".").listFiles(new JavaFileFilter());
        File[] newClass = new File(".").listFiles(new ClassFileFilter());

        List<File> diffJava = diff(initialJava,newJava);
        List<File> diffClass = diff(initialClass,newClass);

        if (newJava.length < initialJava.length + metaArgs.length) {
            throw new Exception("Meta should create 3 new .java files");
        }
        if (newClass.length < initialClass.length + metaArgs.length) {
            throw new Exception("Meta should create 3 new .class files");
        }

        System.out.println("");
        System.out.println("Number of NEW .java files: " + diffJava.size() + ": " + diffJava);
        System.out.println("Number of NEW .class files: " + diffClass.size() + ": " + diffClass);
    }

    private static List<File> diff(File[] a, File[] b) {
        List<File> bigger, other;

        if (a.length < b.length) {
            bigger = Arrays.asList(b);
            other = Arrays.asList(a);
        } else {
            bigger = Arrays.asList(a);
            other = Arrays.asList(b);
        }

        List<File> files = new ArrayList<File>();
        for (File file : bigger) {
            if (!other.contains(file)) {
                files.add(file);
            }
        }

        return files;
    }

    private static class ClassFileFilter implements FileFilter {
        @Override
        public boolean accept(File f) {
            String suffix = ".class";
            return f.getName().toLowerCase().endsWith(suffix);
        }
    }

    private static class JavaFileFilter implements FileFilter {
        @Override
        public boolean accept(File f) {
            String suffix = ".java";
            return f.getName().toLowerCase().endsWith(suffix);
        }
    }
}
