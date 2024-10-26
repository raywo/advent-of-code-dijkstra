package de.raywo.dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {

  private static Dijkstra instance;

  public static Dijkstra getInstance() {
    if (instance == null) {
      instance = new Dijkstra();
    }

    return instance;
  }

  private Dijkstra() {
  }


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


  private int[] preparedDistanceMatrix(Graph graph, int start) {
    int vertexCount = graph.vertexCount();
    int[] distances = new int[vertexCount];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    return distances;
  }

}
