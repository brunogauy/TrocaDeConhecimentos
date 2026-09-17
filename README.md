# Sistema de Troca de Conhecimentos 🔄

## 👥 Integrante
- Bruno Gauy Ramos – RA: 5161403

## 📌 Visão Geral
O sistema é organizado em torno de três entidades. O **Usuário** é quem participa do sistema, identificado por nome e e-mail. A **Habilidade** representa algo que um usuário sabe fazer e está disposto a ensinar, ou algo que ele quer aprender. A **Troca** liga dois usuários — um que oferece uma habilidade e outro que tem interesse nela — registrando esse acordo entre as partes. Na prática, o sistema funciona como um mural de trocas: cada pessoa cadastra o que sabe, e as trocas conectam quem oferece com quem tem interesse em determinada habilidade.

## ✨ Funcionalidades
- CRUD completo de **Usuários** (nome e e-mail), com validação de e-mail único
- CRUD completo de **Habilidades**, com validação de nome único
- CRUD completo de **Trocas**, vinculando um usuário que oferece, um usuário interessado e uma habilidade
- Validação para impedir que o usuário oferecendo e o interessado sejam a mesma pessoa
- Proteção contra exclusão de Usuário ou Habilidade que já tenha Troca vinculada

## 🏗️ Arquitetura
O projeto segue o padrão MVC em camadas (Controller → Service → DAO → Model), implementado com Servlet e JSP puros, sem uso de frameworks como Spring.

```
br.com.mvc
├── controller/     # Servlets: recebem as requisições HTTP
├── service/        # Regras de negócio e validações
├── dao/            # Acesso ao banco de dados
├── model/          # Entidades: Usuario, Habilidade, Troca
└── config/         # Configuração de conexão com o banco (MysqlSingleton)

webapp/WEB-INF/jsp/
├── usuarios/       # lista.jsp e form.jsp
├── habilidades/    # lista.jsp e form.jsp
├── trocas/         # lista.jsp e form.jsp
└── home.jsp
```

## 🗄️ Banco de Dados
O schema é criado automaticamente pelo `init.sql` no banco `mvc_java`:

- **usuarios** — `id`, `nome`, `email` (único)
- **habilidades** — `id`, `nome`
- **trocas** — `id`, `usuario_oferecendo_id`, `usuario_interessado_id`, `habilidade_id` (chaves estrangeiras para `usuarios` e `habilidades`)

O script também insere dados de exemplo: 3 usuários, 3 habilidades (Java, Ingles, Design Grafico) e 3 trocas entre eles.

## 🧰 Tecnologias
- Java 17
- Jakarta Servlet API 6.0.0
- Jakarta Servlet JSP JSTL 3.0.0
- MySQL Connector/J 8.4.0
- MySQL 8.4
- Apache Tomcat 10.1 (jdk21)
- Maven (empacotamento em `.war`)
- Docker / Docker Compose

## 🚀 Como executar

**Pré-requisitos:**
- JDK 21
- Maven
- Docker Desktop

**Passo a passo:**

1. Clone o repositório:
   ```
   git clone https://github.com/brunogauy/TrocaDeConhecimentos.git
   cd TrocaDeConhecimentos
   ```

2. Gere o arquivo `.war` da aplicação:
   ```
   mvn package
   ```

3. Suba os containers do Tomcat e do MySQL:
   ```
   docker-compose up -d
   ```

4. Acesse no navegador:
   ```
   http://localhost:8080/mvc/
   ```

## 🗄️ Conexão com banco
- **Host:** localhost
- **Porta:** 3306
- **Banco:** mvc_java
- **Usuário:** mvc_user
- **Senha:** mvc123

## 📁 Estrutura do projeto
```
TrocaConhecimentos/
├── docker-compose.yml
├── init.sql
├── pom.xml
└── src/main/
    ├── java/br/com/mvc/
    │   ├── config/
    │   ├── controller/
    │   ├── dao/
    │   ├── model/
    │   └── service/
    └── webapp/
        ├── WEB-INF/
        │   ├── jsp/
        │   │   ├── habilidades/
        │   │   ├── trocas/
        │   │   ├── usuarios/
        │   │   └── home.jsp
        │   └── web.xml
        ├── css/
        │   └── estilo.css
        └── index.jsp
```

## ✅ Observações
- O sistema não possui autenticação nem controle de sessão, já que o tema proposto pedia apenas o CRUD das entidades, sem login.
- O Docker automatiza a inicialização do banco de dados (MySQL, via `init.sql`) e do servidor de aplicação (Tomcat), bastando rodar `docker-compose up -d` após gerar o `.war`.
