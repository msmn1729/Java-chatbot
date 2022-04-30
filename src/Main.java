import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String command = "/cal 5 + 4";
//        while ((command = bufferedReader.readLine()) != null) {
        if (command.charAt(0) != '/') {
            System.out.println("명령어의 첫 글자는 '/'으로 시작해야 합니다.");
//                continue;
        }

        String[] commandTokenizer = command.split(" ");
        if (commandTokenizer[0].equals("/cal")) {
            if (commandTokenizer[2].equals("+")) {
                sum(commandTokenizer[1], commandTokenizer[3]);
            }
        }
//        }
    }

    public static void sum(String leftOperand, String rightOperand) {
        int leftNum = Integer.parseInt(leftOperand);
        int rightNum = Integer.parseInt(rightOperand);
        System.out.println(leftNum + rightNum);
    }
}
