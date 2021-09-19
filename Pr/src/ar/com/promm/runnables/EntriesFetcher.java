package ar.com.promm.runnables;

import org.json.JSONException;

import ar.com.promm.datastructures.Command;
import ar.com.promm.interfaces.ArrayParser;
import ar.com.promm.interfaces.EntriesUpdater;
import ar.com.promm.internetaccess.CommandsQueue;
import ar.com.promm.internetaccess.ICommandCallback;
import ar.com.promm.parsers.JSonParser;

public class EntriesFetcher<A> implements Runnable {
	private Command command;
	private EntriesUpdater<A> updateEntries;
	private ArrayParser<A> parser;
	
	
	
	public EntriesFetcher(EntriesUpdater<A> updateEntries, Command command, ArrayParser<A> parser) {
		this.parser=parser;
		this.command=command;
		this.updateEntries=updateEntries;
	}
	

	@Override
	public void run() {
		CommandsQueue.getInstance().queuecmd(command, new ICommandCallback() {
			
			@Override
			public void success(String s) {
				try {
					String cursor = JSonParser.parseCursor(s);
					updateEntries.update(parser.parse(s), cursor);
				} catch (JSONException e) {
					fail();
				}
			}
			
			@Override
			public void fail() {
				updateEntries.update(null, null);
			}
		});
	}
	
}
