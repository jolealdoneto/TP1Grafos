package br.com.lealdn.grafo.traverse;

import java.util.ArrayList;
import java.util.List;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class FloydWarshall extends AbstractAdjMatrixTraverse {
    private List<Edge>[][] pathsForEachNode;

    public FloydWarshall(List<Node> nodes) {
        super(nodes);
    }

    private List<Edge>[][] feedWeightMatrixToPathsForEachNode(Integer[][] weightMatrix) {
        this.pathsForEachNode = new EdgeList[adjMatrix.length][adjMatrix.length];

        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix.length; j++) {
                if (weightMatrix[i][j] != null && weightMatrix[i][j] == 1) {
                    this.pathsForEachNode[i][j] = initializeEdgePath(pathsForEachNode[i][j]);
                    this.pathsForEachNode[i][j].add(retrieveEdgeGiveIndexes(i, j));
                }
            }
        }

        return pathsForEachNode;
    }

    public Integer[][] doFloydWarshall() {
        this.weightMatrix = createWeightMatrix(this.nodes);
        this.adjMatrix = initializeD0Matrix(this.adjMatrix);
        this.pathsForEachNode = feedWeightMatrixToPathsForEachNode(weightMatrix);

        for (int k = 0; k < this.nodes.size(); k++) {
            for (int i = 0; i < this.nodes.size(); i++) {
                for (int j = 0; j < this.nodes.size(); j++) {
                    final int sumMiddle = sum(adjMatrix[i][k], adjMatrix[k][j]);
                    if (sumMiddle < adjMatrix[i][j] && sumMiddle != Integer.MAX_VALUE) {
                        pathsForEachNode[i][j] = initializeEdgePath(pathsForEachNode[i][j]);
                        pathsForEachNode[i][k] = initializeEdgePath(pathsForEachNode[i][k]);
                        pathsForEachNode[k][j] = initializeEdgePath(pathsForEachNode[k][j]);
                        pathsForEachNode[i][j].addAll(pathsForEachNode[i][k]);
                        pathsForEachNode[i][j].addAll(pathsForEachNode[k][j]);

                        adjMatrix[i][j] = sumMiddle;
                    }
                }
            }
        }

        return adjMatrix;
    }

    public void findMostUsedEdgeAndRemoveIt() {
        doFloydWarshall();
        Edge edge = findMostUsedEdgeGiveEdgeList(this.pathsForEachNode);
        if (edge != null) {
            edge.removeEdge();
        }
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
                Integer weight = weightMatrix[i][j];
                adjMatrix[i][j] = weight != null ? weight : Integer.MAX_VALUE;
            }
        }

        return adjMatrix;
    }
}
