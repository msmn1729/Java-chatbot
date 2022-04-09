import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("[서버]: 포트 오픈");
        Socket socket = serverSocket.accept();
        System.out.println("[서버]: 소켓 획득");

//        InputStream is = socket.getInputStream();
//        System.out.println("[서버]: 입력스트림 획득");
//        BufferedInputStream bis = new BufferedInputStream(is, 8192);
//        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
//        BufferedReader br = new BufferedReader(isr);

        Thread thread = new Thread(() -> {
            try {
                OutputStream os = socket.getOutputStream();
                System.out.println("[서버]: 출력스트림 획득");
                BufferedOutputStream bos = new BufferedOutputStream(os, 8192);
                OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw);

                // 키보드 입력을 클라로 넘겨줌
                InputStream inputStream = System.in;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String outputMsg;
                while ((outputMsg = bufferedReader.readLine()) != null) {
                    System.out.println(outputMsg);
                    bw.write(outputMsg);
                    bw.flush();
                    System.out.println("flush 완료");
                }
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

//        String inputMsg;
//        while ((inputMsg = br.readLine()) != null) {
//            System.out.println(inputMsg);
//        }
    }
}
