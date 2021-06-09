package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    Position curPosition;

    public MazeState(Position pos){
        super(pos.toString());
        curPosition = new Position(pos);
    }

    public MazeState(int row, int col, double cost, AState father){
        this.curPosition = new Position(row,col);
        this.stateName = curPosition.toString();
        this.setCost(cost);
        this.setMyFather(father);
    }

    public MazeState(Position pos, double cost, AState father){
        this.curPosition = new Position(pos);
        this.stateName = curPosition.toString();
        this.setCost(cost);
        this.setMyFather(father);
    }

    public Position getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(Position curPosition) {
        this.curPosition = curPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){return false;}
        if (!(obj instanceof MazeState)){return false;}
        return ((MazeState)obj).curPosition.equals(curPosition);
    }

    @Override
    public String toString() {
        return curPosition.toString();
    }
}
