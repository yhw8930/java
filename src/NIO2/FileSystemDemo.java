package NIO2;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FileSystemDemo {

	public static void main(String[] args) throws Exception {
		FileSystem fs = FileSystems.getDefault();
		System.out.println(fs.isReadOnly());
		System.out.println(fs.getSeparator());
		for (FileStore s : fs.getFileStores()) {
			System.out.printf("%s,%s,%s,%s,%s \n", s.name(), s.getTotalSpace(),
					s.getUnallocatedSpace(), s.getUsableSpace(), s.type());
		}

		for (Path p : fs.getRootDirectories()) {
			System.out.println(p);
		}
	}

}
