# entities

## 🔍 Entidad Auditoria
| Campo             | Tipo             | Descripción                                        | Restricciones              |
|-------------------|------------------|----------------------------------------------------|----------------------------|
| `id`              | `Long`           | Identificador único autogenerado.                  |Primary Key.                |
| `accion`          | `String`         | Tipo de acción realizad.                           |No nulo, máx. 50 caracteres.|
| `entidadAfectada` | `String`         | Nombre de la entidad afectada por la acción.       |No nulo, máx. 50 caracteres.|
| `idEntidad`       | `Long`           | Identificador de la entidad afectada.              |No nulo.                    |
| `fecha`           | `LocalDateTime`  | Fecha y hora exacta en la que se realizó la acción.|No nulo.                    |
| `detalle`         | `String`         | Detalles adicionales en formato JSON.              |Formato text                |
| `usuario`         | `Usuario`        | Usuario que realizó la acción registrada.          |Relación bidireccional.     |

## 🔍 Entidad Categoria
| Campo         | Tipo      | Descripción                                                       | Restricciones                     |
|---------------|-----------|-------------------------------------------------------------------|-----------------------------------|
| `id`          | `Long`    | Identificador único autogenerado.                                 |Primary Key.                       |
| `nombre`      | `String`  | Nombre de la categoría.                                           |No nulo, único, máx. 50 caracteres.|
| `descripcion` | `String`  | Descripción detallada de la categoría y los productos que agrupa. |Opcional.                          |

## 🔍 Entidad Cliente
| Campo        | Tipo          | Descripción                                   | Restricciones                                      |
|--------------|---------------|-----------------------------------------------|----------------------------------------------------|
| `id`         | `Long`        | Identificador único autogenerado.             |Primary Key.                                        |
| `nombre`     | `String`      | Nombre completo del cliente o razón social    |No nulo, máx. 100 caracteres.                       |
| `email`      | `String`      | Correo electrónico para facturación.          |No nulo, formato de email válido.                   |
| `telefono`   | `String`      | Teléfono de contacto principal.               |No nulo, mínimo 10 caracteres, puede incluir + o -. |
| `direccion`  | `String`      | Dirección física para entregas y facturación. |No nulo.                                            |
| `ventas`     | `List<Venta>` | Historial de ventas realizadas al cliente.    |Relación uno a muchos.                              |

## 🔍 Entidad DetallesOrdenCompra
| Campo            | Tipo          | Descripción                                           | Restricciones                    |
|------------------|---------------|-------------------------------------------------------|----------------------------------|
| `id`             | `Long`        | Identificador único autogenerado.                     |Primary Key.                      | 
| `ordenCompra`    | `OrdenCompra` | Orden de compra a la que pertenece este detalle.      |Relación muchos a uno, no nulo.   |
| `producto`       | `Producto`    | Producto solicitado en esta línea de la orden.        |Relación muchos a uno, no nulo.   |
| `cantidad`       | `Integer`     | Cantidad de unidades del producto solicitadas.        |No nulo, valor positivo.          |
| `precioUnitario` | `Double`      | Precio unitario negociado con el proveedor.           |No nulo, valor positivo.          |
| `subtotal`       | `Double`      | Valor subtotal calculado (cantidad × precioUnitario). |Campo calculado (no persistente). |

## 🔍 Entidad DetallesVenta
| Campo             | Tipo       | Descripción                                           | Restricciones                    |
|-------------------|------------|-------------------------------------------------------|----------------------------------|
| `id`              | `Long`     | Identificador único autogenerado.                     |Primary Key.                      |
| `cantidad`        | `Integer`  | Cantidad de unidades vendidas del producto.           |No nulo.                          |
| `precioUnitario`  | `Double`   | Precio unitario aplicado al momento de la venta.      |No nulo.                          |
| `venta`           | `Venta`    | 	Venta a la que pertenece este detalle.               |Relación muchos a uno, no nulo.   |
| `producto`        | `Producto` | Producto específico vendido.                          |Relación muchos a uno, no nulo.   |
| `subtotal`        | `Double`   | Valor subtotal calculado (cantidad × precioUnitario). |Campo calculado (no persistente). |

## 🔍 Entidad EstadoOrden
| Valor      | Descripción                                                          |
|------------|----------------------------------------------------------------------|
| PENDIENTE  | La orden ha sido creada pero aún no se ha enviado al proveedor.      |
| ENVIADA    | La orden ha sido enviada al proveedor y está en espera de recepción. |
| RECIBIDA   | Los productos de la orden han sido recibidos satisfactoriamente.     |
| CANCELADA  | La orden ha sido cancelada y no será procesada.                      |

## 🔍 Entidad HistorialEstadoOrden
| Campo            | Tipo            | Descripción                                               | Restricciones                  |
| `id`             | `Long`          | Identificador único autogenerado.                         |Primary Key.                    |
| `ordenCompra`    | `OrdenCompra`   | Orden de compra asociada a este cambio de estado.         |Relación muchos a uno, no nulo. |
| `estadoAnterior` | `EstadoOrden`   | Estado en el que se encontraba la orden antes del cambio. |No nulo. Key.                   |
| `estadoNuevo`    | `EstadoOrden`   | Nuevo estado al que cambió la orden.                      |No nulo.                        |
| `fechaCambio`    | `LocalDateTime` | Fecha y hora en que se realizó el cambio de estado.       |Primary Key.                    |
| `usuario`        | `Usuario`       | Usuario que realizó el cambio de estado.                  |Relación muchos a uno.          |
| `observacion`    | `String`        | Comentario explicativo o motivo del cambio de estado.     |Máx. 255 caracteres.Key.        |

