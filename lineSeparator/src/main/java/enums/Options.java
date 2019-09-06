package enums;

import lombok.Getter;

import java.text.MessageFormat;
import java.util.stream.Stream;

public enum Options {
    HELP("-h"),
    ENCODING("-e"),
    LINE_SEPARATOR("-s"),
    LINE_LENGTH("-l"),
    FILE_PATH("-f");

    @Getter
    private final String type;
    Options(String type) {
        this.type = type;
    }

    public static Options getEnumName(String str) {
        return Stream.of(
                values()
        ).filter(
                v -> v.getType().equals(str)
        ).findFirst().orElseThrow(
                () -> new IllegalArgumentException(
                        MessageFormat.format(
                                "無効なオプションが指定されています。（指定オプション：[{}]）"
                                , str
                        )
                )
        );
    }
}
