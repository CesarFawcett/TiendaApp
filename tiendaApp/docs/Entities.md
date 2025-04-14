# entities

## 🔍 Entidad Producto
| Campo             | Tipo          | Descripción                                | Restricciones                |
|-------------------|---------------|--------------------------------------------|------------------------------|
| `id`              | `Long`        | Identificador único autogenerado.          |Primary Key, no nulo.         |
| `nombre`          | `String`      | Nombre del producto.                       |Máx. 100 caracteres, no nulo. |
| `descripcion`     | `String`      | Detalles adicionales del producto.         |Máx. 200 caracteres, opcional.|
| `precio`          | `Double`      | Precio unitario del producto.              |No nulo, debe ser positivo.   |
| `stock`           | `Integer`     | Cantidad disponible en inventario.         |No nulo, no negativo.         |
| `categoria`       | `Categoria `  | ID de la categoría asociada (foreign key). |No nulo.                      |