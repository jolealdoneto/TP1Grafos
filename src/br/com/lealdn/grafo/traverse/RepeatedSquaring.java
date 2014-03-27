package br.com.lealdn.grafo.traverse;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.lealdn.grafo.elements.Node;
import br.com.lealdn.grafo.elements.Edge;

public class RepeatedSquaring extends AbstractAdjMatrixTraverse {
    private List<Edge>[][] pathsForEachNode;

    public RepeatedSquaring(List<Node> nodes) {
        super(nodes);
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
        Edge edge = findMostUsedEdgeGiveEdgeList(this.pathsForEachNode);
        if (edge != null) {
            edge.removeEdge();
        }
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

    class EdgeList extends ArrayList<Edge> {private static final long serialVersionUID = 1L;}
    private List<Edge> initializeEdgePath(List<Edge> edgePath) {
        if (edgePath == null) {
            edgePath = new EdgeList();
        }

        return edgePath;
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
