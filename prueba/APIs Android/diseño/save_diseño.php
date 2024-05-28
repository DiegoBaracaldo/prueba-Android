<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("../db.php"); // conexión a la base de datos

    // obtener datos JSON en el cuerpo de la solicitud
    $data = json_decode(file_get_contents('php://input'), true);

    if (isset($data['nombre_diseño'], $data['codigo'], $data['descripcion'], $data['archivo'])) {
        $nombre_diseño = $data['nombre_diseño'];
        $codigo = $data['codigo'];
        $descripcion = $data['descripcion'];
        $archivo = base64_decode($data['archivo']); // convertir de Base64 a binario

        // Uso de declaraciones preparadas para prevenir inyecciones SQL
        $stmt = $mysql->prepare("INSERT INTO Diseño (nombre_diseño, codigo, descripcion, archivo) VALUES (?, ?, ?, ?)");
        $stmt->bind_param("siss", $nombre_diseño, $codigo, $descripcion, $archivo);

        if ($stmt->execute()) {
            echo json_encode(["message" => "Diseño creado exitosamente"]);
        } else {
            echo json_encode(["message" => "Error al crear diseño: " . $stmt->error]);
        }

        $stmt->close();
        $mysql->close();
    } else {
        echo json_encode(["message" => "Datos incompletos"]);
    }
} else {
    echo json_encode(["message" => "Método no permitido"]);
}
?>
