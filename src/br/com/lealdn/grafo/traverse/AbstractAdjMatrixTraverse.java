package br.com.lealdn.grafo.traverse;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.lealdn.grafo.elements.Node;
import br.com.lealdn.grafo.elements.Edge;

public abstract class AbstractAdjMatrixTraverse {
    protected Integer[][] adjMatrix;
    protected Integer[][] weightMatrix;
    protected List<Node> nodes;

    public AbstractAdjMatrixTraverse(List<Node> nodes) {
        this.nodes = new ArrayList<Node>(nodes);
        Collections.sort(nodes);
    }

    private int getNodeIndex(Node node) {
        return nodes.indexOf(node);
    }

    public List<List<Node>> getCommunities() {
        BFS bfs = new BFS(this.nodes);
        return bfs.getCommunities();
    }

    public Set<Integer> createSet(final Integer node1, final Integer node2) {
        return new HashSet<Integer>() {{
            add(node1);
            add(node2);
        }};
    }

    protected Map.Entry<Edge, Integer> findMostUsedEdgeGiveEdgeList(List<Edge>[][] pathsForEachNode) {
        Map<Edge, Integer> edgeCount = new HashMap<Edge, Integer>();
        Set<Set<Integer>> alreadyCounted = new HashSet<Set<Integer>>();
        for (int i = 0; i < this.nodes.size(); i++) {
            for (int j = 0; j < this.nodes.size(); j++) {
                if (pathsForEachNode[i][j] != null) {
                    Set<Integer> nodeSet = createSet(i, j);
                    if (!alreadyCounted.contains(nodeSet)) {
                        alreadyCounted.add(nodeSet);

                        for (Edge edge : pathsForEachNode[i][j]) {
                            if (!edgeCount.containsKey(edge)) {
                                edgeCount.put(edge, 0);
                            }

                            edgeCount.put(edge, edgeCount.get(edge) + 1);
                        }
                    }
                }
            }
        }

        Map.Entry<Edge, Integer> maxRow = new AbstractMap.SimpleEntry<Edge, Integer>(null, -1);
        for (Map.Entry<Edge, Integer> row : edgeCount.entrySet()) {
            if (maxRow.getValue() < row.getValue()) {
                maxRow = row;
            }
        }
        if (maxRow.getKey() != null) {
            return maxRow;
        }
        return null;
    }

    protected int sum(Integer int1, Integer int2) {
        if (int1 != null && int2 != null) {
            if (int1 == Integer.MAX_VALUE || int2 == Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            else
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

    protected Edge retrieveEdgeGiveIndexes(int i, int j) {
        Node node = this.nodes.get(i);

        return node.findEdge(this.nodes.get(j));
    }

    public void printMatrix(Integer[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.println("i: " + (i+1) + " j: " + (j+1) + " | " + matrix[i][j]);
            }
        }
    }

    protected Integer[][] createWeightMatrix(List<Node> nodes) {
        weightMatrix = new Integer[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);

            for (Edge edge : node.getEdges()) {
                weightMatrix[getNodeIndex(edge.getNode1())][getNodeIndex(edge.getNode2())] = 1;
                weightMatrix[getNodeIndex(edge.getNode2())][getNodeIndex(edge.getNode1())] = 1;
            }
        }
        return weightMatrix;
    }
}
