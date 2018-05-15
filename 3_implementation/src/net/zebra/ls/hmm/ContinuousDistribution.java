/**
 * 
 */
package net.zebra.ls.hmm;

/**
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public abstract class ContinuousDistribution extends AtomicDistribution {


	/**
	 * 
	 */
	protected double epsilon = Util.PROB_EPSILON;
	
	
	/**
	 * 
	 */
	public ContinuousDistribution() {
		super();
	}
	

	/**
	 * 
	 * @return
	 */
	public double getEpsilon() {
		// TODO Auto-generated method stub
		return epsilon;
	}

	
	/**
	 * 
	 * @param epsilon
	 */
	public void setEpsilon(double epsilon) {
		// TODO Auto-generated method stub
		this.epsilon = epsilon;
	}

	
}
