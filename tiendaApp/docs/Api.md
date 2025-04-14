# Endpoint (estructuracion de los controller)

### Productos (`/producto`)
### Productos (`/categoria`)
### Productos (`/proveedor`)
### Productos (`/OrdenCompra`)
### Productos (`/DetallesOrdenCompra`)


## Crear Producto (`/`)
{
  "nombre": "Azúcar refinada",
  "descripcion": "Azúcar blanca en paquete de 1kg",
  "precio": 5000.00,
  "stock": 30,
  "categoria": {
    "id": 2  // (ej: "Granos")
  }
}

## Crear Categoria (`/`)
{
  "nombre": "Lácteos",
  "descripcion": "Productos derivados de la leche",
}

## Crear Proveedor (`/`)
{
  "nombre": "Distribuidora Lácteos S.A.",
  "contacto": "Carlos Rojas",
  "telefono": "+57 3155551234",
  "direccion": "Av. Principal #78-90"
}

## Crear OrdenCompra (`/`)
{
  "fecha": "2025-06-15",
  "proveedor": {
    "id": 1  // ID de un proveedor existente
  },
  "detalles": [
    {
      "producto": {"id": 3},
      "cantidad": 10,
      "precioUnitario": 12000.50
    },
    {
      "producto": {"id": 5},
      "cantidad": 5,
      "precioUnitario": 8000.00
    }
  ],
  "estado": "PENDIENTE"
}

## Crear DetallesOrdenCompra (`/`)
{
  "ordenCompra": {
    "id": 1
  },
  "producto": {
    "id": 3
  },
  "cantidad": 5,
  "precioUnitario": 12000.00
}