# VueBank - Seu pseudobanco digital!

<center>
		<p align="center">Métricas deste projeto:</p>
	<p align="center">
    <img src="https://img.shields.io/github/languages/count/Felipe-Holanda/transferencia?color=%2304D361" alt="Languages" />
    <img src="https://img.shields.io/github/repo-size/Felipe-Holanda/transferencia" alt="Repo size" />
    <img src="https://img.shields.io/github/last-commit/Felipe-Holanda/transferencia" alt="Last commit" />
    <img src="https://img.shields.io/github/license/Felipe-Holanda/transferencia" alt="License" />
		</p>
</center>

Esta aplicação foi desenvolvida para cumprir um desafio proposto na Segunda-Feira (08/09), onde eu deveria cumprir alguns requisitos:

### Acesse o deploy desta aplicação [clicando aqui](https://vuebank.vercel.app/)

> Aplicação + pequena documentação no README explicando suas decisões arquiteturais, versões de linguagem, ferramentas utilizadas e instruções para a subida do projeto.
 
Os requisitos principais seriam:
- Possibilidade de cadastro de usuários
- Usuários deveriam ter uma propriedade "conta" no formato XXXXXXXXXX
- Usuários devem agendar transferências entre si utilizando a conta.
- As transferências tem uma taxa de acordo com a seguinte tabela:

|Dias de transferência| R$ (Taxa fixa) | Taxa percentual
|--|--|--|
| 0 dias | R$3,00 | 2,5% 
| 1 a 10 dias | R$12,00 | 0,0% 
| 11 a 20 dias | R$0,00 | 8,2%
| 21 a 30 dias | R$0,00 | 6,9%
| 31 a 40 dias | R$0,00 | 4,7%
| 41 a 50 dias | R$0,00 | 1,7%

<small>Caso não haja uma taxa aplicável, informar ao usuário e não permitir agendamento.</small>

## Funcionalidades

- <b> Cadastro de usuários </b>
O usuário pode cadastrar-se, e gerenciar seus próprios dados livremente, ver seu histórico de transações, bem como visualizar dados parciais de outros usuários utilizando o número da conta.
- <b> Segurança e Privilégios</b>
As rotas possuem proteção por nível (USUARIO e ADMINISTRADOR). E para acessar as demais rotas, o usuário deve estar devidamente autenticado com o seu token JWT.
- <b> Transações </b>
O usuário pode "depositar" saldo em sua conta, transferir insantâneamente e agendar novas transferências no sistema. As transferências agendadas são executadas somenta na data especificada. O back end lida com estas transações tranquilamente através de Scheduling, efetuando o débito e liberação do valor somente na data especificada.
- <b> Painel Administrativo </b>
O administrador consegue gerir a aplicação, bem como ativar/desativar e deletar usuários através de seu painel administrativo, gerenciar as taxas existentes, visualizar métricas, transações e demais elementos. <b>Sim</b>, as taxas são gerenciadas de forma dinâmica, garantindo uma excelente gestão em cenários onde elas precisem de eventuais reajustes.

- <b> Recuperação de acesso </b>
Caso o usuário esqueça sua senha, ele pode recuperar seu acesso através de código OTP, enviado para o email cadastrado via SNMP pelo sistema.

## Capturas de tela

### Tela de Login:
![Tela de Login](https://img001.prntscr.com/file/img001/1-sV527bTwu3NtGpfqjhgw.png)
### Tela de Cadastro:
![Tela de cadastro](https://img001.prntscr.com/file/img001/DbYv9TQVTJCvqG-PPWdd0Q.png)
### Redefinição de senha:
![Redefinição de senha](https://img001.prntscr.com/file/img001/1CGIiJzTQeSZWdCVoabk6A.png)
![Redefinicao de senha - Codigo OTP](https://img001.prntscr.com/file/img001/d6gkXnOqQH6m6gXOB5U1kw.png)
![Redefinicao de senha - nova senha](https://img001.prntscr.com/file/img001/NEYUqC0DSVG1rtHE1_ZxWQ.png)
### Dashboard:
![Dashboard](https://img001.prntscr.com/file/img001/i5hgoNgiQjmfBt-GeXrDqw.png)
### Transferência:
!![Etapa 1](https://img001.prntscr.com/file/img001/d0r3vCB9SNm3bu4emyj47w.png)
![Etapa 2](https://img001.prntscr.com/file/img001/x95yv35IRgGz1U7v-7mYvg.png)
![Etapa 3](https://img001.prntscr.com/file/img001/iysaFq-tQri0y4pP68UuPA.png)
### Depósito:
![Etapa 1](https://img001.prntscr.com/file/img001/6rWFenLDQEOEC2Dp6KsZcQ.png)
![Etapa 2](https://img001.prntscr.com/file/img001/8p8uystQQGaaX6FZSXjyDQ.png)
![Etapa 3](https://img001.prntscr.com/file/img001/8EqnBrm_T4Svh5AWOe57uw.png)
### Painel Admin:
![Dashboard admin](https://img001.prntscr.com/file/img001/vmHnvMlhQPmP-QM_dPtBzw.png)
![admin usuarios](https://img001.prntscr.com/file/img001/O54yksKGQIaOoldlW1r4TQ.png)
![admin transaciones](https://img001.prntscr.com/file/img001/h-Q16bETRtChU1uD8Uc9Cg.png)
![taxas](https://img001.prntscr.com/file/img001/nNjyncb-Rbeiz7ilXSvMow.png)


## Informações técnicas do projeto:

### Front End
Para desenvolver o frontend foi utilizado VueJS (como proposto no desafio) 
- [JavaScript](https://developer.mozilla.org/pt-BR/docs/Web/JavaScript)
- [VueJS 3.5](https://vuejs.org/)
- [Vue-Router](https://router.vuejs.org/)
- [Vue-Toastification](https://vue-toastification.maronato.dev/)
- [Axios](https://axios-http.com/ptbr/docs/intro)
- [Yarn](https://yarnpkg.com/)

Para mais detalhes intrínsecos ao front end, você pode acessar a documentação específica do front end [clicando aqui](https://github.com/Felipe-Holanda/transferencia/tree/main/frontend)

### Back End
- [Java 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
- [Spring Boot 2.7](https://spring.io/blog/2023/11/23/spring-boot-2-7-18-available-now)
- [Spring MVC]()
- [Spring Security](https://spring.io/projects/spring-security)
- [Lombok](https://projectlombok.org/)
- [H2]()
- [Hibernate](https://hibernate.org/)
- [Maven](https://maven.apache.org/)

Para mais detalhes intrínsecos ao front end, você pode acessar a documentação específica do front end [clicando aqui](https://github.com/Felipe-Holanda/transferencia/tree/main/backend)

<center>
<small>Desenvolvido com <b><3</b> por Felipe Holanda</small>
</center>
