import enums.LineSeparator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import properties.Options;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
class Executor {
    int execute(Options options){
        log.info("処理開始");
        try {
            log.info(
                    "ファイル読込オプション（文字コード：[{}]、改行コード：[{}]、改行文字数：[{}]、読込ファイルパス：[{}]）"
                    , options.getEncode()
                    , options.getLineSeparator().getBytes()
                    , options.getLineLength()
                    , options.getFilePath()
            );
            Path inputFilePath = Paths.get(options.getFilePath());
            List<String> stringList = Files.readAllLines(
                    inputFilePath
                    , Charset.forName(options.getEncode())
            );
            String string = String.join("", stringList);
//            log.debug(string);
            List<String> separated = new ArrayList<>();
            for (int point = 0; point < string.length(); point += options.getLineLength()) {
//                log.debug("全体文字数：[{}]、現在のポインター：[{}]", string.length(), point);
                int endIndex = options.getLineLength() > string.length() ? string.length() : point + options.getLineLength();
                separated.add(string.substring(point, endIndex));
            }
            Path outputFilePath = Paths.get(
                    inputFilePath.getParent().toString()
                    , inputFilePath.getFileName() + ".bk"
            );
            FileUtils.writeLines(
                    outputFilePath.toFile()
                    , options.getEncode()
                    , separated
                    , options.getLineSeparator()
            );
        } catch (IOException e) {
            log.error("ファイル読込・書込に失敗しました。（ファイルパス：[{}]）", options.getFilePath(), e);
            return 9;
        }
        log.info("処理終了");
        return 0;
    }
}
