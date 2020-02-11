package ksaito.renameFiles;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
class Executor {
  private String inputDirPath;

  void run() {
    try {
      Path target = Paths.get(inputDirPath);
      AtomicInteger counter = new AtomicInteger(0);

      if (target.toFile().isDirectory()) {
        Optional.ofNullable(target.toFile().listFiles()).ifPresent(files -> {
          Arrays.stream(files)
                  .sorted(Comparator.naturalOrder())
                  .filter(file -> file.toPath().endsWith(".jpg") || file.toPath().endsWith(".png"))
                  .forEach(file -> {
                    System.out.println("処理ファイル名：" + file.getName());
                    try {
                      Files.move(
                              file.toPath()
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
                      System.exit(1);
                    }
                  });
        });
      } else {
        throw new Exception("指定されたパスがディレクトリではない");
      }

      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
