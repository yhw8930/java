package NIO2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsynReadDemo {

	private static class Attachment {
		public Path path;
		public ByteBuffer buffer;
		public AsynchronousFileChannel channel;
	}

	private static class ReadHandler implements
			CompletionHandler<Integer, Attachment> {

		@Override
		public void completed(Integer result, Attachment attachment) {
			byte[] data = attachment.buffer.array();
			System.out.println(new String(data));
			try {
				attachment.channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void failed(Throwable exc, Attachment attachment) {
			// TODO Auto-generated method stub

		}

	}

	public static void main(String[] args) throws Exception {
		Path path = Paths.get("aw.txt");
		AsynchronousFileChannel afc = AsynchronousFileChannel.open(path,
				StandardOpenOption.READ);
		ReadHandler handler = new ReadHandler();
		ByteBuffer buffer = ByteBuffer.allocate((int) afc.size());
		Attachment a = new Attachment();
		a.path = path;
		a.buffer = buffer;
		a.channel = afc;

		afc.read(buffer, 0, a, handler);
	}

}
