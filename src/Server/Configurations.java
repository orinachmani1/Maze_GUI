package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Configurations {

    private static Configurations instance = null;
    static Properties properties = new Properties();
    private static InputStream inputStream;
    OutputStream outputStream;


    public static Configurations getInstance(){
        if (instance==null)
        {
            try {
                instance=new Configurations();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    private  Configurations() throws IOException {

        outputStream = new FileOutputStream("resources/config.properties");
        properties.setProperty(("threadPoolSize"), ("5"));
        properties.setProperty(("mazeGeneratingAlgorithm"),("MyMazeGenerator"));
        properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
        properties.store(outputStream,null);
    }
    public static AMazeGenerator getGeneratingAlgorithm() throws IOException {
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        String GeneratingAlgorithm= properties.getProperty("mazeGeneratingAlgorithm");

        if (GeneratingAlgorithm.equals("SimpleMazeGenerator")){
            return new SimpleMazeGenerator();
        }

        else if(GeneratingAlgorithm.equals("EmptyMazeGenerator")){
            return new EmptyMazeGenerator();
        }

        else
            {return new MyMazeGenerator();}
    }
    public String SoltuionAlgoName(){
        return properties.getProperty("mazeSearchingAlgorithm");
    }
    public static ISearchingAlgorithm getSearchingAlgorithm() throws IOException {
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        String searchAlgo= properties.getProperty("mazeSearchingAlgorithm");

        if(searchAlgo.equals("DeptFirstSearch")){
            return new DepthFirstSearch();
        }
        else if (searchAlgo.equals("BreadthFirstSearch")){
            return new BreadthFirstSearch();
        }
        else{
            return new BestFirstSearch();
        }
    }

    public static int numberOfThreads() {
        try {
            inputStream = new FileInputStream("resources/config.properties");
            properties.load(inputStream);
            String numOfThreads = properties.getProperty("threadPoolSize");
            return Integer.parseInt(numOfThreads);
        }
        catch (Exception E){
        }
        return 1;
    }


}