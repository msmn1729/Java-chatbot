import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import static java.text.MessageFormat.format;

public class Main {
    public static final Exit EXIT = new Exit();
    public static final File FILE = new File();
    public static final Update UPDATE = new Update();
    public static final Banner BANNER = new Banner();
    public static final String METADATA_DELIM = " ";
    public static final String SPACE = " ";
    public static final String RELATIVE_PROJECT_LOCATION = "";

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = createBufferedReader(System.in);

        printProgramBanner();
        String command;
        while ((command = bufferedReader.readLine()) != null) {
            if (command.charAt(0) != CommandContant.PREFIX) {
                System.out.println(format("warning: 명령어의 첫 글자는 `{0}` 으로 시작해야 합니다.", CommandContant.PREFIX));
                continue;
            }

            StringTokenizer stringTokenizer = new StringTokenizer(command, METADATA_DELIM);
            String metaData = stringTokenizer.nextToken();

            if (metaData.equals(CommandContant.CALCULATOR)) {
                calculator(stringTokenizer);
            } else if (metaData.equals(CommandContant.SYSTEM)) {
                metaData = stringTokenizer.nextToken();
                if (EXIT.contain(metaData)) {
                    exitProgram();
                } else if (FILE.contain(metaData)) {
                    printProjectAbsolutePath();
                } else if (UPDATE.contain(metaData)) {
                    updateMessage(stringTokenizer);
                }
            }
        }
    }

    public static BufferedReader createBufferedReader(InputStream inputStream) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);

        return new BufferedReader(inputStreamReader);
    }

    public static void updateExitMessage(StringTokenizer stringTokenizer) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(ProjectPath.RELATIVE_SYSTEM_EXIT);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        StringBuilder stringBuilder = new StringBuilder();
        while (stringTokenizer.hasMoreTokens()) {
            stringBuilder.append(stringTokenizer.nextToken()).append(SPACE);
        }
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void updateMessage(StringTokenizer stringTokenizer) throws IOException {
        String nextToken = stringTokenizer.nextToken();
        StringBuilder stringBuilder = new StringBuilder();

        if (EXIT.contain(nextToken)) {
            updateExitMessage(stringTokenizer);
            return;
        } else if (!BANNER.contain(nextToken)) {
            // b, banner 메타데이터가 생략된 경우도 고려해야함
            // ex) /system u MyProgram Launched!! v1.0.1
            stringBuilder.append(nextToken).append(SPACE);
        }

        while (stringTokenizer.hasMoreTokens()) {
            stringBuilder.append(stringTokenizer.nextToken()).append(SPACE);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(ProjectPath.RELATIVE_SYSTEM_BANNER);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void printProgramBanner() throws IOException {
        BufferedReader bufferedReader = createBufferedReader(new FileInputStream(ProjectPath.RELATIVE_SYSTEM_BANNER));
        String bannerMessage;

        while ((bannerMessage = bufferedReader.readLine()) != null) {
            System.out.println(bannerMessage);
        }
    }

    public static void printProjectAbsolutePath() {
        Path relativePath = Paths.get(RELATIVE_PROJECT_LOCATION);
        String absolutePath = relativePath.toAbsolutePath().toString();
        System.out.println(absolutePath);
    }

    public static void exitProgram() throws IOException {
        BufferedReader bufferedReader = createBufferedReader(new FileInputStream(ProjectPath.RELATIVE_SYSTEM_EXIT));
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

        MathOperation mathOperation = MathOperation.forName(operator);
        switch (mathOperation) {
            case PLUS:
                System.out.println(leftNum + rightNum);
                break;
            case MINUS:
                System.out.println(leftNum - rightNum);
                break;
            case MULTI:
                System.out.println(leftNum * rightNum);
                break;
            case DIV:
                if (rightNum == 0) {
                    System.out.println("error: 0으로 나눌 수 없습니다.");
                    return;
                }
                System.out.println(leftNum / rightNum);
                break;
        }
    }
}
