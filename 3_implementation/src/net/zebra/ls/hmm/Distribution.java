/**
 * 
 */
package net.zebra.ls.hmm;

/**
 * This interface represents a probability distribution.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface Distribution {

	
	/**
	 * Getting the defined probability at point x according to application.
	 * 
	 * @param x specified point.
	 * @return defined probability at point x according to application.
	 */
	double getProb(Obs x);
	
	
	/**
	 * 
	 * @param x
	 * @param kComp
	 * @return
	 */
	double getProb(Obs x, int kComp);
	
	
}
