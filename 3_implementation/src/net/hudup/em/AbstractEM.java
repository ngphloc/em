package net.hudup.em;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.EventListenerList;

import net.hudup.core.alg.AbstractAlg;
import net.hudup.core.alg.SetupAlgEvent;
import net.hudup.core.alg.SetupAlgEvent.Type;
import net.hudup.core.alg.SetupAlgListener;
import net.hudup.core.data.DataConfig;
import net.hudup.core.data.Dataset;
import net.hudup.core.data.Fetcher;
import net.hudup.core.data.Profile;
import net.hudup.core.logistic.ui.UIUtil;


/**
 * <code>AbstractEM</code> is the most abstract class for expectation maximization (EM) algorithm.
 * It implements partially the interface {@link EM}.
 * For convenience, implementation of an EM algorithm should extend this class.
 * 
 * @author Loc Nguyen
 * @version 1.0*
 */
public abstract class AbstractEM extends AbstractAlg implements EM {

	
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
	 * Data sample for EM.
	 */
	protected Dataset dataset = null;
	
	/**
	 * Data sample for EM.
	 */
	protected Fetcher<Profile> sample = null;
	
	
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
	 * Holding a list of listeners.
	 * 
	 */
    protected EventListenerList listenerList = new EventListenerList();
    
    
	/**
	 * Default constructor.
	 */
	public AbstractEM() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public synchronized void setup(Dataset dataset, Object... info) throws Exception {
		unsetup();
		this.dataset = dataset;
		this.sample = dataset.fetchSample();
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
	public synchronized void unsetup() {
		try {
			if (sample != null)
				sample.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			sample = null;
		}

		if (dataset != null && dataset.isExclusive())
			dataset.clear();
		dataset = null;
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
	
	
	/**
	 * Getting dataset
	 * @return dataset.
	 */
	protected Dataset getDataset() {
		return dataset;
	}
	
	
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


	@Override
	public void manifest() {
		// TODO Auto-generated method stub
		JDialog manifestDlg = new JDialog(UIUtil.getFrameForComponent(null), "Manifest", true);
		manifestDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		manifestDlg.setLocationRelativeTo(null);
		manifestDlg.setSize(400, 200);
		manifestDlg.setLayout(new BorderLayout());
		
		JPanel body = new JPanel(new BorderLayout());
		manifestDlg.add(body, BorderLayout.CENTER);
		
		JTextArea txtDesc = new JTextArea(getDescription());
		txtDesc.setEditable(false);
		txtDesc.setLineWrap(true);
		txtDesc.setWrapStyleWord(true);
		body.add(new JScrollPane(txtDesc), BorderLayout.CENTER);
		
		JPanel footer = new JPanel();
		manifestDlg.add(footer, BorderLayout.SOUTH);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manifestDlg.dispose();
			}
		});
		footer.add(btnOK);
		
		manifestDlg.setVisible(true);
	}


	@Override
	public void addSetupListener(SetupAlgListener listener) {
		// TODO Auto-generated method stub
		synchronized (listenerList) {
			listenerList.add(SetupAlgListener.class, listener);
		}
	}


	@Override
	public void removeSetupListener(SetupAlgListener listener) {
		// TODO Auto-generated method stub
		synchronized (listenerList) {
			listenerList.remove(SetupAlgListener.class, listener);
		}
	}


	/**
	 * Getting an array of listeners for this EM.
	 * @return array of listeners for this EM.
	 */
	protected SetupAlgListener[] getSetupListeners() {
		// TODO Auto-generated method stub
		synchronized (listenerList) {
			return listenerList.getListeners(SetupAlgListener.class);
		}
	}


    /**
     * Firing (issuing) an event from this EM to all listeners. 
     * @param evt event from this EM.
     */
	protected void fireSetupEvent(SetupAlgEvent evt) {
		// TODO Auto-generated method stub
		SetupAlgListener[] listeners = getSetupListeners();
		for (SetupAlgListener listener : listeners) {
			try {
				listener.receivedSetup(evt);
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
		try {
			unsetup();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}

	
}
