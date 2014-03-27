package br.com.lealdn.grafo.traverse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import java.util.Random;
import java.util.Set;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class Johnson {
    private List<Node> nodes;
    private Map<Edge, Integer> edgeCount;

    public Johnson(List<Node> nodes) {
        this.nodes = new ArrayList<Node>(nodes);
    }


    public Node addNewSVertex(List<Node> nodes) {
        Node node = new Node();
        node.setId(new Random().nextInt());

        for (Node vertex : nodes) {
            Edge edge = new Edge(new Random().nextInt(), node, vertex);
            edge.setWeight(0);

            node.addToEdges(edge);
        }
        this.nodes.add(node);

        return node;
    }

    private void recalculateWeights(List<Node> nodes) {
        for (Node node : nodes) {
            for (Edge edge : node.getEdges()) {
                edge.setWeight(edge.getWeight() + edge.getNode1().getWeight() - edge.getNode2().getWeight());
            }
        }
    }
    private void removeNode(List<Node> nodes, Node toRemove) {
        nodes.remove(toRemove);
    }

    public void findMostUsedEdgeAndRemoveIt() {
        doJohnson();

        Map.Entry<Edge, Integer> maxRow = null;
        for (Map.Entry<Edge, Integer> countEntry : edgeCount.entrySet()) {
            if (maxRow == null) {
                maxRow = countEntry;
            }
            else {
                if (maxRow.getValue() < countEntry.getValue()) {
                    maxRow = countEntry;
                }
            }
        }

        if (maxRow != null) {
            System.out.println("Most used: " + maxRow.getKey().getNode1().getId() + "->" + maxRow.getKey().getNode2().getId());
            maxRow.getKey().removeEdge();
        }
    }

    public int getNumberOfCommunities() {
        BFS bfs = new BFS(this.nodes);
        return bfs.getNumberOfCommunities();
    }


    public boolean doJohnson() {
        Node extraVertex = addNewSVertex(nodes);
        BellmanFord bf = new BellmanFord(nodes);

        if (bf.doBellmanFord(extraVertex)) {
            recalculateWeights(nodes);
            removeNode(nodes, extraVertex);

            edgeCount = new HashMap<Edge, Integer>();
            Dijkstra dijkstra = new Dijkstra(nodes);
            for (Node node : nodes) {
                dijkstra.doDijkstra(node);
                for (Map.Entry<Node, List<Edge>> path : dijkstra.getEdgePath().entrySet()) {
                    for (Edge edge : path.getValue()) {
                        if (!edgeCount.containsKey(edge)) {
                            edgeCount.put(edge, 0);
                        }

                        edgeCount.put(edge, edgeCount.get(edge) + 1);
                    }
                }
            }

            return true;
        }
        else {
            return false;
        }
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
