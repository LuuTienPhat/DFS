/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DFS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import view.view2;

/**
 *
 * @author Phat
 */
public class Graph {

    public class Neighbor {

        public int vertexNum;
        public int weightNum;

        public Neighbor(int vnum) {
            this.vertexNum = vnum;
        }

        public Neighbor(int vnum, int weightNum) {
            this.vertexNum = vnum;
            this.weightNum = weightNum;
        }
    }

    public class Vertex {

        public String name;
        public LinkedList<Neighbor> adjList;
        public int x, y;
        public Vertex parent;

        public Vertex(String name) {
            this.name = name;
            this.adjList = new LinkedList<>();
        }

        public Vertex(String name, int x, int y) {
            this.name = name;
            this.adjList = new LinkedList<>();
            this.x = x;
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Vertex{" + "name=" + name + ", x=" + x + ", y=" + y + '}';
        }
    }

    public int V;
    public LinkedList<Vertex> vertexList;
    public boolean directed;
    public boolean isWeightedGraph;

    public Graph() {
        this.vertexList = new LinkedList<>();
    }

    public Graph(boolean isDirectedGraph, boolean isWeightedGraph) {
        this.vertexList = new LinkedList<>();
        this.directed = isDirectedGraph;
        this.isWeightedGraph = isWeightedGraph;
    }

    public Graph(int V, boolean directed) {
        this.V = V;
        this.vertexList = new LinkedList<>();
        this.directed = directed;
    }

    public Graph(int[] s, boolean directed) {
        this.V = s.length;
        this.vertexList = new LinkedList<>();
        this.directed = directed;

        for (int i = 0; i < s.length; i++) {
            addVertex(Integer.toString(s[i]));
        }
    }

    public Graph(int[] s, boolean directed, boolean isWeightedGraph) {
        this.V = s.length;
        this.vertexList = new LinkedList<>();
        this.directed = directed;
        this.isWeightedGraph = isWeightedGraph;

        for (int i = 0; i < s.length; i++) {
            addVertex(Integer.toString(s[i]));
        }
    }

    public Graph(String[] s, boolean directed, boolean isWeightedGraph) {
        this.V = s.length;
        this.vertexList = new LinkedList<>();
        this.directed = directed;
        this.isWeightedGraph = isWeightedGraph;

        for (String item : s) {
            addVertex(item);
        }
    }

    public Graph(String[] s, boolean directed) {
        this.V = s.length;
        this.vertexList = new LinkedList<>();
        this.directed = directed;

        for (String item : s) {
            addVertex(item);
        }
    }

    public int indexForName(String name) {
        for (int v = 0; v < vertexList.size(); v++) {
            if (vertexList.get(v).name.equals(name)) {
                return v;
            }
        }
        return -1;
    }

    public void addVertex(String name) {
        int i;
        for (i = 0; i < vertexList.size(); i++) {
            if (name.compareTo(vertexList.get(i).name) < 0) {
                break;
            }
        }
        vertexList.add(i, new Vertex(name));
    }

    public void addVertex(String name, int x, int y) {
//        int i;
//        for (i = 0; i < vertexList.size(); i++) {
//            if (name.compareTo(vertexList.get(i).name) < 0) {
//                break;
//            }
//        }
//        vertexList.add(i, new Vertex(name, x, y));
        vertexList.add(new Vertex(name, x, y));
    }

