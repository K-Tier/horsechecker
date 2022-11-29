import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            //URL url = new URL("https://race.netkeiba.com/special/index.html?id=0096");
            URL url = new URL("https://race.netkeiba.com/special/index.html?id=0075");
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "euc-jp");
            int i = isr.read();
            String checkId;
            while(i != -1){
                
                System.out.print((char) i);
                i = isr.read();
            }
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}