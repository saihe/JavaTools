package ksaito.renameFiles;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
class Executor {
  private String inputDirPath;

  void run() {
    System.out.println("開始");
    System.out.println("対象パス：" + inputDirPath);
    try {
      Path target = Paths.get(inputDirPath);
      AtomicInteger counter = new AtomicInteger(0);

      if (Files.isDirectory(target)) {
        System.out.println("内部ファイル取得");
        System.out.println("取得ファイル数：" + Files.list(target).count());
        Optional.ofNullable(Files.walk(target, 2))
          .ifPresent(pathStream ->
              pathStream
                .sorted(Comparator.naturalOrder())
                .filter(path -> !Files.isDirectory(path))
                .filter(path ->  path.toString().endsWith(".jpg") || path.toString().endsWith("png"))
                .forEach(path -> {
                  System.out.println("処理ファイル名：" + path.getFileName());
                  try {
                    Files.move(
                      path
                      , Paths.get(
                        inputDirPath
                        , StringUtils.leftPad(
                          String.valueOf(counter.incrementAndGet())
                          , 3
                          , "0")
                      )
                      , StandardCopyOption.ATOMIC_MOVE
                    );
                  } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("異常終了");
                    System.exit(1);
                  }
                })
                );
      } else {
        throw new Exception("指定されたパスがディレクトリではない（" + inputDirPath + "）");
      }

      System.out.println("終了");
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("異常終了");
      System.exit(1);
    }
  }
}
