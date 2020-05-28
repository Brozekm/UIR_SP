package algorithms;

import loadFiles.LoadFromFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Binary {
    public static void getBinaryFeatures(LoadFromFile document){
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
            }
        }

        document.setMap(map);
    }
}
