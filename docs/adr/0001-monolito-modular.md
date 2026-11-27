# ADR-0001: Monólito modular em uma única aplicação Spring Boot

- **Status:** Aceita
- **Data:** 2026-11-27
- **Referência:** [Especificação de domínio e requisitos v1.0](../especificacao-dominio-e-requisitos-v1.0.pdf), seções 2 e 11

## Contexto

O Order Registration vai evoluir de um CRUD de produtos e pedidos para um sistema com
estoque reservado, pagamento simulado e ciclo de vida de pedido. Essas regras exigem
consistência entre conceitos (reservar estoque ao criar o pedido, liberar se o pagamento
for rejeitado).

Hoje o código é organizado por camada técnica (`controllers`, `services`, `repositories`,
`domains`). Qualquer classe pode usar qualquer repositório, então nada impede que, por
exemplo, a lógica de pedidos altere o estoque diretamente no banco.

Havia também um serviço separado (`order-registration-user-api`, Quarkus) para usuários,
que compartilha a coleção `Users` com a API principal.

## Decisão

O sistema será um **monólito modular**: uma única aplicação Spring Boot, um único deploy,
dividida em módulos que representam limites de negócio:

| Módulo       | Responsabilidade                                   |
|--------------|----------------------------------------------------|
| Identity     | Usuários, autenticação e permissões                |
| Catalog      | Produtos, categorias e preços                      |
| Inventory    | Estoque, reservas e movimentações                  |
| Ordering     | Criação de pedidos, cálculo e ciclo de vida        |
| Payment      | Registro e processamento dos pagamentos            |
| Notification | Comunicação dos resultados aos clientes            |
| Shared       | Só o que é realmente compartilhado, sem regra de negócio |

Regras:

1. Um módulo expõe uma API pública (interfaces, serviços de aplicação ou eventos internos).
2. Um módulo **não** acessa os repositórios internos de outro módulo. Exemplo: Ordering
   pede uma reserva ao Inventory pela API dele, nunca grava na coleção de estoque.
3. Microsserviços só serão considerados quando houver uma necessidade demonstrada.

## Alternativas consideradas

- **Manter a organização por camada:** simples, mas não deixa visíveis os limites de
  negócio e não impede o acoplamento entre conceitos.
- **Microsserviços (um serviço por conceito):** traria rede, consistência distribuída e
  operação de vários deploys sem um problema real que justifique esse custo. A
  especificação (seção 17) deixa isso fora do escopo.

## Consequências

- **Positivas:** um único deploy e um único banco; consistência mais fácil (chamadas
  locais e, se preciso, transações); limites de negócio explícitos no código; os
  módulos podem virar serviços no futuro se surgir a necessidade.
- **Negativas / custos:** é preciso reorganizar os pacotes atuais; os limites só valem se
  forem verificados. A ideia é verificá-los com um teste automatizado (Spring Modulith,
  em um passo próprio).
- **Sobre a `order-registration-user-api`:** não seguirá como serviço independente. Será
  avaliada a incorporação ao módulo Identity e depois o arquivamento, após verificar
  dependências.
