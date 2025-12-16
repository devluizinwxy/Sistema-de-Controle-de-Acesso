🎯 Sistema de Controle de Acesso

Projeto de Java que simula um sistema de controle de acesso em uma organização, registrando e auditando tentativas de entrada em áreas restritas.

🔹 Sobre o Projeto

Este sistema foi desenvolvido para ler arquivos de entrada, processar tentativas de acesso usando Java Streams e NIO e gerar relatórios detalhados em arquivo de texto.
O projeto demonstra conceitos de orientação a objetos, exceções personalizadas e processamento declarativo com Streams.

Principais conceitos aplicados:

Orientação a objetos (POO)

Interfaces e herança (Autenticavel, Pessoa e subclasses)

Enumerações (NivelAcesso)

Streams API (map, filter, collect, groupingBy)

Arquivos com NIO (Path, Files)

🔹 Estrutura do Projeto
ControleAcesso/
│
├─ src/
│   ├─ entities/          # Classes de domínio (Pessoa, Visitante, Funcionario, Gerente, Administrador, AreaRestrita)
│   ├─ interfaces/        # Interface Autenticavel
│   ├─ exception/         # Exceções personalizadas
│   ├─ entities/enum/     # Enum NivelAcesso
│   ├─ utils/             # Classes auxiliares (TentativaBruta)
│   ├─ ControladorAcessos.java
│   └─ Main.java
│
├─ pessoas.txt            # Arquivo de entrada de pessoas
├─ areas.txt              # Arquivo de entrada de áreas
├─ tentativas.txt         # Arquivo de entrada de tentativas
├─ relatorio_acessos.txt  # Relatório gerado automaticamente
└─ README.md

🔹 Funcionalidades

✅ Carregamento de dados de pessoas, áreas e tentativas de acesso

✅ Validação de e-mails e senhas

✅ Controle de acesso baseado em níveis (VISITANTE, FUNCIONARIO, GERENTE, ADMINISTRADOR)

✅ Registro de logs detalhados (PERMITIDO / NEGADO)

✅ Estatísticas e agrupamentos com Streams (groupingBy, partitioningBy)

✅ Geração automática de relatório completo em arquivo texto

🔹 Como Executar

Coloque os arquivos de entrada (pessoas.txt, areas.txt, tentativas.txt) na raiz do projeto.

Compile todas as classes:

javac src/**/*.java


Execute o programa:

java -cp src Main


O sistema irá gerar automaticamente relatorio_acessos.txt com todas as tentativas registradas e suas estatísticas.

🔹 Tecnologias Utilizadas

Java 17+

Java NIO (Path, Files)

Java Streams API

Orientação a objetos (POO)

Exceções personalizadas

🔹 Aprendizados e Boas Práticas

Uso de Map para acesso rápido a pessoas e áreas

Tratamento de exceções com mensagens claras (EmailInvalidoException, SenhaInvalidaException)

Processamento declarativo com Streams

Estrutura modular, coesa e de fácil manutenção

🔹 Preview do Relatório
RELATÓRIO DE CONTROLE DE ACESSO
===============================
Total de tentativas: 11
Permitidas: 6
Negadas: 5

DETALHAMENTO DAS TENTATIVAS:
2025-03-10T08:15 | Recepcao | P01 - Ana Visitante | PERMITIDO
2025-03-10T08:20 | Cofre | P01 - Ana Visitante | NEGADO (NIVEL_INSUFICIENTE)
...

🔹 Contato

Feito por Luis de Jesus Fernandes – https://www.linkedin.com/in/luis-fernandes-3a4a15207/
 | https://github.com/devluizinwxy
