package ar.com.promm.enums;

public final class Enums {
	
	public enum COMMANDTYPE {
		ALLSTORES("stores"), MORESTORES("fetch"), ALLPROMMS("all"), MOREPROMMS("fetch"), UPVOTE("vote"), DOWNVOTE("downvote"), SETID("setid");
		
		private String readable;
		private COMMANDTYPE(String s) {
			this.readable=s;
		}
		
		@Override
		public String toString() {
			return this.readable;
		}
	};

	public enum STATE {GETTINGMORE, FORCINGREFRESH, IDLE};
	
	public enum LANGUAGE {
		ES("ES");
		private String readable;
		private LANGUAGE(String s) {
			this.readable=s;
		}

		public String getMessage() {
			return this.readable;
		}
	};
	

}