    public Vertex getVertex(String vertexName) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexName.compareTo(vertexList.get(i).name) == 0) {
                return vertexList.get(i);
            }
        }
        return null;
    }

    public void addNeighbor(LinkedList<Neighbor> adjList, int vertexNum) {
        int i = 0;
        for (Neighbor nbr : adjList) {
            if (vertexList.get(vertexNum).name.compareTo(vertexList.get(nbr.vertexNum).name) < 0) {
                break;
            }
            i++;
        }
        adjList.add(i, new Neighbor(vertexNum));
    }

    public void addNeighbor(LinkedList<Neighbor> adjList, int vertexNum, int weightNum) {
        int i;
        for (i = 0; i < adjList.size(); i++) {
            Neighbor nbr = adjList.get(i);
            if (weightNum < nbr.weightNum) {
                break;
            }
//            else if (weightNum == nbr.weightNum) {
//                if (vertexList.get(vertexNum).name.compareTo(vertexList.get(nbr.vertexNum).name) < 0) {
//                    break;
//                } else if (vertexList.get(vertexNum).name.compareTo(vertexList.get(nbr.vertexNum).name) > 0) {
//                    i++;
//                    break;
//                }
//            }
        }
        if (i == adjList.size()) {
            adjList.addLast(new Neighbor(vertexNum, weightNum));
        } else {
            adjList.add(i, new Neighbor(vertexNum, weightNum));
        }

    }

    public void addEdge(int number, int neighborNumber) {
        String name = Integer.toString(number);
        String neighborName = Integer.toString(neighborNumber);

        int v1 = indexForName(name);
        int v2 = indexForName(neighborName);

        addNeighbor(vertexList.get(v1).adjList, v2);
        if (!this.directed) {
            addNeighbor(vertexList.get(v2).adjList, v1);
        }
    }

    public void addEdge(int number, int neighborNumber, int weightNumber) {
        String name = Integer.toString(number);
        String neighborName = Integer.toString(neighborNumber);

        int v1 = indexForName(name);
        int v2 = indexForName(neighborName);

        addNeighbor(vertexList.get(v1).adjList, v2, weightNumber);
        if (!this.directed) {
            addNeighbor(vertexList.get(v2).adjList, v1, weightNumber);
        }
    }

    public void addEdge(String name, String neighborName, int weightNumber) {
        // read vertex names and translate to vertex numbers
        int v1 = indexForName(name);
        int v2 = indexForName(neighborName);

        // add v2 to front of v1's adjacency list and
        // add v1 to front of v2's adjacency list
        //adjLists[v1].adjList = new Neighbor(v2, vertexList[v1].adjList);
        addNeighbor(vertexList.get(v1).adjList, v2, weightNumber);
        if (!this.directed) {
            //adjLists[v2].adjList = new Neighbor(v1, vertexList[v2].adjList);
            addNeighbor(vertexList.get(v2).adjList, v1, weightNumber);
        }
    }

    public void addEdge(String name, String neighborName) {
        // read vertex names and translate to vertex numbers
        int v1 = indexForName(name);
        int v2 = indexForName(neighborName);

        // add v2 to front of v1's adjacency list and
        // add v1 to front of v2's adjacency list
        //adjLists[v1].adjList = new Neighbor(v2, vertexList[v1].adjList);
        addNeighbor(vertexList.get(v1).adjList, v2);
        if (!this.directed) {
            //adjLists[v2].adjList = new Neighbor(v1, vertexList[v2].adjList);
            addNeighbor(vertexList.get(v2).adjList, v1);
        }
    }

    // recursive dfs
    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        System.out.println("visiting " + vertexList.get(v).name);
        for (Neighbor nbr : vertexList.get(v).adjList) {
            if (!visited[nbr.vertexNum]) {
                //System.out.println("\n" + vertexList[v].name + "--" + vertexList[nbr.vertexNum].name);

                System.out.println("\n" + vertexList.get(v).name + "--" + vertexList.get(nbr.vertexNum).name);
                dfs(nbr.vertexNum, visited);
            }
        }
    }

    public void dfs(String name) {
        int index = indexForName(name);

        boolean[] visited = new boolean[vertexList.size()];
        for (int v = index; v < visited.length; v++) {
            if (!visited[v]) {
                System.out.println("\nSTARTING AT " + vertexList.get(v).name);
                dfs(v, visited);
            }
        }

        for (int v = 0; v < index; v++) {
            if (!visited[v]) {
                System.out.println("\nSTARTING AT " + vertexList.get(v).name);
                dfs(v, visited);
            }
        }
    }

    public void print() {
        for (int v = 0; v < vertexList.size(); v++) {
            System.out.print(vertexList.get(v).name);
            for (Neighbor nbr : vertexList.get(v).adjList) {
                System.out.print(" --> " + vertexList.get(nbr.vertexNum).name);
            }
            System.out.println("\n");
        }
    }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter graph input file name: ");
