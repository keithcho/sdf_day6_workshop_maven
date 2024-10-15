package server;

import java.io.*;
import java.util.*;

public class Cookie {
    private static List<String> cookies;

    public static void init(String cookieFile) throws IOException {
        cookies = getDataFromText(cookieFile);
    }

    public static String getRandomCookie() {
        String randomCookie = "";
        if (!cookies.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(cookies.size());
            randomCookie = cookies.get(randomIndex);
        } else {
            System.err.println("No cookies in cookie file");
        }
        return randomCookie;
    }

    public static List<String> getDataFromText(String cookieFile) throws IOException {
        List<String> cookies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cookieFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                cookies.add(line);
            }
        }
        return cookies;
    }
}
