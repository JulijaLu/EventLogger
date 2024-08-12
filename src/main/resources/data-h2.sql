CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    time TIMESTAMP NOT NULL DEFAULT NOW(),
    type ENUM ('DEBUG', 'INFO', 'WARNING', 'ERROR') NOT NULL,
    message TEXT NOT NULL,
    userId INT NOT NULL,
    transactionId INT NOT NULL
);

INSERT INTO events (time, type, message, userId, transactionId) values
    (now(), 'DEBUG', 'event pending', 10101, 333555666),
    (now(), 'INFO', 'event pending', 11111, 111555222);