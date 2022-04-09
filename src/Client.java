import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7777);
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
                OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw);

                InputStream inputStream = System.in;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String outputMsg;
                while ((outputMsg = bufferedReader.readLine()) != null) {
                    bw.write(outputMsg);
                    bw.flush();
                    System.out.println("flush 완료");
                }
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        thread.start();

        String inputMsg;
        while ((inputMsg = br.readLine()) != null) {
            System.out.println(inputMsg);
        }
        System.out.println("[클라]: 출력완료");
    }
}
