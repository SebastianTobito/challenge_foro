CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    fecha DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    curso ENUM('JAVA', 'JAVASCRIPT', 'MYSQL', 'SOFTSKILLS', 'CSS') NOT NULL,
    CONSTRAINT unique_titulo UNIQUE (titulo(255))
);
