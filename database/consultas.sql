USE menu_al_dia;

-- INSERT: ejemplo de plato
INSERT INTO plato (categoria_id, nombre, descripcion, precio_base)
VALUES (1, 'Milanesa con papas', 'Milanesa de carne acompañada con papas fritas', 9500.00);

-- INSERT: menú diario
INSERT INTO menu (fecha)
VALUES (CURDATE())
ON DUPLICATE KEY UPDATE fecha = VALUES(fecha);

-- INSERT: asociar plato al menú actual
INSERT INTO menu_plato (menu_id, plato_id, precio_dia)
SELECT m.id, p.id, p.precio_base
FROM menu m
JOIN plato p ON p.nombre = 'Milanesa con papas'
WHERE m.fecha = CURDATE()
ON DUPLICATE KEY UPDATE precio_dia = VALUES(precio_dia);

-- SELECT: menú publicado del día
SELECT p.nombre, p.descripcion, mp.precio_dia, mp.disponible
FROM menu m
INNER JOIN menu_plato mp ON mp.menu_id = m.id
INNER JOIN plato p ON p.id = mp.plato_id
WHERE m.fecha = CURDATE();

-- UPDATE: marcar plato agotado
UPDATE menu_plato
SET disponible = FALSE
WHERE menu_id = (SELECT id FROM menu WHERE fecha = CURDATE())
  AND plato_id = (SELECT id FROM plato WHERE nombre = 'Milanesa con papas' LIMIT 1);

-- UPDATE: publicar menú
UPDATE menu
SET estado = 'PUBLICADO', fecha_publicacion = NOW()
WHERE fecha = CURDATE();

-- SELECT: historial de un plato
SELECT m.fecha, mp.precio_dia, mp.disponible
FROM menu_plato mp
INNER JOIN menu m ON m.id = mp.menu_id
INNER JOIN plato p ON p.id = mp.plato_id
WHERE p.nombre = 'Milanesa con papas'
ORDER BY m.fecha DESC;

-- DELETE: quitar plato del menú, sin borrar el plato del catálogo
DELETE mp
FROM menu_plato mp
INNER JOIN menu m ON m.id = mp.menu_id
INNER JOIN plato p ON p.id = mp.plato_id
WHERE m.fecha = CURDATE()
  AND p.nombre = 'Milanesa con papas';
