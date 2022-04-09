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

        Thread thread = new Thread(() -> {
            try {
                OutputStream os = socket.getOutputStream();
                System.out.println("[클라]: 출력스트림 획득");
                BufferedOutputStream bos = new BufferedOutputStream(os, 8192);

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
        System.out.println("[클라]: 출력완료");
    }
}
