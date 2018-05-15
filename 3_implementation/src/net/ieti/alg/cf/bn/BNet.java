/**
 * 
 */
package net.ieti.alg.cf.bn;

import java.util.List;

/**
 * This interface represents a most abstract Bayesian network.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface BNet {

	
	/**
	 * Adding root nodes.
	 * @param rootNodes root nodes.
	 */
	void addRootNodes(BNode...rootNodes);
	
	
	/**
	 * Getting root nodes.
	 * @return root nodes.
	 */
	List<BNode> getRootNodes();
	
	
}
