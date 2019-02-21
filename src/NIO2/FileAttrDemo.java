package NIO2;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;

public class FileAttrDemo {

	public static void main(String[] args) throws Exception {
		Path p = Paths.get("C:");
		FileStore s = Files.getFileStore(p);
		System.out.println(s.supportsFileAttributeView(BasicFileAttributeView.class));
		System.out.println(s.supportsFileAttributeView(PosixFileAttributeView.class));
	}

}
