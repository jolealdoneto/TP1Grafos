package br.com.lealdn.grafo.input;

import java.util.ArrayList;
import java.util.List;

import br.com.lealdn.grafo.elements.Node;

public class ManualReader implements Reader {

    @Override
    public List<Node> read() {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(null, 1, null));
        nodes.add(new Node(null, 2, null));
        nodes.add(new Node(null, 3, null));
        nodes.add(new Node(null, 4, null));
        nodes.add(new Node(null, 5, null));
        nodes.add(new Node(null, 6, null));
        nodes.add(new Node(null, 7, null));
        nodes.add(new Node(null, 8, null));
        nodes.add(new Node(null, 9, null));
        nodes.add(new Node(null, 10, null));
        return null;
    }

}
