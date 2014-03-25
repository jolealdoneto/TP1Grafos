package br.com.lealdn.grafo.elements;

public class Edge {
    private int id;
    private Node node1;
    private Node node2;

    public Edge(int id, Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.id = id;
    }

    public Node getOtherNode(Node node) {
        return node.equals(this.getNode1()) ? this.getNode2() : this.getNode1();
    }

    public void removeEdge() {
        this.getNode1().removeEdgeFromList(this);
        this.getNode2().removeEdgeFromList(this);
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
     * @return the node1
     */
    public Node getNode1() {
        return node1;
    }

    /**
     * @param node1 the node1 to set
     */
    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    /**
     * @return the node2
     */
    public Node getNode2() {
        return node2;
    }

    /**
     * @param node2 the node2 to set
     */
    public void setNode2(Node node2) {
        this.node2 = node2;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((node1 == null) ? 0 : node1.hashCode()) + ((node2 == null) ? 0 : node2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (id != other.id)
			return false;
		
		return true;
	}
}
