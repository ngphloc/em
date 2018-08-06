package net.hudup.em;

import net.hudup.core.alg.TestingAlg;


/**
 * <code>EM</code> is the most abstract interface for all expectation maximization (EM) algorithm.
 * Its main method is {@link #learn()} used to learn parameters.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface EM extends TestingAlg {

	
	/**
	 * Maximum number of iterations.
	 */
	final static int EM_MAX_ITERATION = 10000;
	
	
	/**
	 * Default epsilon for terminated condition, which is the bias between current parameter and estimated parameter. 
	 */
	final static double EM_DEFAULT_EPSILON = 0.001;
	
	
	/**
	 * Getting current iteration.
	 * @return current iteration. Return 0 if the algorithm does not run yet or run failed. 
	 */
	int getCurrentIteration();
	
	
	/**
	 * Getting current parameter.
	 * @return current parameter. Return null if the algorithm does not run yet or run failed. 
	 */
	Object getCurrentParameter();
	
	
	/**
	 * Getting estimated parameter.
	 * @return estimated parameter. Return null if the algorithm does not run yet or run failed. 
	 */
	Object getEstimatedParameter();
	
	
}
