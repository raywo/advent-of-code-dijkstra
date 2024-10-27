package de.raywo.dijkstra;

/**
 * Represents a node in a graph with an associated distance.
 * This class is primarily used in graph algorithms such as Dijkstra's algorithm
 * to track nodes and their distances from a starting point.
 */
public class Node implements Comparable<Node> {
  int node, distance;


  /**
   * Constructs a new Node with a specified identifier and distance.
   *
   * @param node the identifier of the node
   * @param distance the distance associated with the node
   */
  Node(int node, int distance) {
    this.node = node;
    this.distance = distance;
  }


  /**
   * Compares this node with the specified node for order based on their distances.
   *
   * @param other the node to be compared
   * @return a negative integer, zero, or a positive integer as this node's distance
   *         is less than, equal to, or greater than the specified node's distance
   */
  public int compareTo(Node other) {
    return Integer.compare(this.distance, other.distance);
  }
}
