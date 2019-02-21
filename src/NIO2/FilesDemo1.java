package NIO2;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesDemo1 {

	public static void main(String[] args) throws Exception {
		Path p1=Paths.get("f8.txt");
		if(Files.notExists(p1)){
			Files.createFile(p1);
		}
		//
		
		Path p2=Paths.get("d1/d2");
		//Files.createDirectories(p2);
		
		System.out.println(Files.createTempFile("ttt", ".tmp").toRealPath());
		
		//Files.createSymbolicLink(Paths.get("C:\\f8.txt"), Paths.get("D:\\java\\java8s4\\f8.txt"));
	
		System.out.println(Files.exists(Paths.get("C:\\f8.txt")));
	}

}
