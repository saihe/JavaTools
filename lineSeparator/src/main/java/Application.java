import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

import static java.lang.System.*;

@Slf4j
public class Application {
    /**
     * ファイルを取得し指定文字数で改行する
     * @param args .
     */
    public static void main(String[] args) {
        // プロパティ取得・設定
        properties.Options options;
        try {
            options = new properties.Options();
            // オプション取得・設定
            getOptions(args, options);
//            System.getProperties().propertyNames();
//            System.getProperties().keySet().stream().sorted().map(Object::toString).forEach(p -> {
//                log.debug("{}：{}", p, System.getProperty(p));
//            });

            // 処理
            exit(new Executor().execute(options));
        } catch (IOException e) {
            log.error("プロパティの読み込みに失敗しました。", e);
            exit(9);
        }
    }

    private static void getOptions(String[] args, properties.Options options) {
        log.info("起動引数：[{}]", String.join(",", args));
        for (int arrayIndex = 0; arrayIndex < args.length; arrayIndex++) {
            String arg = args[arrayIndex];
            String value = "";
            if (
                    arg.startsWith("-")
                            && !arg.equals("-h")
                            && arrayIndex + 1 < args.length
            ) {
                 value = args[arrayIndex + 1];
            }
            try {
                if (arg.startsWith("-")) {
                    switch (enums.Options.getEnumName(arg)) {
                        case DEBUG:
                            out.println("デバッグモードで起動します。");
                            setProperty("org.slf4j.simpleLogger.log.MyClass","DEBUG");
                            break;
                        case HELP:
                            out.println("このアプリケーションはテキストファイルを指定した文字数ずつ改行して新しいファイルを作成します。");
                            out.println("指定可能オプションは以下の通りです。");
                            out.println("[-d]：デバッグモードへ変更");
                            out.println("[-h]：ヘルプ表示");
                            out.println("[-e 文字コード]：文字コード指定（初期値：UTF-8） ex) -e JIS");
                            out.println("[-s 改行コード]：改行コード指定（初期値：CRLF） ex) -s LF");
                            out.println("[-l 改行文字数]：改行文字数指定（初期値：120） ex) -l 250");
                            out.println("[-f 取込ファイルパス]：取込ファイルパス指定（初期値：なし、指定必須） ex) -f C:/tmp/test.txt");
                            exit(0);
                        case ENCODING:
                            options.setEncode(value);
                            break;
                        case LINE_SEPARATOR:
                            options.setLineSeparator(value);
                            break;
                        case LINE_LENGTH:
                            options.setLineLength(Integer.parseInt(value));
                            if (Objects.isNull(options.getLineLength())) {
                                throw new IllegalArgumentException("[-l]オプションでは正しい数値を入力してください。");
                            }
                            break;
                        case FILE_PATH:
                            options.setFilePath(value);
                            break;
                        default:
                            throw new IllegalStateException(MessageFormat.format(
                                    "無効なオプションが指定されています。（指定オプション：[{0}]）"
                                    , arg
                            ));

                    }
                }
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
