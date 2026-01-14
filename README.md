# 📦 LogisTrack API

**LogiTrack** é uma API RESTful desenvolvida para gestão e rastreamento de encomendas logísticas. O sistema permite o despacho de pacotes, atualização de status em tempo real e consulta pública de rastreio com cálculo automático de tempo em trânsito.

O principal foco técnico deste projeto é a implementação de **Segurança de Dados e Transformação** utilizando o padrão **DTO (Data Transfer Object)**, garantindo que dados sensíveis do banco (Oracle) não sejam expostos diretamente aos consumidores da API.

---

## 🚀 Tecnologias e Stacks

* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3.x
    * Spring Web (REST)
    * Spring Data JPA (Persistência)
    * Spring Validation (Validação de DTOs)
* **Banco de Dados:** Oracle Database Free (via Docker)
* **Documentação:** SpringDoc OpenAPI (Swagger UI)
* **Ferramentas:** Maven, Lombok

---
## 🪪 Próximos passos
- [x] **CRUD de Encomendas:** Criação e gerenciamento de pacotes.
- [x] **Gestão de Endereços:** Entidade separada para organização logística.
- [x] **Integração com ViaCEP:** Preenchimento automático de dados de endereço via API externa.
- [ ] **Segurança (Em breve):** Autenticação e autorização com níveis de acesso (User/Admin).
- [ ] **Testes Unitários(Em breve):** Criação de testes unitários com JUnit e Mockito.
      
---

## ⚙️ Pré-requisitos

Para rodar este projeto, você precisará de:

1.  **JDK 17** ou superior instalado.
2.  **Docker** rodando na máquina.
3.  Uma IDE (IntelliJ ou Eclipse).

---

## 🛠️ Configuração do Banco de Dados (Docker)

O projeto utiliza a imagem oficial `oracle/database:free`. Execute o comando abaixo para subir o banco:

```bash
docker run -d --name oracle-db \
  -p 1521:1521 \
  -e ORACLE_PWD=SuaSenhaForte123 \
  [container-registry.oracle.com/database/free:latest](https://container-registry.oracle.com/database/free:latest)
