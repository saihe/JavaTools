package enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public enum LineSeparator {
    LF("\n"),
    CR("\r"),
    CRLF("\r\n");

    @Getter
    private final String character;
    LineSeparator(String character) {
        this.character = character;
    }
    public static LineSeparator getCharacter(String character) {
        return Stream.of(
                values()
        ).filter(
                v -> v.getCharacter().equals(character)
        ).findFirst().orElseGet(
                () -> {
                    log.info(
                            "指定された改行コードが存在しないため、初期値を使用します。（指定改行コード：[{}]）"
                            , character
                    );
                    return CRLF;
                }
        );
    }
}
