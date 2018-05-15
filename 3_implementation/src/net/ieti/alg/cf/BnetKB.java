/**
 * 
 */
package net.ieti.alg.cf;

import net.hudup.core.alg.Alg;
import net.hudup.core.alg.KBaseAbstract;
import net.hudup.core.data.DataConfig;
import net.hudup.core.data.Dataset;
import net.ieti.alg.cf.bn.BNet;

/**
 * This class represents knowledge base for the collaborative filtering algorithm based on Bayesian network.
 * 
 * @author ShahidNaseem, Anum Shafiq, Loc Nguyen
 * @version 1.0
 *
 */
public class BnetKB extends KBaseAbstract {


	/**
	 * Serial version UID for serializable class.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Internal Bayesian network.
	 */
	protected BNet bnet = null;
	
	
	@Override
	public void learn(Dataset dataset, Alg alg) {
		// TODO Auto-generated method stub
		super.learn(dataset, alg);
		
		//Coding here, learning Bayesian network from dataset.
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.load();
		
		//Coding here, loading Bayesian network from file.
	}


	@Override
	public void export(DataConfig storeConfig) {
		// TODO Auto-generated method stub
		super.export(storeConfig);
		
		//Coding here, saving Bayesian network to file.
	}


	/**
	 * Getting Bayesian network.
	 * @return Bayesian network.
	 */
	public BNet getBNet() {
		return bnet;
	}
	
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return bnet != null;
	}

	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "IETI.bayesnet.kb";
	}


	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		bnet = null;
	}
	

}
