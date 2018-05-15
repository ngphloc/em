package net.ieti.alg.cf.bn;

import net.hudup.core.data.Fetcher;
import net.hudup.core.data.Profile;

/**
 * This interface represents a learning algorithm for Bayesian network.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface BLearning {

	
	/**
	 * The main method learns or create Bayesian network from training data.
	 * @param input specified training data.
	 * @param param additional parameter.
	 * @return Bayesian network from specified training data.
	 */
	BNet learn(Fetcher<Profile> input, Object param);
	
	
}
