/**
 * 
 */
package net.ieti.alg.cf;

import java.io.InputStream;
import java.io.OutputStream;

import net.hudup.core.alg.Alg;
import net.hudup.core.alg.KBaseAbstract;
import net.hudup.core.data.DataConfig;
import net.hudup.core.data.Dataset;
import net.hudup.core.data.Fetcher;
import net.hudup.core.data.Profile;
import net.hudup.core.logistic.UriAdapter;
import net.hudup.core.logistic.xURI;
import net.ieti.alg.cf.bn.BNet;
import net.ieti.alg.cf.bn.EMLearning;
import net.ieti.alg.cf.bn.Factory;
import net.ieti.alg.cf.bn.FactoryImpl;

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
	 * File extension of Bayesian network.
	 */
	public final static String BNET_FILEEXT = "bnet";
	
	
	/**
	 * Internal Bayesian network.
	 */
	protected BNet bnet = null;
	
	
	/**
	 * Default constructor.
	 */
	public BnetKB() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public void learn(Dataset dataset, Alg alg) {
		// TODO Auto-generated method stub
		super.learn(dataset, alg);
		
		//Modifying following code to learn Bayesian network from rating matrix.
		try {
			Fetcher<Profile> sample = dataset.fetchSample();
			sample.close();
			EMLearning learning = new EMLearning();
			bnet = learning.learn(sample, null);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.load();
		
		try {
			UriAdapter adapter = new UriAdapter(config);
			xURI bnetUri = getBNetUri();
			
			InputStream in = adapter.getInputStream(bnetUri);
			bnet = getBnetFactory().createNetwork();
			bnet.load(in);
			in.close();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}


	@Override
	public void export(DataConfig storeConfig) {
		// TODO Auto-generated method stub
		super.export(storeConfig);
		if (bnet == null)
			return;
		
		try {
			UriAdapter adapter = new UriAdapter(config);
			xURI bnetUri = getBNetUri();
			
			OutputStream out = adapter.getOutputStream(bnetUri, true);
			bnet.save(out);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Getting default factory to create Bayesian network.
	 * @return default factory to create Bayesian network.s
	 */
	protected Factory getBnetFactory() {
		return new FactoryImpl();
	}
	
	
	/**
	 * Getting Bayesian network.
	 * @return Bayesian network.
	 */
	public BNet getBNet() {
		return bnet;
	}
	
	
	/**
	 * Getting default URI of unit storing Bayesian network.
	 * @return default URI of unit storing Bayesian network.
	 */
	protected xURI getBNetUri() {
		return config.getStoreUri().concat(
				getName() + "." + BNET_FILEEXT);
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
