package classifiers;

import loadFiles.LoadFromFile;

import java.util.*;

public class NaiveBayes {
    public static int uniqueWordsInAllClasses(List<LoadFromFile> train){
        List<String> list = new LinkedList<>();
        for (LoadFromFile f:train){
            String[] arr = f.getText().trim().split("\\s+");
            for(String s: arr){
                if(s.length()>2){
                    if(!list.contains(s)) {
                        list.add(s);
                    }
                }

            }

        }

        return list.size();
    }

    public static HashMap<String, Double> getTotalWordsInClass(List<LoadFromFile> train, String[] classes){
        HashMap<String, Double> totalWordsInClass = new HashMap<>();
        for(String cl: classes){
            for(LoadFromFile file : train){
                if(isClassInResult(cl,file.getResult())){
                    double value = 0;
                    for(Map.Entry<String, Double> entry : file.getMap().entrySet()){
                        value += entry.getValue();
                    }
                    if(!totalWordsInClass.containsKey(cl)){
                        totalWordsInClass.put(cl,value);
                    }else {
                        totalWordsInClass.put(cl,totalWordsInClass.get(cl)+value);
                    }
                }
            }

        }
        return totalWordsInClass;
    }

    public static String NaiveBayes(List<LoadFromFile> train,LoadFromFile test, HashMap<String, Double> totalWordsInClass,int uniqueWords){
        String[] arr = test.getText().trim().split("\\s+");
        List<String> words = new LinkedList<>();
        for (String word:arr){
            if(word.length()>2){
                words.add(word);
            }
        }
        HashMap<String,Double> result = new HashMap<>();

        for(Map.Entry<String, Double> clEntry : totalWordsInClass.entrySet()){
            double probability = 0;
            for (LoadFromFile f: train){
                if(isClassInResult(clEntry.getKey(),f.getResult())){
                    probability++;
                }
            }
            probability = probability/train.size();

            for (String word : words){
                double wordCountInClass = 0;
                for(LoadFromFile docTrain: train){
                    if(isClassInResult(clEntry.getKey(),docTrain.getResult())){

                        if(docTrain.getMap().containsKey(word)){
                            wordCountInClass += docTrain.getMap().get(word);
                        }

                    }

                }
                probability *= (wordCountInClass+1.0)/(clEntry.getValue()+uniqueWords);


            }
            result.put(clEntry.getKey(),probability);
        }
        String tmpStr = Collections.max(result.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();



        return tmpStr;
    }

    private static boolean isClassInResult(String cl, String[] result) {
        for (String s: result){
            if(s.equals(cl)){
                return true;
            }
        }
        return false;
    }
}
