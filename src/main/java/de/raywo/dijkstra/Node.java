package de.raywo.dijkstra;

public class Node implements Comparable<Node> {
  int node, distance;


  Node(int node, int distance) {
    this.node = node;
    this.distance = distance;
  }


  public int compareTo(Node other) {
    return Integer.compare(this.distance, other.distance);
  }
}
