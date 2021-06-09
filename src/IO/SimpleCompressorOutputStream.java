package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    public OutputStream out;
    private int lastByte;
    private int counter=0;
    private int current;

    public SimpleCompressorOutputStream(OutputStream outStream) {
        this.out = outStream;
        lastByte = 0;
        counter=0;

    }
    public void write(int b){
        //Must be implemented

    }
    public void write(byte[] b) throws IOException {
        int sizeOfArray=13;
        int testPos=13;
        int addPos=13;
        byte current=b[testPos];
        //Need to fix the issue with the first one
        while(testPos<b.length)
        {
            if(b[testPos]!=b[testPos-1])
            {
                sizeOfArray++;
            }
            testPos++;
        }
        byte[] finalArray = new byte[sizeOfArray+1];

        for (int i = 0; i<12;i++)
        {
            finalArray[i]= b[i];
        }
        testPos=13;
        finalArray[12]=b[12];

        while(testPos<b.length)
        {
            if(b[testPos]==b[testPos-1])
            {
                counter++;
                if  (b.length-1==testPos)
                {
                    finalArray[addPos]=(byte)(counter+1);
                    testPos++;
                    continue;
                }
                testPos++;

            }
            else
            {
                finalArray[addPos]=(byte)(counter+1);
                counter=0;
                addPos++;
                testPos++;
            }
            if (b.length==testPos && b[b.length-1]==1 && b[b.length-2]==0 )
            {
                finalArray[addPos]=(byte)(1);

            }

        }
        out.write(finalArray);

    }
}
