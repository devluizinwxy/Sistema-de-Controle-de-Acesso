Sistema de Controle de Acesso
1️⃣ Objetivo

Simula um sistema de controle de acesso para uma organização, registrando tentativas de entrada em áreas restritas.
Lê arquivos de entrada (pessoas.txt, areas.txt, tentativas.txt), processa os dados com Java NIO e Streams e gera um relatório (relatorio_acessos.txt).

2️⃣ Estrutura do Projeto
src/
├─ entities/         # Classes de domínio: Pessoa, Visitante, Funcionario, Gerente, Administrador, AreaRestrita
├─ interfaces/       # Interface Autenticavel
├─ exception/        # Exceções personalizadas
├─ entities/enum/    # Enum NivelAcesso
├─ utils/            # Classes auxiliares (TentativaBruta)
├─ ControladorAcessos.java
└─ Main.java

pessoas.txt
areas.txt
tentativas.txt
relatorio_acessos.txt
README.md

3️⃣ Arquivos de Entrada

pessoas.txt: id; nome; email; senha; nível de acesso

areas.txt: nome da área; nível mínimo de acesso

tentativas.txt: id da pessoa; nome da área; senha informada; data/hora ISO

Todos devem estar na raiz do projeto para que o programa funcione corretamente.

4️⃣ Como Executar

Certifique-se de que os arquivos de entrada estão na raiz do projeto.

Compile todas as classes:

javac src/**/*.java


Execute o sistema:

java -cp src Main


O relatório será gerado automaticamente como relatorio_acessos.txt.

5️⃣ Funcionalidades

Validação de e-mails e senhas ao carregar os dados

Autenticação de usuários com interface Autenticavel

Controle de acesso baseado no nível mínimo de cada área

Registro de logs detalhados (permitido/negado)

Estatísticas com Streams e Collectors.groupingBy

Relatório completo em arquivo texto usando NIO

6️⃣ Boas Práticas

Map para buscas rápidas

Exceções personalizadas para clareza

Streams para processamento declarativo

Cada classe com responsabilidade única

7️⃣ Testes Realizados

Tentativas válidas e inválidas

Pessoas e áreas inexistentes

Senhas corretas e incorretas

Conferência manual do relatório final


