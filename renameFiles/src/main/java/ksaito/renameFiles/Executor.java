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

import static ksaito.common.Util.exit;
import static ksaito.common.Util.println;

@Builder
class Executor {
    private String inputDirPath;

    void run() {
        println("開始");
        println("対象パス：" + inputDirPath);
        try {
            Path target = Paths.get(inputDirPath);
            AtomicInteger jpgCounter = new AtomicInteger(0);
            AtomicInteger pngCounter = new AtomicInteger(0);

            if (Files.isDirectory(target)) {
                println("取得ファイル数：" + Files.walk(target).count());
                Optional.ofNullable(Files.walk(target, 2))
                    .ifPresent(pathStream ->
                        pathStream
                            .sorted(Comparator.naturalOrder())
                            .filter(path -> !Files.isDirectory(path))
                            .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png"))
                            .forEach(path -> {
                                String fileName = path.getFileName().toString();
                                println("処理ファイル名：" + fileName);
                                Path movedPath = Paths.get(
                                    inputDirPath
                                    , StringUtils.leftPad(
                                        String.valueOf(
                                            fileName.endsWith(".jpg") ? jpgCounter.incrementAndGet() : pngCounter.incrementAndGet()
                                        )
                                        , 3
                                        , "0") + fileName.substring(fileName.lastIndexOf(".")
                                    )
                                );
                                println("リネーム後ファイル名：" + movedPath);

                                try {
                                    Files.move(
                                        path
                                        , movedPath
                                        , StandardCopyOption.ATOMIC_MOVE

                                    );
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    println("異常終了");
                                    exit(1);
                                }
                            })
                    );
            } else {
                throw new Exception("指定されたパスがディレクトリではない（" + inputDirPath + "）");
            }

            println("処理ファイル数：" + jpgCounter.addAndGet(pngCounter.intValue()));
            println("終了");
            exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            println("異常終了");
            exit(1);
        }
    }
}
