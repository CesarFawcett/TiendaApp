const ProductosSection = ({
  productos, loading, error, itemsPerPage, setItemsPerPage,
  handleEditClick, handleDeleteProducto, showProductoModal,
  setShowProductoModal, nuevoProducto, handleInputChange,
  handleProductoSubmit, showEditModal, editingProducto,
  handleEditSubmit, handleEditInputChange, fetchProductos
}) => (
  <div className={styles.productosContainer}>
    {/* Encabezado */}
    <div className={styles.productosHeader}>
      <h1><FaBoxes /> Productos</h1>
      <button onClick={() => setShowProductoModal(true)} className={styles.nuevoProductoBtn}>
        <FaPlus /> NUEVO PRODUCTO
      </button>
    </div>

    {/* Barra de herramientas */}
    <ProductosToolbar 
      itemsPerPage={itemsPerPage} 
      setItemsPerPage={setItemsPerPage}
    />

    {/* Tabla de productos */}
    <ProductosTable
      productos={productos}
      loading={loading}
      error={error}
      itemsPerPage={itemsPerPage}
      handleEditClick={handleEditClick}
      handleDeleteProducto={handleDeleteProducto}
      fetchProductos={fetchProductos}
    />

    {/* Modales */}
    <ProductoModal
      show={showProductoModal}
      onClose={() => setShowProductoModal(false)}
      title="Nuevo Producto"
      producto={nuevoProducto}
      handleChange={handleInputChange}
      handleSubmit={handleProductoSubmit}
    />

    <ProductoModal
      show={showEditModal}
      onClose={() => setShowEditModal(false)}
      title="Editar Producto"
      producto={editingProducto}
      handleChange={handleEditInputChange}
      handleSubmit={handleEditSubmit}
      isEdit
    />
  </div>
);