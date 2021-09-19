package ar.com.promm.internetaccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ar.com.promm.App;
import ar.com.promm.PrommCfg;
import ar.com.promm.Logger;
import ar.com.promm.datastructures.Command;

public class SocketsManager {


	private SocketsManager() {
	}

	private static IOException NULLSOCKET = new IOException("Null socket");

	private static IOException NOCONNETION = new IOException(
			"No hay conexión a internet");

	private static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) App.get()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static Socket createSocket() {
		Socket s = null;
		Logger.log("Attempting to create a Socket..");

		if (!isOnline()) {
			Logger.log("No hay conexión a internet");
			return null;
		}
		try {
			s = new Socket(PrommCfg.remoteHost, PrommCfg.remotePort);
			sendMessage(s, PrommCfg.HandShakeMessage);
			BufferedReader input = getBufferedReader(s);
			Logger.err("Server replies: " + readFromBufferedReader(input));
		} catch (UnknownHostException e) {
			Logger.err("Unknown host exception: " + e.getMessage());
		} catch (IOException e) {
			Logger.err("IO exception: " + e.getMessage());
		} catch (Exception e) {
			Logger.err("General exception (?!): " + e.toString());
		}
		Logger.log("Retornando socket");
		return s;
	}

	private static void sendMessage(Socket socket, String message)
			throws IOException {
		Logger.log("Attempting to send message: " + message + "");

		if (!isOnline())
			throw (NOCONNETION);

		if (socket == null)
			throw (NULLSOCKET);
		OutputStreamWriter osw = new OutputStreamWriter(
				socket.getOutputStream());
		osw.write(message+"\n");
		osw.flush();
		Logger.log("Message sent");
	}

	public static void sendCommand(Socket s, Command c) throws IOException {
		sendMessage(s, c.serialize());
	}
	
	public static BufferedReader getBufferedReader(Socket s) throws IOException {
		Logger.log("Trying to get BufferedReader");
		if (s == null)
			throw (NULLSOCKET);
		try {
			InputStreamReader isr = new InputStreamReader(s.getInputStream(),
					"UTF-8");
			return new BufferedReader(isr);
		} catch (Exception e) {
			Logger.log("ERROR: " + e.getMessage());
		}
		return null;
	}

	public static String readFromBufferedReader(BufferedReader input)
			throws IOException {
		String res = input.readLine();
		Logger.log("Reading from BufferedReader: " + res);
		return res;
	}
	
	public static boolean check(Socket socket) {
		return (socket != null && socket.isBound() && socket.isConnected() && !socket
				.isClosed());
	}


}
