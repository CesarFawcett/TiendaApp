"use client";
import { useState, useEffect } from 'react';
import styles from '../styles/page.module.css';
import {
  FaTh,FaUsers,FaBoxes,FaShoppingCart,FaChartLine,FaClipboardCheck,
  FaBars,FaSignOutAlt,FaPlus,FaSearch,FaEdit,FaTrash,
} from 'react-icons/fa';

export default function Home() {

  // =============================================
  // ESTADOS
  // =============================================

  // Estados generales
  const [activeSection, setActiveSection] = useState('Dashboard');
  const [userName] = useState('Nombre del usuario');
  const [sidebarCollapsed, setSidebarCollapsed] = useState(false);
  const [itemsPerPage, setItemsPerPage] = useState(5);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Estados para productos
  const [productos, setProductos] = useState([]);
  const [showProductoModal, setShowProductoModal] = useState(false);
  const [nuevoProducto, setNuevoProducto] = useState({
    nombre: '',
    descripcion: '',
    precio: 0,
    stock: 0,
    fecha: 2025-12-10,
    categoriaId: 1,
  });
  const [showEditModal, setShowEditModal] = useState(false);
  const [editingProducto, setEditingProducto] = useState(null);

  // Estados para usuarios
  const [usuarios, setUsuarios] = useState([]);
  const [showUsuarioModal, setShowUsuarioModal] = useState(false);
  const [nuevoUsuario, setNuevoUsuario] = useState({
  nombre: '',
  email: '',
  password: '',
  rol: 'USER' // Valor por defecto
  });
  const [showEditUsuarioModal, setShowEditUsuarioModal] = useState(false);
  const [editingUsuario, setEditingUsuario] = useState(null);
  
  // =============================================
  // EFECTOS
  // =============================================

  useEffect(() => {
    if (activeSection === 'Productos') {
      fetchProductos();
    }
  }, [activeSection]);

  useEffect(() => {
  if (activeSection === 'Usuarios') {
    fetchUsuarios();
  }
  }, [activeSection]);

  // =============================================
  // FUNCIONES GENERALES
  // =============================================

  const toggleSection = (section) => {
    setActiveSection(section);
  };

  const toggleSidebar = () => {
    setSidebarCollapsed(!sidebarCollapsed);
  };

  const handleLogout = () => {
    // Elimina el token o datos de sesión
  localStorage.removeItem('authToken'); // o sessionStorage
  // Redirige a la página de login
  window.location.href = '/login'; // Asegúrate de tener esta ruta
  // Opcional: Limpiar estados relacionados
  setUser(null);
  // Agrega aquí cualquier otra limpieza necesaria
    // Lógica para cerrar sesión
  };

  // =============================================
  // FUNCIONES PARA PRODUCTOS
  // =============================================

  const fetchProductos = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch('http://localhost:8080/productos');
      if (!response.ok) throw new Error('Error al cargar productos');
      const data = await response.json();
      setProductos(Array.isArray(data) ? data : [data]);
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleProductoSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/productos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(nuevoProducto),
      });

      if (!response.ok) throw new Error('Error al crear producto');

      setShowProductoModal(false);
      setNuevoProducto({
        nombre: '',
        descripcion: '',
        precio: 0,
        stock: 0,
        fecha: 2025-12-10,
        categoriaId: 1,
      });
      await fetchProductos();
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
    }
  };

  const handleDeleteProducto = async (id) => {
  if (window.confirm('¿Estás seguro de que deseas eliminar este producto?')) {
    try {
      const response = await fetch(`http://localhost:8080/productos/${id}`, {
        method: 'DELETE',
      });

      if (!response.ok) throw new Error('Error al eliminar producto');
      await fetchProductos();
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
    }
  }
};

const handleEditClick = (producto) => {
  setEditingProducto(producto);
  setShowEditModal(true);
};

const handleEditSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/productos/${editingProducto.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editingProducto),
    });

    if (!response.ok) throw new Error('Error al actualizar producto');

    setShowEditModal(false);
    setEditingProducto(null);
    await fetchProductos();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};
const handleEditInputChange = (e) => {
  const { name, value } = e.target;
  setEditingProducto({
    ...editingProducto,
    [name]: 
      name === 'precio' ? parseFloat(value) :
      name === 'stock' || name === 'categoriaId' ? parseInt(value) :
      value
  });
};


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNuevoProducto({
      ...nuevoProducto,
      [name]:
        name === 'precio'
          ? parseFloat(value)
          : name === 'stock' || name === 'categoriaId'
          ? parseInt(value)
          : value,
    });
  };

