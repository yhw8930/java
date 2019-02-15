package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingClient {

	public static void main(String[] args) throws Exception {
		InetAddress address = InetAddress.getByName("localhost");
		int port = 9000;
		InetSocketAddress serverAddress = new InetSocketAddress(address, port);
		Selector selector = Selector.open();
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(serverAddress);
		channel.register(selector, SelectionKey.OP_CONNECT
				| SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		while (true) {
			if (selector.select() > 0) {
				boolean status = processReadySet(selector.selectedKeys());
				if (status) {
					break;
				}
			}
		}
	}

	public static boolean processReadySet(Set<SelectionKey> keys)
			throws Exception {
		Iterator<SelectionKey> iterator = keys.iterator();
		SelectionKey key = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			iterator.remove();
			if (key.isConnectable()) {
				processConnect(key);
			}
			if (key.isReadable()) {

			}
			if (key.isWritable()) {
				BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
				
				processWrite(key, reader.readLine());
			}
		}
		return false;
	}

	public static boolean processConnect(SelectionKey key) {
		SocketChannel c = (SocketChannel) key.channel();
		try {
			while (c.isConnectionPending()) {
				c.finishConnect();
			}
		} catch (IOException e) {
			key.cancel();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String processRead() {
		return "";
	}

	public static void processWrite(SelectionKey key, String msg)
			throws Exception {
		SocketChannel c = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
		c.write(buffer);
	}
}
