Menú Al Día – TP3

Programa de consola desarrollado en Java para gestionar los platos y menús diarios de un bar. Es parte del mismo proyecto utilizado en el TP2, donde se desarrolló la aplicación web con Spring Boot y MySQL.

Este módulo funciona de forma independiente y guarda los datos en memoria. Al cerrar el programa, los registros no se conservan.
Requisitos
- Java 17 o superior (JDK con java y javac).
- Para ejecutar esta versión de consola no hace falta instalar Maven ni configurar MySQL.

Cómo ejecutarlo
En macOS o Linux, desde la raíz del repositorio:
cd tp3-console
./run.sh
En Windows, con Java configurado en el PATH:
cd tp3-console
run.bat

Para ejecutar las pruebas en macOS o Linux:
./test.sh

Funciones del programa
1. Registrar platos.
2. Crear combos a partir de dos o más platos.
3. Listar el catálogo ordenado por nombre.
4. Buscar platos y combos por nombre.
5. Crear un menú para una fecha.
6. Agregar platos o combos al menú.
7. Consultar un menú.
8. Cambiar la disponibilidad de un elemento.
9. Publicar un menú.
10. Salir.

Programación orientada a objetos
Encapsulamiento: Atributos privados y métodos para acceder o modificar los datos.
Abstracción: ElementoMenu es una clase abstracta.
Herencia: Plato y Combo heredan de ElementoMenu.
Polimorfismo: Se manejan platos y combos como ElementoMenu, con métodos que se comportan según el tipo de objeto.


El programa también utiliza constructores, listas y mapas, estructuras if, switch, for y while, y manejo de excepciones para validar entradas y operaciones. Se incluyen funciones de búsqueda y ordenamiento del catálogo.

Organización
- modelo/: clases de platos, combos y menús.
- servicio/GestorMenus.java: operaciones del catálogo y los menús.
- MainConsola.java: menú interactivo.
- src/test/: pruebas del programa.
- PRUEBAS.md: casos de prueba y ejemplo de uso.

La aplicación web del TP2 se mantiene en el directorio principal del repositorio y conserva su conexión a MySQL. Este módulo de consola no comparte datos con ella.