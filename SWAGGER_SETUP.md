# Configuración de Swagger/OpenAPI en el Proyecto

## ✅ ¿Qué se ha configurado?

Se ha agregado **Swagger/OpenAPI 3** a tu proyecto Spring Boot utilizando `springdoc-openapi`.

### 1. Dependencia agregada en `pom.xml`

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```

### 2. Archivo de configuración creado

**Archivo:** `src/main/java/com/ventadirecta/pruebatecnica/config/OpenApiConfig.java`

Este archivo configura la información básica de tu API como título, versión, descripción, contacto, etc.

### 3. Anotaciones agregadas a los controladores

Se han agregado anotaciones de Swagger a tus controladores:

- **`@Tag`**: Define el nombre y descripción del grupo de endpoints
- **`@Operation`**: Describe cada operación/endpoint
- **`@ApiResponses`**: Documenta las posibles respuestas HTTP
- **`@Parameter`**: Documenta los parámetros de entrada
- **`@Schema`**: Documenta los modelos/DTOs

### 4. Configuración en `application.properties`

```properties
# SWAGGER / OPENAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

## 🚀 Cómo usar Swagger

### Paso 1: Recargar el proyecto en IntelliJ IDEA

1. Abre el panel de Maven (botón "Maven" en el lado derecho)
2. Haz clic en el ícono de "Reload All Maven Projects" (🔄)
3. Espera a que termine de indexar

O desde la terminal:
```bash
./mvnw clean install -DskipTests
```

### Paso 2: Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

O desde IntelliJ IDEA:
- Click derecho en `PruebaTecnicaApplication.java`
- Selecciona "Run 'PruebaTecnicaApplication'"

### Paso 3: Acceder a Swagger UI

Una vez que la aplicación esté ejecutándose, abre tu navegador y accede a:

**Swagger UI (Interfaz gráfica):**
```
http://localhost:8080/swagger-ui.html
```

**JSON de la especificación OpenAPI:**
```
http://localhost:8080/api-docs
```

## 📚 Características de Swagger UI

En la interfaz de Swagger podrás:

✅ Ver todos tus endpoints documentados
✅ Ver los modelos/DTOs y sus propiedades
✅ Probar los endpoints directamente desde el navegador
✅ Ver ejemplos de peticiones y respuestas
✅ Ver los códigos de respuesta HTTP posibles

## 📝 Ejemplo de uso en controladores

### Para toda la clase del controlador:

```java
@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoControllerV1 {
    // ...
}
```

### Para métodos específicos:

```java
@GetMapping
@Operation(summary = "Obtener todos los productos", 
           description = "Retorna una lista de todos los productos disponibles")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
    @ApiResponse(responseCode = "500", description = "Error del servidor")
})
public ResponseEntity<List<ProductoDTO>> getAllProductos() {
    // ...
}
```

### Para parámetros:

```java
@GetMapping("/{id}")
public ResponseEntity<ProductoDTO> getProducto(
    @Parameter(description = "ID del producto", required = true) 
    @PathVariable Long id) {
    // ...
}
```

### Para modelos/DTOs:

```java
@Schema(description = "Objeto de transferencia de datos para Producto")
public class ProductoDTO {
    
    @Schema(description = "Identificador único del producto", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del producto", example = "Laptop HP", required = true)
    private String nombre;
    
    // ...
}
```

## 🎯 Próximos pasos

1. **Agrega más anotaciones** a tus otros controladores (SucursalControllerV1, VentaControllerV1)
2. **Documenta los DTOs** con `@Schema` para mejor información en Swagger
3. **Personaliza** la configuración en `OpenApiConfig.java` con tu información

## 🔧 Solución de problemas

### Si ves errores en el IDE:

1. Recargar el proyecto Maven (botón 🔄 en el panel de Maven)
2. Invalidar caché: `File > Invalidate Caches > Invalidate and Restart`

### Si no aparece la interfaz de Swagger:

1. Verifica que la aplicación esté ejecutándose
2. Revisa los logs de la consola
3. Intenta acceder a: `http://localhost:8080/swagger-ui/index.html`

## 📖 Recursos adicionales

- [Documentación oficial de springdoc-openapi](https://springdoc.org/)
- [OpenAPI Specification](https://swagger.io/specification/)
- [Swagger Annotations Guide](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)

