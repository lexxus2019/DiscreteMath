package parser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    private static final Set<Character> firstRangOps = Set.of('*');
    private static final Set<Character> secondRangOps = Set.of('+');
    private static final Set<Character> thirdRangOps = Set.of('>', '-');

    public static final Character negOps = '~';

    private static final Set<Character> allOps = Stream.of(firstRangOps, secondRangOps, thirdRangOps).flatMap(Set::stream).collect(Collectors.toUnmodifiableSet());


    public static List<String> parentheses(String s) {
        var open = 0;
        var opens = new HashSet<Integer>();
        var close = 0;
        var closeds = new HashSet<Integer>();

        var res = new LinkedList<String>();

        var str = "";

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
                opens.add(i);

                if (open == close + 1 && !str.isEmpty()) {
                    addPart(res, str);
                    str = "";
                }

                str += s.charAt(i);
            }
            else if (s.charAt(i) == ')') {
                close++;
                closeds.add(i);
                str += s.charAt(i);
                if (open > 0 && open == close && !str.isEmpty()) {
                    addPart(res, str);
                    str = "";
                }
            }
            else {
                str += s.charAt(i);
            }
        }

        return res;
    }

    private static void addPart(LinkedList<String> res, String str) {
        if (isOps(str.charAt(str.length() - 1)) && str.length() > 1) {
            var part = str.substring(0, str.length() - 1);
            var sign = str.substring(str.length() - 1);
            res.add(part);
            res.add(sign);
        }
        else
            res.add(str);
    }

    public static int prefferedOps(List<String> list) {
        var index1 = 0;
        var index2 = 0;
        var index3 = 0;

        for (int i = 0; i < list.size(); i++) {
            var item = list.get(i);
            if (item.length() == 1) {
                if (thirdRangOps.contains(item.charAt(0)) && index3 == 0) {
                    index3 = i;
                    ////return i;
                }

                if (secondRangOps.contains(item.charAt(0)) && index2 == 0) {
                    index2 = i;
                    ////return i;
                }

                if (firstRangOps.contains(item.charAt(0)) && index1 == 0) {
                    index1 = i;
                    ////return i;
                }
            }
        }

        ////return index2 != 0 ? index2 : index3;
        return index3 != 0 ? index3 : index2 != 0 ? index2 : index1;
    }

    public static int prefferedOps(String data) {
        var index1 = 0;
        var index2 = 0;
        var index3 = 0;

        for (int i = 0; i < data.length(); i++) {
            var ch = data.charAt(i);
                if (thirdRangOps.contains(ch) && index3 == 0) {
                    index3 = i;
                    ////return i;
                }

                if (secondRangOps.contains(ch) && index2 == 0) {
                    index2 = i;
                    ////return i;
                }

                if (firstRangOps.contains(ch) && index1 == 0) {
                    index1 = i;
                }

        }

        return index3 != 0 ? index3 : index2 != 0 ? index2 : index1;
    }

    public static boolean containsOps(String data) {
        for (int i = 0; i < data.length(); i++) {
            var c = data.charAt(i);
            if (firstRangOps.contains(c)) {
                return true;
            }

            if (secondRangOps.contains(c)) {
                return true;
            }

            if (thirdRangOps.contains(c)) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsNeg(String data) throws Exception {
        var indx = data.indexOf(negOps);

        if (indx > 0) throw new Exception("negs");

        return indx == 0;
    }

    public static boolean isOps(char c) {
        if (firstRangOps.contains(c)) {
            return true;
        }

        if (secondRangOps.contains(c)) {
            return true;
        }

        if (thirdRangOps.contains(c)) {
            return true;
        }

        return false;
    }

    public static boolean containsParentheses(String data) {
        return data.lastIndexOf('(') > 0;
    }
}
