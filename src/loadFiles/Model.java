package loadFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Model {
    private String modelName;
    private String algorithm;
    private String classifier;
    private List<LoadFromFile> trainedData;
    private HashMap<String, Double> totalWordsInClass;
    private int uniqueWords;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public List<LoadFromFile> getTrainedData() {
        return trainedData;
    }

    public void setTrainedData(List<LoadFromFile> trainedData) {
        this.trainedData = trainedData;
    }

    public HashMap<String, Double> getTotalWordsInClass() {
        return totalWordsInClass;
    }

    public void setTotalWordsInClass(HashMap<String, Double> totalWordsInClass) {
        this.totalWordsInClass = totalWordsInClass;
    }

    public int getUniqueWords() {
        return uniqueWords;
    }

    public void setUniqueWords(int uniqueWords) {
        this.uniqueWords = uniqueWords;
    }


}
