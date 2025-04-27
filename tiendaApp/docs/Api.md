# Endpoint (estructuracion de los controller)

### Producto (`/producto`)
### categoria (`/categoria`)
### proveedor (`/proveedor`)
### OrdenCompra (`/OrdenCompra`)
### DetallesOrdenCompra (`/DetallesOrdenCompra`)
### Cliente (`/Cliente`)
### Usuario (`/Usuario`)
### Venta (`/Venta`)
### DetallesVenta (`/DetallesVenta`)
### Auditoria (`/Auditoria`)



## Crear Producto (`/`)
{
  "nombre": "Manzanas Gala",
  "descripcion": "Manzanas rojas dulces y jugosas",
  "precio": 1.99,
  "stock": 100,
  "fecha": "2025-05-10",
  "categoriaId": 1  
}

## Crear Categoria (`/`)
{
  "nombre": "Lácteos",
  "descripcion": "Productos derivados de la leche",
}

## Crear Proveedor (`/`)
{
  "nombre": "Distribuidora Dulce S.A.",
  "contacto": "María González",
  "telefono": "+573001234567",
  "email": "ventas@dulcedistribuidora.com",
  "direccion": "Calle 12 #34-56, Bogotá"
}

## Crear OrdenCompra (`/`)
{
  "fecha": "2023-11-20",
  "estado": "PENDIENTE",
  "total": 150000.00,
  "proveedor": {
    "id": 1
  },
  "detalles": [
    {
      "producto": {
        "id": 1
      },
      "cantidad": 30,
      "precioUnitario": 5000.00
    }
  ]
}

## Crear DetallesOrdenCompra (`/`)
{
  "ordenCompra": {
    "id": 1
  },
  "producto": {
    "id": 1
  },
  "cantidad": 30,
  "precioUnitario": 5000.00
}

## Crear Cliente (`/`)
{
  "nombre": "Supermercado El Ahorro",
  "email": "compras@elahorro.com",
  "telefono": "+5712345678",
  "direccion": "Av. Principal #123-45, Medellín"
}

## Crear Usuario (`/`)
{
  "nombre": "Carlos Mendoza",
  "email": "carlos.mendoza@sistema.com",
  "contraseña": "Cmendoza2023",
  "rol": "VENTAS"
}

## Crear Venta (`/`)
{
  "fecha": "2023-11-25T09:30:00",
  "usuario": {
    "id": 1
  },
  "detalles": [
    {
      "producto": {
        "id": 1
      },
      "cantidad": 5,
      "precioUnitario": 5500.00
    }
  ]
}

## Crear DetallesVenta (`/`)
{
  "venta": {
    "id": 1
  },
  "producto": {
    "id": 1
  },
  "cantidad": 5,
  "precioUnitario": 5500.00
}

## Crear Auditoria (`/`)
{
  "accion": "ACTUALIZACIÓN",
  "entidadAfectada": "Producto",
  "idEntidad": 1,
  "fecha": "2023-11-25T10:15:22",
  "detalle": "{\"campo\":\"precio\", \"valorAnterior\":5000.00, \"valorNuevo\":5500.00}",
  "usuario": {
    "id": 1
  }
}