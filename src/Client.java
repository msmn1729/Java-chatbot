import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.0.2", 7777);
        System.out.println("[클라]: 소켓 획득");

        InputStream is = socket.getInputStream();
        System.out.println("[클라]: 입력스트림 획득");
        BufferedInputStream bis = new BufferedInputStream(is, 8192);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        // 채팅 기록 저장
        FileOutputStream fileOutputStream = new FileOutputStream("src/chatLog/client_log");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 8192);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);

        Thread thread = new Thread(() -> {
            try {
                InputStream inputStream = System.in;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream os = socket.getOutputStream();
                System.out.println("[클라]: 출력스트림 획득");
                BufferedOutputStream bos = new BufferedOutputStream(os, 8192);
                OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw);

                int len;
                char[] cBuffer = new char[2];

                StringBuilder sb = new StringBuilder("[클라이언트]: ");
                while ((len = inputStreamReader.read(cBuffer)) != -1) {
                    String outputMsg = new String(cBuffer, 0, len);
                    osw.write(outputMsg);
                    osw.flush();
                    for (int i = 0; i < len; i++) {
                        if (outputMsg.charAt(i) == '\n') {
                            outputStreamWriter.write(sb.toString() + '\n');
                            outputStreamWriter.flush();
                            sb.setLength(0); // sb초기화
                            sb.append("[클라이언트]: ");
                        } else {
                            sb.append(outputMsg.charAt(i));
                        }
                    }
                }
                osw.close();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        String msg;
        while ((msg = br.readLine()) != null) {
            System.out.println(msg);
            outputStreamWriter.write("[서버]: " + msg + "\n");
            outputStreamWriter.flush();
        }
        System.out.println("[클라]: 출력완료");
    }
}
