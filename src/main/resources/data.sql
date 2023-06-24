INSERT
    IGNORE INTO tb_usuarios (
        idUsuario,
        nome,
        sobrenome,
        email,
        senha
    )
VALUES
    (
        1,
        "Teste",
        "Teste",
        "teste@ekos.com.br",
        "$2a$12$fbOS.d7OBQ5oM8SteE5V5.yvq7sbYdxHYT4YiNLRgBFwb3lqigX72"
    );