/**
 * 
 */
package net.zebra.ls.hmm;

import java.util.List;
import java.util.Random;

/**
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public final class FactoryImpl implements Factory {
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	private static List<List<Double>> createMatrix(double[][] data) {
		List<List<Double>> matrix = Util.newList(data.length);
		for (int i = 0; i < data.length; i++) {
			List<Double> rowData = Util.newList(data[i].length);
			for (int j = 0; j < data[i].length; j++) {
				rowData.add(data[i][j]);
			}
			matrix.add(rowData);
		}
		
		return matrix;
	}
	
	
	/**
	 * 
	 * @author Loc Nguyen
	 * @version 1.0
	 *
	 */
	public static class HMMWrapper implements HMM {
		
		
		/**
		 * 
		 */
		protected DefaultHMM defaultHMM;
		
		
		/**
		 * 
		 * @param defaultHMM
		 */
		public HMMWrapper(DefaultHMM defaultHMM) {
			this.defaultHMM = defaultHMM;
		}


		@Override
		public int n() {
			// TODO Auto-generated method stub
			return defaultHMM.getStateNumber();
		}


		@Override
		public double a(int stateI, int stateJ) {
			// TODO Auto-generated method stub
			return defaultHMM.getA(stateI, stateJ);
		}


		@Override
		public double pi(int stateI) {
			// TODO Auto-generated method stub
			return defaultHMM.getPI(stateI);
		}


		@Override
		public double b(int stateI, Obs obs) {
			// TODO Auto-generated method stub
			return defaultHMM.getB(stateI, obs, -1);
		}


		@Override
		public double evaluate(List<Obs> obsSeq) {
			// TODO Auto-generated method stub
			return defaultHMM.prob(obsSeq);
		}


		@Override
		public List<Integer> uncover(List<Obs> obsSeq) {
			// TODO Auto-generated method stub
			return defaultHMM.viterbi(obsSeq);
		}


		@Override
		public void learn(List<Obs> obsSeq) {
			// TODO Auto-generated method stub
			defaultHMM.em(obsSeq, DefaultHMM.EM_MAX_ITERATION);
		}
		
		
		/**
		 * 
		 * @return
		 */
		public DefaultHMM getHMMImpl() {
			return defaultHMM;
		}


		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return defaultHMM.toString();
		}
		
	}
	
	
	@Override
	public HMM createDiscreteHMM(double[][] A, double[] PI, double[][] B) {
		// TODO Auto-generated method stub
		DefaultHMM defaultHMM = new DefaultHMM();
		defaultHMM.A = createMatrix(A);
		defaultHMM.PI = Util.newList(PI.length);
		for (int i = 0; i < PI.length; i++) {
			defaultHMM.PI.add(PI[i]);
		}
		
		defaultHMM.B = Util.newList(B.length);
		for (int i = 0; i < B.length; i++) {
			ProbabilityTable dist = new ProbabilityTable(B[i].length);
			for (int j = 0; j < B[i].length; j++) {
				dist.setProb(j, B[i][j]);
			}
			defaultHMM.B.add(dist); 
		}
		
		defaultHMM.getPrinter().close();
		return new HMMWrapper(defaultHMM);
	}


	@Override
	public HMM createDiscreteHMM(int nState, int mObs) {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		double sum = 0;
		
		double[][] A = new double[nState][nState];
		for (int i = 0; i < nState; i++) {
			sum = 0;
			for (int j = 0; j < nState; j++) {
				double a = rnd.nextDouble();
				A[i][j] = a;
				sum += a;
			}
			if (sum == 0) {
				int k = rnd.nextInt(nState);
				while((A[i][k] = rnd.nextDouble()) != 0) {}
				sum = A[i][k];
			}
			
			for (int j = 0; j < nState; j++)
				A[i][j] = A[i][j] / sum;
		}
		
		double[] PI = new double[nState];
		sum = 0;
		for (int i = 0; i < nState; i++) {
			double pi = rnd.nextDouble();
			PI[i] = pi;
			sum += pi;
		}
		if (sum == 0) {
			int k = rnd.nextInt(nState);
			while((PI[k] = rnd.nextDouble()) != 0) {}
			sum = PI[k];
		}
		for (int i = 0; i < nState; i++)
			PI[i] = PI[i] / sum;
		
		double[][] B = new double[nState][mObs];
		for (int i = 0; i < nState; i++) {
			sum = 0;
			for (int j = 0; j < mObs; j++) {
				double b = rnd.nextDouble();
				B[i][j] = b;
				sum += b;
			}
			if (sum == 0) {
				int k = rnd.nextInt(mObs);
				while((B[i][k] = rnd.nextDouble()) != 0) {}
				sum = B[i][k];
			}
			for (int j = 0; j < mObs; j++)
				B[i][j] = B[i][j] / sum;
		}

		return createDiscreteHMM(A, PI, B);
	}


	@Override
	public HMM createNormalHMM(double[][] A, double[] PI, double[] means,
			double[] variances, double epsilon) {
		// TODO Auto-generated method stub
		DefaultHMM defaultHMM = new DefaultHMM();
		defaultHMM.A = createMatrix(A);
		defaultHMM.PI = Util.newList(PI.length);
		for (int i = 0; i < PI.length; i++) {
			defaultHMM.PI.add(PI[i]);
		}
		
		defaultHMM.B = Util.newList(means.length);
		for (int i = 0; i < means.length; i++) {
			NormalDistribution dist = new NormalDistribution(means[i], variances[i]);
			dist.setEpsilon(epsilon);
			defaultHMM.B.add(dist); 
		}
		
		defaultHMM.getPrinter().close();
		return new HMMWrapper(defaultHMM);
	}


	@Override
	public HMM createExponentialHMM(double[][] A, double[] PI, double[] means, double epsilon) {
		// TODO Auto-generated method stub
		DefaultHMM defaultHMM = new DefaultHMM();
		defaultHMM.A = createMatrix(A);
		defaultHMM.PI = Util.newList(PI.length);
		for (int i = 0; i < PI.length; i++) {
			defaultHMM.PI.add(PI[i]);
		}
		
		defaultHMM.B = Util.newList(means.length);
		for (int i = 0; i < means.length; i++) {
			ExponentialDistribution dist = new ExponentialDistribution(means[i]);
			dist.setEpsilon(epsilon);
			defaultHMM.B.add(dist); 
		}
		
		defaultHMM.getPrinter().close();
		return new HMMWrapper(defaultHMM);
	}


	@Override
	public HMM createNormalMixtureHMM(double[][] A, double[] PI, double[][] means,
			double[][] variances, double[][] weights, double epsilon) {
		// TODO Auto-generated method stub
		DefaultHMM defaultHMM = new DefaultHMM();
		defaultHMM.A = createMatrix(A);
		defaultHMM.PI = Util.newList(PI.length);
		for (int i = 0; i < PI.length; i++) {
			defaultHMM.PI.add(PI[i]);
		}
		
		defaultHMM.B = Util.newList(weights.length);
		for (int i = 0; i < weights.length; i++) {
			MixtureDistribution dist = MixtureDistribution.createNormalMixture(
					means[i], variances[i], weights[i]);
			
			int K = dist.getComponentCount();
			for (int k = 0; k < K; k++) {
				((NormalDistribution)dist.getComponent(k)).setEpsilon(epsilon);
			}
			
			defaultHMM.B.add(dist);
		}
		
		defaultHMM.getPrinter().close();
		return new HMMWrapper(defaultHMM);
	}

	
	
}
