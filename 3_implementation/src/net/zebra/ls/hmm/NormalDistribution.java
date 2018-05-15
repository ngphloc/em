/**
 * 
 */
package net.zebra.ls.hmm;

import java.util.List;


/**
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public class NormalDistribution extends ContinuousDistribution {

	
	/**
	 * 
	 */
	protected org.apache.commons.math3.distribution.NormalDistribution norm;
	
	
	/**
	 * 
	 * @param mean
	 * @param variance
	 */
	public NormalDistribution(double mean, double variance) {
		super();
		setParameters(mean, variance);
	}
	
	
	/**
	 * 
	 */
	public NormalDistribution() {
		this(0, 1);
	}
	
	
	@Override
	public double getProb(Obs x) {
		// TODO Auto-generated method stub
		double value = ((MonoObs)x).value;
		double epsilon = getEpsilon();
		return norm.probability(value - epsilon, value + epsilon);
	}


	@Override
	public double getProb(Obs x, int kComp) {
		// TODO Auto-generated method stub
		return getProb(x);
	}


	@Override
	public void learn(List<Obs> O, List<Double> glist) {
		// TODO Auto-generated method stub
		int T = O.size() - 1;
		if (T < 0) return;

		double numerator1 = 0;
		double denominator = 0;
		List<Double> G = Util.newList(T+1);
		for (int t = 0; t <= T; t++) {
			double g = glist.get(t);
			numerator1 += g * ((MonoObs)(O.get(t))).value;
			denominator += g;
			G.add(g);
		}
		if (denominator == 0)
			return;
		double mean = numerator1/denominator;
		
		double numerator2 = 0;
		for (int t = 0; t <= T; t++) {
			double d = ((MonoObs)(O.get(t))).value - mean;
			numerator2 += G.get(t)*d*d;
		}
		double variance = numerator2/denominator;
		
		if (variance != 0)
			setParameters(mean, variance);
	}


	/**
	 * 
	 * @param mean
	 * @param variance
	 */
	public void setParameters(double mean, double variance) {
		this.norm = new org.apache.commons.math3.distribution.NormalDistribution(mean, Math.sqrt(variance));
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Normal distribution (mean=" + Util.DECIMAL_FORMAT + ", variance=" + Util.DECIMAL_FORMAT + ")", norm.getNumericalMean(), norm.getNumericalVariance());
	}
}
