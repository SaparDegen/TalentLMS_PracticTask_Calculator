import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two numbers and an arithmetic operation on numbers (for example a+b):");
        String str = scanner.nextLine();

        int operType = 0;                   //Type of ariphm operation: 1(+), 2(-), 3(*), 4(/)
        int numberType = 0;                 //Arabic number = 1, Roman number = 2
        int tt = 0;
        String a = "", b = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                if (!Character.isDigit(str.charAt(i))) {
                    if (str.charAt(i) == 'I' || str.charAt(i) == 'V' || str.charAt(i) == 'X') {
                        if (tt == 0 && numberType != 1) {
                            a += str.charAt(i);
                            numberType = 2;
                        } else if (tt == 1 && numberType != 1) {
                            b += str.charAt(i);
                            numberType = 2;
                        } else {
                            throw new Exception("The entered numbers are of different types");
                        }
                    } else if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/') {
                        if (operType == 0) {
                            operType = str.charAt(i) == '+' ? 1 : str.charAt(i) == '-' ? 2 : str.charAt(i) == '*' ? 3 : str.charAt(i) == '/' ? 4 : -1;
                        } else {
                            throw new Exception("Wrong operation");
                        }
                        tt = 1;
                    } else {
                        throw new Exception("Non-numeric values found");
                    }
                } else {
                    if (tt == 0 && numberType != 2) {
                        a += str.charAt(i);
                        numberType = 1;
                    } else if (tt == 1 && numberType != 2) {
                        b += str.charAt(i);
                        numberType = 1;
                    } else {
                        throw new Exception("The entered numbers are of different types");
                    }
                }
            }
        }

        int a1 = 0, b1 = 0;
        double result = 0;
        if (a != "" && b != "") {
            if (numberType == 1) {                       //Arabic number = 1
                a1 = Integer.parseInt(a);
                b1 = Integer.parseInt(b);
            } else if (numberType == 2) {                //Roman number = 2
                a1 = romanNumToArabic(a);
                b1 = romanNumToArabic(b);
            }
            if ((a1 >= 1 && a1 <= 10) && (b1 >= 1 && b1 <= 10)) {
                if (operType == 1) {                    //if operType 1 - summation
                    result = a1 + b1;
                } else if (operType == 2) {             //if operType 2 - subtraction
                    result = a1 - b1;
                } else if (operType == 3) {             //if operType 3 - multiplication
                    result = a1 * b1;
                } else if (operType == 4) {             //if operType 4 - division
                    result = 1.0 * a1 / b1;
                }

                if (numberType == 1) {
                    System.out.println("Equals: " + result);
                } else {
                    System.out.println("Equals: " + arabicNumToRoman(result));
                }
            } else {
                throw new Exception("One of the values is greater than 10");
            }
        } else {
            throw new Exception("One of the values is empty");
        }
    }

    public static int romanNumToArabic(String rNum) throws Exception {
        Map<String, Integer> romanNum = new LinkedHashMap<>();
        romanNum.put("X", 10);
        romanNum.put("IX", 9);
        romanNum.put("V", 5);
        romanNum.put("IV", 4);
        romanNum.put("I", 1);

        String r, r1;
        int a = 100, a1;
        int c1 = 1, c2 = 1;
        int res = 0;
        for (int i = 0; i < rNum.length(); i++) {
            a1 = a;
            if (rNum.length() == 1 || i == rNum.length() - 1) {
                a = romanNum.get(String.valueOf(rNum.charAt(i)));
            } else {
                r = String.valueOf(rNum.subSequence(i, i + 2));
                r1 = String.valueOf(rNum.charAt(i));
                if (romanNum.containsKey(r)) {
                    a = romanNum.get(r);
                    i++;
                } else {
                    a = romanNum.get(r1);
                }
            }
            if (i == 0 || a1 > a) {
                res += a;
            } else if (a1 == a && (a == 1 || a == 10) && c1 < 3 && c2 < 10) {
                if (a == 1) {
                    c1++;
                } else {
                    c2++;
                }
                res += a;
            } else {
                throw new Exception("Roman numeral entered is not in correct format");
            }
        }
        return res;
    }

    public static String arabicNumToRoman(double num) {
        Map<Integer, String> romanNum = new LinkedHashMap<>();
        romanNum.put(10, "X");
        romanNum.put(9, "IX");
        romanNum.put(5, "V");
        romanNum.put(4, "IV");
        romanNum.put(1, "I");

        String result = "";
        while (num > 0) {
            double finalNum = num;
            int aInt = romanNum.entrySet().stream().map(x -> x.getKey()).filter(x -> finalNum >= x).max(Integer::compareTo).get();
            result += romanNum.get(aInt);
            num -= aInt;
        }

        return result;
    }
}