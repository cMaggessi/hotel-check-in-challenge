
# Hotel Check In Challenge

Uma pequena aplicação em Spring Rest com os requisitos do app, usando o padrão de organização do mercado e boas práticas de código como desacoplamento / responsabilidade única.




## Regras de negócio

- Diária do quarto para hospedagem individual - R$100 (de seg a qui) e R$120 (de sex a dom).

- Diária do quarto para hospedagem com acompanhante - R$130 (de seg a qui) e R$150 (de sex a dom).

- Diária do quarto para hospedagem com criança - R$160 (de seg a qui) e R$180 (de sex a dom).

- Validação de hospedagem

- Assumir que a data inicial da reserva é a data atual da requisição.

- Receber o nome do cliente responsável pela reserva, o tipo de quarto desejado e a data final da
  reserva.

## Uso

Apenas rode o "HoteApplication"

### Rotas

```bash

 GET - http://localhost:8080/api/v1/hello
 POST - http://localhost:8080/api/v1/reserve
   
```  

### Exemplo de disparo requisição
```
  POST - http://localhost:8080/api/v1/reserve
  {
    "clientName": "John Doe",
    "roomType": "FAMILY",
    "checkOutDate": "2024-05-15"
  }
```
    
