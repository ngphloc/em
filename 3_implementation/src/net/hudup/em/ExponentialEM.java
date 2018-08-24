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
		this.estimatedParameter = this.currentParameter = this.previousParameter = this.statistics = null;
		this.currentIteration = 0;
		this.estimatedParameter = this.currentParameter = initializeParameter();
		if (this.estimatedParameter == null)
			return null;
		
		this.currentIteration = 1;
		int maxIteration = getMaxIteration();
		while(this.currentIteration < maxIteration) {
			Object tempStatistics = expectation(this.currentParameter);
			if (tempStatistics == null)
				break;
			
			this.statistics = tempStatistics;
			this.estimatedParameter = maximization(this.statistics);
			if (this.estimatedParameter == null)
				break;
			
			//Firing event
			fireSetupEvent(new EMLearningEvent(this, this.dataset, this.statistics));
			
			boolean terminated = terminatedCondition(this.estimatedParameter, this.currentParameter, this.previousParameter);
			if (terminated)
				break;
			else {
				this.previousParameter = this.currentParameter;
				this.currentParameter = this.estimatedParameter;
				this.currentIteration++;
			}
		}
		
		if (this.estimatedParameter != null)
			this.currentParameter = this.estimatedParameter;
		else if (this.currentParameter != null)
			this.estimatedParameter = this.currentParameter;
		
		return this.estimatedParameter;
	}


}
