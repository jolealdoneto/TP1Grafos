package br.com.lealdn.grafo;

import java.util.List;

import br.com.lealdn.grafo.elements.Node;
import br.com.lealdn.grafo.input.FileReader;
import br.com.lealdn.grafo.traverse.BFS;
import br.com.lealdn.grafo.traverse.AbstractAdjMatrixTraverse;
import br.com.lealdn.grafo.traverse.FloydWarshall;
import br.com.lealdn.grafo.traverse.Johnson;
import br.com.lealdn.grafo.traverse.RepeatedSquaring;
import br.com.lealdn.grafo.visualization.View;

public class Start {

    public static void main(String[] args) {
        FileReader fr = new FileReader("/home/neto/prj/ufmg/01_2014/PAA/TP1/TP1/input/jose.gml");
        List<Node> nodes = fr.read();

        //BFS bfs = new BFS(nodes);

        //while (bfs.getNumberOfCommunities() < 2) {
        //    bfs.findMostUsedEdgeAndRemoveIt();
        //}

        //View.view(bfs.getNodes());

        //RepeatedSquaring rs = new RepeatedSquaring(nodes);
        //while (rs.getNumberOfCommunities() < 2) {
        //    rs.findMostUsedEdgeAndRemoveIt();
        //}
        //
        //View.view(rs.getNodes());
        
        FloydWarshall fw = new FloydWarshall(nodes);
        while (fw.getNumberOfCommunities() < 4) {
            fw.findMostUsedEdgeAndRemoveIt();
        }

        View.view(fw.getNodes());
        
        //Johnson johnson = new Johnson(nodes);
        //while (johnson.getNumberOfCommunities() < 4) {
        //    johnson.findMostUsedEdgeAndRemoveIt();
        //}

        //View.view(johnson.getNodes());
    }

}
