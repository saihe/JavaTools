package enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public enum LineSeparator {
    LF("\n", "LF"),
    CR("\r", "CR"),
    CRLF("\r\n", "CRLF");

  @Getter
  private final String character;
  @Getter
  private final String name;
    LineSeparator(String character, String name) {
        this.character = character;
        this.name = name;
    }
  public static LineSeparator getEnumByCharacter(String character) {
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
  public static LineSeparator getEnumByName(String name) {
    return Stream.of(
            values()
    ).filter(
            v -> v.getName().equals(name)
    ).findFirst().orElseGet(
            () -> {
              log.info(
                      "指定された改行コードが存在しないため、初期値を使用します。（指定改行コード：[{}]）"
                      , name
              );
              return CRLF;
            }
    );
  }
  public static String getCharactorByName(String name) {
    return Stream.of(
            values()
    ).filter(
            v -> v.getName().equals(name)
    ).findFirst().orElseGet(
            () -> {
              log.info(
                      "指定された改行コードが存在しないため、初期値を使用します。（指定改行コード：[{}]）"
                      , name
              );
              return CRLF;
            }
    ).getCharacter();
  }
}
