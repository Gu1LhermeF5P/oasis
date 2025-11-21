#  OASIS - Equilíbrio Híbrido Inteligente

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Messaging-orange?style=for-the-badge&logo=rabbitmq)
![AI](https://img.shields.io/badge/Spring_AI-Generative-blue?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-Container-blue?style=for-the-badge&logo=docker)

> **FIAP - Java Advanced** | Solução de bem-estar corporativo com Inteligência Artificial e Arquitetura Resiliente.

---
## 👥 Integrantes do Grupo

| Nome | RM |
| :--- | :--- |
| Larissa de Freitas Moura | 555136 |
| Guilherme Francisco | 557648 |

## 📖 Sobre o Projeto

O **Oasis** é uma plataforma web desenvolvida para combater o *burnout* e promover o bem-estar no ambiente de trabalho híbrido. Utilizando **Inteligência Artificial Generativa**, o sistema analisa o estado emocional do usuário e sugere "Rituais de Descompressão" personalizados (como pausas para respiração, técnicas de foco ou alongamentos).

A aplicação foi construída seguindo os princípios de **arquitetura resiliente**, garantindo funcionamento mesmo em caso de falhas externas.

### 🌐 Deploy em Nuvem
A aplicação está rodando na nuvem (Railway):
**[Acessar OASIS ](https://oasis-production-0020.up.railway.app/login)**
---
A aplicação está rodando na nuvem (Azure):
**[Acessar OASIS ](https://app-oasis-rm557648-rm55136.azurewebsites.net )** 
---

## 🚀 Funcionalidades Principais

* **✨ Geração de Rituais com IA:** Integração com LLMs (Groq/Google Gemini) para criar sugestões personalizadas baseadas no sentimento do usuário.
* **⚡ Mensageria Assíncrona:** Uso de **RabbitMQ** para processamento desacoplado de eventos de criação.
* **🧠 Tempo Inteligente:** O sistema calcula automaticamente o tempo de duração do ritual (5, 10 ou 25 min) baseando-se nas palavras-chave do sentimento.
* **⏲️ Timer de Foco:** Ferramenta visual integrada no frontend para auxiliar na execução do ritual.
* **🛡️ Autenticação:** Login customizado e seguro com Spring Security.
* **📱 Interface Moderna:** Frontend responsivo construído com **Thymeleaf**, **Tailwind CSS** e **DaisyUI**.
* **🔄 CRUD Completo:** Criar, Ler, Editar e Arquivar (Deletar) rituais.
* **🔒 Resiliência (Circuit Breaker):** Fallback automático ("Modo Offline") caso a API de IA esteja indisponível ou sem saldo.

---

## 🛠️ Stack Tecnológica

* **Backend:** Java 17, Spring Boot 3.3.5
* **Banco de Dados:** H2 Database (Em memória)
* **IA:** Spring AI (Cliente OpenAI compatível com Groq/Gemini)
* **Mensageria:** RabbitMQ
* **Frontend:** Thymeleaf, HTML5, Tailwind CSS, DaisyUI
* **Containerização:** Docker (Dockerfile Multi-stage incluso)

---

## 🔐 Credenciais de Acesso

Para fins de avaliação, utilize o usuário administrador pré-configurado:

| Usuário | Senha |
| :--- | :--- |
| `admin` | `123456` |

---

## 📦 Como Rodar Localmente

### Pré-requisitos
* Java JDK 17+.
* Docker (Opcional, para rodar o RabbitMQ).

### 1. Configurar a Chave de IA
No arquivo `src/main/resources/application.properties`, defina sua chave da API (Groq ou Google):

```properties
spring.ai.openai.api-key=SUA_CHAVE_AQUI
2. Subir o RabbitMQ (Docker)
Se tiver Docker instalado, execute o comando para iniciar a mensageria:

Bash

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
Nota: Se não rodar o RabbitMQ, a aplicação funcionará normalmente, mas exibirá logs de erro de conexão no console (tratamento de falha).

3. Executar a Aplicação
Na raiz do projeto (onde está o pom.xml), execute:

Windows (Powershell):

PowerShell

./mvnw clean spring-boot:run
Linux/Mac:

Bash

./mvnw clean spring-boot:run
4. Acessar
Abra o navegador em: http://localhost:8080/login

☁️ Como Rodar com Docker (Build Completo)
O projeto possui um Dockerfile multi-stage pronto para produção.

Construir a imagem:

Bash

docker build -t oasis-app .
Rodar o container:

Bash

docker run -p 8080:8080 -e OPENAI_API_KEY=sua_chave_aqui oasis-app
🧪 Diferenciais Técnicos (Para Avaliação)
Tratamento de Falhas (Resiliência): Implementação de blocos try-catch robustos nos serviços de integração. Se a API da IA cair ou estourar a cota (429), o sistema captura o erro e gera um ritual "Offline" padrão, sem quebrar a experiência do usuário.

Arquitetura Hexagonal (Simplificada): Separação clara entre Domínio, Serviços, Controladores e Configurações.

Cloud Native: Aplicação Stateless (sem estado de sessão no servidor), pronta para escalabilidade horizontal em orquestradores como Kubernetes.

📂 Estrutura do Projeto
Plaintext

br.com.fiap.oasis.oasis
├── config          # Configurações (Security, RabbitMQ, Web, I18n)
├── controller      # Controladores MVC (Web) e REST
├── domain          # Entidades JPA e DTOs (Records)
├── repository      # Interfaces Spring Data JPA
├── service         # Regras de Negócio (CRUD, IA, Lógica de Tempo)
├── messaging       # Consumidores de filas (RabbitMQ Listener)
└── exception       # Manipulador global de exceções
# 🌴 OASIS - Equilíbrio Híbrido Inteligente

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Messaging-orange?style=for-the-badge&logo=rabbitmq)
![AI](https://img.shields.io/badge/Spring_AI-Generative-blue?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-Container-blue?style=for-the-badge&logo=docker)


© 2025 Oasis Tech. All rights reserved.
