package tree;

import lombok.Getter;
import lombok.Setter;
import parser.Parser;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Node {
    private String data;
    private Node left;
    private Node right;
    private Character op;
    private int level;
    private Node parent;

    private Integer value = null;

    protected Node(String data) throws Exception {
        this.data = data;
        this.parse();
    }

    private Node(String data, Node parent) throws Exception {
        this.data = data;
        this.parent = parent;
        this.level = parent.getLevel() + 1;
        this.parse();
    }

    public List<Node> linearizeChields() throws Exception {
        var left = getLeft();
        var right = getRight();
        var list = new LinkedList<Node>();
        if (left == null && right == null) {
            list.add(this);
            return list;
        }

        if (left == null) throw new Exception();

        list.addAll(left.linearizeChields());

        if (op != Parser.negOps) {
            list.addAll(right.linearizeChields());
        }

        list.add(this);
        return list;
    }

    public List<Node> getTails() throws Exception {
        var left = getLeft();
        var right = getRight();
        var list = new LinkedList<Node>();
        if (left == null && right == null) {
            list.add(this);
            return list;
        }

        if (left == null) throw new Exception();

        list.addAll(left.getTails());

        if (op != Parser.negOps) {
            list.addAll(right.getTails());
        }

        return list;
    }

    protected void resetValues() throws Exception {
        var left = getLeft();
        var right = getRight();

        if (left == null && right == null) {
            value = null;
            return;
        }

        if (left == null) throw new Exception();

        left.resetValues();

        if (op != Parser.negOps) {
            right.resetValues();
        }

        value = null;
    }

    private void opsParse() throws Exception {
        var indexOfOps = Parser.prefferedOps(data);

        if (indexOfOps != 0) {
            op = data.substring(indexOfOps, indexOfOps + 1).charAt(0);
            var left = "";
            var right = "";

            left = data.substring(0, indexOfOps);
            right = data.substring(indexOfOps + 1);

            this.left = new Node(left, this);

            this.right = new Node(right, this);
        }
    }

    private void parse() throws Exception {
        if (Parser.containsParentheses(data)) {
            parentesisParse();
        }
        else {
            if (Parser.containsOps(data)) {
                opsParse();
            }
            else {
                if (Parser.containsNeg(data)) {
                    negsParser();
                }
            }
        }
    }

    private void negsParser() throws Exception {
        op = Parser.negOps;
        var left = "";

        left = data.substring(1);

        this.left = new Node(left, this);
    }

    private void parentesisParse() throws Exception {
        var start = Parser.parentheses(data);
        var indexOfOps = Parser.prefferedOps(start);

        if (indexOfOps != 0) {
            op = start.get(indexOfOps).charAt(0);
            var left = "";
            var right = "";

            if (indexOfOps == 1) {
                var s = start.get(0);
                if (s.startsWith("(") && s.endsWith(")")) {
                    left = s.substring(1, s.length() - 1);
                }
                else {
                    left = s;
                }
            }
            else {
                for (int i = 0; i < indexOfOps; i++) {
                    left += start.get(i);
                }
            }

            if (indexOfOps == start.size() - 1) throw new Exception("");

            if (indexOfOps == start.size() - 2) {
                var s = start.get(start.size() - 1);
                if (s.startsWith("(") && s.endsWith(")")) {
                    right = s.substring(1, s.length() - 1);
                }
                else {
                    right = s;
                }
            }
            else {
                for (int i = indexOfOps + 1; i < start.size(); i++) {
                    right += start.get(i);
                }
            }

            this.left = new Node(left, this);

            this.right = new Node(right, this);
        }
    }
}
