package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;
    //ArrayList<Byte> mazeData;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {

        if (b==null){return 1;}

        byte[] copy = new byte[b.length];
        this.in.read(copy);

        for (int i = 0; i <12 ; i++) {
            b[i]=copy[i];
        }

        int rows = b[0]*127+b[1];
        int cols = b[2]*127+b[3];
        int size = ((rows*cols)/8 + 1);

        ArrayList<int[]> cells = new ArrayList<>();

        for (int i = 12; i < size + 12 ; i++) {
            byte curByte = copy[i];
            int[] bits = getBits(curByte);
            cells.add(bits);
        }

        int index=12;
        for (int i = 0; i < cells.size()-1 ; i++) {
            for (int j = 0; j < cells.get(i).length ; j++) {
                b[index] = (byte) cells.get(i)[j];
                index++;
            }
        }
        return 0;
    }

    public int[] getBits(byte curByte) {
        int[] bitsList = new int[8];
        int x = (int)curByte;
        if(x<0){x+=256;}

        int index=7;
        while (x > 0)
        {
           int tmp = x%2;
           x /= 2;
           bitsList[index] = tmp;
           index--;
        }

        return bitsList;

    }
}
