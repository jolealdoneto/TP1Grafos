package br.com.lealdn.grafo.traverse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class Johnson implements AllPairs {
    private List<Node> nodes;
    private Map<Edge, Integer> edgeCount;
    private Dijkstra.Datastructure datastructure;

    public Johnson(List<Node> nodes) {
        this(nodes, Dijkstra.Datastructure.LIST);
    }

    public Johnson(List<Node> nodes, Dijkstra.Datastructure datastructure) {
        this.nodes = new ArrayList<Node>(nodes);
        this.datastructure = datastructure;
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

    public Map.Entry<Edge, Integer> findMostUsedEdgeAndRemoveIt() {
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
            maxRow.getKey().removeEdge();
        }
        return maxRow;
    }

    public List<List<Node>> getCommunities() {
        BFS bfs = new BFS(this.nodes);
        return bfs.getCommunities();
    }


    public Set<Node> createSet(final Node node1, final Node node2) {
        return new HashSet<Node>() {{
            add(node1);
            add(node2);
        }};
    }

    public boolean doJohnson() {
        Node extraVertex = addNewSVertex(nodes);
        BellmanFord bf = new BellmanFord(nodes);
        Set<Set<Node>> alreadyCounted = new HashSet<Set<Node>>();

        if (bf.doBellmanFord(extraVertex)) {
            recalculateWeights(nodes);
            removeNode(nodes, extraVertex);

            edgeCount = new HashMap<Edge, Integer>();
            Dijkstra dijkstra = new Dijkstra(nodes, this.datastructure);
            for (Node node : nodes) {
                dijkstra.doDijkstra(node);
                for (Map.Entry<Node, List<Edge>> path : dijkstra.getEdgePath().entrySet()) {
                    Set<Node> nodeSet = createSet(node, path.getKey());
                    if (!alreadyCounted.contains(nodeSet)) {
                        alreadyCounted.add(nodeSet);
                        for (Edge edge : path.getValue()) {
                            if (!edgeCount.containsKey(edge)) {
                                edgeCount.put(edge, 0);
                            }

                            edgeCount.put(edge, edgeCount.get(edge) + 1);
                        }
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
