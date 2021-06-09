package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    public ServerStrategyGenerateMaze(){};
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            ByteArrayOutputStream b = new ByteArrayOutputStream();

            Object mazeSizeObj = fromClient.readObject();
            int[] mazeSize = (int[])mazeSizeObj;
            //IMazeGenerator generator = Configurations.g
            //IMazeGenerator generator = new MyMazeGenerator();//TODO enable according to configuration
            AMazeGenerator generator = Configurations.getGeneratingAlgorithm();

            Maze maze = generator.generate(mazeSize[0],mazeSize[1]);
            byte[] mazeAsBytes = maze.toByteArray();
            MyCompressorOutputStream myCompressor = new MyCompressorOutputStream(b);
            myCompressor.write(mazeAsBytes);

            toClient.writeObject(b.toByteArray());
            toClient.flush();
            //System.out.println("maze generated");
            fromClient.close();
            toClient.close();
            b.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
