CREATE DATABASE IF NOT EXISTS mvc_java
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE mvc_java;

SET NAMES utf8mb4;

-- =========================================
-- TABELA DE USUÁRIOS
-- =========================================

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- =========================================
-- TABELA DE HABILIDADES
-- =========================================

CREATE TABLE habilidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- =========================================
-- TABELA DE TROCAS
-- =========================================

CREATE TABLE trocas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_oferecendo_id INT,
    usuario_interessado_id INT,
    habilidade_id INT,
    FOREIGN KEY (usuario_oferecendo_id) REFERENCES usuarios(id),
    FOREIGN KEY (usuario_interessado_id) REFERENCES usuarios(id),
    FOREIGN KEY (habilidade_id) REFERENCES habilidades(id)
);

-- =========================================
-- DADOS PARA TESTE
-- =========================================

INSERT INTO usuarios (nome, email)
VALUES
    ('João Silva', 'joao@email.com'),
    ('Maria Souza', 'maria@email.com'),
    ('Pedro Santos', 'pedro@email.com');

INSERT INTO habilidades (nome)
VALUES
    ('Java'),
    ('Ingles'),
    ('Design Grafico');

INSERT INTO trocas (
    usuario_oferecendo_id,
    usuario_interessado_id,
    habilidade_id
)
VALUES
    (1, 2, 1),
    (2, 3, 2),
    (3, 1, 3);

-- =========================================
-- CONSULTA DE EXEMPLO
-- =========================================

SELECT
    t.id,
    uo.nome AS oferece,
    ui.nome AS interessado,
    h.nome AS habilidade
FROM trocas t
INNER JOIN usuarios uo
    ON uo.id = t.usuario_oferecendo_id
INNER JOIN usuarios ui
    ON ui.id = t.usuario_interessado_id
INNER JOIN habilidades h
    ON h.id = t.habilidade_id
ORDER BY t.id;
