package NIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MapBufferDemo {

	public static void main(String[] args) throws Exception {
		FileInputStream fis=new FileInputStream("f2.txt");
		FileChannel c=fis.getChannel();
		MappedByteBuffer mbb=c.map(MapMode.READ_ONLY, 0, c.size());
		while(mbb.hasRemaining()){
			System.out.print((char)mbb.get());
		}
	}

}
