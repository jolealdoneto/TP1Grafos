package br.com.lealdn.grafo.traverse;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class BFS {
    private List<Node> nodes;
    private Map<Node, List<Edge>> edgePath;

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

    public BFS(List<Node> nodes) {
        this.nodes = new ArrayList<Node>(nodes);
    }

    private void initializeMapForNode(Map<Node, List<Edge>> edgeList, Node node) {
        if (!edgeList.containsKey(node)) {
            edgeList.put(node, new ArrayList<Edge>());
        }
    }
    private void initializeNodeList(List<Node> nodes) {
        for (Node node : nodes) {
            node.setColor(Color.white);
        }
    }
    public Map<Edge, Integer> doBFS(Node root) {
        initializeNodeList(this.nodes);

        root.setColor(Color.GRAY);
        root.setWeight(0);
        root.setRoot(null);

        edgePath = new HashMap<Node, List<Edge>>();
        Queue<Node> queue = new LinkedList<Node>();
        initializeMapForNode(edgePath, root);
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Edge e : node.getEdges()) {
                final Node adj = e.getOtherNode(node);
                initializeMapForNode(edgePath, adj);

                if (adj.getColor() == Color.white) {
                    adj.setColor(Color.GRAY);
                    adj.setWeight(node.getWeight() + 1);
                    adj.setRoot(node);
                    List<Edge> adjEdgePath = edgePath.get(adj);
                    adjEdgePath.addAll(edgePath.get(node));
                    adjEdgePath.add(e);

                    queue.add(adj);
                }
            }
            node.setColor(Color.black);
        }

        Map<Edge, Integer> edgeCount = new HashMap<Edge, Integer>();
        for (Map.Entry<Node, List<Edge>> row : edgePath.entrySet()) {
            for (Edge e : row.getValue()) {
                if (!edgeCount.containsKey(e)) {
                    edgeCount.put(e, 0);
                }

                edgeCount.put(e, edgeCount.get(e) + 1);
            }
        }
        return edgeCount;
    }

    public void findMostUsedEdgeAndRemoveIt() {
        Map<Edge, Integer> edgeCount = new HashMap<Edge, Integer>();
        for (Node node : this.nodes) {
            Map<Edge, Integer> localCount = doBFS(node);

            for (Map.Entry<Edge, Integer> row : localCount.entrySet()) {
                if (!edgeCount.containsKey(row.getKey())) {
                    edgeCount.put(row.getKey(), 0);
                }
                edgeCount.put(row.getKey(), edgeCount.get(row.getKey()) + row.getValue());
            }
        }
        
        Map.Entry<Edge, Integer> maxEntry = new AbstractMap.SimpleEntry<Edge, Integer>(null, 0);
        for (Map.Entry<Edge, Integer> row : edgeCount.entrySet()) {
            if (maxEntry.getValue() < row.getValue()) {
                maxEntry = new AbstractMap.SimpleEntry<Edge, Integer>(row);
            }
        }

        if (maxEntry.getKey() != null) {
            maxEntry.getKey().removeEdge();
        }
    }

    public int getNumberOfCommunities() {
        List<Node> nodes = new ArrayList<Node>(this.nodes);
        int communityNumber = 0;
        while(!nodes.isEmpty()) {
            Node node = nodes.get(0);
            doBFS(node);
            nodes.removeAll(this.edgePath.keySet());
            communityNumber++;
        }

        return communityNumber;
    }
}
