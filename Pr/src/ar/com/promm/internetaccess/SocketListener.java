package ar.com.promm.internetaccess;

import java.io.BufferedReader;
import java.net.Socket;

public class SocketListener implements Runnable {

	private Socket socket;
	private CommandsQueue commandsQueue;

	public SocketListener(CommandsQueue commandsQueue, Socket s) {
		this.commandsQueue = commandsQueue;
		this.socket = s;
	}

	@Override
	public void run() {
		boolean killme = false;
		while (!killme) {
			BufferedReader input;
			try {
				input = SocketsManager.getBufferedReader(socket);
				String res = new String();
				res = SocketsManager.readFromBufferedReader(input);
				commandsQueue.textReceived(res);
				System.out.println("AWANTA1");
			} catch (Exception e) {
				commandsQueue.connectionLost();
				System.out.println("stacktrace de conexion perdida:");
				e.printStackTrace();
				killme=true;
			}
		}
	}

}
