# 📦 LogisTrack API

**LogiTrack** é uma API RESTful desenvolvida para gestão e rastreamento de encomendas logísticas. O sistema permite o despacho de pacotes, atualização de status em tempo real e consulta pública de rastreio.

O principal foco técnico deste projeto é a implementação de **Segurança de Dados e Transformação** utilizando o padrão **DTO (Data Transfer Object)**, garantindo que dados sensíveis do banco de dados não sejam expostos diretamente aos consumidores da API.

---

## 🚀 Tecnologias e Stacks
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Maven](https://img.shields.io/badge/apachemaven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)</br></br>
* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3.x
    * Spring Web (REST)
    * Spring Data JPA (Persistência)
    * Spring Validation (Validação de DTOs)
* **Banco de Dados:** PostgreSQL (via Docker)
* **Documentação:** SpringDoc OpenAPI (Swagger UI)
* **Ferramentas:** Maven, Lombok, Docker Compose


---

## 🪪 Funcionalidades e Roadmap

- [x] **CRUD de Encomendas:** Criação e gerenciamento de pacotes.
- [x] **Gestão de Endereços:** Entidade separada para organização logística.
- [x] **Integração com ViaCEP:** Preenchimento automático de dados de endereço via API externa.
- [x] **Ambiente Containerizado:** Configuração completa de banco de dados com Docker.
- [ ] **Segurança (Em breve):** Autenticação e autorização com níveis de acesso (User/Admin).
- [ ] **Testes Unitários (Em breve):** Criação de testes unitários com JUnit e Mockito.

---

## ⚙️ Pré-requisitos

Para rodar este projeto, você precisará de:

1.  **JDK 17** ou superior instalado.
2.  **Docker** e **Docker Compose** instalados na máquina.
3.  Uma IDE (IntelliJ ou Eclipse).

---

## 🛠️ Configuração do Banco de Dados (Docker)

O projeto utiliza o **PostgreSQL** rodando em container. Não é necessário instalar o banco manualmente na sua máquina.

### 1. Subindo o Banco
Na raiz do projeto (onde está o arquivo `docker-compose.yml`), execute:

```bash
docker-compose up -d
