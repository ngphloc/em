package net.hudup.em;

/**
 * This abstract class model a expectation maximization (EM) algorithm for exponential family.
 * In other words, probabilistic distributions in this class belongs to exponential family.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public abstract class ExponentialEM extends AbstractEM {

	
	/**
	 * Serial version UID for serializable class.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Default constructor.
	 */
	public ExponentialEM() {
		// TODO Auto-generated constructor stub
		super();
	}

	
	/**
	 * This method implement expectation step (E-step) of EM.
	 * @param currentParameter current parameter.
	 * @param info additional information.
	 * @return sufficient statistic given current parameter.
	 * @throws Exception if any error raises
	 */
	protected abstract Object expectation(Object currentParameter, Object...info) throws Exception;
	
	
	/**
	 * This method implement maximization step (M-step) of EM.
	 * @param currentStatistic current sufficient statistic.
	 * @param info additional information.
	 * @return estimated parameter given current sufficient statistic.
	 * @throws Exception if any error raises
	 */
	protected abstract Object maximization(Object currentStatistic, Object...info) throws Exception;
	
	
	@Override
	public synchronized Object learn(Object...info) throws Exception {
		// TODO Auto-generated method stub
		estimatedParameter = currentParameter = null;
		currentIteration = 0;
		estimatedParameter = currentParameter = initializeParameter();
		if (estimatedParameter == null)
			return null;
		
		currentIteration = 1;
		int maxIteration = getMaxIteration();
		while(currentIteration < maxIteration) {
			statistics = expectation(currentParameter);
			if (statistics == null)
				break;
			estimatedParameter = maximization(statistics);
			if (estimatedParameter == null)
				break;
			
			//Firing event
			fireSetupEvent(new EMLearningEvent(this, this.dataset, statistics));
			
			boolean terminated = terminatedCondition(currentParameter, estimatedParameter);
			if (terminated)
				break;
			else {
				currentParameter = estimatedParameter;
				currentIteration++;
			}
		}
		
		if (estimatedParameter != null)
			currentParameter = estimatedParameter;
		else if (currentParameter != null)
			estimatedParameter = currentParameter;
		
		return estimatedParameter;
	}


}
