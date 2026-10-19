Pruebas – TP3

Para ejecutar las pruebas, abrir una terminal dentro de tp3-console/ y correr:
./test.sh

Las pruebas usan datos en memoria y no necesitan MySQL ni Maven.

Casos de prueba
Registrar un plato:	Se guarda con su código, nombre y precio.
Buscar por nombre: Encuentra coincidencias sin distinguir mayúsculas de minúsculas.
Listar catálogo: Los elementos aparecen ordenados alfabéticamente.
Ingresar datos: inválidos El programa muestra o controla el error sin guardar datos incorrectos.
Crear un combo:	Se registra un combo con dos o más platos.
Repetir un plato en un combo: No permite agregar dos veces el mismo plato.
Crear un menú: Permite agregar elementos sin duplicarlos.
Agotar un plato de un combo: El combo pasa a figurar como no disponible.
Publicar un menú: Cambia su estado de borrador a publicado.
Publicar un menú vacío:	No permite publicarlo.
Agregar platos después de publicar:	No permite modificar la composición del menú.
Modificar la lista interna de un combo:	La lista no puede alterarse directamente desde afuera.


Prueba manual
Para comprobar el funcionamiento del menú interactivo, ejecutar ./run.sh y seguir este ejemplo:
1. Elegir 1 y registrar Milanesa (categoría: Principal, precio: 9800, descripción: Con papas).
2. Elegir 1 y registrar Ensalada (categoría: Entrada, precio: 4500, descripción: Mixta).
3. Elegir 2 y crear el combo Almuerzo (precio: 12000) con los códigos 1,2.
4. Elegir 3 para consultar el catálogo.
5. Elegir 5 y crear un menú con la fecha del día.
6. Elegir 6 para agregar el elemento con código 1 al menú.
7. Elegir 6 nuevamente para agregar el combo con código 3.
8. Elegir 9 para publicar el menú.
9. Elegir 7 para consultar el menú publicado.
10. Elegir 0 para salir.

Las fechas se ingresan en formato AAAA-MM-DD (por ejemplo, 2026-10-19).