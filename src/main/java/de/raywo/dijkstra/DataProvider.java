package de.raywo.dijkstra;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

  private final List<List<Edge>> graph;

  public DataProvider(String filePath) throws IOException, URISyntaxException {
    graph = buildGraph(readMatrixFromFile(filePath));
  }


  public List<List<Edge>> getGraph() {
    return graph;
  }


  public int vertexCount() {
    return graph.size();
  }


  private int[][] readMatrixFromFile(String filePath) throws IOException, URISyntaxException {
    Path path = Path.of(filePath);
    List<String> lines = Files.readAllLines(path);

    return parseCavernMap(lines);
  }


  private int[][] parseCavernMap(List<String> input) {
    int[][] map = new int[input.size()][];

    for (int y = 0; y < input.size(); y++) {
      String line = input.get(y);
      map[y] = new int[line.length()];

      for (int x = 0; x < line.length(); x++) {
        int riskLevel = Integer.parseInt(line.substring(x, x + 1));
        map[y][x] = riskLevel;
      }
    }

    return map;
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
