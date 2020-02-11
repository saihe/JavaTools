package ksaito.renameFiles;

import java.nio.file.Paths;

public class Application {
  public static void main(String[] args) {
    if (args.length > 0) {
      Executor.builder().
              inputDirPath(args[0])
              .build()
              .run();
    } else {
      Executor.builder()
              .inputDirPath(Paths.get("").toAbsolutePath().toString())
              .build()
              .run();
    }
  }
}
