<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Verificar que se haya proporcionado un código
    if (!isset($_GET['codigo']) || empty($_GET['codigo'])) {
        echo json_encode(["error" => "Código no otorgado"]);
        exit();
    }

    $codigo = $_GET['codigo'];

    // Validar que el código sea un número
    if (!filter_var($codigo, FILTER_VALIDATE_INT)) {
        echo json_encode(["error" => "Código inválido"]);
        exit();
    }

    // Trae la conexión a la base de datos
    require_once("../db.php");

    // Usa una declaración preparada para evitar inyecciones SQL
    $query = $mysql->prepare("SELECT * FROM diseño WHERE codigo = ?");
    $query->bind_param("i", $codigo);

    // Ejecuta la consulta
    $query->execute();
    $result = $query->get_result();

    // Verifica si se encontró algún resultado
    if ($result->num_rows > 0) {
        $array = $result->fetch_assoc();
        echo json_encode($array);
    } else {
        echo json_encode(["error" => "Diseño no encontrado con código $codigo"]);
    }

    // Cierra el resultado y la conexión
    $result->close();
    $mysql->close();
}
?>
