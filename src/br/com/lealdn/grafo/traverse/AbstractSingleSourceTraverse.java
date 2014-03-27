package br.com.lealdn.grafo.traverse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public abstract class AbstractSingleSourceTraverse {
    protected List<Node> nodes;

    public AbstractSingleSourceTraverse(List<Node> nodes) {
        this.nodes = new ArrayList<Node>(nodes);
    }

    public void initializeEdgePathIfNull(Map<Node, List<Edge>> edgePath, Node node) {
        if (!edgePath.containsKey(node)) {
            edgePath.put(node, new ArrayList<Edge>());
        }
    }

    protected void initializeSingleSource(Node root) {
        for (Node node : this.nodes) {
            node.setWeight(Integer.MAX_VALUE);
            node.setRoot(null);
        }
        root.setWeight(0);
    }
    
    protected boolean relax(Node node, Node otherNode, Edge edge) {
        if (otherNode.getWeight() > sum(node.getWeight(), edge.getWeight())) {
            otherNode.setWeight(sum(node.getWeight(), edge.getWeight()));
            otherNode.setRoot(node);

            return true;
        }
        return false;
    }

    protected int sum(Integer int1, Integer int2) {
        if (int1 != null && int2 != null) {
            if (int1 == Integer.MAX_VALUE || int2 == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return int1 + int2;
        }

        return Integer.MAX_VALUE;
    }

    /**
     * @return the nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
