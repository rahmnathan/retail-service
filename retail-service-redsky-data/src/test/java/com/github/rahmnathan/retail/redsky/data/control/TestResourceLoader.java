package com.github.rahmnathan.retail.redsky.data.control;

import java.io.*;

public class TestResourceLoader {

    static String loadStringFromFile(String path) throws IOException {
        File exampleResponse = new File(path);
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(exampleResponse));
        br.lines().forEachOrdered(stringBuilder::append);
        br.close();

        return stringBuilder.toString();
    }
}
