package parser;

public class Calc {

    public static Integer calc(Integer a, Integer b, Character op)
    {
        switch (op) {
            case '+':
                // OR
                if (a.equals(1) || b.equals(1)) return 1;
                return 0;
            case '*':
                // AND
                if (a.equals(1) && b.equals(1)) return 1;
                return 0;
            case '-':
                // equals
                if (a.equals(b)) return 1;
                return 0;
            case '>':
                // ????
                if (a.equals(1) && b.equals(0)) return 0;
                return 1;
            case '~':
                // neg
                if (a.equals(0)) return 1;
                if (a.equals(1)) return 0;
        }

        return null;
    }
}
