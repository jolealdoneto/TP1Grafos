package br.com.lealdn.grafo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.lealdn.grafo.elements.Node;
import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.input.Reader;
import br.com.lealdn.grafo.input.TPCustomFormatReader;
import br.com.lealdn.grafo.traverse.AllPairs;
import br.com.lealdn.grafo.traverse.BFS;
import br.com.lealdn.grafo.traverse.Dijkstra;
import br.com.lealdn.grafo.traverse.FloydWarshall;
import br.com.lealdn.grafo.traverse.Johnson;
import br.com.lealdn.grafo.traverse.RepeatedSquaring;

public class Start {
    final static int FILENAME = 0;
    final static int COMMUNITY = 1;
    final static int ALGORITHM = 2;

    public static AllPairs assignAlgorithmAccordingToChoice(String[] args,
            List<Node> nodes) {
        if (args.length > 2) {
            final int algorithm = Integer.valueOf(args[ALGORITHM]);
            switch (algorithm) {
            case 0:
                return new BFS(nodes);
            case 1:
                return new RepeatedSquaring(nodes);
            case 2:
                return new FloydWarshall(nodes);
            case 3:
                return new Johnson(nodes, Dijkstra.Datastructure.LIST);
            case 4:
                return new Johnson(nodes, Dijkstra.Datastructure.HEAP);
            }
        }
        printUse();
        throw new RuntimeException("Algoritmo não reconhecido!");
    }

    public static int getCommunityNumber(String[] args) {
        return Integer.valueOf(args[COMMUNITY]);
    }

    public static void printUse() {
        System.out
                .println("usage: tp.java <arquivo aresta> <numero comunidades> <algoritmo>");
        System.out.println("\nAlgoritmos:");
        System.out
                .println("\t0: BFS, 1: RepeatedSquaring, 2: FloydWarshall, 3: Johnson w\\ list, 4: Johnson w\\ heap");
    }

    public static void printOutNodes(Map<Node, Integer> nodes) {
        for (Map.Entry<Node, Integer> entry : nodes.entrySet()) {
            System.out.println(entry.getKey().getId() + " " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            printUse();
            return;
        }

        final Reader fr = new TPCustomFormatReader(args[FILENAME]);
        final int communityNumber = getCommunityNumber(args);

        List<Node> nodes = fr.read();
        AllPairs algorithm = assignAlgorithmAccordingToChoice(args, nodes);

        List<List<Node>> communities = null;
        while(true) {
            communities = algorithm.getCommunities();
            if (communities.size() == communityNumber)
                break;
            Map.Entry<Edge, Integer> row = algorithm.findMostUsedEdgeAndRemoveIt();
            //System.out.println("Aresta removida: " + row.getKey().getNode1().getId() + "-" + row.getKey().getNode2().getId() + ":" + row.getValue());
        }
        int currentCommunity = 1;
        Map<Node, Integer> sortedMap = new TreeMap<Node, Integer>();
        for (List<Node> nodesInCommunity : communities) {
            for (Node node : nodesInCommunity) {
                sortedMap.put(node, currentCommunity);
            }
            currentCommunity++;
        }
        printOutNodes(sortedMap);
        //View.view(algorithm.getNodes());
    }
}
