package br.com.lealdn.grafo.traverse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class Dijkstra extends AbstractSingleSourceTraverse {
    private Map<Node, List<Edge>> edgePath;

    public Dijkstra(List<Node> nodes) {
        super(nodes);
    }

    interface Extratable<T> {
        public T extractMin(); 
        public boolean isEmpty();
    }

    final Comparator<Node> comparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return ((Integer)o1.getWeight()).compareTo(o2.getWeight());
        }
    };

    class ExtratableArrayList extends ArrayList<Node> implements Extratable<Node> {
        public ExtratableArrayList(List<Node> nodes) {
            super(nodes);
        }

        public Node extractMin() {
            Node minNode = get(0);
            for (Node node : this) {
                if (comparator.compare(minNode, node) > 0) {
                    minNode = node;
                }
            }
            this.remove(minNode);

            return minNode;
        }
    }
    class ExtratablePriorityQueue extends PriorityQueue<Node> implements Extratable<Node> {
        public ExtratablePriorityQueue(List<Node> nodes) {
            super(nodes.size(), comparator);

            for (Node node : nodes) {
                this.add(node);
            }
        }

        public Node extractMin() {
            return poll();
        }
    }

    public void doDijkstra(Node root) {
        edgePath = new HashMap<Node, List<Edge>>();

        initializeSingleSource(root);
        Extratable<Node> extratable = new ExtratableArrayList(this.nodes);

        while (!extratable.isEmpty()) {
            Node node = extratable.extractMin();
            for (Edge edge : node.getEdges()) {
                Node otherNode = edge.getOtherNode(node);
                if (relax(node, otherNode, edge)) {
                    initializeEdgePathIfNull(edgePath, otherNode);
                    initializeEdgePathIfNull(edgePath, node);

                    List<Edge> path = edgePath.get(otherNode);
                    path.clear();
                    path.addAll(edgePath.get(node));
                    path.add(edge);
                }
            }
        }

        System.out.println("----------");
        System.out.println("root: " + root.getId());
        for (Map.Entry<Node, List<Edge>> row : edgePath.entrySet()) {
            System.out.println("----------");
            System.out.println("row: " + row.getKey().getId());
            for (Edge edge : row.getValue()) {
                System.out.println("edge: " + edge.getNode1().getId() + "->" + edge.getNode2().getId());
            }
        }
        
        System.out.println();
    }

    /**
     * @return the edgePath
     */
    public Map<Node, List<Edge>> getEdgePath() {
        return edgePath;
    }

    /**
     * @param edgePath the edgePath to set
     */
    public void setEdgePath(Map<Node, List<Edge>> edgePath) {
        this.edgePath = edgePath;
    }

}
