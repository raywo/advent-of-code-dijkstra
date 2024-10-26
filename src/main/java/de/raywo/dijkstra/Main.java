package de.raywo.dijkstra;

import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.text.NumberFormat;

public class Main {

  public static void main(String[] args) throws IOException {
    StopWatch expandWatch = new StopWatch();

    String filePath = "src/main/resources/ray-step-2.txt";

    DataProvider provider = new DataProvider(filePath);

    System.out.println("erster Teil:");
    doCalculation(provider.getGraph());

    System.out.println("\nzweiter Teil (expandierte Matrix):");
    expandWatch.start();
    final Graph expandedGraph = provider.getExpandedGraph(5);
    expandWatch.stop();
    System.out.println("Expansion dauerte " + expandWatch.getTime() + "ms");
    doCalculation(expandedGraph);
  }


  private static void doCalculation(Graph graph) {
    StopWatch calcWatch = new StopWatch();
    int source = 0;
    int target = graph.vertexCount() - 1;

    calcWatch.start();
    int[] distances = Dijkstra.getInstance().calculateShortestPath(graph, source, target);
    calcWatch.stop();

    NumberFormat format = NumberFormat.getIntegerInstance();
    format.setGroupingUsed(true);
    String formattedTarget = format.format(target + 1);

    System.out.println("Das Gesamtrisiko vom Start (0) zum Ziel (" + formattedTarget + ") beträgt: " + distances[target]);
    System.out.println("Die Berechnung dauerte " + calcWatch.getTime() + "ms.");
  }

}
