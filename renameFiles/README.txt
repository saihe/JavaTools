# 概要
起動引数に指定したディレクトリ内のファイルをファイル名の昇順で取得し、001〜999までの連番を振りファイル名を変更するモジュール。
起動引数を指定しない場合はカレントディレクトリを参照する

# 使用方法

## gradeで実行する
ディレクトリがJavaToolsである状態で、
```
./gradlew :renameFiles:run --args="$(pwd)./input"
```
※--argsには読み込ませたいディレクトリを指定する
　→$(pwd)./inputなら、コマンドを実行したディレクトリ/input

## jarを生成する
ディレクトリがJavaToolsである状態で、
```
./gradlew :renameFiles:jar
```
renameFilesディレクトリのbuild/libsにjarが生成される
jar実行時の起動引数に対象にするディレクトリパスを指定する