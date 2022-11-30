import parser.Calc;
import parser.Parser;
import tree.Node;
import tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class z_log {


    public static void main(String[] args) throws Exception {
        var a = "(C>(~A-B+C))>(A*C+B-A*B*C)";
        var tree = new Tree(a);

        var params = List.of("A", "B", "C");
        var resultsMap = new HashMap<String, List<Integer>>();

        var numOfSolves = ((Double)Math.pow(2, params.size())).intValue();
        for (int i = 0; i <  numOfSolves; i++) {
            HashMap<String, Integer> xyz = getInputSet(params, i);
            var res = calcRow(tree, xyz);

            var chields = tree.linearizeChields();
            for (var chieldName : chields.stream().map(Node::getData).distinct().sorted(Comparator.comparing(x->x)).collect(Collectors.toUnmodifiableList())) {
                var chield = chields.stream().filter(x->x.getData().equals(chieldName)).findFirst().get();
                if (resultsMap.containsKey(chieldName)) {
                    resultsMap.get(chieldName).add(chield.getValue());
                }
                else {
                    var list = new LinkedList<Integer>();
                    list.add(chield.getValue());
                    resultsMap.put(chieldName, list);
                }
            }

            printResult(params, xyz, res);
        }

        var keys = resultsMap.keySet();
        for (var key: keys) {
            System.out.println("results of " + key +":");
            var res = resultsMap.get(key);
            for (var item : res) {
                System.out.println(item);
            }
        }
    }

    private static void printResult(List<String> params, HashMap<String, Integer> xyz, Integer res) {
        var sb = new StringBuilder();
        for (var pos = 0; pos < params.size(); pos++) {
            var key = params.get(pos);
            sb.append( key+ "=" + xyz.get(key) + " ");
        }
        sb.append("res = " + res);

        System.out.println(sb.toString());
    }

    private static HashMap<String, Integer> getInputSet(List<String> params, int i) {
        var xyz = new HashMap<String, Integer>();
        for (var pos = 0; pos < params.size(); pos++) {

            int mask = 1 << pos;
            int isSet = (i & mask) > 0 ? 1 : 0;

            xyz.put(params.get(params.size() - pos - 1), isSet);
        }
        return xyz;
    }

    private static Integer calcRow(Tree tree, HashMap<String, Integer> xyz) throws Exception {
        tree.resetValues();
        tree.setValues(xyz);
        solve(tree);
        return tree.getValue();
    }

    private static void solve(Tree tree) throws Exception {
        var line = tree.linearizeChields();

        while (tree.getValue() == null) {
            var curItem = line.stream().filter(
                    x -> x.getLeft() != null && x.getLeft().getValue() != null &&
                            (x.getOp() == Parser.negOps || (x.getRight() != null && x.getRight().getValue() != null)) &&
                         x.getValue() == null).findFirst();

            if (curItem.isPresent()) {
                var node = curItem.get();
                var calc = Calc.calc(node.getLeft().getValue(), node.getOp() != Parser.negOps ? node.getRight().getValue() : null, node.getOp());
                node.setValue(calc);
            }
            else {
                throw new Exception("solution not found");
            }
        }
    }
}
