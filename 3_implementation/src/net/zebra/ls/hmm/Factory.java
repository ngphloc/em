/**
 * 
 */
package net.zebra.ls.hmm;

/**
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface Factory {
	
	
	/**
	 * Creating discrete hidden Markov model.
	 * 
	 * @param A
	 * @param PI
	 * @param B
	 * @return Discrete hidden Markov model
	 */
	HMM createDiscreteHMM(double[][] A, double[] PI, double[][] B);


	/**
	 * 
	 * @param nState
	 * @param mObs
	 * @return
	 */
	HMM createDiscreteHMM(int nState, int mObs);

	
	/**
	 * 
	 * @param A
	 * @param PI
	 * @param means
	 * @param variances
	 * @return
	 */
	HMM createNormalHMM(double[][] A, double[] PI, double[] means, double[] variances, double epsilon);

	
	/**
	 * 
	 * @param A
	 * @param PI
	 * @param means
	 * @return
	 */
	HMM createExponentialHMM(double[][] A, double[] PI, double[] means, double epsilon);

	
	/**
	 * 
	 * @param A
	 * @param PI
	 * @param means
	 * @param variances
	 * @return
	 */
	HMM createNormalMixtureHMM(double[][] A, double[] PI, double[][] means, double[][] variances, double[][] weights, double epsilon);
}
