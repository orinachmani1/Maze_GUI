package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;

public class Maze3DState extends AState {
    Position3D curPosition;

    public Maze3DState(Position3D pos){
        super(pos.toString());
        curPosition = new Position3D(pos);
    }

    public Maze3DState(Position3D pos, AState father){
        this.curPosition = new Position3D(pos);
        setStateName(curPosition.toString());
        this.setMyFather(father);
    }

    public Position3D getCurPosition() {
        return curPosition;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null){return false;}
        if (!(obj instanceof MazeState)){return false;}
        return ((Maze3DState)obj).curPosition.equals(curPosition);
    }

    @Override
    public String toString() {
        return curPosition.toString();
    }
}
