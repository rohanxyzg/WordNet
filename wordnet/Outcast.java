/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;


    }

    public String outcast(String[] nouns) {
        int[] distance = new int[nouns.length];
        int max = Integer.MIN_VALUE;
        int maxDistPos = 0;
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                distance[i] += wordNet.distance(nouns[i], nouns[j]);

            }
            if (distance[i] > max) {
                max = distance[i];
                maxDistPos = i;
            }
        }
        return nouns[maxDistPos];


    }


    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}
