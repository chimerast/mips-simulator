## これは何
MIPSの回路図の動作をシミュレートしたものです。CPUの構造の勉強用です。簡易アセンブラもついてます。

Computer Organization and Design: The Hardware/Software Interface
http://www.amazon.co.jp/dp/0123747503

readme.pdfも参照。

## 起動方法
java MipsSimulator

## 使い方
1. 「読み込み」を押す
2. 出てきたダイアログに「fib.asm」と入力する
3. 開始ボタンを押すとなんだか動き出す
4. スライダーバーで計算機の速度が変えられる
5. リアルクロックモードで回路間の同期を取らなくなる（速度を上げると暴走する）
6. fib.asmはフィボナッチ数列の8番目を計算する。停止するとv0に21が入る。
