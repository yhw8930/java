package NIO2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;

public class FilesDemo2 {

	public static void main(String[] args) throws Exception {
		Path src=Paths.get("f2.txt");
		Path des=Paths.get("C:\\new.txt");
		Files.copy(src, des,StandardCopyOption.REPLACE_EXISTING);
		FileTime t=Files.getLastModifiedTime(src);
		System.out.println(t.toMillis());
		System.out.println(t);
		
		System.out.println(Files.probeContentType(src));
		
		Files.lines(src).forEach(System.out::println);
		
		String s=new String(Files.readAllBytes(src));
		System.out.println(s);
		System.out.println("--------------------------------");
		Files.readAllLines(src).forEach(System.out::println);
		
		Files.write(src, "Hello11".getBytes(),StandardOpenOption.APPEND);
	}

}
