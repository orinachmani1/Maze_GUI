package IO;

import java.io.IOException;
import java.io.InputStream;
public class SimpleDecompressorInputStream extends  InputStream{
    InputStream in;
    @Override
    public int read() throws IOException {
        return 0;
    }
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }
    public int read(byte[] b) throws IOException {
        byte[] readedArray = new byte[b.length];
        this.in.read(readedArray);
        byte test= 0;
        int index=12;
        byte firstByteType;
        for (int i = 0; i<12;i++)
        {
            b[i]=readedArray[i];
        }
        firstByteType=readedArray[12];
        if(firstByteType==1)
        { test=1;}

//        for (int i=13; i<readedArray.length;i++)
        int i =13;
        while (readedArray[i]!=0)
        {
            for (int j=0;j<readedArray[i];j++)
            {
                b[index]=test;
                index++;
            }
            i++;
            if (test==0){test=1;}
            else{test=0;}
        }

        return 0;
    }
}
