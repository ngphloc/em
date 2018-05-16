/**
 * 
 */
package net.ieti.alg.cf.bn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	
	/**
	 * Loading Bayesian network from storage.
	 * @param in specified input stream.
	 * @throws IOException if any IO error raises.
	 */
	void load(InputStream in) throws IOException;
	
	
	/**
	 * Saving Bayesian network to storage.
	 * @param out specified output stream.
	 * @throws IOException if any IO error raises.
	 */
	void save(OutputStream out) throws IOException;
	
	
}
