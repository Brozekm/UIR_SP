package pMain;

import algorithms.Binary;
import algorithms.TF;
import algorithms.TFIDF;
import classifiers.Knn;
import classifiers.NaiveBayes;
import loadFiles.LoadFromFile;
import loadFiles.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static Model mainModel = new Model();
    private static List<LoadFromFile> trData = new LinkedList<>();
    public static void main(String[] args) {
       if(args.length == 1){
            fillModelWithData(args[0]);
            makeGui(args);
        }else if(args.length==6){
            WithParameters.RunWithParameters(args);
        }else{
            System.err.println("Wrong parameters count");
        }
    }

    private static void fillModelWithData(String position){
        File rFile = new File("models/"+position+".model");
        try {
            Scanner sc = new Scanner(rFile);
            if (sc.hasNextLine()){
                String r = sc.nextLine();
                mainModel.setClassifier(r);
            }
            if(sc.hasNextLine()){
                String r = sc.nextLine();
                mainModel.setAlgorithm(r);
            }
            if(mainModel.getClassifier().equals("NaiveBayes")){
                if(sc.hasNextLine()){
                    String r = sc.nextLine();
                    String[] arr = r.trim().split("\\s+");
                    mainModel.setUniqueWords(Integer.parseInt(arr[1]));
                }
                HashMap<String,Double> map = new HashMap<>();
                while(sc.hasNextLine()){
                    String r = sc.nextLine();
                    if(r.equals("NEXT")){
                        break;
                    }
                    String[] arr = r.trim().split("\\s+");
                    map.put(arr[0],Double.parseDouble(arr[1]));
                }
                mainModel.setTotalWordsInClass(map);
            }else {
                if (sc.hasNextLine()){
                    String r = sc.nextLine();
                }
            }
            while (sc.hasNextLine()){
                loadTrainData(sc);
            }
            System.out.println("Hotovo");


        } catch (FileNotFoundException e) {
            System.err.println("Model "+position+" was not found.");
            System.exit(1);
        }

    }

    private static void loadTrainData(Scanner sc) {
        LoadFromFile file = new LoadFromFile("NoName");
        String tmp = sc.nextLine();
        String[] arr = tmp.trim().split("\\s+");
        file.setResult(arr);
        tmp = sc.nextLine();
        file.setText(tmp);

        HashMap<String, Double> map = new HashMap<>();
        while(sc.hasNextLine()){
            String s = sc.nextLine();
            if(s.equals("NEXT")){
                file.setMap(map);
                trData.add(file);
                return;
            }
            String[] pole = s.trim().split("\\s+");
            map.put(pole[0],Double.parseDouble(pole[1]));
        }

    }


    private static void makeGui(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("UIR_SEM - A18B0180P - Martin Brožek");

        JPanel top = makeTopGui(args[0]);
        JPanel bottom = makeBottomGui();
        JPanel center = makeCenterGui();

        frame.setLayout(new BorderLayout());
        frame.add(center,BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.add(top, BorderLayout.NORTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel makeTopGui(String nameOfModel) {
        JPanel top = new JPanel();
        JLabel lText = new JLabel("Name of model: "+nameOfModel, SwingConstants.CENTER);
        lText.setFont(new Font("Calibri", Font.BOLD, 20));
        top.add(lText);
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return top;
    }

    private static JPanel makeBottomGui() {
        JPanel bottom = new JPanel();
        JButton btnExit = new JButton("Exit");
        bottom.setBackground(Color.WHITE);
        bottom.add(btnExit);
        bottom.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnExit.addActionListener(e -> System.exit(0));

        return bottom;
    }

    private static JPanel makeCenterGui() {
        JPanel center = new JPanel();
        center.setPreferredSize(new Dimension(600,400));
        TextArea area = new TextArea();
        area.setPreferredSize(new Dimension(200,200));

        JLabel lResult = new JLabel("Result: -------", SwingConstants.CENTER);
        JLabel infoText = new JLabel("Text here:", SwingConstants.CENTER);
        infoText.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        lResult.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        JButton btnGo = new JButton("GO");

        center.setLayout(new BorderLayout());
        center.add(infoText, BorderLayout.NORTH);
        center.add(area, BorderLayout.CENTER);


        JPanel botCenter = new JPanel();
        JButton btnDelete =  new JButton("Delete");
        botCenter.setLayout(new BorderLayout());
        botCenter.add(btnDelete, BorderLayout.EAST);
        botCenter.add(btnGo, BorderLayout.CENTER);
        botCenter.add(lResult,BorderLayout.SOUTH);
        center.add(botCenter, BorderLayout.SOUTH);
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                lResult.setText("Result: -------");
            }
        });
        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(area.getText().length()!=0){
                    LoadFromFile file = new LoadFromFile("Test");
                    file.setText(area.getText());
                    String tmpResult = getResult(file);
                    lResult.setText("Result: "+tmpResult);
                }
            }
        });

        return center;
    }

    private static String getResult(LoadFromFile file) {
        switch (mainModel.getAlgorithm()){
            case "TF":
                TF.getTFFeatures(file);
                break;
            case "TFIDF":
                TFIDF.getTFIDFFeatures(file,trData);
                break;
            case "Binary":
                Binary.getBinaryFeatures(file);
                break;
            default:
                System.err.println("Error");
        }
        switch (mainModel.getClassifier()){
            case "NaiveBayes":
                String tmpStr = NaiveBayes.NaiveBayes(trData, file, mainModel.getTotalWordsInClass(),mainModel.getUniqueWords());
                return tmpStr;

            case "Knn":
                String tmpKnn = Knn.Knn(trData,file);
                return tmpKnn;

            default:
                System.err.println("Error");
        }
        return "Error";
    }
}
