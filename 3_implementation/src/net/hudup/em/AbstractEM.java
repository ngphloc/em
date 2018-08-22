package net.hudup.em;

import java.util.Arrays;
import java.util.List;

import net.hudup.core.Util;
import net.hudup.core.alg.AbstractTestingAlg;
import net.hudup.core.alg.SetupAlgEvent;
import net.hudup.core.alg.SetupAlgEvent.Type;
import net.hudup.core.data.DataConfig;
import net.hudup.core.data.Dataset;
import net.hudup.core.data.Fetcher;
import net.hudup.core.data.Profile;


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
	 * Previous parameter.
	 */
	protected Object previousParameter = null;

	
	/**
	 * Current parameter.
	 */
	protected Object currentParameter = null;
	
	
	/**
	 * Current parameter.
	 */
	protected Object estimatedParameter = null;
	
	
	/**
	 * Current statistics.
	 */
	protected Object statistics = null;

	
	/**
	 * Default constructor.
	 */
	public AbstractEM() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void setup(Dataset dataset, Object... info) throws Exception {
		unsetup();
		this.dataset = dataset;
		if (info != null && info.length > 0 && (info[0] instanceof Fetcher<?>))
			this.sample = (Fetcher<Profile>)info[0];
		else
			this.sample = dataset.fetchSample();
		
		this.estimatedParameter = this.currentParameter = this.previousParameter = this.statistics = null;
		this.currentIteration = 0;
		learn();
		
		SetupAlgEvent evt = new SetupAlgEvent(
				this,
				Type.done,
				this,
				dataset,
				" (t = " + this.getCurrentIteration() + ") learned models: " + this.getDescription());
		fireSetupEvent(evt);
	}

	
	@Override
	public void setup(Fetcher<Profile> sample, Object... info) throws Exception {
		// TODO Auto-generated method stub
		List<Object> additionalInfo = Util.newList();
		additionalInfo.add(sample);
		additionalInfo.addAll(Arrays.asList(info));

		setup((Dataset)null, additionalInfo.toArray());
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
	 * @param estimatedParameter estimated parameter.
	 * @param currentParameter current parameter.
	 * @param previousParameter previous parameter.
	 * @param info additional information.
	 * @return true if the EM algorithm can stop.
	 */
	protected abstract boolean terminatedCondition(Object estimatedParameter, Object currentParameter, Object previousParameter, Object... info);
	
	
	@Override
	public synchronized int getCurrentIteration() {
		// TODO Auto-generated method stub
		return currentIteration;
	}

	
	/**
	 * Getting previous parameter.
	 * @return previous parameter.
	 */
	public synchronized Object getPreviousParameter() {
		// TODO Auto-generated method stub
		return previousParameter;
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
	 * Setting specified parameter and iteration to this regression model. Please use this method carefully.
	 * @param parameter specified parameter.
	 * @param iteration current parameter.
	 */
	public synchronized void setParameter(Object parameter, int iteration) {
		this.currentParameter = parameter;
		this.estimatedParameter = parameter;
		this.currentIteration = iteration;
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
	public synchronized Object getStatistics() {
		// TODO Auto-generated method stub
		return statistics;
	}


	/**
	 * Setting current statistics.
	 * @param statistics specified statistics.
	 */
	public synchronized void setStatistics(Object statistics) {
		this.statistics = statistics;
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
