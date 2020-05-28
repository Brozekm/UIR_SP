package loadFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadFileTypes {
    private String name;
    private String[] result;

    public LoadFileTypes(String name) {
        this.name = name;
        this.result = getFile(name);
    }

    public String[] getResult() {
        return result;
    }
    

    private String[] getFile(String position){
        String str = "";
        File rFile = new File(position);
        try {
            Scanner sc = new Scanner(rFile);
            while (sc.hasNextLine()){
                str += sc.nextLine();
                str +=" ";
            }
        } catch (FileNotFoundException e) {
            System.err.println("File "+position+" with list of classification of classes was not found!");
            System.exit(1);
        }
        str = str.replaceAll("[^a-zA-Zá-žÁ-Ž]", " ");
        String[] arr = str.trim().split("\\s+");
        return arr;
    }
}

