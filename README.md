# SpeedPay

O SpeedPay é um projeto acadêmico que consiste em uma aplicação desktop de automação de pagamento de salários para os funcionários de uma empresa. O SpeedPay importa a partir de **[um template de planilha Excel](https://www.mediafire.com/file/0vcbkp1q61q3vvn/Sal%25C3%25A1rios.xlsx/file)** o nome, ID do funcionário na conta Stripe e o salário, cadastrando esses dados em um banco de dados. O usuário pode realizar o pagamento de um ou mais funcionários ao mesmo tempo com apenas dois cliques. Os pagamentos são realizados utilizando a **[Stripe API](https://stripe.com/docs/api)**.

## 🚀 Começando

Para obter uma cópia do SpeedPay, faça o download do projeto. Em seguida, compile-o levando em consideração as suas dependências. Para isso, é sugerido a utilização de um gerenciador de dependências, como **[Maven](https://maven.apache.org/install.html)** ou **[Gradle](https://gradle.org/install/)**. Além do mais, em ````application.service.Payments````, substitua o valor da chave Stripe pela que está disponível em seu painel.

### 📋 Pré-requisitos

```
- Java 17 ou superior
- MySQL Server 
```

### 🔧 Instalação

Instalando o **[JDK 17 ou superior](https://www.azul.com/downloads/?package=jdk#zulu)**:

```
- Faça o download do JDK. Se você usa Windows, é recomendável escolher a opção de arquivo .msi.

- Após a instalação, abra o prompt de comandos e digite o comando "java –version" para verificar 
se o Java instalado se encontra no caminho. Caso isto não aconteça, crie uma variável de sistema de 
nome JAVA_HOME, especificando o caminho até o JDK. No Windows, o JDK se encontra dentro da pasta 
Java em "Arquivos de Programas".
```
Instalando o **[MySQL Server](https://dev.mysql.com/downloads/installer/)**:

```
- Após realizar o download do instalador, instale os seguintes produtos: MySQL Server, MySQL Workbench, 
MySQL Shell, ConnectorODBC, ConnectorJ, MySQL Documentation, Samples and Examples.

- Na instalação do MySQL Server, crie um usuário de nome "root" e senha "admin".

- Abra o MySQL Workbench, clique no usuário que você criou e insira a senha.
```
Criando o **[banco de dados](http://twixar.me/LR6m)**:

```
- Faça o download do script SQL.

- No MySQL Workbench, selecione "File", e em seguida "Open SQL Script". Selecione o script SQL e o execute.
```
Download da **[aplicação](https://www.mediafire.com/file/lyipqnw5176rryv/SpeedPay-2.0.rar/file)**:

```
- Faça o download da pasta da aplicação. No Windows, é recomendável que a pasta seja extraída em 
"Disco Local C:\".

- Se desejar, crie um atalho na área de trabalho para o arquivo SpeedPay.exe.
```

**OBSERVAÇÃO:** O executável disponível para download está utiizando uma chave Stripe de teste. Caso queira montar a sua própria aplicação, certifique-se de modificá-la antes de realizar a compilação. Você pode utilizar **[Launch4J](https://sourceforge.net/projects/launch4j/)** para construir um executável a partir de um arquivo jar.

## 🛠️ Construído com

* [JavaFX](https://openjfx.io/) - Framework usado para a interface gráfica
* [Apache POI](https://poi.apache.org/) - API para lidar com planilhas Excel
* [JDBC Connector](https://www.mysql.com/products/connector/) - Utilizado para interagir com o banco de dados
* [Stripe](https://stripe.com/docs/api) - API para lidar com processamento de pagamentos na plataforma Stripe

## ✒️ Autores

* **Zicri** - *Código* - [ZiKriDev](https://github.com/ZiKriDev)
* **Emerson** - *Código* - [Emersonok](https://github.com/Emersonok)
* **Milena** - *Documentação* - [Milconstx](https://github.com/Milconstx)
* **Caio** - *Documentação* - [Kaiodee](https://github.com/Kaiodee)
* **Renata** - *Documentação* - [Kaisasemw](https://github.com/Kaisasemw)

## 🎁 Agradecimentos

* Agradecemos especialmente à nossa professora da disciplina de Programação Orientada a Objetos por nos desafiar a realizar este projeto.

---
