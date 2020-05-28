package algorithms;

import loadFiles.LoadFromFile;


import java.util.*;

public class TF {
    public static void getTFFeatures(LoadFromFile document){
        HashMap<String, Double> map = new HashMap<>();
        String[] arr = document.getText().trim().split("\\s+");
        List<String> words = new LinkedList<>();
        for (String word: arr){
            if(word.length()>2){
                words.add(word);
            }
        }
        for(String word: words){
            if(!map.containsKey(word)){
                map.put(word,1.0);
            }else{
                map.put(word, map.get(word)+1);
            }
        }
        for(Map.Entry<String, Double> entry : map.entrySet()){
            entry.setValue(entry.getValue()/words.size());
        }

        document.setMap(map);
    }

}
