package properties;

import enums.LineSeparator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

@Data
@Slf4j
public class Options {
    /** 文字コード. */
    private String encode;
    /** 改行コード. */
    private String lineSeparator;
    /** 改行文字数. */
    private Integer lineLength;
    /** ファイルパス. */
    private String filePath;

    public Options() throws IOException {
        Properties properties = new Properties();
        Path propPath = Paths.get("./", "user.properties");
        if (Files.exists(propPath)) {
            properties.load(
                    Files.newInputStream(
                            propPath
                            , StandardOpenOption.READ
                    )
            );
            this.encode = properties.getProperty("options.encode", "");
            this.lineSeparator = properties.getProperty("options.line.separator", "");
            this.lineLength = Integer.getInteger(properties.getProperty("options.line.length", "0"));
            this.filePath = properties.getProperty("options.file.path", "");
        } else {
            try {
                log.info("jarと同じディレクトリ内にプロパティファイルが存在しないため、jarに含まれるプロパティファイルを使用。");
                properties.load(this.getClass().getResourceAsStream("/application.properties"));
                this.encode = properties.getProperty("options.encode", "");
                this.lineSeparator = properties.getProperty("options.line.separator", "");
                this.lineLength = Integer.getInteger(properties.getProperty("options.line.length", "0"));
                this.filePath = properties.getProperty("options.file.path", "");
            } catch (Exception e) {
                log.info("プロパティファイルが存在しないため、デフォルトを使用。");
                this.encode = "JIS";
                this.lineSeparator = "\n";
                this.lineLength = 120;
                this.filePath = "C:/work/test/files/merchant_fee_return_0010_20190831";
            }
        }
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = LineSeparator.getCharactorByName(lineSeparator);
    }
}
