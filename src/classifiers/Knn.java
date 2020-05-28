package classifiers;

import loadFiles.LoadFromFile;

import java.util.*;

public class Knn {

    private static final int nn = 1;

    public static String Knn(List<LoadFromFile> train, LoadFromFile test){
        String[] arr = test.getText().trim().split("\\s+");
        List<String> words = new LinkedList<>();
        for (String word:arr){
            if(word.length()>2){
                words.add(word);
            }
        }

        HashMap<LoadFromFile,Double> distances = new HashMap<>();

        for(LoadFromFile file:train){
            double distance = 0;

            for (Map.Entry<String, Double> entry: test.getMap().entrySet()){
                double value = 0;
                try {
                    value = file.getMap().get(entry.getKey());
                }catch (NullPointerException e){
                    //nothing
                }
                distance += (entry.getValue() - value)*(entry.getValue() - value);
            }

            distance = Math.sqrt(distance);
            distances.put(file, distance);

        }

        Map<String, Integer> classes = new HashMap<>();

        for (int i = 0 ; i < nn;i++){
            LoadFromFile nearest = Collections.max(distances.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
            distances.remove(nearest);
            for (String result:nearest.getResult()){
                if (!classes.containsKey(result)){
                    classes.put(result,1);
                }else {
                    classes.put(result,classes.get(result)+1);
                }
            }
        }




        String tmpStr = Collections.min(classes.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();


        return tmpStr;
    }


}
