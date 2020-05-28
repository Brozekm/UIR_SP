package loadFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LoadFromFile {
    private String name;
    private String[] result;
    private String text;
    private HashMap<String, Double> map;

    public LoadFromFile(String name) {
        this.name = name;

    }

    public void setResult(String[] result) {
        this.result = result;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String[] getResult() {
        return result;
    }

    public HashMap<String, Double> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Double> map) {
        this.map = map;
    }

    public String getFile(String position){
        String str = "";
        File rFile = new File(position);
        try {
            Scanner sc = new Scanner(rFile);
            if (sc.hasNextLine()){
                String r = sc.nextLine();
                r =r.toLowerCase();
                this.result = r.trim().split("\\s+");
            }
            while (sc.hasNextLine()){
                str += sc.nextLine();
                str +=" ";
            }
        } catch (FileNotFoundException e) {
            System.err.println("File "+position+" was not found.");
            System.exit(1);
        }
        str = str.replaceAll("[^a-zA-Zá-žÁ-Ž]", " ");
        str = str.toLowerCase();
        return str;
    }
}
