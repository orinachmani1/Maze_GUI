package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start = System.currentTimeMillis();
        Maze3D generate = generate(depth,row, column);
        long end = System.currentTimeMillis();

        return end-start;
    }
}