// =============================================
// FUNCIONES PARA Usuarios
// =============================================

const fetchUsuarios = async () => {
  setLoading(true);
  setError(null);
  try {
    const response = await fetch('http://localhost:8080/usuarios');
    if (!response.ok) throw new Error('Error al cargar usuarios');
    const data = await response.json();
    setUsuarios(Array.isArray(data) ? data : [data]);
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  } finally {
    setLoading(false);
  }
};

const handleUsuarioSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch('http://localhost:8080/usuarios', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevoUsuario),
    });

    if (!response.ok) throw new Error('Error al crear usuario');

    setShowUsuarioModal(false);
    setNuevoUsuario({
      nombre: '',
      email: '',
      password: '',
      rol: 'USER'
    });
    await fetchUsuarios();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleEditUsuarioClick = (usuario) => {
  setEditingUsuario(usuario);
  setShowEditUsuarioModal(true);
};

const handleEditUsuarioSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/usuarios/${editingUsuario.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editingUsuario),
    });

    if (!response.ok) throw new Error('Error al actualizar usuario');

    setShowEditUsuarioModal(false);
    setEditingUsuario(null);
    await fetchUsuarios();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleDeleteUsuario = async (id) => {
  if (window.confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
    try {
      const response = await fetch(`http://localhost:8080/usuarios/${id}`, {
        method: 'DELETE',
      });

      if (!response.ok) throw new Error('Error al eliminar usuario');
      await fetchUsuarios();
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
    }
  }
};

const handleUsuarioInputChange = (e) => {
  const { name, value } = e.target;
  setNuevoUsuario({
    ...nuevoUsuario,
    [name]: value
  });
};

const handleEditUsuarioInputChange = (e) => {
  const { name, value } = e.target;
  setEditingUsuario({
    ...editingUsuario,
    [name]: value
  });
};

