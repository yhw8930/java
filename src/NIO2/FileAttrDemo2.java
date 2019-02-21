package NIO2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Map;

public class FileAttrDemo2 {

	public static void main(String[] args) throws Exception {
		Path p = Paths.get("f2.txt");
		System.out.println(Files.getAttribute(p, "dos:readonly"));
		Files.setAttribute(p, "lastModifiedTime", FileTime.from(Instant.now()));

		BasicFileAttributes fa = Files.readAttributes(p,
				BasicFileAttributes.class);
		Map<String, Object> fa1 = Files.readAttributes(p,
				"size,lastModifiedTime");
		fa1.forEach((k, v) -> {
			System.out.println(k + ":" + v);
		});

		BasicFileAttributeView bfv = Files.getFileAttributeView(p,
				BasicFileAttributeView.class);
		BasicFileAttributes bf=bfv.readAttributes();
		
	}
}
