package net.ieti.alg.cf.bn;

import java.util.List;

/**
 * This interface represents a most abstract node Bayesian network.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface BNode {

	
	/**
	 * Setting parents nodes.
	 * @param parentNodes specified parent nodes.
	 */
	void setParents(BNode...parentNodes);
	
	
	/**
	 * Getting parent nodes.
	 * @return list of parent nodes.
	 */
	List<BNode> getParents();
	
	
	/**
	 * Getting child nodes.
	 * @return list of child nodes.
	 */
	List<BNode> getChildren();
	
	
	/**
	 * Setting conditional probability table (CPT).
	 * @param probs conditional probability table.
	 */
	void setProbs(double...probs);
	
	
	/**
	 * Getting conditional probability table (CPT).
	 * @return conditional probability table (CPT).
	 */
	double[] getProbs();
	
	
}
