package de.raywo.dijkstra;

import java.util.ArrayList;
import java.util.List;

public class Graph {

  private final List<List<Edge>> graph;
  private final int[][] matrix;
  private Graph derivedGraph = null;


  public Graph(int[][] matrix) {
    this.graph = buildGraph(matrix);
    this.matrix = matrix;
  }


  public int vertexCount() {
    return graph.size();
  }


  public int dimension() {
    return matrix.length;
  }


  public List<Edge> getEdges(int lineNo) {
    return graph.get(lineNo);
  }


  public int[][] getMatrix() {
    return matrix;
  }


  public Graph getDerivedGraph() {
    if (derivedGraph != null) return derivedGraph;

    int rows = matrix.length;
    int cols = matrix[0].length;
    int[][] result = new int[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        final int newValue = matrix[i][j] + 1;
        result[i][j] = newValue <= 9 ? newValue : 1;
      }
    }

    this.derivedGraph = new Graph(result);

    return derivedGraph;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    int cols = matrix[0].length;

    for (int[] row : matrix) {
      for (int j = 0; j < cols; j++) {
        sb.append(row[j]);
      }

      sb.append("\n");
    }

    return sb.toString();
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
