package de.raywo.dijkstra;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

  private final DataProvider provider;


  public Dijkstra(DataProvider provider) throws IOException, URISyntaxException {
    this.provider = provider;
  }


  public int[] calculateShortestPath(int start,
                                     int target) {
    final int[] distances = preparedDistanceMatrix(start);

    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.add(new Node(start, 0));

    while (!pq.isEmpty()) {
      Node current = pq.poll();
      int u = current.node;

      if (u == target) {
        return distances;
      }

      final List<List<Edge>> graph = provider.getGraph();

      for (Edge edge : graph.get(u)) {
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


  private int[] preparedDistanceMatrix(int start) {
    int vertexCount = provider.vertexCount();
    int[] distances = new int[vertexCount];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    return distances;
  }


  public static void main(String[] args) throws IOException, URISyntaxException {
//    String filePath = "src/main/resources/day15-ray.txt";
    String filePath = "src/main/resources/day15-ray-pt2-ex.txt";

    DataProvider provider = new DataProvider(filePath);
    Dijkstra dijkstra = new Dijkstra(provider);
    int source = 0;
    int target = provider.vertexCount() - 1;

    long startTime = System.nanoTime();
    int[] distances = dijkstra.calculateShortestPath(source, target);
    long endTime = System.nanoTime();
    long durationNS = endTime - startTime;
    double durationMS = durationNS / 1000000.0;
    double durationSec = durationMS / 1000.0;

    final NumberFormat numberFormatter = NumberFormat.getNumberInstance();
    numberFormatter.setGroupingUsed(true);
    final String durationFormatted = numberFormatter.format(durationNS);

    numberFormatter.setMaximumFractionDigits(5);
    numberFormatter.setMinimumFractionDigits(5);
    final String milliSecondsFormatted = numberFormatter.format(durationMS);
    final String secondsFormatted = numberFormatter.format(durationSec);

    System.out.println("Das Gesamtrisiko vom Start zum Ziel beträgt: " + distances[target]);
    System.out.println("Die Berechnung dauerte " + durationFormatted
        + " Nanosekunden (" + milliSecondsFormatted + "ms, "
        + secondsFormatted + "s)");
  }
}
