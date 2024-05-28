<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Verifica que se haya proporcionado un Codigo
    if (!isset($_GET['codigo']) || empty($_GET['codigo'])) {
        echo json_encode(["error" => "Codigo no proporcionado"]);
        exit();
    }

    $id = $_GET['codigo'];

    // Validar que el Codiigo sea un número
    if (!filter_var($id, FILTER_VALIDATE_INT)) {
        echo json_encode(["error" => "Codigo inválido"]);
        exit();
    }

    // Trae la conexión a la base de datos
    require_once("../db.php");

    // Usa una declaración preparada para evitar inyecciones SQL
    $query = $mysql->prepare("SELECT * FROM bodega WHERE codigo = ?");
    $query->bind_param("i", $id);

    // Ejecuta la consulta
    $query->execute();
    $result = $query->get_result();

    // Verifica si se encontró algún resultado
    if ($result->num_rows > 0) {
        $array = $result->fetch_assoc();
        echo json_encode($array);
    } else {
        echo json_encode(["error" => "Bodega no encontrada con codigo $id"]);
    }

    // Cierra el resultado y la conexión
    $result->close();
    $mysql->close();
}
?>
