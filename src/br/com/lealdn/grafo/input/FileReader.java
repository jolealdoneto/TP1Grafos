package br.com.lealdn.grafo.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.com.lealdn.grafo.elements.Edge;
import br.com.lealdn.grafo.elements.Node;

public class FileReader implements Reader {
    private String filename;

    public FileReader(String filename) {
        this.filename = filename;
    }

    private class EOF extends Exception {}

    private String readLine(BufferedReader br) throws IOException, EOF {
        return readLine(br, "");
    }
    private String readLine(BufferedReader br, String firstLine) throws IOException, EOF {
        String line = firstLine != null && !firstLine.isEmpty() ? firstLine : br.readLine();
        checkForEOF(line);
        return line.trim();
    }
    private void checkForEOF(String line) throws EOF {
        if (line == null) {
            throw new EOF();
        }
    }

    private Node findNode(BufferedReader br, String firstLine) throws IOException, EOF {
        String line = readLine(br, firstLine);
        if (line.equals("node")) {
            readLine(br);
            line = readLine(br);
            if (line.indexOf("id") > -1) {
                final int id = Integer.valueOf(line.substring(line.indexOf("id") + 3).trim());

                Node node = new Node();
                node.setId(id);

                return node;
            }
        }
        return null;
    }
    
    private Edge findEdge(BufferedReader br, String firstLine, Map<Integer, Node> nodeMap) throws IOException, EOF {
        String line = readLine(br, firstLine);
        if (line.equals("edge")) {
            br.readLine();
            line = readLine(br);
            int source = 0;
            int target = 0;
            if (line.indexOf("source") > -1) {
                source = Integer.valueOf(line.substring(6).trim());
            }
            line = readLine(br);
            if (line.indexOf("target") > -1) {
                target = Integer.valueOf(line.substring(6).trim());
            }

            Node node1 = nodeMap.get(source);
            Node node2 = nodeMap.get(target);
            if (node1 != null && node2 != null) {
                Edge edge = new Edge(new Random().nextInt(), node1, node2);

                node1.addToEdges(edge);
                node2.addToEdges(edge);

                return edge;
            }
        }
        return null;
    }

    private List<Node> readGraph() {
        Path file = Paths.get(this.filename);

        Map<Integer, Node> nodes = new HashMap<Integer, Node>();
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(file, Charset.forName("UTF-8"));

            String line = null;
            while (true) {
                line = br.readLine();
                Node node = findNode(br, line);
                if (node != null) {
                    nodes.put(node.getId(), node);
                }
                else {
                    findEdge(br, line, nodes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(EOF eof) {

        } finally {
            try {
                if (br != null) { 
                    br.close();
                }
            } catch(IOException e) {}
        }

        return new ArrayList<Node>(nodes.values());
    }

    @Override
    public List<Node> read() {
        return readGraph();
    }
}
