package de.raywo.dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * DataProvider is responsible for loading and managing graph data from a file.
 * It can read a matrix from a file, create a graph from that matrix, and
 * expand the graph by a specified tile count.
 */
public class DataProvider {

  private final Graph graph;
  private Graph expandedGraph = null;


  /**
   * Constructs a DataProvider instance by reading a matrix from the specified
   * file path and creating a graph from that matrix.
   *
   * @param filePath the path to the file that contains the matrix data.
   * @throws IOException if an I/O error occurs reading from the file or a
   *                     malformed or unmappable byte sequence is read.
   */
  public DataProvider(String filePath) throws IOException {
    graph = new Graph(readMatrixFromFile(filePath));
  }


  /**
   * Retrieves the original graph loaded by the DataProvider.
   *
   * @return the graph loaded from the data source.
   */
  public Graph getGraph() {
    return graph;
  }


  /**
   * Returns an expanded version of the original graph by a specified tile count.
   * If the expanded graph is already computed, it returns the cached version.
   * Every new tile is either derived from its left or top neighbor. How the
   * derivation works is described in {@link Graph#getDerivedGraph()}.
   *
   * @param tileCount the number of times the original graph is to be expanded
   *                  both horizontally and vertically.
   * @return the expanded graph.
   */
  public Graph getExpandedGraph(int tileCount) {
    if (expandedGraph == null) {
      expandedGraph = new Graph(getExpandedMatrix(tileCount));
    }

    return expandedGraph;
  }


  /**
   * Generates an expanded matrix from the original graph matrix based on the
   * specified tile count. Each new tile is derived from its left or top
   * neighbor's values, expanded by a specific logic defined in
   * the {@code expandedValue} method.
   *
   * @param tileCount the number of times to replicate the original matrix both
   *                  horizontally and vertically.
   *                  Must be between 2 and 70 inclusive.
   * @return a 2D array (int[][]) representing the expanded matrix.
   * @throws IllegalArgumentException if the tile count is not within the
   *                                  valid range.
   */
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


  /**
   * Computes the expanded value based on the provided base value.
   * If the base value incremented by one exceeds 9, it wraps around to 1.
   *
   * @param baseValue the base value to be expanded.
   * @return the incremented value of baseValue or wraps around to 1 if it exceeds 9.
   */
  private int expandedValue(int baseValue) {
    return baseValue + 1 <= 9 ? baseValue + 1 : 1;
  }


  /**
   * Reads a matrix from the specified file path, where each line in the file
   * represents a row in the matrix.
   *
   * @param filePath the path to the file that contains the matrix data.
   * @return a 2D array (int[][]) representing the matrix read from the file.
   * @throws IOException if an I/O error occurs reading from the file or a
   *                     malformed or unmappable byte sequence is read.
   */
  private int[][] readMatrixFromFile(String filePath) throws IOException {
    Path path = Path.of(filePath);
    List<String> lines = Files.readAllLines(path);

    return parseCavernMap(lines);
  }


  /**
   * Parses a list of strings representing rows of a cavern map and converts it
   * into a 2D array of integers where each integer represents the risk level at
   * that position.
   *
   * @param input a list of strings where each string represents a row in the
   *              cavern map
   * @return a 2D array of integers where each integer indicates the risk
   * level at that position
   */
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
