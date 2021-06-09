package algorithms.mazeGenerators;

import algorithms.mazeGenerators.IMazeGenerator;

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long start = System.currentTimeMillis();
        Maze generate = generate(rows, cols);
        long end = System.currentTimeMillis();
        return end-start;
    }

}
