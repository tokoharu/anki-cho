(以下、雑なreadmeです)

## これはなに？

主に競プロの復習用に用いていたものです。 

自分だけで使うので動いてさえいれば良いと思っていたものを急遽公開することにしたので適当コードです。

酷い点は直していただけると助かります。

また、このような長文に対応できているいい感じの単語帳的なツールがあれば教えてください。

##使い方

### 例の紹介
1. java -jar RoteLearning.jar で起動

2. Fileボタンを押し、 ankicho-example.inを選択

3. Nextボタンを押す。もしくはAllProblemタブを押す。
作成者が実際に作ったページを見ることができます。


### 新規ファイルの作り方
1. java -jar RoteLearning.jar  をすると起動

2. ankicho-init.inを別のところにコピーして、付けたい名前をつけます

3. 起動したanki-choでFileボタンを押し、先ほど作ったファイルを選択します。

4. addpageするなりしてページを増やしましょう

5. Saveを押すことで上書き保存されます。


### 機能一覧

File : データが保存されているファイルを選んでください

Save : 読み込んだファイルに対して上書きします。（これを押さないと変更が保存されないので注意
       
main page : nextを押せば問題が出てきます。さらに押せばその解説が出てきます

add page : addを押せば問題を増やすことができます

edit page : all problems ページで選択されている問題を編集することができます

all problems : 問題一覧。 change posで問題の順序を変えることが出来ます(挿入形式です)

## ankicho-example

サンプルです。面白そうな問題or知識を少し入れました。

## その他

### 保存形式 :

  JSON形式
  
  問題はoriginalNumと問題文と解説文から成る。
  
  originalNumberは今のところ無意味。他の問題から参照したい時に便利かと思って作ったけど結局使わずに終わってる感じ
  
### Tagについて : 

  Tagはかなり便利なのでつけようと考えていたけれど結局できてない
  
  Tree形式のTagがいいなあと思っていたら結局できなかった
  
  その残骸が色々残っている（ファイルや保存ファイルの末尾を見ればわかる）
  
### 検索について : 

  あると便利だと思いますが作ってないです
  
  今まではgrepで対処してしまっていた
