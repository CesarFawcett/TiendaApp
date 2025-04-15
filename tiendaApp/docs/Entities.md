# entities

## 🔍 Entidad Producto
| Campo             | Tipo          | Descripción                                | Restricciones                |
|-------------------|---------------|--------------------------------------------|------------------------------|
| `id`              | `Long`        | Identificador único autogenerado.          |Primary Key, no nulo.         |
| `nombre`          | `String`      | Nombre del producto.                       |Máx. 100 caracteres, no nulo. |
| `descripcion`     | `String`      | Detalles adicionales del producto.         |Máx. 200 caracteres, opcional.|
| `precio`          | `Double`      | Precio unitario del producto.              |No nulo, debe ser positivo.   |
| `stock`           | `Integer`     | Cantidad disponible en inventario.         |No nulo, no negativo.         |
| `fecha`           | `LocalDate`   | Fecha de creación.                         |No nula.                      |
| `categoria`       | `Categoria `  | ID de la categoría asociada (foreign key). |No nulo.                      |

## 🔍 Entidad Categoria
| Campo             | Tipo          | Descripción                                | Restricciones                     |
|-------------------|---------------|--------------------------------------------|-----------------------------------|
| `id`              | `Long`        | Identificador único autogenerado.          |Primary Key.                       |
| `nombre`          | `String`      | Nombre de la categoría.                    |No nulo, único, máx. 50 caracteres.|
| `descripcion`     | `String`      | Detalles adicionales.                      |Máx. 200 caracteres, opcional.     |

## 🔍 Entidad Proveedor
| Campo             | Tipo                | Descripción                                | Restricciones                     |
|-------------------|---------------------|--------------------------------------------|-----------------------------------|
| `id`              | `Long`              | Identificador único autogenerado.          |Primary Key.                       |
| `nombre`          | `String`            | Nombre legal del proveedor.                |No nulo, máx. 100 caracteres.      |
| `contacto`        | `String`            | Persona de contacto.                       |No nulo, máx. 100 caracteres.      |
| `telefono`        | `String`            | Teléfono de contacto.                      |Formato internacional válido.      |
| `email`           | `String`            | Email de contacto.                         |Formato internacional válido.      |
| `direccion`       | `String`            | Dirección física..                         |Máx. 200 caracteres.               |
| `ordenesCompra`   | `List<OrdenCompra>` | Órdenes de compra asociadas.               |Relación bidireccional.            |

## 🔍 Entidad OrdenCompra
| Campo             | Tipo                      | Descripción                         | Restricciones                     |
|-------------------|---------------------------|-------------------------------------|-----------------------------------|
| `id`              | `Long`                    | Identificador único autogenerado.   |Primary Key.                       |
| `fecha`           | `LocalDate`               | Fecha de creación.                  |No nula.                           |
| `estado`          | `EstadoOrden`             | Estado del pedido.                  |Valores: PENDIENTE, ENVIADA, etc.  |
| `total`           | `Double`                  | totalidad de compra                 |No nula.                           |
| `proveedor`       | `Proveedor`               | Proveedor asociado.                 |No nulo (clave foránea).           |
| `detalles`        | `List<DetalleOrdenCompra>`| Productos solicitados.              |Relación bidireccional.            |


## 🔍 Entidad DetallesOrdenCompra
| Campo             | Tipo           | Descripción                        | Restricciones    |
|-------------------|----------------|------------------------------------|------------------|
| `id`              | `Long`         | Identificador único autogenerado.  |Primary Key.      |
| `ordenCompra`     | `OrdenCompra`  | Orden de compra asociada           |No nulo           |
| `producto`        | `Producto`     | Producto solicitado en la orden    |No nulo           |
| `cantidad`        | `Integer`      | Cantidad de unidades               |Mayor a 0         |
| `precioUnitario`  | `Double`       | Precio unitario del producto       |Mayor a 0         |
| `subtotal`        | `Double`       | totalidad de la orden de compra    |No nula.          |

## 🔍 Entidad Cliente
| Campo             | Tipo         | Descripción                        | Restricciones                 |
|-------------------|--------------|------------------------------------|-------------------------------|
| `id`              | `Long`       | Identificador único autogenerado.  |Primary Key.                   |
| `nombre`          | `String`     | Nombre legal del proveedor.        |No nulo, máx. 100 caracteres.  |
| `email`           | `String`     | Email de contacto.                 |Formato internacional válido.  |
| `telefono`        | `String`     | Teléfono de contacto.              |Formato internacional válido.  |
| `direccion`       | `String`     | Dirección física..                 |Máx. 200 caracteres.           |

## 🔍 Entidad Auditoria
| Campo             | Tipo             | Descripción                           | Restricciones             |
|-------------------|------------------|---------------------------------------|---------------------------|
| `id`              | `Long`           | Identificador único autogenerado.     |Primary Key.               |
| `accion`          | `String`         | Tipo de acción realizad.              |No nulo.                   |
| `entidadAfectada` | `String`         | Entidad afectada por la acción        |Entidad afectada, No nulo. |
| `idEntidad`       | `Long`           | ID de la entidad afectada             |No nulo.                   |
| `fecha`           | `LocalDateTime`  | Fecha y hora de la acción             |No nulo.                   |
| `detalle`         | `String`         | Detalles adicionales en formato JSON  |Formato text               |
| `usuario`         | `Usuario`        | ID del usuario                        |Relación bidireccional.    |

## 🔍 Entidad DetallesVenta
| Campo             | Tipo       | Descripción                        | Restricciones          |
|-------------------|------------|------------------------------------|------------------------|
| `id`              | `Long`     | Identificador único autogenerado.  |Primary Key.            |
| `cantidad`        | `Integer`  | Cantidad de productos vendidos     |No nulo.                |
| `precioUnitario`  | `Double`   | Precio unitario                    |No nulo.                |
| `venta`           | `Venta`    | Identificador de la venta          |Relación bidireccional. |
| `producto`        | `Producto` | Identificador del producto         |No nulo.                |
| `subtotal`        | `Double`   | totalidad de la venta              |no insertable           |

## 🔍 Entidad EstadoOrden
| Campo             | Tipo                  | Descripción                        | Restricciones    |
|-------------------|-----------------------|------------------------------------|------------------|
|                   |                       |                                    |                  |

## 🔍 Entidad Usuario
| Campo         | Tipo           | Descripción                       | Restricciones                |
|---------------|----------------|-----------------------------------|------------------------------|
| `id`          | `Long`         | Identificador único autogenerado. |Primary Key.                  |
| `nombre`      | `String`       | Nombre legal del proveedor.       |No nulo, máx. 100 caracteres. |
| `email`       | `String`       | Email de contacto.                |Formato internacional válido. |
| `contraseña`  | `String`       | Contraseña encriptada             |máx. 20 caracteres.           |
| `rol`         | `String`       | Rol del usuario                   |Formato internacional válido. |
| `ventas`      | `List<Venta>`  | Ventas solicitados.               |Relación bidireccional.       |

## 🔍 Entidad Venta
| Campo       | Tipo                 | Descripción                        | Restricciones         |
|-------------|----------------------|------------------------------------|-----------------------|
| `id`        | `Long`               | Identificador único autogenerado.  |Primary Key.           |
| `fecha`     | `LocalDateTime`      | Fecha y hora de la acción          |No nulo.               |
| `usuario`   | `Usuario`            | Identificador de usuario           |No nulo.               |
| `detalles`  | `List<DetallesVenta>`| Ventas detalladas solicitados.     |Relación bidireccional.|