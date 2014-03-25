package br.com.lealdn.grafo.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable {
    private int id;
    private String label;
    private List<Edge> edges;
    private Color color;
    private Node root;
    private int weight;

    public Node() {
        this.edges = new ArrayList<Edge>();
    }

    public Node(List<Edge> edges, int id, String label) {
        this.edges = new ArrayList<Edge>(edges);
        this.id = id;
        this.label = label;
    }

    public void addToEdges(Edge edge) {
        this.edges.add(edge);
    }
    public void removeEdgeFromList(Edge edge) {
        this.edges.remove(edge);
    }

    public Edge findEdge(Node toNode) {
        for (Edge edge : this.getEdges()) {
            Node node1 = edge.getNode1();
            Node node2 = edge.getNode2();

            if (node1.equals(this)) {
                if (node2.equals(toNode)) {
                    return edge;
                }
            }
            else if (node2.equals(this)) {
                if (node1.equals(toNode)) {
                    return edge;
                }
            }
        }

        return null;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the edges
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Node other = (Node) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() == Node.class) {
            return this.getId() - ((Node)o).getId();
        }

        return 0;
    }
}
