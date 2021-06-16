package View;

import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MazeDisplayer extends Canvas {
    private int[][] maze;
    private Solution solution;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;

    private int goalRow;
    private int goalCol;

    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameGoal = new SimpleStringProperty();
    StringProperty imageFileNameWinner = new SimpleStringProperty();
    StringProperty imageFileNamePath = new SimpleStringProperty();
    public boolean winner = false;

    public MazeDisplayer()
    {
        setImageFileNamePlayer("Resources/boy.jpg");
        setImageFileNameWall("Resources/nemo.PNG");
        //setImageFileNameWall("Resources/shark.jpg");
        setImageFileNameGoal("Resources/goal.png");
        setImageFileNameWinner("Resources/winner.jpg");
        setImageFileNamePath("Resources/green.PNG");
    }

    private void setImageFileNamePath(String imageFileNamePath) {
        this.imageFileNamePath.set(imageFileNamePath);
    }

    private void setImageFileNameWinner(String imageFileNameWinner) {
        this.imageFileNameWinner.set(imageFileNameWinner);
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    private String getImageFileNameGoal() { return imageFileNameGoal.get();}

    private String getImageFileNameWinner() {
        return this.imageFileNameWinner.get();
    }
    private String getImageFileNamePath() { return this.imageFileNamePath.get();}



//    public String imageFileNameWallProperty() {
//        return imageFileNameWall.get();
//    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

//    public String imageFileNamePlayerProperty() {
//        return imageFileNamePlayer.get();
//    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }


    public void drawMaze(int[][] maze) {
        this.maze = maze;
        draw();
    }

    public void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            if(!this.winner)
            {
                drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
                if(solution != null)
                    drawSolution(graphicsContext, cellHeight, cellWidth);
                drawGoal(graphicsContext, cellHeight, cellWidth);
                drawPlayer(graphicsContext, cellHeight, cellWidth);
            }
            /*else
                {
                    //System.out.println("winner image");
                    drawWinnerScreen(graphicsContext, cellHeight, cellWidth);
                    this.winner = false;
                }*/

        }
    }

    /*private void drawWinnerScreen(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getGoalCol() * cellWidth;
        double y = getGoalRow() * cellHeight;
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        //graphicsContext.setFill(Color.GREEN);

        Image winnerImage = null;
        try {
            winnerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(winnerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            System.out.println("winner photo");
            //graphicsContext.drawImage(winnerImage, x, y, cellWidth, cellHeight);
            graphicsContext.drawImage(winnerImage,canvasHeight,canvasWidth);
            graphicsContext.drawImage(winnerImage,x * cellWidth, y * cellHeight, cellWidth, cellHeight);
    }*/



    private void drawGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {

        int row = getGoalRow();
        int col = getGoalCol();
        double x = getGoalCol() * cellWidth;
        double y = getGoalRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image goalImage = null;
        try {
            goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight);
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        // need to be implemented
        //TODO
        double x = getGoalCol() * cellWidth;
        double y = getGoalRow() * cellHeight;
        Solution tmp = this.solution;
        ArrayList<AState> solutionList =  tmp.getSolutionPath();

        int playerCurrentRow = this.playerRow;
        int playerCurrentCol = this.playerCol;

        int goalRow = this.goalRow;
        int goalCol = this.goalCol;
        String goalState= String.format("{%d,%d}", goalRow, goalCol);

        Image pathImage = null;
        try {
            pathImage = new Image(new FileInputStream(getImageFileNamePath()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(pathImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(pathImage, x, y, cellWidth, cellHeight);

        int rows = this.maze.length;
        int cols = this.maze[0].length;
        String curState;
        for (int row = 0; row < rows ; row++) {
            for (int col = 0; col < cols; col++) {
                curState = String.format("{%d,%d}", row, col);
                if ((row != playerCurrentRow) || (col != playerCurrentCol))
                {
                    boolean partOfSolution = checkState(solutionList,curState);
                    boolean isGoal = curState.equals(goalState);
                    if (partOfSolution && !isGoal) {
                        graphicsContext.drawImage(pathImage, col * cellWidth, row * cellHeight, cellWidth, cellHeight);
                    }
                }
//                else if((characterStratPositionRow != r)||(characterStartPositionColumn != c)) {
//                    graphicsContext.setFill(Color.BLUE);
//                    graphicsContext.fillRect(characterStartPositionColumn * cellWidth, characterStratPositionRow * cellHeight, cellWidth, cellHeight);
//                }
            }
        }

//        System.out.println("drawing solution...");
    }



    private boolean checkState(ArrayList<AState> solutionList, String curState) {

        LinkedList<String> solutionStates = new LinkedList<String>();
        for (int i = 0; i < solutionList.size(); i++ )
        {
            String state = solutionList.get(i).toString();
            //System.out.println(state);
            solutionStates.add(state);
        }
        solutionStates.removeFirst();
        boolean found = solutionStates.contains(curState);
        return found;
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);

        Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(maze[i][j] == 1){
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void setGoalRow(int row)
    {
        this.goalRow = row;
    }

    public void setGoalCol(int col)
    {
        this.goalCol = col;
    }

    public int getGoalRow()
    {
        return this.goalRow;
    }

    public int getGoalCol()
    {
        return this.goalCol;
    }
    public void Zoom(){
        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double zoom_fac = 1.05;
                double delta_y = scrollEvent.getDeltaY();
                if(delta_y < 0) {
                    zoom_fac = 2.0 - zoom_fac;
                    setScaleX(getScaleX()*zoom_fac);
                    setScaleY(getScaleY()*zoom_fac);
                }
                else
                {
                    zoom_fac = 1.05;
                    setScaleX(getScaleX()*zoom_fac);
                    setScaleY(getScaleY()*zoom_fac);
                }
                scrollEvent.consume();
            }
        });
    }

}
