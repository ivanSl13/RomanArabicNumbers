import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CalculatorRomanArabicNumbers {
    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение которое хотите вычислить:");
        Scanner scanner = new Scanner(System.in);
        String line;
        line = scanner.nextLine();
        scanner.close();
        String[] all = line.split(" ");
        if(all.length!=3)
            throw new IOException("Error: неправильная запись выражения. Завершение работы.");
        String math= all[1];
        boolean right = checkRoman(all[2]);
        boolean left = checkRoman(all[0]);
        int left1;
        int right1;
        int result = 0;
        if(right && left){
            left1=rimeToArabian(all[0]);
            right1=rimeToArabian(all[2]);
            switch (math) {
                case "+" -> result = left1 + right1;
                case "-" -> result = left1 - right1;
                case "*" -> result = left1 * right1;
                case "/" -> result = (int) left1 / right1;
            }
            if(result<0)
                throw new Exception("Error: в римской системе нет отрицательных чисел");
            System.out.println(arabicToRoman(result));
        }
        if(!right&&!left){
            left1 = Integer.parseInt(all[0]);
            if(left1<1 ||left1>10)
                throw new Exception("Error: Введите число от 1...10");
            right1 = Integer.parseInt(all[2]);
            if(right1<1 ||right1>10)
                throw new Exception("Error: Введите число от 1...10");
            result = switch (math) {
                case "+" -> left1 + right1;
                case "-" -> left1 - right1;
                case "*" -> left1 * right1;
                case "/" -> left1 / right1;
                default -> result;
            };
            System.out.println(result);
        }
        if(left!=right)
            throw new Exception("Error: используются разные системы счислания");
    }
    static boolean checkRoman(String s){
        if(s.equals("I")||s.equals("II")||s.equals("III")||
                s.equals("IV")||s.equals("V")||s.equals("VI")||
                s.equals("VII")||s.equals("VIII")||s.equals("IX")||
                s.equals("X"))
            return true;
        else
            return false;
    }
    static int rimeToArabian(String s){
        int r= switch (s){
            case "I"->1;
            case "II"->2;
            case "III"->3;
            case "IV"->4;
            case "V"->5;
            case "VI"->6;
            case "VII"->7;
            case "VIII"->8;
            case "IX"->9;
            case "X"->10;
            default -> 0;
        };
        return r;
    }
    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 100)) {
            throw new IllegalArgumentException(number + " Должно быть в пределе 0...100");
        }
        List<RomanNumer> romanNumerals = RomanNumer.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumer currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
    public enum RomanNumer {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);
        private int value;
        RomanNumer(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static List<RomanNumer> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumer e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}
