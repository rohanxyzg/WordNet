/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;


//"/home/rohanxyzg/Downloads/wordnet (1)/synsets.txt"
public class WordNet {
    private ST<String, Bag<Integer>> synsetNounIndex;
    private ST<Integer, String> synsetIdIndex;
    private Digraph digraph;
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {

        this.synsetNounIndex = new ST<>();
        this.synsetIdIndex = new ST<>();

        In inSynsets = new In(synsets);
        In inHypernyms = new In(hypernyms);


        while (inSynsets.hasNextLine()) {
            String[] splitSynsets = inSynsets.readLine().split(",");
            String[] nounArray = splitSynsets[1].split(" ");
            for (String noun : nounArray) {
                Bag<Integer> id = new Bag<Integer>();
                if (synsetNounIndex.contains(noun))
                    id = synsetNounIndex.get(noun);
                id.add(Integer.parseInt(splitSynsets[0]));
                synsetNounIndex.put(noun, id);
            }
            synsetIdIndex.put(Integer.parseInt(splitSynsets[0]), splitSynsets[1]);


        }
        digraph = new Digraph(synsetIdIndex.size());


        while (inHypernyms.hasNextLine()) {
            String[] splitHypernyms = inHypernyms.readLine().split(",");
            int source = Integer.parseInt(splitHypernyms[0]);
            for (int i = 1; i < splitHypernyms.length; i++)
                digraph.addEdge(source, Integer.parseInt(splitHypernyms[i]));

        }
        sap = new SAP(digraph);


    }

    public Iterable<String> nouns() {
        return synsetNounIndex;
    }

    public boolean isNoun(String word) {
        return synsetNounIndex.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException("Word not in Wordnet");
        Bag<Integer> idA = synsetNounIndex.get(nounA);
        Bag<Integer> idB = synsetNounIndex.get(nounB);
        return sap.length(idA, idB);

    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException("Word not in Wordnet");
        Bag<Integer> idA = synsetNounIndex.get(nounA);
        Bag<Integer> idB = synsetNounIndex.get(nounB);
        if (sap.ancestor(idA, idB) == -1) {
            return "None";
        }
        else {
            return synsetIdIndex.get(sap.ancestor(idA, idB));
        }
    }
}


