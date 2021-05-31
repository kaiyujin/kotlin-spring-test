# このプロジェクトで試みたこと
### テストフレームワーク選定
https://kotest.io/
- データ駆動のテストで可読性向上と記述力を削減
- テスト範囲のグループ化

### kotestのSpec選定
MyServiceSpecを参照

### Springのテスト（DI)
MyServiceSpecを参照

### Mockライブラリの使い方
https://mockk.io/  
(springmockkは見送り)

- 現在日付の変更
  - MyServiceSpecを参照

### Test report
https://www.eclemma.org/jacoco/trunk/doc/index.html

### テスト用の各種設定値
- テスト用Propertyの読み込み

### Database setup
http://dbsetup.ninja-squad.com/user-guide.html

- test用DB
  - test/resources/application.yml 参照
- DDLの実行  
  `./gradlew build -x test; ./gradlew flywayMigrate -Dflyway.configFiles="gradle/gradle-unit.properties"`
- 外部キー制約の無効化
- シーケンスのクリア 冪等性担保

### テスト作成指針
- 参照系
  - Repository
    - SQLがあるRepositoryはServiceと分けて要テスト。
  - Service
    - ServiceはRepositoryを呼び出すだけの場合、且つRepositoryテストがあればテスト不要
    - Repositoryを呼び出す以外の処理をServiceで実装している場合は要テストRepositoryの結果はMockでよい。
  - Controller
    - ロジックを書く層ではないため、必要な場合のみ。
- 登録/更新系
  - Repository
    - 基本的には不要
  - Service
    - 要テスト
  - Controller
    - ロジックを書く層ではないため、必要な場合のみ。 
- その他
  - Utils
    - 要テスト


### Privateメソッドをテストすべきか。する場合の方法。
- Privateメソッドはテストすべきか論争
  - 結論
    - Privateメソッドもテストすることはあるというスタンスをとる 。  
  - 理由
    - すべてのPrivateメソッドをテストするルールにすると、巨大なPublicメソッドを作ることがあるため。
    - 逆に正しく複雑な処理をメソッドに分解していくと、複雑な判定はPrivateメソッドによるが、すべてのPrivateメソッドをテストしないルールにすると、Publicのテストケースが大きくなり正しくテストされないことがあるため。
  - Privateメソッドのテスト方法
    - ReflectionTestUtils.invokeMethodを利用する。MyServiceSpecを参照
      - リフレクションなのでリファクタ時にIDEで自動変更されないデメリットはある
      - テスト実行時JVMから警告はでるが気にしない(仕方ない)
