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

## 🔍 Entidad Categoria
| Campo             | Tipo          | Descripción                                | Restricciones                     |
|-------------------|---------------|--------------------------------------------|-----------------------------------|
| `id`              | `Long`        | Identificador único autogenerado.            |Primary Key.                       |
| `nombre`          | `String`      | Nombre de la categoría.                    |No nulo, único, máx. 50 caracteres.|
| `descripcion`     | `String`      | Detalles adicionales.                      |Máx. 200 caracteres, opcional.     |

## 🔍 Entidad Proveedor
| Campo             | Tipo                | Descripción                                | Restricciones                     |
|-------------------|---------------------|--------------------------------------------|-----------------------------------|
| `id`              | `Long`              | Identificador único autogenerado.          |Primary Key.                       |
| `nombre`          | `String`            | Nombre legal del proveedor.                |No nulo, máx. 100 caracteres.      |
| `contacto`        | `String`            | Persona de contacto.                       |No nulo, máx. 100 caracteres.      |
| `telefono`        | `String`            | Teléfono de contacto.                      |Formato internacional válido.      |
| `direccion`       | `String`            | Dirección física..                         |Máx. 200 caracteres.               |
| `ordenesCompra`   | `List<OrdenCompra>` | Órdenes de compra asociadas.               |Relación bidireccional.            |

## 🔍 Entidad OrdenCompra
| Campo             | Tipo                      | Descripción                           | Restricciones                     |
|-------------------|---------------------------|---------------------------------------|-----------------------------------|
| `id`              | `Long`                    | Identificador único autogenerado.     |Primary Key.                       |
| `fecha`           | `LocalDate`               | Fecha de creación.                    |No nula.                           |
| `proveedor`       | `Proveedor`               | Proveedor asociado.                   |No nulo (clave foránea).           |
| `detalles`        | `List<DetalleOrdenCompra>`| Productos solicitados.                |Relación bidireccional.            |
| `estado`          | `EstadoOrden`             | Estado del pedido.                    |Valores: PENDIENTE, ENVIADA, etc.  |

## 🔍 Entidad DetallesOrdenCompra
| Campo             | Tipo                  | Descripción                        | Restricciones    |
|-------------------|-----------------------|------------------------------------|------------------|
| `id`              | `Long`                | Identificador único autogenerado.  |Primary Key.      |
| `ordenCompra`     | `OrdenCompra`         | Orden de compra asociada           |No nulo           |
| `producto`        | `Producto`            | Producto solicitado en la orden    |No nulo           |
| `cantidad`        | `Integer`             | Cantidad de unidades               |Mayor a 0         |
| `precioUnitario`  | `Double`              | Precio unitario del producto       |Mayor a 0         |