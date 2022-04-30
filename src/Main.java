import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String command;
        while ((command = bufferedReader.readLine()) != null) {
            if (command.charAt(0) != '/') {
                System.out.println("warning: 명령어의 첫 글자는 '/'으로 시작해야 합니다.");
                continue;
            }

            String[] commandTokenizer = command.split(" ");
            if (commandTokenizer[0].equals("/cal")) {
                String leftOperand = commandTokenizer[1];
                String operator = commandTokenizer[2];
                String rightOperand = commandTokenizer[3];
                calculator(leftOperand, operator, rightOperand);
            }
        }
    }

    public static void calculator(String leftOperand, String operator, String rightOperand) {
        int leftNum, rightNum;

        try {
            leftNum = Integer.parseInt(leftOperand);
        } catch (Exception exception) {
            System.out.println("error: `" + leftOperand + "`는 올바르지 않은 포맷입니다.");
            System.out.println("다음과 같이 포맷 양식을 지켜주세요.");
            System.out.println("`숫자 데이터만 가능합니다.`");
            return;
        }

        try {
            rightNum = Integer.parseInt(rightOperand);
        } catch (Exception exception) {
            System.out.println("error: `" + rightOperand + "`는 올바르지 않은 포맷입니다.");
            System.out.println("다음과 같이 포맷 양식을 지켜주세요.");
            System.out.println("`숫자 데이터만 가능합니다.`");
            return;
        }

        switch (operator) {
            case "+":
                System.out.println(leftNum + rightNum);
                break;
            case "-":
                System.out.println(leftNum - rightNum);
                break;
            case "*":
                System.out.println(leftNum * rightNum);
                break;
            case "/":
                if (rightNum == 0) {
                    System.out.println("error: 0으로 나눌 수 없습니다.");
                    return;
                }
                System.out.println(leftNum / rightNum);
                break;
        }
    }
}
