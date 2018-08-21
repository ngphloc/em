package net.hudup.em;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import net.hudup.core.data.DataConfig;
import net.hudup.core.logistic.MathUtil;

/**
 * This abstract class implements partially expectation maximization (EM) algorithm associated Newton-Raphson method.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public abstract class NewtonGEM extends GEM {

	
	/**
	 * Serial version UID for serializable class.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Name of Newton-Raphson type field, stored in configuration.
	 */
	protected final static String GEM_NEWTON_TYPE_FIELD = "gem_newton_type_field";
	
	
	/**
	 * Default Newton-Raphson type for second-order convergence; 
	 */
	protected final static int GEM_NEWTON_TYPE_DEFAULT = 1;
	
	
	/**
	 * Improved Newton-Raphson type for third-order convergence; 
	 */
	protected final static int GEM_NEWTON_TYPE_IMPROVED = 2;

	
	/**
	 * Default constructor.
	 */
	public NewtonGEM() {
		// TODO Auto-generated constructor stub
		super();
	}

	
	/**
	 * Calculating the first derivative of Q function.
	 * @param parameter1 first parameter.
	 * @param parameter2 second parameter.
	 * @return the first derivative of Q function as gradient vector.
	 */
	protected abstract double[] d1Q(double[] parameter1, double[] parameter2);
	
	
	/**
	 * Calculating the second derivative of Q function.
	 * @param parameter1 first parameter.
	 * @param parameter2 second parameter.
	 * @return the second derivative of Q function as gradient vector.
	 */
	protected abstract double[][] d2Q(double[] parameter1, double[] parameter2);
	
	
	@Override
	protected Object argmaxQ(Object currentParameter, Object...info) throws Exception {
		// TODO Auto-generated method stub
		int newtonType = getConfig().getAsInt(GEM_NEWTON_TYPE_FIELD);
		RealVector theta = new ArrayRealVector((double[])currentParameter);
		RealVector phi = new ArrayRealVector((double[])currentParameter);
		
		if (newtonType == GEM_NEWTON_TYPE_IMPROVED) {
			RealMatrix matrix = MatrixUtils.createRealMatrix(d2Q((double[])currentParameter, (double[])currentParameter));
			RealVector vector = MatrixUtils.inverse(matrix).operate(
					new ArrayRealVector(d1Q((double[])currentParameter, (double[])currentParameter))
				)
				.mapMultiply(0.5);
			phi = phi.subtract(vector);
		}
		
		RealMatrix matrix = MatrixUtils.createRealMatrix(d2Q(phi.toArray(), (double[])currentParameter));
		RealVector vector = MatrixUtils.inverse(matrix).operate(
				new ArrayRealVector(d1Q((double[])currentParameter, (double[])currentParameter))
			);
		theta = theta.subtract(vector);
		
		return theta.toArray();
	}

	
	@Override
	protected boolean terminatedCondition(Object estimatedParameter, Object currentParameter, Object previousParameter, Object... info) {
		// TODO Auto-generated method stub
		double[] parameter1 = (double[])currentParameter;
		double[] parameter2 = (double[])estimatedParameter;
		double threshold = getConfig().getAsReal(EM_EPSILON_FIELD);
		for (int i = 0; i < parameter1.length; i++) {
			if (parameter1[i] == 0) {
				if (parameter2[i] == 0)
					continue;
				else
					return false;
			}
			else {
				double biasRatio = Math.abs(parameter2[i] - parameter1[i]) / Math.abs(parameter1[i]);
				if (biasRatio > threshold)
					return false;
			}
		}
		
		return true;
	}

	
	@Override
	public DataConfig createDefaultConfig() {
		// TODO Auto-generated method stub
		DataConfig config = super.createDefaultConfig();
		config.put(GEM_NEWTON_TYPE_FIELD, GEM_NEWTON_TYPE_DEFAULT);
		return config;
	}
	
	
	@Override
	public String parameterToShownText(Object parameter, Object...info) {
		// TODO Auto-generated method stub
		if (parameter == null || !(parameter instanceof double[]))
			return "";
		
		double[] array = (double[])parameter;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				buffer.append(", ");
			buffer.append(MathUtil.format(array[i]));
		}
		
		return buffer.toString();
	}

	
}
