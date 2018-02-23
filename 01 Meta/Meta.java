import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

// Program-Generating Program
// Main Program Generator
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

public class Meta {
    private static final int NAME_LENGTH = 10;

    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[] {"Hello!"};
        }

        for (int i = 0; i < args.length; i++) {
            StringBuilder randomName = new StringBuilder();

            // generate a list of random lowercase characters
            for (int j = 0; j < NAME_LENGTH; j++) {
                randomName.append((char) ((int) (Math.random() * 26) + 'a'));
            }
            randomName.append(".java");

            File file = new File(randomName.toString());

            System.out.println("");

            writeProgram(file,args[i]);
            compileProgram(file);
            runProgram(file);
        }
    }

    private static void writeProgram(File file, String msg) {
        System.out.println("Writing " + file + " ...");

        try {
            FileWriter out = new FileWriter(file);
            String className = file.getName().split(".java")[0];

            StringBuilder contents = new StringBuilder();
            contents.append("public class %s {\n");
            contents.append("\tpublic static void main(String[] args) {\n");
            contents.append("\t\tSystem.out.println(\"Hello, world! My name is ");
            contents.append(className + " and I have come to say... " + msg + "\");");
            contents.append("\n\t}\n}\n");

            out.write(String.format(contents.toString(), className));
            out.close();
        }
        catch (Exception e) {
            System.err.println("Error writing to: " + file);
            e.printStackTrace();
        }
    }

    private static void compileProgram(File file) {
        System.out.println("Compiling " + file + " ...");

        try {
            String[] cmd = getCmd("javac " + file.getName());
            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor();
        }
        catch (Exception e) {
            System.err.println("Error compiling " + file + ".");
            e.printStackTrace();
        }
    }

    private static void runProgram(File file) {
        System.out.println("Running " + file + " ...");
        System.out.println("");

        String className = file.getName().split(".java")[0];

        try {
            String[] cmd = getCmd("java " + className);

            Process pro = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Program standard out: " + line);
            }

            pro.waitFor();
        }
        catch (Exception e) {
            System.err.println("Error occured when attempting to run " + file);
            e.printStackTrace();
        }
    }

    private static String[] getCmd(String args) {
        String osName = System.getProperty("os.name");
        String[] cmd = new String[3];

        if (osName.equals("Windows NT") || osName.equals("Windows 8.1") || osName.equals("Windows 10")) {
            cmd[0] = "cmd.exe" ;
            cmd[1] = "/C" ;
            cmd[2] = args;
        }
        else if (osName.equals("Windows 95")) {
            cmd[0] = "command.com" ;
            cmd[1] = "/C" ;
            cmd[2] = args;
        }
        else { // use UNIX / LINUX
            System.out.println(osName);
            cmd[0] = "/bin/sh";
            cmd[1] = "-c";
            cmd[2] = args;
        }

        return cmd;
    }
}
