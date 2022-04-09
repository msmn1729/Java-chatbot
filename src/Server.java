import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777); // 클라의 요청을 받기 위한 준비
        System.out.println("[서버]: 포트 오픈");
        Socket socket = serverSocket.accept(); // 클라의 요청을 수락
        System.out.println("[서버]: 소켓 획득");

        InputStream is = socket.getInputStream();
        System.out.println("[서버]: 입력스트림 획득");
        BufferedInputStream bis = new BufferedInputStream(is, 8192);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        Thread thread = new Thread(() -> {
            try {
                OutputStream os = socket.getOutputStream();
                System.out.println("[서버]: 출력스트림 획득");
                BufferedOutputStream bos = new BufferedOutputStream(os, 8192);

                // 키보드 입력을 클라로 넘겨줌
                InputStream inputStream = System.in;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);

                byte[] buffer = new byte[3];
                int bufferLen;
                while ((bufferLen = bufferedInputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, bufferLen);
                    bos.flush();
                }
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        String msg;
        while ((msg = br.readLine()) != null) {
            System.out.println(msg);
        }
        System.out.println("[서버]: 출력완료");
    }
}
