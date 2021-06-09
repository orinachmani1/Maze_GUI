package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private String tempDirectoryPath;
    public ServerStrategySolveSearchProblem() {
        tempDirectoryPath=System.getProperty("java.io.tmpdir");
    }

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fc = new ObjectInputStream(inputStream);
            ObjectOutputStream tc = new ObjectOutputStream(outputStream);
            tc.flush();
            Maze getMaze;
            getMaze=(Maze)fc.readObject();

            String fileP=tempDirectoryPath+getMaze.toString().hashCode()+"maze";
            File file=new File(fileP);
            if(!file.exists()){
                SearchableMaze is= new SearchableMaze(getMaze);
                Configurations configurations = Configurations.getInstance();
                String algoName = configurations.SoltuionAlgoName();
                ASearchingAlgorithm SearchAlgo;
                if (algoName.equals("BreadthFirstSearch")) {
                    SearchAlgo= new BreadthFirstSearch();
                } else if (algoName.equals("DepthFirstSearch")) {
                    SearchAlgo= new DepthFirstSearch();
                } else if (algoName.equals("BestFirstSearch") ){
                    SearchAlgo= new BestFirstSearch();
                } else {
                    SearchAlgo= null;
                }

                Solution solution;
                solution=SearchAlgo.solve(is);
                tc.flush();
                tc.writeObject(solution);
                FileOutputStream fileO = new FileOutputStream(fileP);
                ObjectOutputStream mazeS=new ObjectOutputStream(fileO);
                mazeS.writeObject(solution);
                //System.out.println("aTesta");
                mazeS.close();
            }
            else{
                FileInputStream fileN = new FileInputStream(fileP);
                ObjectInputStream mazeAgain=new ObjectInputStream(fileN);
                tc.writeObject((Solution)mazeAgain.readObject());

                mazeAgain.close();

            }
            fc.close();
            tc.close();




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}