import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        printProgramBanner();
        String command;
        while ((command = bufferedReader.readLine()) != null) {
            if (command.charAt(0) != '/') {
                System.out.println("warning: 명령어의 첫 글자는 '/'으로 시작해야 합니다.");
                continue;
            }

            StringTokenizer stringTokenizer = new StringTokenizer(command, " ");
            String metaData = stringTokenizer.nextToken();

            switch (metaData) {
                case "/cal": {
                    calculator(stringTokenizer);
                    break;
                }
                case "/system": {
                    metaData = stringTokenizer.nextToken();
                    if (metaData.equals("e") || metaData.equals("exit")) {
                        exitProgram();
                    } else if (metaData.equals("f") || metaData.equals("file")) {
                        printProjectAbsolutePath();
                    } else if (metaData.equals("u") || metaData.equals("update")) {
                        updateBannerMessage(stringTokenizer);
                    }
                    break;
                }
            }
        }
    }

    public static void updateBannerMessage(StringTokenizer stringTokenizer) throws IOException {
        String nextToken = stringTokenizer.nextToken();
        StringBuilder stringBuilder = new StringBuilder();

        if (nextToken.equals("b") || nextToken.equals("banner")) {
            while (stringTokenizer.hasMoreTokens()) {
                stringBuilder.append(stringTokenizer.nextToken()).append(" ");
            }

            FileOutputStream fileOutputStream = new FileOutputStream("system/banner.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    public static void printProgramBanner() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("system/banner.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String bannerMessage;

        while ((bannerMessage = bufferedReader.readLine()) != null) {
            System.out.println(bannerMessage);
        }
    }

    public static void printProjectAbsolutePath() {
        Path relativePath = Paths.get("");
        String absolutePath = relativePath.toAbsolutePath().toString();
        System.out.println(absolutePath);
    }

    public static void exitProgram() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("system/goodbye.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String systemExitMessage;

        while ((systemExitMessage = bufferedReader.readLine()) != null) {
            System.out.println(systemExitMessage);
        }
        System.exit(0);
    }

    public static void calculator(StringTokenizer stringTokenizer) {
        String leftOperand = stringTokenizer.nextToken();
        String operator = stringTokenizer.nextToken();
        String rightOperand = stringTokenizer.nextToken();
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
