/**
 * 
 */
package net.ieti.alg.cf.bn;

/**
 * This interface represents a factory to create Bayesian network and other relevant objects.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface Factory {

	
	/**
	 * Create Bayesian network.
	 * @return Bayesian network.
	 */
	BNet createNetwork();
	
	
	/**
	 * Create a node in Bayesian network.
	 * @return a node in Bayesian network.
	 */
	BNode createNode();
	
	
}
