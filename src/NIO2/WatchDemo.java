package NIO2;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchDemo {

	public static void main(String[] args) throws Exception {
		WatchService ws = FileSystems.getDefault().newWatchService();
		Path p = Paths.get("C:");
		WatchKey k = p.register(ws, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		while (true) {
			WatchKey key = ws.take();
			for (WatchEvent<?> e : key.pollEvents()) {
				WatchEvent.Kind kind = e.kind();
				if(kind==StandardWatchEventKinds.OVERFLOW){
					continue;
				}
				WatchEvent<Path> currEvent=(WatchEvent<Path>)e;
				Path path=currEvent.context();
				System.out.println(path+"发生"+kind);
			}
			key.reset();
		}
		//ws.close();
	}

}
