package converter;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String erSourceBase = "Error: sourceBase is wrong!";
        String erNewBase = "Error: newBase is wrong!";
        String notBase = " It must be more than 0 and less than 37";
        String notDigital = "Error: this is not a digital with base = ";

        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        String s1 = scanner.nextLine();

        if (s1.length() < 1 || s1.length() > 2 || !s1.matches("\\d+")) {
            System.out.println(erSourceBase);
            return;
        }
        int sourceBase = Integer.parseInt(s1);
        if (sourceBase > 36 || sourceBase < 1) {
            System.out.println(erSourceBase + notBase);
            return;
        }

        String s = scanner.nextLine();
        if (sourceBase != 1) {
            String sub = "[" + SYMBOLS.substring(0, sourceBase) + "]+";
            if (!(s.matches(sub) || s.matches(sub + "[.]" + sub))) {
                System.out.println(notDigital + sourceBase);
                return;
            }
        } else {
            if (!s.matches("[1]+")) {
                System.out.println(notDigital + sourceBase);
                return;
            }
        }

        String s2 = scanner.nextLine();
        if (s2.length() < 1 || s2.length() > 2 || !s2.matches("\\d+")) {
            System.out.println(erNewBase);
            return;
        }
        int newBase = Integer.parseInt(s2);
        if (newBase > 36 || newBase < 1) {
            System.out.println(erNewBase + notBase);
            return;
        }

        if (sourceBase == 1) {
            System.out.println(Integer.toString(fromBaseOne(s), newBase));
        } else {
            if (!s.contains(".")) {
                if (newBase == 1) {
                    System.out.println(toBaseOne(Integer.parseInt(s, sourceBase)));
                } else
                    System.out.println(Integer.toString(Integer.parseInt(s, sourceBase), newBase));
            } else System.out.println(toNewBase(fromSourceBase(s, sourceBase), newBase));
        }
    }

    final static String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";

    static String toBaseOne(int n) {
        if (n == 0) {
            return "0";
        }
        return "1".repeat(n);
    }

    static int fromBaseOne(String s) {
        return s.length();
    }

    static int getInteger(String s, int sourceBase) {
            return Integer.parseInt(s, sourceBase);
    }

    static double getFractional(String s, int sourceBase) {
        double n = 0;
        for (int i = 0; i < Math.min(s.length(), 5) ; i++) {
            n += SYMBOLS.indexOf(s.charAt(i)) / Math.pow(sourceBase, i + 1);
        }
        return n;
    }

    static double fromSourceBase(String s, int sourceBase) {
        if (sourceBase == 10) {
            return Double.parseDouble(s);
        } else {
            String[] str = s.split("\\.");
            double n = getInteger(str[0], sourceBase);
            if (str.length > 1) {
                n += getFractional(str[1], sourceBase);
            }
            return n;
        }
    }

    static String getIntegerToNewBase(int n, int newBase) {
        if (newBase == 1) {
            return toBaseOne(n);
        } else return Integer.toString(n, newBase);
    }

    static String getFractionalToNewBase(double n, int newBase) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            double d = n * newBase;
            sb.append(SYMBOLS.charAt((int) d));
            n = d - (int) d;
        }
        return sb.toString();
    }

    static String toNewBase(double d, int newBase) {
        return getIntegerToNewBase((int) d, newBase) + "." + getFractionalToNewBase(d - (int) d, newBase);
    }
}
