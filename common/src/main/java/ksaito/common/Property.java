package ksaito.common;

import lombok.Getter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;
import static ksaito.common.Util.*;

@Getter
public class Property {
    private Properties prop;

    public Property() {
        prop = new Properties();

        try {
            if (exists(get(this.getClass().getResource("./application.properties").toURI()))) {
                prop.load(this.getClass().getResourceAsStream("./application.properties"));
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }
}
