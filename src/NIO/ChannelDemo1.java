package NIO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class ChannelDemo1 {

	public static void main(String[] args) throws Exception {
		FileInputStream fis=new FileInputStream("");
		ReadableByteChannel c1=Channels.newChannel(fis);
		
		InputStream is=Channels.newInputStream(c1);
		
		FileChannel c2=fis.getChannel();//只读
		RandomAccessFile f=new RandomAccessFile("", "r");
		//f.get
	}

}
