const UsuariosSection = ({
  usuarios, loading, error, itemsPerPage, setItemsPerPage,
  handleEditUsuarioClick, handleDeleteUsuario, showUsuarioModal,
  setShowUsuarioModal, nuevoUsuario, handleUsuarioInputChange,
  handleUsuarioSubmit, showEditUsuarioModal, editingUsuario,
  handleEditUsuarioSubmit, handleEditUsuarioInputChange, fetchUsuarios
}) => (
  <div className={styles.usuariosContainer}>
    {/* Encabezado */}
    <div className={styles.usuariosHeader}>
      <h1><FaUsers /> Usuarios</h1>
      <button onClick={() => setShowUsuarioModal(true)} className={styles.nuevoUsuarioBtn}>
        <FaPlus /> NUEVO USUARIO
      </button>
    </div>

    {/* Barra de herramientas */}
    <UsuariosToolbar 
      itemsPerPage={itemsPerPage} 
      setItemsPerPage={setItemsPerPage}
    />

    {/* Tabla de usuarios */}
    <UsuariosTable
      usuarios={usuarios}
      loading={loading}
      error={error}
      itemsPerPage={itemsPerPage}
      handleEditUsuarioClick={handleEditUsuarioClick}
      handleDeleteUsuario={handleDeleteUsuario}
      fetchUsuarios={fetchUsuarios}
    />

    {/* Modales */}
    <UsuarioModal
      show={showUsuarioModal}
      onClose={() => setShowUsuarioModal(false)}
      title="Nuevo Usuario"
      usuario={nuevoUsuario}
      handleChange={handleUsuarioInputChange}
      handleSubmit={handleUsuarioSubmit}
    />

    <UsuarioModal
      show={showEditUsuarioModal}
      onClose={() => setShowEditUsuarioModal(false)}
      title="Editar Usuario"
      usuario={editingUsuario}
      handleChange={handleEditUsuarioInputChange}
      handleSubmit={handleEditUsuarioSubmit}
      isEdit
    />
  </div>
);