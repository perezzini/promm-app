package ar.com.promm;

public final class VarAccess<A> {
	private A var;
	

	public A get() {
		return var;
	}

	public void set(A var) {
		this.var = var;
	}

}