## 🔍 Entidad OrdenCompra
| Campo              | Tipo          | Descripción                                                           | Restricciones  |
|--------------------|---------------|-----------------------------------------------------------------------|----------------|
| `id`               | `Long`        | Identificador único autogenerado.                                     |Primary Key.    |
| `fecha`            | `LocalDate`   | Fecha en la que se emite la orden de compra.                          |No nula.        |
| `estado`           | `EstadoOrden` | Estado actual de la orden dentro de su ciclo de vida.                 |Enumerado.      |
| `totalCalculated`  | `Double`      | Valor persistido del total de la orden (sumatoria de los subtotales). |Se actualiza automáticamente.   |
| `proveedor`        | `Proveedor`                  | Proveedor al que se le realiza la orden de compra.     |Relación muchos a uno, no nulo. |
| `detalles`         | `List<DetallesOrdenCompra>`  | Lista de productos solicitados.                        |Relación uno a muchos.          |
| `historialEstados` | `List<HistorialEstadoOrden>` | Historial de cambios de estado de esta orden.          |Relación uno a muchos.          |

## 🔍 Entidad Producto
| Campo          | Tipo          | Descripción                                                    | Restricciones                      |
|----------------|---------------|----------------------------------------------------------------|------------------------------------|
| `id`           | `Long`        | Identificador único autogenerado.                              |Primary Key, no nulo.               |
| `nombre`       | `String`      | Nombre comercial del producto.                                 |No nulo, máximo 100 caracteres.     |
| `descripcion`  | `String`      | Descripción detallada del producto.                            |Opcional, máximo 200 caracteres.    |
| `precio`       | `Double`      | Precio de venta al público.                                    |Debe ser mayor que 0.               |
| `stock`        | `Integer`     | Cantidad de unidades disponibles en el inventario.             |No negativo, no nulo.               |
| `fecha`        | `LocalDate`   | Fecha de vencimiento del producto.                             |Debe ser futura o presente, no nulo.|       
| `categoria`    | `Categoria `  | Categoría a la que pertenece el producto (para clasificación). |Relación muchos a uno, no nulo.     |

## 🔍 Entidad Proveedor
| Campo           | Tipo                | Descripción                                             | Restricciones                                    |
|-----------------|---------------------|---------------------------------------------------------|--------------------------------------------------|
| `id`            | `Long`              | Identificador único autogenerado.                       |Primary Key.                                      |
| `nombre`        | `String`            | Nombre comercial o razón social del proveedor.          |No nulo, máximo 100 caracteres.                   |
| `contacto`      | `String`            | Nombre completo de la persona de contacto.              |No nulo.                                          |
| `telefono`      | `String`            | Número telefónico de contacto directo.                  |Debe cumplir con el patrón de validación, no nulo.|
| `email`         | `String`            | Correo electrónico del proveedor.                       |Formato válido según regex, no nulo.              |
| `direccion`     | `String`            | Dirección física.                                       |No nulo.                                          |
| `ordenesCompra` | `List<OrdenCompra>` | Lista de órdenes de compra realizadas a este proveedor. |Relación uno a mucho.                             |

## 🔍 Entidad Usuario
| Campo         | Tipo           | Descripción                                                  | Restricciones                          |
|---------------|----------------|--------------------------------------------------------------|----------------------------------------|
| `id`          | `Long`         | Identificador único autogenerado.                            |Primary Key.                            |
| `nombre`      | `String`       | Nombre completo del usuario.                                 |No nulo, máximo 100 caracteres.         |
| `email`       | `String`       | Correo electrónico usado como identificador único de acceso. |Formato válido según regex, no nulo.    |
| `contraseña`  | `String`       | Contraseña encriptada.                                       |No nulo, longitud máxima 60 caracteres. |
| `rol`         | `ROL`          | Rol o perfil del usuario en el sistema.                      |Opcional.                               |
| `ventas`      | `List<Venta>`  | Ventas realizadas por este usuario.                          |Relación uno a muchos.                  |

## 🔍 Entidad Venta
| Campo       | Tipo                 | Descripción                                                    | Restricciones         |
|-------------|----------------------|----------------------------------------------------------------|-----------------------|
| `id`        | `Long`               | Identificador único autogenerado.                              |Primary Key.           |
| `fecha`     | `LocalDateTime`      | Fecha y hora exacta en que se realizó la transacción de venta. |No nulo.               |
| `usuario`   | `Usuario`            | Identificador de usuario.                                      |No nulo.               |
| `cliente`   | `Cliente`            | Cliente que realizó la compra y será facturado.  |Relación ManyToOne, no nulo, con index (cliente_id)|
| `detalles`  | `List<DetallesVenta>`| Lista de productos vendidos.  |Relación OneToMany, bidireccional, cascada total y eliminación huérfana|
| `Total`     | `Double`             | Método que calcula el total de la venta sumando subtotales.    |No se almacena en base de datos. |