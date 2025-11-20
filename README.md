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
## 👥 Integrantes do Grupo

| Nome | RM |
| :--- | :--- |
| Larissa de Freitas Moura | 555136 |
| Guilherme Francisco | 557648 |

© 2025 Oasis Tech. All rights reserved.