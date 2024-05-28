<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Verifica que se haya proporcionado una identificación
    if (!isset($_GET['identificacion']) || empty($_GET['identificacion'])) {
        echo json_encode(["error" => "Identificación no proporcionada"]);
        exit();
    }

    $identificacion = $_GET['identificacion'];

    // Validar que la identificación sea un número
    if (!filter_var($identificacion, FILTER_VALIDATE_INT)) {
        echo json_encode(["error" => "Identificación inválida"]);
        exit();
    }

    // Trae la conexión a la base de datos
    require_once("../db.php");

    // Usa una declaración preparada para evitar inyecciones SQL
    $query = $mysql->prepare("SELECT * FROM usuario WHERE identificacion = ?");
    $query->bind_param("i", $identificacion);

    // Ejecuta la consulta
    $query->execute();
    $result = $query->get_result();

    // Verifica si se encontró algún resultado
    if ($result->num_rows > 0) {
        $array = $result->fetch_assoc();
        echo json_encode($array);
    } else {
        echo json_encode(["error" => "Usuario no encontrado con identificación $identificacion"]);
    }

    // Cierra el resultado y la conexión
    $result->close();
    $mysql->close();
}
?>
