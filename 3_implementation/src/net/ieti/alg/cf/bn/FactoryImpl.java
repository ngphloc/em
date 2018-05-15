package net.ieti.alg.cf.bn;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;

/**
 * This class is the default implementation of a factory.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public final class FactoryImpl implements Factory {

	
	@Override
	public BNet createNetwork() {
		// TODO Auto-generated method stub
		return new BNetworkWrapper();
	}

	
	@Override
	public BNode createNode() {
		// TODO Auto-generated method stub
		return new BNodeWrapper();
	}

	
	/**
	 * This class is an implementation of a Bayesian network, which is the wrapper of {@link BayesNet}. 
	 * @author Loc Nguyen
	 * @version 1.0
	 *
	 */
	private static final class BNetworkWrapper implements BNet {

		/**
		 * Internal Bayesian network.
		 */
		private BayesNet bayesNet = null;
		
		/**
		 * Default constructor.
		 */
		BNetworkWrapper() {
			bayesNet = new BayesNet();
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void addRootNodes(BNode... rootNodes) {
			// TODO Auto-generated method stub
			for (BNode node : rootNodes) {
				bayesNet.addNode(((BNodeWrapper)node).bayesNode);
			}
			
		}

		@Override
		public List<BNode> getRootNodes() {
			// TODO Auto-generated method stub
			List<BayesNode> rootNodes = bayesNet.getNodes();
			return BNodeWrapper.toNodeList(rootNodes);
		}
		
	}
	
	
	/**
	 * This class is an implementation of a node in Bayesian network, which is the wrapper of {@link BayesNode}. 
	 * @author Loc Nguyen
	 * @version 1.0
	 *
	 */
	private static final class BNodeWrapper implements BNode {

		/**
		 * Internal node.
		 */
		private BayesNode bayesNode = null;
		
		/**
		 * Default constructor.
		 */
		BNodeWrapper() {
			
		}
		
		@Override
		public void setParents(BNode... parentNodes) {
			// TODO Auto-generated method stub
			List<BayesNode> nodeList = new ArrayList<BayesNode>();
			for (BNode node : parentNodes) {
				nodeList.add(((BNodeWrapper)node).bayesNode);
			}
			bayesNode.setParents(nodeList);
		}

		@Override
		public List<BNode> getParents() {
			// TODO Auto-generated method stub
			List<BayesNode> parentNodes = bayesNode.getParents();
			return toNodeList(parentNodes);
		}

		@Override
		public List<BNode> getChildren() {
			// TODO Auto-generated method stub
			List<BayesNode> childNodes = bayesNode.getChildren();
			return toNodeList(childNodes);
		}

		/**
		 * Converting a specified list of {@link BayesNode} (s) to a list of {@link BNode} (s).
		 * @param bayesNodeList specified list of {@link BayesNode} (s).
		 * @return a list of {@link BNode} (s).
		 */
		private static List<BNode> toNodeList(List<BayesNode> bayesNodeList) {
			List<BNode> nodeList = new ArrayList<BNode>();
			for (BayesNode bayesNode : bayesNodeList) {
				BNodeWrapper wrapper = new BNodeWrapper();
				wrapper.bayesNode = bayesNode;
				nodeList.add(wrapper);
			}
			
			return nodeList;
		}
		
		@Override
		public void setProbs(double... cpt) {
			// TODO Auto-generated method stub
			bayesNode.setProbabilities(cpt);
		}

		@Override
		public double[] getProbs() {
			// TODO Auto-generated method stub
			return bayesNode.getProbabilities();
		}
		
	}
	
	
}
