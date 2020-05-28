package pMain;

import algorithms.Binary;
import algorithms.TF;
import algorithms.TFIDF;
import classifiers.Knn;
import classifiers.NaiveBayes;
import loadFiles.LoadFileTypes;
import loadFiles.LoadFromFile;
import loadFiles.SaveModel;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WithParameters {

    private static DecimalFormat df0 = new DecimalFormat("#");

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private static HashMap<String, Double> totalWordsInClass;

    private static int uniqueWords;

    public static void RunWithParameters(String[] args){
        LoadFileTypes clClasses = new LoadFileTypes(args[0]);
        String[] cl = clClasses.getResult();

        String pathTrain = args[1]+"/";
        File fTrain = new File(args[1]);
        File[] matchingFilesTrain = fTrain.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("lab");
            }
        });

        List<LoadFromFile> listDocTrain = loadDocuments(matchingFilesTrain,pathTrain);
        System.out.println("Files from "+args[1]+" repository were loaded successfully");

        String pathTest = args[2]+"/";
        File fTest = new File(args[2]);
        File[] matchingFilesTest = fTest.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("lab");
            }
        });
        List<LoadFromFile> listDocTest = loadDocuments(matchingFilesTest,pathTest);
        System.out.println("Files from "+args[2]+" repository were loaded successfully");

        System.out.println("Try to execute algorithm ("+args[3]+")");
        runAlgorithms(args[3], listDocTest, listDocTrain);
        System.out.println("Algorithm ("+args[3]+") was executed successfully");

        System.out.println("Try to execute classifier ("+args[4]+")");
        runClassifiers(args[4], listDocTest, listDocTrain, cl);
        System.out.println("Classifier ("+args[4]+") was executed successfully");

        System.out.println("Saving model in models repository");
        if(args[4].equals("NaiveBayes")){
            SaveModel model = new SaveModel(args[5], args[3], args[4], listDocTrain);
            model.setUniqueWords(uniqueWords);
            model.setTotalWordsInClass(totalWordsInClass);
            model.SaveThisModel();
        }else{
            SaveModel model = new SaveModel(args[5], args[3], args[4], listDocTrain);
            model.SaveThisModel();
        }
        System.out.println("Model saved");
    }

    private static void runClassifiers(String arg, List<LoadFromFile> listDocTest, List<LoadFromFile> listDocTrain, String[] cl) {
        double correct = 0;
        int inCorrect = 0;
        switch (arg){
            case "NaiveBayes":
                totalWordsInClass = NaiveBayes.getTotalWordsInClass(listDocTrain,cl);
                uniqueWords = NaiveBayes.uniqueWordsInAllClasses(listDocTrain);

                for(LoadFromFile file: listDocTest){
                    String tmpClass = NaiveBayes.NaiveBayes(listDocTrain, file, totalWordsInClass, uniqueWords);
                        if (isClassInResult(tmpClass,file.getResult())){
                            correct++;
                        }else inCorrect++;
                }
                break;

            case "Knn":
                for(LoadFromFile file: listDocTest){
                    String tmpClass = Knn.Knn(listDocTrain,file);
                    if (isClassInResult(tmpClass,file.getResult())){
                        correct++;
                    }else inCorrect++;
                }
                break;

            default:
                System.err.println("Classifier was not found!");
                System.exit(1);
        }
        double precentage = (correct/listDocTest.size())*100;
        System.out.println("Correct guesses: "+ df0.format(correct));
        System.out.println("InCorrect guesses: "+inCorrect);
        System.out.println("Successfully guessed: "+ df2.format(precentage)+"%");
    }

    private static boolean isClassInResult(String cl, String[] arr) {
        for (String s : arr) {
            if (s.equals(cl)) {
                return true;
            }
        }
        return false;
    }

    private static void runAlgorithms(String arg, List<LoadFromFile> listDocTest, List<LoadFromFile> listDocTrain) {
        switch (arg){
            case "TF":
                for (LoadFromFile file : listDocTrain){
                    TF.getTFFeatures(file);
                }
                for (LoadFromFile file : listDocTest){
                    TF.getTFFeatures(file);
                }
                break;
            case "TFIDF":
                for (LoadFromFile file : listDocTrain){
                    TFIDF.getTFIDFFeatures(file,listDocTrain);
                }
                for (LoadFromFile file : listDocTest){
                    TF.getTFFeatures(file);
                }
                break;
            case "Binary":
                for (LoadFromFile file : listDocTrain){
                    Binary.getBinaryFeatures(file);
                }
                for (LoadFromFile file : listDocTest){
                    TF.getTFFeatures(file);
                }
                break;
            default:
                System.err.println("Algorithm was not found!!");
                System.exit(1);
        }
    }

    private static List<LoadFromFile> loadDocuments(File[] files, String path) {
        List<LoadFromFile> result = new LinkedList<>();
        for (File f:files){
            LoadFromFile tmpLoad = new LoadFromFile(path+f.getName());
            tmpLoad.setText(tmpLoad.getFile(path+f.getName()));
            result.add(tmpLoad);
        }
        return result;
    }

}
