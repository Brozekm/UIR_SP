package loadFiles;

import classifiers.NaiveBayes;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveModel {
    private String modelName;
    private String algorithm;
    private String classifier;
    private List<LoadFromFile> trainedData;
    private HashMap<String, Double> totalWordsInClass;
    private int uniqueWords;

    public void setUniqueWords(int uniqueWords) {
        this.uniqueWords = uniqueWords;
    }

    public void setTotalWordsInClass(HashMap<String, Double> totalWordsInClass) {
        this.totalWordsInClass = totalWordsInClass;
    }

    public SaveModel(String modelName, String algorithm, String classifier, List<LoadFromFile> trainedData) {
        this.modelName = modelName;
        this.algorithm = algorithm;
        this.classifier = classifier;
        this.trainedData = trainedData;
    }

    public void SaveThisModel(){

        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("models/"+this.modelName + ".model"));
        } catch (FileNotFoundException e) {
            System.err.println("Error while printing model");
            System.exit(1);
        }


        out.println(this.classifier);
        out.println(this.algorithm);

        if(this.classifier.equals("NaiveBayes")){
            out.println("uniqueWords "+ this.uniqueWords);
            for (Map.Entry<String, Double> entry: totalWordsInClass.entrySet()){
                out.println(entry.getKey()+" "+entry.getValue());
            }

        }

        for(LoadFromFile file: trainedData){
            printData(out,file);
        }

        out.close();
    }


    private void printData(PrintStream out, LoadFromFile file) {
        out.println("NEXT");
        for (String result: file.getResult()){
            out.print(result+" ");
        }
        out.println("");
        out.println(file.getText());
        for (Map.Entry<String, Double> entry: file.getMap().entrySet()){
            out.println(entry.getKey()+" "+entry.getValue());
        }
    }
}
