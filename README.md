## ユーザー管理のデモプロジェクト

完全に学習用リポジトリっす

学習内容はここらへん↓

* 各モジュールの作成方法
* データバインド
* Spring Security
* Dockerの作成←New！

以下の書籍を参考にしてコーディング

* [Spring解体新書](https://www.amazon.co.jp/%E3%80%90%E5%BE%8C%E6%82%94%E3%81%97%E3%81%AA%E3%81%84%E3%81%9F%E3%82%81%E3%81%AE%E5%85%A5%E9%96%80%E6%9B%B8%E3%80%91Spring%E8%A7%A3%E4%BD%93%E6%96%B0%E6%9B%B8-Boot2%E3%81%A7%E5%AE%9F%E9%9A%9B%E3%81%AB%E4%BD%9C%E3%81%A3%E3%81%A6%E5%AD%A6%E3%81%B9%E3%82%8B%EF%BC%81Spring-Security%E3%80%81Spring-JDBC%E3%80%81Spring-MyBatis%E3%81%AA%E3%81%A9%E5%A4%9A%E6%95%B0%E8%A7%A3%E8%AA%AC%EF%BC%81-ebook/dp/B07H6XLXD7/ref=sr_1_4?__mk_ja_JP=%E3%82%AB%E3%82%BF%E3%82%AB%E3%83%8A&keywords=Spring+boot&qid=1562421688&s=gateway&sr=8-4)

いろいろごちゃごちゃとやってますなぁ…

## Dockerによるプロジェクトの起動について

### DBコンテナの立ち上げ

```cmd
docker container run -v $(PWD)/sql:/docker-entrypoint-initdb.d -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=38365388 -p 3306:3306 -d postgres
```

sqlフォルダ配下のSQLを実行したDBコンテナを作成する。

SQLの実行順序はファイル名のプレフィックス順になる。

### Dockerfileの実装

ベースのイメージはubuntu16.04を使用した。

プロファイルはDocker用のものを指定。

詳細はDockerfileを参照。

### docker-compose.ymlの実装

DBコンテナとAPPコンテナ（Spring Bootの実体、上の項目のDockerfileで作ったやつ）の依存性について実装。

まぁ触り始めだし２つしかコンテナは作らないよ！

### 参考記事

[SpringBootの開発環境をdockerでつくる](https://qiita.com/wataling/items/fa8b74fa50d80b88aea3)