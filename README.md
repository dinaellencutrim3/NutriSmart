# NutriSmart 🥗

Sistema web para gestão nutricional desenvolvido com Java 17, focado em auxiliar nutricionistas no gerenciamento de pacientes, dietas e acompanhamento nutricional de forma moderna, prática e intuitiva.

---

# 📌 Sobre o Projeto

O NutriSmart é uma plataforma em desenvolvimento criada para facilitar o trabalho de nutricionistas por meio de ferramentas inteligentes de acompanhamento alimentar e gestão clínica.

A aplicação possui uma interface moderna, responsiva e interativa, permitindo o controle de pacientes, alimentos e planos alimentares em um único ambiente.

---

# 🚧 Status do Projeto

> ⚠️ Projeto em desenvolvimento.

Novas funcionalidades estão sendo implementadas continuamente, incluindo melhorias no dashboard, autenticação e recursos inteligentes para personalização de dietas.

---

# 🛠️ Tecnologias Utilizadas

## Back-end
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Front-end
- HTML5
- CSS3
- JavaScript
- Bootstrap
- Thymeleaf

---

# ✨ Funcionalidades

## 👨‍⚕️ Área do Nutricionista
- Cadastro de nutricionistas com CRN
- Login e autenticação segura
- Dashboard administrativo
- Gerenciamento de pacientes
- Criação de dietas personalizadas
- Controle nutricional

---

## 🥗 Gestão de Alimentos
- Cadastro de alimentos
- Informações nutricionais
- Consulta de alimentos
- Organização por categorias

### Endpoint
```http
GET /alimentos
```

---

## 📋 Gestão de Dietas
- Criação de planos alimentares
- Associação de dietas aos pacientes
- Personalização nutricional
- Controle calórico

### Endpoint
```http
POST /dietas
```

---

## 👥 Gestão de Pacientes
- Cadastro completo de pacientes
- Histórico alimentar
- Acompanhamento nutricional
- Evolução física e nutricional

### Endpoint
```http
GET /pacientes
```

---

## 🔄 Substituições Inteligentes
Sistema para sugerir substituições de alimentos mantendo equilíbrio nutricional.

### Endpoint
```http
POST /api/substituicoes
```

---

## 📊 Dashboard Analítico
- Métricas nutricionais
- Relatórios
- Indicadores de evolução
- Visualização intuitiva

---

## 🔐 Segurança
- Autenticação com Spring Security
- Controle de acesso
- Proteção de rotas
- Cadastro exclusivo para nutricionistas

### Endpoint
```http
POST /api/usuarios/cadastro
```

---

# 🎨 Interface do Sistema

O projeto possui:
- Landing Page moderna
- Design responsivo
- Animações suaves
- Dashboard intuitivo
- Área do paciente
- Navegação dinâmica

---

# 📂 Estrutura do Projeto

```bash
src/
 ├── main/
 │   ├── java/
 │   │   └── com/nutrismart/
 │   ├── resources/
 │   │   ├── templates/
 │   │   ├── static/
 │   │   │   ├── css/
 │   │   │   ├── js/
 │   │   │   └── img/
 │   │   └── application.properties
 │
 └── test/
```

---

# ▶️ Como Executar o Projeto

## Pré-requisitos
- Java 17
- Maven
- IntelliJ IDEA

---

## Clone o repositório

```bash
git clone https://github.com/seu-usuario/nutrismart.git
```

---

## Execute o projeto

```bash
mvn spring-boot:run
```

Ou execute a classe principal no IntelliJ IDEA.

---

# 🌐 Acesso

```bash
http://localhost:8080
```

---

# 📸 Funcionalidades Visuais

- Hero section moderna
- Estatísticas animadas
- Navbar responsiva
- Cards interativos
- Scroll suave
- Dashboard visual

---

# 🔮 Melhorias Futuras

- Área exclusiva para pacientes
- Upload de exames
- Integração com IA nutricional
- Chat entre nutricionista e paciente
- Relatórios em PDF
- Notificações automáticas
- Aplicativo mobile

---

# 👩‍💻 Desenvolvedoras

Projeto desenvolvido por:

- Dinaellen Cutrim
- Sofia Maciel

Durante os estudos de Engenharia da Computação e desenvolvimento web com Java.

---

# 📄 Licença

Este projeto está sob a licença MIT.
