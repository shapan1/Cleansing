import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Clean {
    private PrintWriter writer = null;
    private static String outPutFolder = null;

    public static void main(String[] args) {

        directoryCheck();
        final File folder = new File("Input");
        listFilesForFolder(folder);
        System.out.println("*****Finished*****");
    }



    public static  void listFilesForFolder(final File folder) {
        Clean obj;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                obj = new Clean();
                System.out.println("Reading file: " + fileEntry.getName());
                obj.readFile(fileEntry.getName());
            }
        }
    }

    private static void directoryCheck() {
        File file = new File("Output");
        if (file.exists()) {
            cleanDirectory();
        } else {
            createDirectory();
            System.out.println("Directory is created!");
        }
    }

    private static void cleanDirectory() {
        File folder = new File("Output");
        for (File fileEntry : folder.listFiles()) {
            fileEntry.delete();
        }
    }

    private static void createDirectory() {
        File file = new File(outPutFolder);
        file.mkdir();
        System.out.println("Directory is created!");
    }

    private void readFile(String fileName) {
        Scanner scanner = null;
        boolean found = false;
        try {
            scanner = new Scanner(new File("./Input/" + fileName), "latin1");
            createFile("./Output/" + fileName + ".mat");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().equals(
                        "Cycles  Time(s)   Protocol Status Capacity(mAh)  Voltage(V)  Current(A)    Power(W)   Temp(C)      Iters")) {
                    found = true;
                    continue;
                }
                if (line.trim().equals("Lower cutoff voltage reached.")) {
                    break;
                }
                if (found) {
                    line = line.replaceAll("[a-zA-Z]+", "");
                    writer.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {

                scanner.close();
                writer.close();
            }
        }
    }

    private void createFile(String fileName) {
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
