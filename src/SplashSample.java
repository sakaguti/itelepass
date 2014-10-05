import java.awt.SplashScreen;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashSample {
    public SplashSample() throws MalformedURLException, IOException {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();

        URL url = new File("images/splash.png").toURI().toURL();
        splashScreen.setImageURL(url);

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException ex) {}
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        new SplashSample();
    }
}