// =============================================
// RENDERIZADO
// =============================================

  return (
    <div className={`${styles.page} ${sidebarCollapsed ? styles.collapsed : ''}`}>

      {/* Sidebar */}
      <aside className={styles.sidebar}>
        <div className={styles.sidebarHeader}>
          <div className={styles.logo}>{!sidebarCollapsed && <h1>Kompta</h1>}</div>
          <button onClick={toggleSidebar} className={styles.menuToggle}><FaBars /></button>
        </div>
        <div className={styles.userWelcome}>{!sidebarCollapsed && <h2>Bienvenido/a {userName}</h2>}</div>
        <nav className={styles.sidebarNav}>
          <button onClick={() => toggleSection('Dashboard')}className={activeSection === 'Dashboard' ? styles.activeButton : ''}title="Dashboard"><FaTh />{!sidebarCollapsed && <span>Dashboard</span>}</button>
          <button onClick={() => toggleSection('Auditoria')}className={activeSection === 'Auditoria' ? styles.activeButton : ''}title="Auditoria"><FaChartLine />{!sidebarCollapsed && <span>Auditoria</span>}</button>
          <button onClick={() => toggleSection('Usuarios')}className={activeSection === 'Usuarios' ? styles.activeButton : ''}title="Usuarios"><FaUsers />{!sidebarCollapsed && <span>Usuarios</span>}</button>
          <button onClick={() => toggleSection('Productos')}className={activeSection === 'Productos' ? styles.activeButton : ''}title="Productos"><FaBoxes />{!sidebarCollapsed && <span>Productos</span>}</button>
          <button onClick={() => toggleSection('Compras')}className={activeSection === 'Compras' ? styles.activeButton : ''}title="Compras"><FaShoppingCart />{!sidebarCollapsed && <span>Compras</span>}</button>
          <button onClick={() => toggleSection('Proveedores')}className={activeSection === 'Proveedores' ? styles.activeButton : ''}title="Proveedores"><FaClipboardCheck />{!sidebarCollapsed && <span>Proveedores</span>}</button>
        </nav>
        <div className={styles.sidebarFooter}>
          <button onClick={handleLogout} className={styles.logoutButton} title="Cerrar sesión"><FaSignOutAlt />{!sidebarCollapsed && <span>Cerrar sesión</span>}</button>
        </div>
      </aside>

      {/* Main Content */}
      <main className={styles.mainContent}>
       
      {/* Dashboard Section */}
      {activeSection === 'Dashboard' && (
        <div className={styles.dashboard}>
          <h2>Bienvenido/a</h2>
          <div className={styles.statsGrid}>
            <div className={styles.statCard}>
              <h3>Gráfico Ventas</h3>
              <p>Últimos 30 días</p>
            </div>
            <div className={styles.statCard}>
              <h3>Productos Top</h3>
              <p>Más vendidos</p>
            </div>
            <div className={styles.statCard}>
              <h3>Últimas Órdenes</h3>
              <p>Tablas</p>
            </div>
            <div className={styles.statCard}>
              <h3>Alertas Pendientes</h3>
              <p>Tablas</p>
            </div>
          </div>
        </div>)}

      {/* Productos Section */}
      {activeSection === 'Productos' && (
        <div className={styles.productosContainer}>
          <div className={styles.productosHeader}>
            <h1><FaBoxes /> Productos</h1>
            <button onClick={() => setShowProductoModal(true)} className={styles.nuevoProductoBtn}><FaPlus /> NUEVO PRODUCTO</button>
          </div>
          <div className={styles.productosToolbar}>
            <div className={styles.showEntries}>
              <span>Mostrar</span>
              <select value={itemsPerPage}onChange={(e) => setItemsPerPage(Number(e.target.value))}>
                <option value={5}>5</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
              </select>
              <span>Entradas</span>
            </div>
            <div className={styles.searchBox}><FaSearch />
            <input type="text" placeholder="Buscar..." />
            </div>
          </div>
          <div className={styles.productosTableContainer}>
              <h2>LISTA DE PRODUCTOS</h2>
              {loading ? (
                <p>Cargando productos...</p>
              ) : error ? (
                <div className={styles.emptyState}>
                  <p>Error al cargar productos: {error}</p>
                  <button onClick={fetchProductos} className={styles.nuevoProductoBtn}>
                    Reintentar
                  </button>
                </div>
              ) : productos.length === 0 ? (
                <div className={styles.emptyState}>
                  <p>No hay productos registrados</p>
                </div>
              ) : (
                <div className={styles.tableWrapper}>
                  <table className={styles.productosTable}>
                    <thead>
                      <tr>
                        <th>#</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Stock</th>
                        <th>Precio</th>
                        <th>Fecha</th>
                        <th>Acciones</th>
                      </tr>
                    </thead>
                    <tbody>
                    {productos.slice(0, itemsPerPage).map((producto, index) => (
                    <tr key={producto.id}>
                     <td>{index + 1}</td>
                     <td>
                      <div className={styles.productName}>
                        <span className={styles.name}>{producto.nombre}</span>
                        {producto.descripcion && (<span className={styles.description}>{producto.descripcion}</span>)}
                      </div>
                     </td>
                      <td>{producto.categoria?.nombre || 'General'}</td>
                        <td>
                          <span className={`${styles.stockBadge} ${producto.stock < 10 ? styles.lowStock : ''}`}>{producto.stock} unidades</span>
                        </td>
                        <td>
                          ${producto.precio.toLocaleString('es-ES', {minimumFractionDigits: 2,maximumFractionDigits: 2,})}
                        </td>
                        <td>
                          {producto.fecha ? new Date(producto.fecha).toLocaleDateString('es-ES') : 'N/A'}
                        </td>
                        <td>
                          <div className={styles.actions}>
                            <button className={`${styles.actionBtn} ${styles.editBtn}`}title="Editar"onClick={() => handleEditClick(producto)}><FaEdit /></button>
                            <button className={`${styles.actionBtn} ${styles.deleteBtn}`}title="Eliminar"onClick={() => handleDeleteProducto(producto.id)}><FaTrash /></button>
                          </div>
                        </td>
                    </tr>))}
                    </tbody>
                  </table>
                </div>)}
           </div>

        {/* Modal Nuevo Producto */}
        {showProductoModal && (
          <div className={styles.modalOverlay}>
            <div className={styles.modalContent}>
              <h2>Nuevo Producto</h2>
               <form onSubmit={handleProductoSubmit} className={styles.modalForm}>

                    <fieldset className={styles.formFieldset}>
                      <legend className={styles.formLegend}>Información General</legend>
                      <div className={styles.formGroup}>
                        <label>Nombre:</label>
                        <input type="text" name="nombre" value={nuevoProducto.nombre}onChange={handleInputChange}required/>
                      </div>
                      <div className={styles.formGroup}>
                        <label>Descripción:</label>
                        <textarea name="descripcion" value={nuevoProducto.descripcion}onChange={handleInputChange}/>
                      </div>
                    </fieldset>

                    <fieldset className={styles.formFieldset}>
                      <legend className={styles.formLegend}>Precio y Stock</legend>
                      <div className={styles.formGroup}>
                        <label>Precio:</label>
                        <input type="number" name="precio" value={nuevoProducto.precio}onChange={handleInputChange}step="0.01" min="0" required/>
                      </div>
                      <div className={styles.formGroup}>
                        <label>Stock:</label>
                        <input type="number" name="stock" value={nuevoProducto.stock}onChange={handleInputChange}min="0" required/>
                      </div>
                      <div className={styles.formGroup}>
                        <label>fecha:</label>
                        <input type="localDate" name="fecha" value={nuevoProducto.fecha}onChange={handleInputChange}min="2025-12-10" required/>
                      </div>
                    </fieldset>

                    <fieldset className={styles.formFieldset}>
                      <legend className={styles.formLegend}>Categoría</legend>
                      <div className={styles.formGroup}>
                        <label>Categoría ID:</label>
                        <input type="number" name="categoriaId" value={nuevoProducto.categoriaId}onChange={handleInputChange}min="1" required/>
                      </div>
                    </fieldset>

                    <div className={styles.modalButtons}>
                      <button type="button" onClick={() => setShowProductoModal(false)}className={styles.cancelButton}>
                        Cancelar</button>
                      <button type="submit" className={styles.submitButton}>
                        Guardar Producto
                      </button>
                    </div>
                </form>
              </div>
            </div>)}

        {/* Modal Editar Producto */}
        {showEditModal && editingProducto && (
          <div className={styles.modalOverlay}>
            <div className={styles.modalContent}>
              <h2> Editar Producto </h2>
                <form onSubmit={handleEditSubmit} className={styles.modalForm}>

                  <fieldset className={styles.formFieldset}>
                    <legend className={styles.formLegend}>Información General</legend>
                      <div className={styles.formGroup}>
                        <label> Nombre : </label>
                        <input type="text" name="nombre" value={editingProducto.nombre}onChange={handleEditInputChange}required/>
                      </div>
                      <div className={styles.formGroup}>
                        <label> Descripción : </label>
                        <textarea name="descripcion" value={editingProducto.descripcion}onChange={handleEditInputChange}/>
                      </div>
                  </fieldset>

                  <fieldset className={styles.formFieldset}>
                    <legend className={styles.formLegend}> Precio y Stock </legend>
                      <div className={styles.formGroup}>
                        <label> Precio : </label>
                        <input type="number" name="precio" value={editingProducto.precio}onChange={handleEditInputChange}step="0.01" min="0" required/>
                      </div>
                      <div className={styles.formGroup}>
                        <label> Stock : </label>
                        <input type="number" name="stock" value={editingProducto.stock}onChange={handleEditInputChange}min="0" required/>
                      </div>
                      <div className={styles.formGroup}>
                        <label> Fecha : </label>
                        <input type="date" name="fecha" value={editingProducto.fecha}onChange={handleEditInputChange}required/>
                      </div>
                  </fieldset>

                  <fieldset className={styles.formFieldset}>
                    <legend className={styles.formLegend}> Categoría </legend>
                      <div className={styles.formGroup}>
                        <label> Categoría ID : </label>
                        <input type="number" name="categoriaId" value={editingProducto.categoriaId} onChange={handleEditInputChange}min="1"required/>
                      </div>
                  </fieldset>

                    <div className={styles.modalButtons}>
                      <button type="button" onClick={() => setShowEditModal(false)}className={styles.cancelButton}>
                        Cancelar
                      </button>
                      <button type="submit" className={styles.submitButton}>
                        Guardar Cambios
                      </button>
                    </div>
                </form>
            </div>
          </div>)}
        </div>)}

      {activeSection === 'Usuarios' && (
       <div className={styles.usuariosContainer}>
        <div className={styles.usuariosHeader}>
        <h1><FaUsers /> Usuarios </h1>
          <button onClick={() => setShowUsuarioModal(true)} className={styles.nuevoUsuarioBtn}><FaPlus /> NUEVO USUARIO </button>
        </div>
        <div className={styles.usuariosToolbar}>
          <div className={styles.showEntries}>
           <span> Mostrar </span>
            <select value={itemsPerPage}onChange={(e) => setItemsPerPage(Number(e.target.value))}>
             <option value={5}>5</option>
             <option value={10}>10</option>
             <option value={20}>20</option>
            </select>
           <span> Entradas </span>
          </div>
         <div className={styles.searchBox}><FaSearch /><input type="text" placeholder="Buscar..." /></div>
        </div>
       <div className={styles.usuariosTableContainer}>
       <h2> LISTA DE USUARIOS </h2>
      {loading ? (
        <p> Cargando usuarios... </p>
      ) : error ? (
        <div className={styles.emptyState}>
          <p> Error al cargar usuarios : {error}</p>
          <button onClick={fetchUsuarios} className={styles.nuevoUsuarioBtn}>
            Reintentar
          </button>
        </div>
      ) : usuarios.length === 0 ? (
        <div className={styles.emptyState}>
          <p> No hay usuarios registrados </p>
        </div>
      ) : (
        <div className={styles.tableWrapper}>
          <table className={styles.usuariosTable}>
            <thead>
              <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Rol</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {usuarios.slice(0, itemsPerPage).map((usuario, index) => (
                <tr key={usuario.id}>
                  <td>{index + 1}</td>
                  <td>{usuario.nombre}</td>
                  <td>{usuario.email}</td>
                  <td>{usuario.rol}</td>
                  <td>
                    <div className={styles.actions}>
                      <button className={`${styles.actionBtn} ${styles.editBtn}`}title="Editar" onClick={() => handleEditUsuarioClick(usuario)}><FaEdit /></button>
                      <button className={`${styles.actionBtn} ${styles.deleteBtn}`}title="Eliminar" onClick={() => handleDeleteUsuario(usuario.id)}><FaTrash /></button>
                    </div>
                  </td>
                </tr>))}
            </tbody>
          </table>
        </div>)}
      </div>

     {/* Modal Nuevo Usuario */}
     {showUsuarioModal && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2> Nuevo Usuario </h2>
          <form onSubmit={handleUsuarioSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información Básica</legend>
              <div className={styles.formGroup}>
                <label>Nombre:</label>
                <input type="text" name="nombre" value={nuevoUsuario.nombre}onChange={handleUsuarioInputChange} required/>
              </div>
              <div className={styles.formGroup}>
                <label>Email:</label>
                <input type="email" name="email" value={nuevoUsuario.email}onChange={handleUsuarioInputChange}required/>
              </div>
              <div className={styles.formGroup}>
                <label>Contraseña:</label>
                <input type="password" name="password" value={nuevoUsuario.password}onChange={handleUsuarioInputChange}required/>
              </div>
              <div className={styles.formGroup}>
                <label>Rol:</label>
                <select name="rol" value={nuevoUsuario.rol}onChange={handleUsuarioInputChange}required>
                  <option value="USUARIO">Usuario</option>
                  <option value="ADMINISTRADOR">Administrador</option>
                  <option value="VENDEDOR">VENDEDOR</option>
                  <option value="ALMACEN">ALMACEN</option>
                </select>
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowUsuarioModal(false)}className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Usuario
              </button>
            </div>
          </form>
        </div>
      </div>)}

      {/* Modal Editar Usuario */}
      {showEditUsuarioModal && editingUsuario && (
       <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Editar Usuario</h2>
          <form onSubmit={handleEditUsuarioSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información Básica</legend>
               <div className={styles.formGroup}>
                <label>Nombre:</label>
                <input type="text" name="nombre" value={editingUsuario.nombre}onChange={handleEditUsuarioInputChange}required/>
               </div>
              <div className={styles.formGroup}>
                <label>Email:</label>
                <input type="email" name="email" value={editingUsuario.email}onChange={handleEditUsuarioInputChange}required/>
              </div>
              <div className={styles.formGroup}>
                <label>Rol:</label>
                <select name="rol" value={editingUsuario.rol}onChange={handleEditUsuarioInputChange}required>
                  <option value="USUARIO">Usuario</option>
                  <option value="ADMINISTRADOR">Administrador</option>
                  <option value="VENDEDOR">VENDEDOR</option>
                  <option value="ALMACEN">ALMACEN</option>
                </select>
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowEditUsuarioModal(false)}className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Cambios
              </button>
            </div>
          </form>
        </div>
       </div>)}
      {/* otros */}
     </div>)}
     </main>
  </div>);
}