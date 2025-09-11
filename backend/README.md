# 🏦 Evolve Bank - Sistema de Transferências

Um sistema bancário moderno desenvolvido com Spring Boot para gerenciamento de usuários, transações financeiras e administração de taxas.

## 📑 Sumário

1. [Visão Geral](#-visão-geral)
2. [Arquitetura e Padrões](#-arquitetura-e-padrões)
3. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
4. [Estrutura do Projeto](#-estrutura-do-projeto)
5. [Entidades do Sistema](#-entidades-do-sistema)
6. [Funcionalidades](#-funcionalidades)
7. [API Endpoints](#-api-endpoints)
8. [Segurança](#-segurança)
9. [Sistema de Agendamento](#-sistema-de-agendamento)
10. [Configuração e Execução](#-configuração-e-execução)
11. [Banco de Dados](#-banco-de-dados)
12. [Validações e Tratamento de Erros](#-validações-e-tratamento-de-erros)

## 🎯 Visão Geral

O **Evolve Bank** é uma aplicação backend robusta que simula um sistema bancário completo, oferecendo funcionalidades como:

- **Gestão de Usuários**: Cadastro, autenticação e perfis
- **Transações Financeiras**: Transferências, depósitos e agendamentos
- **Sistema de Taxas**: Cálculo dinâmico baseado em prazo
- **Administração**: Painel administrativo completo
- **Segurança**: Autenticação JWT e controle de acesso
- **Processamento Automatizado**: Execução de transações agendadas

## 🏗️ Arquitetura e Padrões

### Padrão Arquitetural
- **Arquitetura em Camadas (Layered Architecture)**
- **Padrão MVC (Model-View-Controller)**
- **Repository Pattern** para acesso a dados
- **Service Layer Pattern** para lógica de negócio
- **DTO Pattern** para transferência de dados

### Estrutura de Camadas

```
┌─────────────────────────┐
│     Controller Layer    │  ← Endpoints REST
├─────────────────────────┤
│      Service Layer      │  ← Lógica de Negócio
├─────────────────────────┤
│    Repository Layer     │  ← Acesso a Dados
├─────────────────────────┤
│      Entity Layer       │  ← Modelos de Dados
└─────────────────────────┘
```

### Princípios SOLID Aplicados
- **Single Responsibility**: Cada classe tem uma responsabilidade específica
- **Open/Closed**: Extensível através de interfaces
- **Liskov Substitution**: Uso de interfaces e herança
- **Interface Segregation**: Interfaces específicas por funcionalidade
- **Dependency Inversion**: Injeção de dependências via Spring

## 🛠️ Tecnologias Utilizadas

### Core Framework
- **Spring Boot 2.7.18** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - API REST
- **Spring Validation** - Validação de dados
- **Spring Mail** - Envio de emails

### Banco de Dados
- **H2 Database** - Banco em memória para desenvolvimento
- **Hibernate** - ORM para mapeamento objeto-relacional

### Segurança e Autenticação
- **JWT (JSON Web Tokens)** - Autenticação stateless
- **BCrypt** - Hash de senhas

### Utilitários
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências
- **Jackson** - Serialização JSON

### Versão Java
- **Java 11** - Versão LTS utilizada

## 📁 Estrutura do Projeto

```
src/main/java/com/bank/evolve/
├── 📁 config/           # Configurações do sistema
│   ├── SecurityConfig.java
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   ├── TransactionScheduler.java
│   ├── TokenCleanupScheduler.java
│   ├── DataLoader.java
│   ├── GlobalErrorHandler.java
│   └── CustomAccessDeniedHandler.java
├── 📁 controller/       # Controladores REST
│   ├── AuthController.java
│   ├── UserController.java
│   ├── TransactionController.java
│   └── AdminController.java
├── 📁 dto/             # Data Transfer Objects
│   ├── 📁 request/     # DTOs de entrada
│   └── 📁 response/    # DTOs de saída
├── 📁 entity/          # Entidades JPA
│   ├── User.java
│   ├── Transaction.java
│   ├── TransferTaxes.java
│   └── PasswordResetToken.java
├── 📁 enums/           # Enumerações
│   ├── Roles.java
│   ├── TransactionStatus.java
│   ├── TransactionDirection.java
│   └── TransactionTypes.java
├── 📁 repository/      # Repositórios JPA
├── 📁 service/         # Interfaces de serviço
│   └── 📁 impl/       # Implementações dos serviços
├── 📁 helper/          # Classes auxiliares
├── 📁 util/           # Utilitários
└── EvolveApplication.java # Classe principal
```

## 🗃️ Entidades do Sistema

### 👤 User (Usuário)
```java
- id: Long (PK)
- fullName: String
- email: String (unique)
- phone: String (unique)
- cpf: String (unique)
- passwordHash: String
- accountNumber: String (unique)
- balance: Double (default: 0.0)
- isActive: Boolean (default: true)
- role: Roles (default: USER)
```

### 💰 Transaction (Transação)
```java
- id: Long (PK)
- amount: Double
- description: String
- taxes: Double
- sender: User (FK)
- recipient: User (FK)
- targetDate: LocalDate
- timestamp: LocalDateTime
- transactionHash: String (unique)
- status: TransactionStatus
```

### 📊 TransferTaxes (Taxas de Transferência)
```java
- id: Long (PK)
- amountDays: Integer
- fixedTax: Double
- taxPercentage: Double
```

### 🔑 PasswordResetToken (Token de Reset de Senha)
```java
- id: Long (PK)
- token: String
- user: User (FK)
- expiryDate: LocalDateTime
```

## ⚡ Funcionalidades

### 🔐 Autenticação e Autorização
- **Registro de usuários** com validação de dados
- **Login** com geração de JWT
- **Recuperação de senha** via email
- **Controle de acesso** baseado em roles (USER/ADMIN)

### 👥 Gestão de Usuários
- **Perfil do usuário**: visualização e edição
- **Soft delete**: desativação de contas
- **Consulta por número da conta** (dados mascarados)
- **Administração**: CRUD completo de usuários

### 💸 Sistema de Transações
- **Transferências** entre contas
- **Depósitos** na conta própria
- **Agendamento** de transações futuras
- **Cancelamento** de transações pendentes
- **Histórico** completo de transações
- **Hash único** para cada transação

### 💹 Sistema de Taxas
- **Cálculo dinâmico** baseado no prazo
- **Taxa fixa + percentual** configurável
- **Administração** de regras de taxas
- **Simulação** de custos antes da transação

### 🤖 Processamento Automatizado
- **Scheduler diário** (9h) para processar transações agendadas
- **Limpeza automática** de tokens expirados
- **Atualização de status** das transações

## 🔌 API Endpoints

### 🔐 Autenticação (`/auth`)
```http
POST /auth                           # Login
POST /auth/password-reset/request    # Solicitar reset de senha
POST /auth/password-reset/confirm    # Confirmar reset de senha
```

### 👤 Usuários (`/user`)
```http
POST /user                          # Cadastrar usuário
GET /user                           # Obter dados do usuário logado
PATCH /user                         # Atualizar dados do usuário
DELETE /user                        # Desativar conta do usuário
GET /user/account/{accountNumber}   # Buscar por número da conta
```

### 💰 Transações (`/transaction`)
```http
POST /transaction/calculate         # Calcular taxas
POST /transaction                   # Criar transferência
PUT /transaction                    # Fazer depósito
GET /transaction                    # Listar transações do usuário
DELETE /transaction/{id}            # Cancelar transação
```

### 🛡️ Administração (`/admin`)
```http
# Usuários
GET /admin/user                     # Listar todos os usuários
GET /admin/user/id/{id}            # Obter usuário por ID
PATCH /admin/user/id/{id}          # Editar usuário
PUT /admin/user/id/{id}            # Bloquear/desbloquear usuário
DELETE /admin/user/id/{id}         # Soft delete do usuário
DELETE /admin/user/id/{id}/hard    # Hard delete do usuário

# Taxas
POST /admin/taxes                  # Criar regra de taxa
GET /admin/taxes                   # Listar regras de taxas
PATCH /admin/taxes/id/{id}         # Editar regra de taxa
DELETE /admin/taxes/id/{id}        # Deletar regra de taxa

# Transações
GET /admin/transactions            # Listar todas as transações
GET /admin/transactions/id/{id}    # Listar transações de um usuário
```

## 🔒 Segurança

### Autenticação JWT
- **Token Bearer** nas requisições
- **Expiração configurável** (1 hora por padrão)
- **Secret key** customizável
- **Validação automática** em todas as rotas protegidas

### Controle de Acesso
```java
// Rotas públicas
POST /user                 # Cadastro
POST /auth/**             # Autenticação
GET /h2-console/**        # Console H2 (dev)

// Rotas de usuário autenticado
GET|PATCH|DELETE /user    # Perfil do usuário
/transaction/**           # Transações

// Rotas administrativas
/admin/**                 # Apenas ADMIN
```

### Validações de Segurança
- **Senhas criptografadas** com BCrypt
- **Validação de CPF** no formato brasileiro
- **Email único** por usuário
- **Conta única** gerada automaticamente
- **Tokens de reset** com expiração

### CORS Configuration
- **Origens permitidas**: todas (*)
- **Métodos**: GET, POST, PUT, DELETE, OPTIONS
- **Headers**: todos permitidos

## ⏰ Sistema de Agendamento

### TransactionScheduler
```java
@Scheduled(cron = "0 0 9 * * ?") // Executa diariamente às 9h
```

**Funcionalidades:**
- **Processamento automático** de transações agendadas
- **Verificação de saldo** antes da execução
- **Atualização de status** (PENDING → COMPLETED/CANCELLED)
- **Logs detalhados** de execução
- **Tratamento de erros** individualizado

### TokenCleanupScheduler
```java
@Scheduled(fixedRate = 300000) // Executa a cada 5 minutos
```

**Funcionalidades:**
- **Limpeza automática** de tokens expirados
- **Otimização** do banco de dados
- **Manutenção preventiva**

## 🚀 Configuração e Execução

### Pré-requisitos
- **Java 11** ou superior
- **Maven 3.6+**
- **IDE** de preferência (IntelliJ, Eclipse, VS Code)

### Variáveis de Ambiente
```properties
# JWT Configuration
app.jwt.secret=base64-encoded-secret
app.jwt.expiration-ms=3600000

# Email Configuration
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### Executando a Aplicação

1. **Clone o repositório**
```bash
git clone <repository-url>
cd backend
```

2. **Configure as propriedades**
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edite o arquivo com suas configurações
```

3. **Execute a aplicação**
```bash
# Via Maven
./mvnw spring-boot:run

# Via IDE
# Execute a classe EvolveApplication.java
```

4. **Acesse a aplicação**
```
API: http://localhost:8080
H2 Console: http://localhost:8080/h2-console
```

### Perfis de Execução
- **Desenvolvimento**: Usa H2 em arquivo
- **Console H2**: Habilitado para debug
- **Logs**: DEBUG para SQL queries

## 🗄️ Banco de Dados

### Configuração H2
```properties
spring.datasource.url=jdbc:h2:file:./data/evolve
spring.datasource.username=sa
spring.datasource.password=
```

### Características
- **Persistência**: Arquivo local (`./data/evolve.mv.db`)
- **Auto-DDL**: Schema criado automaticamente
- **Console Web**: Disponível em `/h2-console`
- **Dialect**: H2Dialect para otimizações

### Schema Principal
```sql
-- Tabelas principais
users           # Dados dos usuários
transactions    # Registros de transações
transfer_taxes  # Regras de taxas
password_reset_token # Tokens de reset
```

### DataLoader
- **Inicialização automática** com dados padrão
- **Usuário admin** pré-configurado
- **Regras de taxas** padrão
- **Dados de desenvolvimento**

## ✅ Validações e Tratamento de Erros

### Validações de Entrada
```java
// Registro de usuário
@NotBlank(message = "Nome completo obrigatório")
@Email(message = "Email deve ser válido")
@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,}$")

// Transações
@NotNull(message = "Valor obrigatório")
@NotBlank(message = "Conta destino obrigatória")
@JsonFormat(pattern = "yyyy-MM-dd")
```

### Tratamento Global de Erros
```java
@ControllerAdvice
public class GlobalErrorHandler {
    // Captura todas as exceções
    // Retorna respostas padronizadas
    // Logs detalhados para debug
}
```

### Tipos de Erro
- **400 Bad Request**: Dados inválidos
- **401 Unauthorized**: Token inválido/expirado
- **403 Forbidden**: Acesso negado
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Conflito de dados (email/CPF duplicado)
- **500 Internal Server Error**: Erro interno

### Mensagens Personalizadas
- **Português brasileiro** para melhor UX
- **Detalhes específicos** por tipo de validação
- **Códigos de erro** consistentes
- **Stack trace** apenas em desenvolvimento

## 📝 Considerações Finais

### Pontos Fortes
- ✅ **Arquitetura limpa** e bem estruturada
- ✅ **Segurança robusta** com JWT
- ✅ **Validações abrangentes**
- ✅ **Processamento automatizado**
- ✅ **Documentação detalhada**
- ✅ **Tratamento de erros** consistente

### Melhorias Futuras
- 🔄 Implementar testes unitários e de integração
- 📊 Adicionar métricas e monitoramento
- 🔐 Implementar rate limiting
- 📱 Adicionar API de notificações
- 🔍 Implementar auditoria de transações
- 🌐 Suporte a múltiplas moedas

### Contato
Desenvolvido por **Felipe Holanda**
- Email: contact.felipeholanda@gmail.com
- GitHub: Felipe-Holanda

---

**Evolve Bank** - Sistema bancário moderno e seguro 🏦✨
