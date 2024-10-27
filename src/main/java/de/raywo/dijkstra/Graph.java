package de.raywo.dijkstra;

import java.util.ArrayList;
import java.util.List;



/**
 * Represents a graph structure using both an adjacency list and an
 * adjacency matrix.
 *
 * @author Ray Wojciechowski
 */
public class Graph {

  private final List<List<Edge>> graph;
  private final int[][] matrix;
  private Graph derivedGraph = null;


  /**
   * Constructs a Graph object from a given 2D adjacency matrix.
   *
   * @param matrix the 2D array representing the adjacency matrix of the graph.
   */
  public Graph(int[][] matrix) {
    this.graph = buildGraph(matrix);
    this.matrix = matrix;
  }


  /**
   * Returns the number of vertices in the graph.
   *
   * @return the number of vertices in the graph.
   */
  public int vertexCount() {
    return graph.size();
  }


  /**
   * Returns the dimension of the graph, which is the number of vertices along one side of the adjacency matrix.
   *
   * @return the dimension of the graph.
   */
  public int dimension() {
    return matrix.length;
  }


  /**
   * Retrieves the list of edges connected to a specific vertex in the graph.
   *
   * @param lineNo the index of the vertex in the graph for which the edges are
   *               to be retrieved.
   * @return a list of edges connected to the specified vertex.
   */
  public List<Edge> getEdges(int lineNo) {
    return graph.get(lineNo);
  }


  /**
   * Retrieves the adjacency matrix representing the graph.
   *
   * @return a 2D array that represents the adjacency matrix of the graph.
   */
  public int[][] getMatrix() {
    return matrix;
  }


  /**
   * Generates and returns a derived graph where each element in the adjacency
   * matrix is incremented by 1, wrapping around to 1 if the original value is
   * 9.
   *
   * @return the derived graph with updated matrix values.
   */
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


  /**
   * Constructs a graph representation from a given 2D matrix where each cell
   * in the matrix represents a vertex and its edges are determined by its
   * adjacent cells (up, down, left, right).
   *
   * @param matrix the 2D array (int[][]) representing the weights of the graph's edges.
   * @return a list of lists of Edge objects representing the graph, where the
   *         outer list corresponds to each vertex and the inner list contains
   *         the edges from that vertex to its adjacent vertices.
   */
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
