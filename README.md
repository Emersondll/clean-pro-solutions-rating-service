# Clean Pro Solutions - Rating Service ⭐

## 🎯 Papel no Ecossistema
O **Rating Service** garante a qualidade da plataforma através do feedback dos usuários. Ele gerencia:
- Avaliações de clientes sobre os serviços prestados.
- Cálculo de média de estrelas para profissionais.
- Comentários e métricas de satisfação.

## 🚀 Tecnologias
- **Java 21** & **Spring Boot 3.3.4**
- **MongoDB** (Persistência de avaliações)
- **RabbitMQ** (Eventos de finalização de serviço para disparar pedidos de avaliação)
- **Netflix Eureka** (Service Discovery)

## 🛠️ Como Executar

### 1. Execução Isolada (Individual)
Para rodar este serviço e suas dependências:
```bash
docker-compose up -d --build
```
O serviço estará disponível em `http://localhost:8088`.

### 2. Execução Integrada
Este serviço é orquestrado pelo projeto principal [Clean Pro Platform](../README.md).

---
© 2026 Clean Pro Solutions - Desenvolvido por Emerson Lima.
