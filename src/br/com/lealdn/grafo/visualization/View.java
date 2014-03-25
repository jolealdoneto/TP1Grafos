package br.com.lealdn.grafo.visualization;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class View {
	
	
	public static void view(List<Node> nodes) {
		 Graph<Integer, String> g = new UndirectedSparseGraph<Integer, String>();
		 for (Node node : nodes) {
			 g.addVertex(node.getId());
		 }
		 
		 for (Node node : nodes) {
			 for (Edge edge : node.getEdges()) {
				 g.addEdge(String.valueOf(edge.getId()), edge.getNode1().getId(), edge.getNode2().getId());
			 }
		 }
		 
		 
		// The Layout<V, E> is parameterized by the vertex and edge types
		 Layout<Integer, String> layout = new CircleLayout<Integer, String>(g);
		 layout.setSize(new Dimension(1000,1000)); // sets the initial size of the space
		 // The BasicVisualizationServer<V,E> is parameterized by the edge types
		 BasicVisualizationServer<Integer,String> vv = 
		 new BasicVisualizationServer<Integer,String>(layout);
		 vv.setPreferredSize(new Dimension(1000, 1000)); //Sets the viewing area size
		 vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		 
		 JFrame frame = new JFrame("Simple Graph View");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.getContentPane().add(vv); 
		 frame.pack();
		 frame.setVisible(true); 
	}

}
