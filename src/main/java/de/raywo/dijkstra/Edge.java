package de.raywo.dijkstra;

/**
 * The Edge class represents a connection between two nodes in a graph.
 * Each edge has a target node and a weight associated with it.
 *
 * @author Ray Wojciechowski (AI generated)
 */
public class Edge {
  int targetNode, weight;


  /**
   * Constructs an Edge with the specified target node and weight.
   *
   * @param target the target node this edge points to
   * @param weight the weight of this edge
   */
  Edge(int target, int weight) {
    this.targetNode = target;
    this.weight = weight;
  }
}
