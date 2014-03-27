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
import java.util.Set;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class BellmanFord extends AbstractSingleSourceTraverse {
    public BellmanFord(List<Node> nodes) {
        super(nodes);
    }

    public boolean doBellmanFord(Node root) {
        initializeSingleSource(root);
        Set<Edge> edges = new HashSet<Edge>();

        for (Node n : this.nodes) {
            for (Node node : this.nodes) {
                for (Edge edge : node.getEdges()) {
                    edges.add(edge);
                    relax(node, edge.getOtherNode(node), edge);
                }
            }
        }

        for (Edge edge : edges) {
            if (edge.getNode2().getWeight() > edge.getNode1().getWeight() + edge.getWeight()) {
                return false;
            }
        }
        return true;
    }
}
