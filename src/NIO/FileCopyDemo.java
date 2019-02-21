package NIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopyDemo {

	public static void main(String[] args) throws Exception {
		try (FileChannel src = new FileInputStream("f2.txt").getChannel();
				FileChannel des = new FileOutputStream("f2_bak.txt")
						.getChannel()) {
			//src.transferTo(0, src.size(), des);
			des.transferFrom(src, 0, src.size());
		}
	}

}
