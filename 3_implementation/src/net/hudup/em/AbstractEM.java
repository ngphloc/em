package net.hudup.em;

import net.hudup.core.alg.AbstractTestingAlg;
import net.hudup.core.data.DataConfig;


/**
 * <code>AbstractEM</code> is the most abstract class for expectation maximization (EM) algorithm.
 * It implements partially the interface {@link EM}.
 * For convenience, implementation of an EM algorithm should extend this class.
 * 
 * @author Loc Nguyen
 * @version 1.0*
 */
public abstract class AbstractEM extends AbstractTestingAlg implements EM {

	
	/**
	 * Serial version UID for serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Name of maximum iteration.
	 */
	protected final static String EM_MAX_ITERATION_FIELD = "em_max_iteration";
	
	
	/**
	 * Name of epsilon field for EM, stored in configuration.
	 */
	protected final static String EM_EPSILON_FIELD = "em_epsilon";

	
	/**
	 * Current iteration.
	 */
	protected int currentIteration = 0;
	
	
	/**
	 * Current parameter.
	 */
	protected Object currentParameter = null;
	
	
	/**
	 * Current parameter.
	 */
	protected Object estimatedParameter = null;
	
	
	/**
	 * Default constructor.
	 */
	public AbstractEM() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Initializing parameter at the first iteration of EM process.
	 * @return initialized parameter at the first iteration of EM process.
	 */
	protected abstract Object initializeParameter();
	
	
	/**
	 * Setting the terminated condition for EM.
	 * The usual terminated condition is that the bias between current parameter and estimated parameter is smaller than a positive predefined epsilon.
	 * However the terminated condition is dependent on particular application.
	 * @param currentParameter current parameter.
	 * @param estimatedParameter estimated parameter.
	 * @param info additional information.
	 * @return true if the EM algorithm can stop.
	 */
	protected abstract boolean terminatedCondition(Object currentParameter, Object estimatedParameter, Object... info);
	
	
	@Override
	public synchronized int getCurrentIteration() {
		// TODO Auto-generated method stub
		return currentIteration;
	}


	@Override
	public synchronized Object getCurrentParameter() {
		// TODO Auto-generated method stub
		return currentParameter;
	}


	@Override
	public synchronized Object getEstimatedParameter() {
		// TODO Auto-generated method stub
		return estimatedParameter;
	}


	@Override
	public Object getParameter() {
		// TODO Auto-generated method stub
		return getEstimatedParameter();
	}
	
	
	/**
	 * Getting maximum number of iterations.
	 * @return maximum number of iterations.
	 */
	public int getMaxIteration() {
		DataConfig config = getConfig();
		int maxIteration = 0;
		if (config.containsKey(EM_MAX_ITERATION_FIELD))
			maxIteration = config.getAsInt(EM_MAX_ITERATION_FIELD);
		if (maxIteration <= 0)
			return EM_MAX_ITERATION;
		else
			return maxIteration;
	}
	
	
	@Override
	public DataConfig createDefaultConfig() {
		// TODO Auto-generated method stub
		DataConfig config = super.createDefaultConfig();
		config.put(EM_EPSILON_FIELD, EM_DEFAULT_EPSILON);
		config.put(EM_MAX_ITERATION_FIELD, EM_MAX_ITERATION);
		return config;
	}


}
