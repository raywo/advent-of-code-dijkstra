package de.raywo.dijkstra;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataProvider {

  private final Graph graph;


  public DataProvider(String filePath) throws IOException, URISyntaxException {
    graph = new Graph(readMatrixFromFile(filePath));
  }


  public Graph getGraph() {
    return graph;
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
}
