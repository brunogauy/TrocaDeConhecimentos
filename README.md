# Troca de Conhecimentos

Sistema onde usuários oferecem habilidades que sabem e buscam habilidades que querem aprender, registrando trocas entre eles.

## Sobre o projeto

O sistema é organizado em torno de três entidades. O **Usuário** é quem participa do sistema, identificado por nome e e-mail. A **Habilidade** representa algo que um usuário sabe fazer e está disposto a ensinar, ou algo que ele quer aprender. A **Troca** liga dois usuários: um oferece uma habilidade e o outro oferece outra em contrapartida, registrando esse acordo entre as partes. Na prática, o sistema funciona como um mural de trocas: cada pessoa cadastra o que sabe e o que quer aprender, e as trocas conectam quem tem o que o outro precisa.

## Arquitetura

O projeto segue o padrão MVC em camadas (Controller → Service → DAO → Model), implementado com Servlet e JSP puros, sem uso de frameworks como Spring. Essa estrutura segue o modelo ensinado na disciplina: os Servlets recebem as requisições e delegam a regra de negócio para a camada de Service, que por sua vez usa a camada de DAO para acessar o banco de dados; as JSPs ficam responsáveis apenas pela exibição dos dados.

## Pré-requisitos

- **JDK 21** — necessário para compilar e rodar o código Java do projeto.
- **Maven** — gerencia as dependências e empacota a aplicação em um arquivo `.war`.
- **Docker Desktop** — sobe os containers do Tomcat (servidor de aplicação) e do MySQL (banco de dados).

## Como rodar

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

## Estrutura de pastas

```
src/main/java/br/com/mvc
├── controller/     # Servlets: recebem as requisições HTTP
├── service/        # Regras de negócio
├── dao/            # Acesso ao banco de dados
├── model/          # Entidades: Usuario, Habilidade, Troca
└── config/         # Configuração de conexão com o banco

src/main/webapp
├── WEB-INF/jsp/    # Páginas JSP, organizadas por entidade
└── css/            # Estilos da aplicação
```
