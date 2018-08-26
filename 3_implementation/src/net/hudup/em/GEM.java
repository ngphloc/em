package net.hudup.em;

/**
 * This class represents the generalized expectation maximization (GEM) algorithm.
 * It inherits directly from {@link AbstractEM}.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public abstract class GEM extends AbstractEM {

	
	/**
	 * Serial version UID for serializable class.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Default constructor.
	 */
	public GEM() {
		// TODO Auto-generated constructor stub
		super();
	}

	
	/**
	 * Finding a maximizer of the conditional expectation Q based on current parameter.
	 * @param currentParameter current parameter.
	 * @param info additional information.
	 * @return a maximizer of the conditional expectation Q based on current parameter.
	 * @throws Exception if any error raises.
	 */
	protected abstract Object argmaxQ(Object currentParameter, Object...info) throws Exception;
	
	
	@Override
	public synchronized Object learn(Object...info) throws Exception {
		// TODO Auto-generated method stub
		this.estimatedParameter = this.currentParameter = this.previousParameter = this.statistics = null;
		this.currentIteration = 0;
		this.estimatedParameter = this.currentParameter = initializeParameter();
		initializeNotify();
		if (this.estimatedParameter == null) {
			finishNotify();
			return null;
		}
		
		this.currentIteration = 1;
		int maxIteration = getMaxIteration();
		while(this.currentIteration < maxIteration) {
			this.estimatedParameter = argmaxQ(this.currentParameter);
			if (this.estimatedParameter == null)
				break;
			
			//Firing event
			fireSetupEvent(new EMLearningEvent(this, this.dataset, null));
			
			boolean terminated = terminatedCondition(this.estimatedParameter, this.currentParameter, this.previousParameter);
			if (terminated)
				break;
			else {
				this.previousParameter = this.currentParameter;
				this.currentParameter = this.estimatedParameter;
				this.currentIteration++;
				permuteNotify();
			}
			
		}
		
		if (this.estimatedParameter != null)
			this.currentParameter = this.estimatedParameter;
		else if (this.currentParameter != null)
			this.estimatedParameter = this.currentParameter;

		finishNotify();
		return this.estimatedParameter;
	}


}
