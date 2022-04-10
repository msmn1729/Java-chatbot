public class Protocol {
    public String createMsg(String clientMsg) {
        if (clientMsg.equals("1")) {
            return "Hello world";
        } else if (clientMsg.equals("2")) {
            return "Hi";
        } else {
            return clientMsg;
        }
    }
}
