package algorithms;

import loadFiles.LoadFromFile;

import java.util.*;

public class TFIDF {
    public static void getTFIDFFeatures(LoadFromFile document, List<LoadFromFile> list){
        TF.getTFFeatures(document);
        HashMap<String, Double> map = document.getMap();

        for(Map.Entry<String, Double> entry: document.getMap().entrySet()){
            String tmpStr = entry.getKey();
            double wordFileCount = wordInFiles(tmpStr, list);
            double tmpIdf = Math.log(list.size()/wordFileCount);
            map.put(tmpStr,map.get(tmpStr)*tmpIdf);
        }

        document.setMap(map);
    }



    private static double wordInFiles(String word, List<LoadFromFile> list) {
        int count = 0;

        for(LoadFromFile file: list){
            if(file.getText().contains(word)){
                count++;
            }

        }
        return count;
    }

}
