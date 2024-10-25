package de.raywo.dijkstra;

import java.util.ArrayList;
import java.util.List;

public class Graph {

  private final List<List<Edge>> graph;


  public Graph(int[][] matrix) {
    this.graph = buildGraph(matrix);
  }


  public int vertexCount() {
    return graph.size();
  }


  public List<Edge> getEdges(int lineNo) {
    return graph.get(lineNo);
  }


  private List<List<Edge>> buildGraph(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    int vertexCount = rows * cols;

    List<List<Edge>> graph = new ArrayList<>();
    for (int i = 0; i < vertexCount; i++) {
      graph.add(new ArrayList<>());
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int node = i * cols + j;

        if (i > 0) {
          int targetNode = (i - 1) * cols + j;
          graph.get(node).add(new Edge(targetNode, matrix[i - 1][j]));
        }
        if (i < rows - 1) {
          int targetNode = (i + 1) * cols + j;
          graph.get(node).add(new Edge(targetNode, matrix[i + 1][j]));
        }
        if (j > 0) {
          int targetNode = i * cols + (j - 1);
          graph.get(node).add(new Edge(targetNode, matrix[i][j - 1]));
        }
        if (j < cols - 1) {
          int targetNode = i * cols + (j + 1);
          graph.get(node).add(new Edge(targetNode, matrix[i][j + 1]));
        }
      }
    }

    return graph;
  }
}
