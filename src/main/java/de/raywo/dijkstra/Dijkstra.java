package de.raywo.dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * The Dijkstra class is a singleton implementation of Dijkstra's algorithm
 * for finding the shortest paths between nodes in a graph.
 */
public class Dijkstra {

  private static Dijkstra instance;


  /**
   * Provides access to the singleton instance of the Dijkstra class.
   * If the instance does not already exist, it is created.
   *
   * @return the singleton instance of the Dijkstra class.
   */
  public static Dijkstra getInstance() {
    if (instance == null) {
      instance = new Dijkstra();
    }

    return instance;
  }


  /**
   * Private constructor for the Dijkstra class. This constructor is private to
   * ensure that the Dijkstra class follows the singleton pattern, preventing
   * external instantiation.
   */
  private Dijkstra() {
  }


  /**
   * Calculates the shortest path from the start node to the target node in
   * the given graph using Dijkstra's algorithm and returns the distances of
   * all nodes from the start node. (Shortest path means the path with the
   * lowest weights on it.)
   *
   * @param graph   the graph containing the nodes and edges
   * @param start   the start node from which to calculate the shortest path
   * @param target  the target node to which the shortest path is calculated
   * @return an array representing the shortest distances from the start node
   *         to each node in the graph
   */
  public int[] calculateShortestPath(Graph graph,
                                     int start,
                                     int target) {
    final int[] distances = preparedDistanceMatrix(graph, start);

    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.add(new Node(start, 0));

    while (!pq.isEmpty()) {
      Node current = pq.poll();
      int u = current.node;

      if (u == target) {
        return distances;
      }

      for (Edge edge : graph.getEdges(u)) {
        int v = edge.targetNode;
        int weight = edge.weight;

        if (distances[u] + weight < distances[v]) {
          distances[v] = distances[u] + weight;
          pq.add(new Node(v, distances[v]));
        }
      }
    }

    return distances;
  }


  /**
   * Initializes and prepares the distance matrix for the graph, setting the
   * distance to the start vertex as 0 and all other distances to Integer.MAX_VALUE.
   *
   * @param graph the graph for which the distance matrix is prepared
   * @param start the start vertex index
   * @return an array representing the initial distances from the start vertex
   *         to all other vertices
   */
  private int[] preparedDistanceMatrix(Graph graph, int start) {
    int vertexCount = graph.vertexCount();
    int[] distances = new int[vertexCount];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    return distances;
  }

}
