package de.raywo.dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataProvider {

  private final Graph graph;
  private Graph expandedGraph = null;


  public DataProvider(String filePath) throws IOException {
    graph = new Graph(readMatrixFromFile(filePath));
  }


  public Graph getGraph() {
    return graph;
  }


  public Graph getExpandedGraph(int tileCount) {
    if (expandedGraph == null) {
      expandedGraph = new Graph(getExpandedMatrix(tileCount));
    }

    return expandedGraph;
  }


  private int[][] getExpandedMatrix(int tileCount) {
    if (!(tileCount >= 2 && tileCount <= 70)) {
      throw new IllegalArgumentException("Tile count must be between 2 and 5.");
    }

    int[][] matrix = graph.getMatrix();
    int rows = matrix.length;
    int cols = matrix[0].length;
    int[][] result = new int[rows * tileCount][cols * tileCount];

    // 0 0 | 1 1 | 2 2
    // 0 0 | 1 1 | 2 2
    // 3 3 | 4 4 | 5 5
    // 3 3 | 4 4 | 5 5
    // 6 6 ] 7 7 | 8 8
    // 6 6 ] 7 7 | 8 8
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // fill top left tile
        result[i][j] = matrix[i][j];

        // fill remaining tiles of first line
        for (int col = 1; col < tileCount; col++) {
          final int jPrevK = j + cols * (col - 1);
          final int jk = j + cols * col;

          // take values from tile on the left
          final int leftTileValue = result[i][jPrevK];
          result[i][jk] = expandedValue(leftTileValue);
        }

        // fill remaining lines (take values from row above)
        for (int line = 1; line < tileCount; line++) {
          final int iPrevK = i + rows * (line - 1);
          final int ik = i + (rows * line);

          for (int col = 0; col < tileCount; col++) {
            final int jt = j + cols * col;
            final int topValue = result[iPrevK][jt];
            result[ik][jt] = expandedValue(topValue);
          }
        }
      }
    }

    return result;
  }


  private int expandedValue(int baseValue) {
    return baseValue + 1 <= 9 ? baseValue + 1 : 1;
  }


  private int[][] readMatrixFromFile(String filePath) throws IOException {
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
}
