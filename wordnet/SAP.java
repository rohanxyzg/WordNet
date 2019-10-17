import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SAP {

    private class SAPResult {
        int ancestor;
        int length;

        public SAPResult(int ancestor, int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }

    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        this.G = new Digraph(G);
    }

    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= G.V() || w >= G.V())
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        return findSAP(bfs1, bfs2).length;
    }

    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= G.V() || w >= G.V())
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        return findSAP(bfs1, bfs2).ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (int vertex : v)
            if (vertex < 0 || vertex >= G.V())
                throw new IllegalArgumentException();
        for (int vertex : w)
            if (vertex < 0 || vertex >= G.V())
                throw new IllegalArgumentException();
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        return findSAP(bfs1, bfs2).length;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (int vertex : v)
            if (vertex < 0 || vertex >= G.V())
                throw new IllegalArgumentException();
        for (int vertex : w)
            if (vertex < 0 || vertex >= G.V())
                throw new IllegalArgumentException();
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        return findSAP(bfs1, bfs2).ancestor;
    }


    private SAPResult findSAP(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        SAPResult result = new SAPResult(-1, -1);
        for (int i = 0; i < G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int length = bfs1.distTo(i) + bfs2.distTo(i);
                if (result.length == -1 || length < result.length) {
                    result.length = length;
                    result.ancestor = i;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