//        String file = sc.nextLine();
//        Graph graph = new Graph(file);

//        int[] s = {0, 1, 2, 3, 4, 5};
//        Graph graph = new Graph(s, true);
//        graph.addEdge(4, 5);
//        graph.addEdge(0, 2);
//        //graph.addEdge(0, 1);
//
//        graph.addEdge(4, 0);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
//        //graph.addEdge(2, 3);
//        //graph.addEdge(3, 4);
//
//        graph.addEdge(4, 1);
        String[] s = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        Graph graph = new Graph(s, true);
        graph.addEdge("a", "b");
        graph.addEdge("c", "a");
        graph.addEdge("e", "f");
        graph.addEdge("b", "e");

        graph.addEdge("b", "i");
        graph.addEdge("h", "b");

        graph.addEdge("d", "c");
        graph.addEdge("c", "e");

        graph.addEdge("g", "e");
        graph.addEdge("i", "h");
//
//        graph.print();
//        graph.dfs("d");

//        Graph graph = new Graph(s, true, true);
//        graph.addEdge("a", "b", 15);
//        graph.addEdge("a", "c", 25);
//        graph.addEdge("e", "f", 10);
//        graph.addEdge("b", "e", 10);
//        
//        graph.addEdge("b", "i", 25);
//        graph.addEdge("b", "h", 5);
//
//        graph.addEdge("c", "d", 10);
//        graph.addEdge("c", "e", 20);
//        
//        graph.addEdge("e", "g", 5);
//        graph.addEdge("h", "i", 15);
//        graph.addEdge("a", "b", 15);
//        graph.addEdge("c", "a", 25);
//        graph.addEdge("e", "f", 10);
//        graph.addEdge("b", "e", 10);
//
//        graph.addEdge("b", "i", 25);
//        graph.addEdge("h", "b", 5);
//
//        graph.addEdge("d", "c", 10);
//        graph.addEdge("c", "e", 20);
//
//        graph.addEdge("g", "e", 5);
//        graph.addEdge("i", "h", 15);
//        graph.print();
//        graph.dfs("a");
    }
}

//public class Graph {
//    private int V; // No. of vertices
// 
//    // Array  of lists for
//    // Adjacency List Representation
//    private LinkedList<Integer> adj[];
// 
//    // Constructor
//    @SuppressWarnings("unchecked") Graph(int v)
//    {
//        V = v;
//        adj = new LinkedList[v];
//        for (int i = 0; i < v; ++i)
//            adj[i] = new LinkedList();
//    }
// 
//    // Function to add an edge into the graph
//    public void addEdge(int v, int w)
//    {
//        adj[v].add(w); // Add w to v's list.
//    }
// 
//    // A function used by DFS
//    public void DFSUtil(int v, boolean visited[])
//    {
//        // Mark the current node as visited and print it
//        visited[v] = true;
//        System.out.print(v + " ");
// 
//        // Recur for all the vertices adjacent to this
//        // vertex
//        Iterator<Integer> i = adj[v].listIterator();
//        while (i.hasNext()) {
//            int n = i.next();
//            if (!visited[n])
//                DFSUtil(n, visited);
//        }
//    }
// 
//    // The function to do DFS traversal. It uses recursive
//    // DFSUtil()
//    public void DFS()
//    {
//        // Mark all the vertices as not visited(set as
//        // false by default in java)
//        boolean visited[] = new boolean[V];
// 
//        // Call the recursive helper function to print DFS
//        // traversal starting from all vertices one by one
//        for (int i = 0; i < V; ++i)
//            if (visited[i] == false)
//                DFSUtil(i, visited);
//    }
// 
//    // Driver Code
//    public static void main(String args[])
//    {
//        Graph g = new Graph(4);
// 
//        g.addEdge(0, 1);
//        g.addEdge(0, 2);
//        g.addEdge(1, 1);
//        g.addEdge(1, 2);
//        g.addEdge(2, 0);
//        g.addEdge(2, 3);
//        g.addEdge(3, 3);
// 
//        System.out.println(
//            "Following is Depth First Traversal");
// 
//        g.DFS();
//    }
//}
