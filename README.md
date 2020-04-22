# AGIBANK {SOUTH} - LEITURA E PROCESSAMENTO DE ARQUIVOS

Bem vindo.

Este app foi construído para a avaliação de desenvolimento no AGIBANK;

**Como justificativa para o desenvolvimento temos:**

Você deve criar um sistema de análise de dados, onde o sistema deve importar lotes de arquivos, ler e analisar os dados e produzir um relatório. Existem 3 tipos de dados dentro desses arquivos. 
#### Para cada tipo de dados há um layout diferente.
##### Dados do vendedor
 Os dados do vendedor têm o formato id 001 e a linha terá o seguinte formato: 001çCPFçNameçSalary

##### Dados do cliente
  Os dados do cliente têm o formato id 002 e a linha terá o seguinte formato: 002çCNPJçNameçBusiness Area

##### Dados de vendas
  Os dados de vendas têm o formato id 003. Dentro da linha de vendas, existe a lista de itens, que é envolto por colchetes []. A linha terá o seguinte formato: 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

##### Dados de Exemplo
O seguinte é um exemplo dos dados que o sistema deve ser capaz de ler.  
> 001ç1234567891234çPedroç50000   
> 001ç3245678865434çPauloç40000.99   
> 002ç2345675434544345çJose da SilvaçRural   
> 002ç2345675433444345çEduardo PereiraçRural   
> 003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro   
> 003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo  

##### Análise de dados
- Seu sistema deve ler dados do diretório padrão, localizado em %HOMEPATH%/data/in.
- O sistema deve ler somente arquivos .dat.
- Depois de processar todos os arquivos dentro do diretório padrão de entrada, o sistema deve criar um arquivo dentro do diretório de saída padrão, localizado em %HOMEPATH%/data/out.
- O nome do arquivo deve seguir o padrão, {flat_file_name} .done.dat.

##### O conteúdo do arquivo de saída deve resumir os seguintes dados:
- Quantidade de clientes no arquivo de entrada
- Quantidade de vendedor no arquivo de entrada
- ID da venda mais cara
- O pior vendedor
- O sistema deve estar funcionando o tempo todo.
- Todos os arquivos novos estar disponível, tudo deve ser executado
- Seu código deve ser escrito em Java.
- Você tem total liberdade para utilizar google com o que você precisa. Sinta-se à vontade para escolher qualquer biblioteca externa se for necessário. 

##### Critérios de Avaliação
- Clean Code
- Simplicity
- Logic
- SOC (Separation of Concerns)
- Flexibility/Extensibility
- Scalability/Performance

## Desenvolvimento

### Tecnologia

- [X] JAVA
- [X] SpringBoot / SpringBatch
- [ ] Teste unitário. - Não foi implementado por falta de tempo hábil;

Após as definições do Escopo tratadas no dia 20/03/2020 ás 12:00, iniciei o desenvolvimento, gastando mais ou menos 15 Horas para tal. Nele está previsto a aplicação batch que fica monitorando a criação de arquivos .dat na pasta %HOMEPATH%/data/in. Assim que o arquivo no layout pré definido anteriormente entrar na pasta, a aplicação lê e processa salvando o resultado com o nome {nome_arquivo}.done.dat e o arquivo original com o nome {nome_arquivo}.processed.dat na pasta %HOMEPATH%/data/out.
  
### Orientações para deploy e uso no ambiente de Desenvolvimento

## Subindo o Projeto

Para executar o projeto, execute o comando abaixo:

````
mvn clean package -DskipTests=true
java -jar -Dspring.profiles.active=local target/sales-0.0.1-SNAPSHOT.jar
````

## TODO
* Criar um Serializador no mesmo formato do Deserializador para gravar o arquivo de saida
* Implementar testes unitários

## Meta
Willian Robson Moraes 😜 – [LINKEDIN](https://www.linkedin.com/in/willmoraes) – <willian_200@hotmail.com>

https://github.com/wrmoraes/desafio-backend-arquivo