package br.com.lealdn.grafo.traverse;

import java.util.List;
import java.util.Map;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public interface AllPairs {
    public List<Node> getNodes();
    public Map.Entry<Edge, Integer> findMostUsedEdgeAndRemoveIt();
    public List<List<Node>> getCommunities();
}
