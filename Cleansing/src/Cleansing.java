import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Cleansing {
    private PrintWriter writer = null;
    private static String outPutFolder = null;
    private static String inputFolder =  "Input";

    public static void main(String[] args) {

        if (args.length > 0) {
            outPutFolder = args[0];
            directoryCheck(outPutFolder);
        } else {
            outPutFolder = "Output";
        }
        File file = new File(inputFolder);
        listFilesForFolder(file);
        System.out.println("*****Finished*****");
    }

    private static void listFilesForFolder( File folder) {
        Cleansing obj;
        for (String fileEntry : folder.list()) {
            obj = new Cleansing();
            System.out.println("Reading file: " + fileEntry);
            obj.readFile(fileEntry);
            
        }
    }
    private static void directoryCheck(String folder) {
        File file = new File(folder);
        if (file.exists()) {
            cleanDirectory();
        } else {
            createDirectory();
            System.out.println("Directory is created!");
        }
    }

    private static void cleanDirectory() {
        File folder = new File(outPutFolder);
        for ( File fileEntry : folder.listFiles()) {
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
            scanner = new Scanner(new File("./" + inputFolder + "/" + fileName), "latin1");
            createFile("./" + outPutFolder + "/" + fileName + ".mat");
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
