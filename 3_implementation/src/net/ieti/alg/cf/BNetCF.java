package net.ieti.alg.cf;

import java.util.Set;

import net.hudup.core.alg.Alg;
import net.hudup.core.alg.KBase;
import net.hudup.core.alg.RecommendParam;
import net.hudup.core.alg.cf.ModelBasedCF;
import net.hudup.core.data.RatingVector;


/**
 * This class represents a collaborative filtering algorithm based on Bayesian network.
 * 
 * @author ShahidNaseem, Anum Shafiq, Loc Nguyen
 * @version 1.0
 *
 */
public class BNetCF extends ModelBasedCF {

	
	/**
	 * Serial version UID for serializable class.
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public RatingVector estimate(RecommendParam param, Set<Integer> queryIds) {
		//Coding here, estimating missing values.
		return null;
	}

	
	@Override
	public RatingVector recommend(RecommendParam param, int maxRecommend) {
		//Coding here, recommending items.
		return null;
	}

	
	@Override
	public KBase createKB() {
		// TODO Auto-generated method stub
		return new BnetKB();
	}

	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "IETI.bayesnet";
	}

	@Override
	public Alg newInstance() {
		// TODO Auto-generated method stub
		return new BNetCF();
	}

	
}
