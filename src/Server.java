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

        // 채팅기록을 파일로 저장
        FileOutputStream fileOutputStream = new FileOutputStream("src/chatLog/server_log");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 8192);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);

        Thread thread = new Thread(() -> {
            try {
                // 키보드 입력을 받음
                InputStream inputStream = System.in;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream os = socket.getOutputStream();
                System.out.println("[서버]: 출력스트림 획득");
                BufferedOutputStream bos = new BufferedOutputStream(os, 8192);
                OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw);

                int len;
                char[] cBuffer = new char[2];

                StringBuilder sb = new StringBuilder("[서버]: ");
                int prefixLen = sb.length();
                StringBuilder msg = new StringBuilder();
                String serverHTML = new String(new FileInputStream("src/Hello.html").readAllBytes(), StandardCharsets.UTF_8);
                String clientMsg = "HTTP/1.1 200 OK\nContent-Type: application/octet-stream; charset=UTF-8\nContent-Disposition: attachment; filename=\"hello.html\"\n\n" + serverHTML + "\n";
                osw.write(clientMsg);
                osw.flush();
//                while ((len = inputStreamReader.read(cBuffer)) != -1) {
//                    String outputMsg = new String(cBuffer, 0, len);
//                    osw.write(outputMsg);
//                    osw.flush();
//
//                    int st = 0;
//                    for (int i = 0; i < len; i++) {
//                        if (outputMsg.charAt(i) == '\n') {
//                            sb.append(cBuffer, st, st - i);
//                            outputStreamWriter.write(sb.toString() + '\n');
//                            outputStreamWriter.flush();
//                            sb.setLength(prefixLen);
//                            st = i + 1;
//                        } else {
////                            sb.append(outputMsg.charAt(i));
//                        }
//                    }
//                    break;
//                }
                osw.close();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        InputStream is = socket.getInputStream();
        System.out.println("[서버]: 입력스트림 획득");
        BufferedInputStream bis = new BufferedInputStream(is, 8192);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String msg;
        Protocol protocol = new Protocol();
        while ((msg = br.readLine()) != null) {
            String newMsg = protocol.createMsg(msg);
            System.out.println(newMsg);
            outputStreamWriter.write("[클라이언트 IP]: " + msg + "\n");
            outputStreamWriter.flush();
        }
        System.out.println("[서버]: 출력완료");
    }
}
