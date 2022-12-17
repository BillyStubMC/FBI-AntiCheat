package fun.billy.fbi.util.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.Deque;

@UtilityClass
public class GraphUtil {

    public GraphResult getGraph(final Deque<Double> values) {
        final StringBuilder graph = new StringBuilder();
        double largest = 0.0;
        for (final double value : values) {
            if (value > largest) {
                largest = value;
            }
        }
        final int GRAPH_HEIGHT = 2;
        int negatives = 0;
        for (int i = GRAPH_HEIGHT - 1; i > 0; --i) {
            final StringBuilder sb = new StringBuilder();
            for (final double index : values) {
                final double value2 = GRAPH_HEIGHT * index / largest;
                if (value2 > i && value2 < i + 1) {
                    sb.append(String.format("%s+", ChatColor.GREEN));
                } else {
                    ++negatives;
                    sb.append(String.format("%s-", ChatColor.RED));
                }
            }
            graph.append(sb.toString());
        }
        return new GraphResult(negatives);
    }

    public class GraphResult {
        private final int negatives;

        public int getNegatives() {
            return this.negatives;
        }

        public GraphResult(final int negatives) {
            this.negatives = negatives;
        }
    }
}
