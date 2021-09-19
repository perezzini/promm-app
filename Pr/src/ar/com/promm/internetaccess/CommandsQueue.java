package ar.com.promm.internetaccess;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import ar.com.promm.IdRetriever;
import ar.com.promm.datastructures.Command;
import ar.com.promm.enums.Enums.COMMANDTYPE;


public class CommandsQueue {
	private Socket s;
	private static ConcurrentLinkedQueue<ICommandCallback> cmdCallbacksQueue = new ConcurrentLinkedQueue<ICommandCallback>();
	private static CommandsQueue _this;
	private Thread oldthread;

	private CommandsQueue () {
		createSocket();	
	}
	
	public synchronized static CommandsQueue getInstance() {
		if (_this == null)
			_this = new CommandsQueue();
		return _this;
	}
	
	private void createSocket() {
		if (this.oldthread != null)
			this.oldthread.interrupt();
		// interrupt pone en null el socket, asi que este es el orden correcto.
		s = SocketsManager.createSocket();
		this.oldthread = new Thread(new SocketListener(this,s));
		this.oldthread.start();
		Command setidcmd = new Command(COMMANDTYPE.SETID);
		setidcmd.setId(IdRetriever.getId());
		queueresponselesscmd(setidcmd);
	}
	
	public synchronized void queuecmd(Command c, ICommandCallback callback) {
		try {
			if (!SocketsManager.check(s)) {
				connectionLost();
				createSocket();
			}
			cmdCallbacksQueue.add(callback);
			SocketsManager.sendCommand(s, c);
		} catch (IOException e) {
			connectionLost();
		}
	}

	public synchronized void connectionLost() {
		System.out.println("Se pudrio la conexion");
		for (ICommandCallback cmdcallback : cmdCallbacksQueue) {
			cmdcallback.fail();
		}
		this.s=null;
		
	}

	public synchronized void textReceived(String res) {
		System.out.println("AWANTA");
		cmdCallbacksQueue.poll().success(res);
	}

	public void queueresponselesscmd(Command c) {
		queuecmd(c, new ICommandCallback() {
			
			@Override
			public void success(String s) {
			}
			
			@Override
			public void fail() {
			}
		});
		
	}

}
