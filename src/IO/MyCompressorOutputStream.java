package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyCompressorOutputStream extends OutputStream {

    OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        byte[] finalCompressedMaze = compress(b);
        for (int i = 0; i < finalCompressedMaze.length ; i++) {
            out.write(finalCompressedMaze[i]);
        }

        //out.write(finalCompressedMaze);
    }

    public byte[] compress(byte[] mazeData) {

        ArrayList<Byte> compressedMazeData = new ArrayList();

        for (int i = 0; i < 12; i++)
        {
            compressedMazeData.add(mazeData[i]);
        }

        int index = 12;
        int countEight = 0;
        Integer sum=0;

        for (int i = index; i < mazeData.length; i++) {
            byte curCell = mazeData[i];//[00000001]/[00000000]
            countEight++;
            if (curCell>0){
                sum+=(int) Math.pow(2,(8-countEight));
            }

            if(countEight==8)
            {
                compressedMazeData.add(getDecValue(sum).byteValue());
                //System.out.println(i + " : " + sum);
                countEight=0;
                sum=0;
            }
        }
        if (countEight > 0 && countEight < 8)
        {
            compressedMazeData.add(getDecValue(sum).byteValue());
        }

        byte[] compressedMaze = convertToBytesArray(compressedMazeData);

        return compressedMaze;
    }

    private byte[] convertToBytesArray(ArrayList<Byte> compressedMazeData) {

        int size =compressedMazeData.size();
        byte[] toReturn = new byte[size];
        for (int i = 0; i < size; i++) {
            toReturn[i] = compressedMazeData.get(i);
        }
        return toReturn;
    }

    public Integer getDecValue(Integer num)
    {
        return num & 0xFF;
    }
}
