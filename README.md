# PrestaPonto

PrestaPonto e uma plataforma de agendamento de servicos orientada a dispositivos moveis e baseada na web, que atua como um marketplace especializado para servicos gerais. O objetivo central do projeto e eliminar a fragmentacao digital e a ineficiencia dos processos manuais, que resultam em atritos para clientes e em problemas operacionais como overbooking para prestadores autonomos.

## Desenvolvedores
- Alexandre Aguiar Leite
- Gabriel Barreto Fragoso
- Gabriel Zanella Mendes
- Ilan Pires Damasceno
- Lucas Tavares De Farias
- Thais Sousa Rocha
- Thiago Estevão Da Silva
- Vitor Paulino Gonçalves

## Visao Geral

O repositorio esta organizado em:
- `prestaponto-api`: backend Spring Boot
- `prestaponto-ui`: frontend Angular
- `docker-compose.yml`: orquestracao local integrada com frontend, backend e PostgreSQL

## Pre-Requisitos
- Java 21
- Maven Wrapper
- Node.js 22
- npm
- Docker
- Docker Compose

## Configuração de Ambiente
O arquivo de exemplo oficial fica na raiz do projeto: `.env.example`
1. Copie `.env.example` para `.env`.
2. Preencha os valores das variaveis de ambiente.
3. Suba os servicos conforme o ambiente desejado.

## Como Rodar
### Ambiente Local Completo com Docker Compose
Esse modo sobe banco, backend e frontend de forma integrada.
1. Configure o arquivo `.env` na raiz.
2. Execute:
```bash
docker compose up --build
```

Acesso padrao:
- Frontend: `http://localhost:80`
- Backend: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

### Ambiente Local com Backend e Frontend Fora de Container
Esse modo e util quando o time quer desenvolvimento local com tooling nativo e banco em container.
1. Configure o arquivo `.env` na raiz.
2. Suba apenas o banco:
```bash
docker compose up -d db
```
3. Em outro terminal, suba o backend:
```bash
cd prestaponto-api
./mvnw spring-boot:run
```
4. Em outro terminal, suba o frontend:
```bash
cd prestaponto-ui
npm install
npm start
```

Acesso padrao:
- Frontend: `http://localhost:4200`
- Backend: `http://localhost:8080`
- PostgreSQL: `localhost:5432`
