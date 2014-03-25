package br.com.lealdn.grafo.traverse;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lealdn.grafo.elements.Node;
import br.com.lealdn.grafo.elements.Edge;

public class RepeatedSquaring {
    private Integer[][] adjMatrix;
    private Integer[][] weightMatrix;
    private List<Node> nodes;
    private List<Edge>[][] pathsForEachNode;

    public RepeatedSquaring(List<Node> nodes) {
        this.nodes = new ArrayList<Node>(nodes);
        Collections.sort(nodes);
    }

    private int getNodeIndex(Node node) {
        return nodes.indexOf(node);
    }
    public Integer[][] doRepeatedSquaring() {
        this.weightMatrix = createWeightMatrix(this.nodes);
        this.adjMatrix = initializeD0Matrix(this.adjMatrix);
        this.pathsForEachNode = new EdgeList[adjMatrix.length][adjMatrix.length];

        for (int i = 1; i < this.nodes.size()-1; i++) {
            adjMatrix = extendSP(adjMatrix, weightMatrix);
        }

        return adjMatrix;
    }

    public void findMostUsedEdgeAndRemoveIt() {
        doRepeatedSquaring();
        Map<Edge, Integer> edgeCount = new HashMap<Edge, Integer>();
        for (int i = 0; i < this.nodes.size(); i++) {
            for (int j = 0; j < this.nodes.size(); j++) {
                if (pathsForEachNode[i][j] != null) {
                    for (Edge edge : pathsForEachNode[i][j]) {
                        if (!edgeCount.containsKey(edge)) {
                            edgeCount.put(edge, 0);
                        }

                        edgeCount.put(edge, edgeCount.get(edge) + 1);
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
            maxRow.getKey().removeEdge();
        }
    }

    public int getNumberOfCommunities() {
        BFS bfs = new BFS(this.nodes);
        return bfs.getNumberOfCommunities();
    }

    private Integer[][] extendSP(Integer[][] adjMatrix, Integer[][] weightMatrix) {
        Integer[][] newAdjMatrix = new Integer[adjMatrix.length][adjMatrix.length];

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                newAdjMatrix[i][j] = Integer.MAX_VALUE;

                if (adjMatrix[i][j] != Integer.MAX_VALUE) {
                    newAdjMatrix[i][j] = adjMatrix[i][j];
                    continue;
                }

                Map.Entry<Integer, Integer> minPath = new AbstractMap.SimpleEntry<Integer, Integer>(Integer.MAX_VALUE, Integer.MAX_VALUE);
                for (int k = 0; k < adjMatrix.length; k++) {
                    if (minPath.getValue() > sum(adjMatrix[i][k], weightMatrix[k][j])) {
                        minPath = new AbstractMap.SimpleEntry<Integer, Integer>(k, sum(adjMatrix[i][k], weightMatrix[k][j]));
                    }
                }
                newAdjMatrix[i][j] = minPath.getValue();
                if (minPath.getValue() != Integer.MAX_VALUE) {
                    int k = minPath.getKey();

                    pathsForEachNode[i][j] = initializeEdgePath(pathsForEachNode[i][j]);
                    pathsForEachNode[i][k] = initializeEdgePath(pathsForEachNode[i][k]);
                    Edge incomingEdge = retrieveEdgeGiveIndexes(k, j);

                    pathsForEachNode[i][j].addAll(pathsForEachNode[i][k]);
                    pathsForEachNode[i][j].add(incomingEdge);
                }
            }
        }

        return newAdjMatrix;
    }

    private int sum(Integer int1, Integer int2) {
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

    class EdgeList extends ArrayList<Edge> {}
    private List<Edge> initializeEdgePath(List<Edge> edgePath) {
        if (edgePath == null) {
            edgePath = new EdgeList();
        }

        return edgePath;
    }

    private Edge retrieveEdgeGiveIndexes(int i, int j) {
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

    private Integer[][] createWeightMatrix(List<Node> nodes) {
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
    private Integer[][] initializeD0Matrix(Integer[][] adjMatrix) {
        adjMatrix = new Integer[nodes.size()][nodes.size()];
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                adjMatrix[i][j] = i == j ? 0 : Integer.MAX_VALUE;
            }
        }

        return adjMatrix;
    }
}
