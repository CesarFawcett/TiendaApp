"use client";
import { useState, useEffect } from 'react';
import styles from '../styles/page.module.css';
import {
  FaTh,FaUsers,FaBoxes,FaShoppingCart,FaChartLine,FaClipboardCheck,
  FaBars,FaSignOutAlt,FaPlus,FaSearch,FaEdit,FaTrash,
} from 'react-icons/fa';
//====================================
import { Bar } from 'react-chartjs-2';
import axios from 'axios';

// Importa y registra los componentes necesarios de Chart.js
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement, // Necesario para Doughnut/Pie charts
  Title,
  Tooltip,
  Legend
} from 'chart.js';

// Registra los componentes (Asegúrate de que ArcElement esté aquí si usas Doughnut/Pie)
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement, // Asegúrate de registrar ArcElement
  Title,
  Tooltip,
  Legend
);
// Componente reutilizable para el modal/overlay del gráfico grande
const ChartModal = ({ children, onClose }) => {
  return (
    <div className={styles.chartModalOverlay} onClick={onClose}>
      <div className={styles.chartModalContent} onClick={(e) => e.stopPropagation()}>
        <button onClick={onClose} className={styles.chartModalCloseBtn}>X</button>
        {children}
      </div>
    </div>
  );
};

// Componente para el Gráfico de Ventas
const GraficoVentas = ({ isMini = false }) => { // <--- AGREGAR la prop isMini
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [
      {
        label: 'Ventas', // Etiqueta más genérica para mini
        data: [],
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }
    ]
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchVentas = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await axios.get('http://localhost:8080/ventas/ultimas-ventas');
        const ventas = response.data;
        
        const labels = ventas.map(v => 
          new Date(v.fecha).toLocaleDateString()
        );
        const data = ventas.map(v => v.total);
        
        setChartData({
          labels: labels.reverse(),
          datasets: [
            {
              label: 'Ventas últimos 30 días', // Etiqueta completa para el gráfico grande
              data: data.reverse(),
              backgroundColor: 'rgba(54, 162, 235, 0.5)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1
            }
          ]
        });
      } catch (err) {
        console.error('Error fetching ventas:', err);
        setError('No se pudieron cargar los datos de ventas.');
      } finally {
        setLoading(false);
      }
    };

    fetchVentas();
  }, []);

  const options = {
    responsive: true,
    maintainAspectRatio: !isMini, // <--- MODIFICADO: Adapta el tamaño según si es mini o no
    plugins: {
      legend: {
        display: !isMini, // <--- MODIFICADO: Oculta la leyenda en versión mini
        position: 'top',
      },
      title: {
        display: !isMini, // <--- MODIFICADO: Oculta el título en versión mini
        text: 'Ventas últimos 30 días'
      },
      tooltip: {
        enabled: !isMini, // <--- MODIFICADO: Deshabilita tooltips en versión mini
      }
    },
    scales: {
      y: {
        beginAtZero: true,
        display: !isMini, // <--- MODIFICADO: Oculta el eje Y en versión mini
        title: {
          display: !isMini,
          text: 'Monto ($)'
        },
        ticks: { // <--- AGREGADO: Para controlar las etiquetas en mini
          maxTicksLimit: isMini ? 3 : 5,
          font: {
            size: isMini ? 8 : 12
          }
        }
      },
      x: {
        display: !isMini, // <--- MODIFICADO: Oculta el eje X en versión mini
        title: {
          display: !isMini,
          text: 'Fecha'
        },
        ticks: { // <--- AGREGADO: Para controlar las etiquetas en mini
          maxTicksLimit: isMini ? 3 : 5,
          font: {
            size: isMini ? 8 : 12
          }
        }
      }
    }
  };

  if (loading) return isMini ? <p>Cargando...</p> : <p>Cargando ventas...</p>;
  if (error) return isMini ? <p>Error</p> : <p className={styles.errorMessage}>{error}</p>;
  if (chartData.labels.length === 0) return isMini ? <p>Sin datos</p> : <p>No hay datos de ventas para mostrar.</p>;

  return (
    // <div className={isMini ? styles.chartMini : styles.chartContainer}> <--- Asigna clase CSS según isMini
    <div className={isMini ? styles.chartMini : styles.chartContainer}>
      {isMini ? null : <h2>Ventas últimos 30 días</h2>} {/* <--- No muestra título en mini */}
      <Bar data={chartData} options={options} />
    </div>
  );
};

const GraficoProductosTop = ({ isMini = false }) => {
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [
      {
        label: 'Cantidad Vendida',
        data: [],
        backgroundColor: [
          'rgba(255, 99, 132, 0.6)', 'rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)',
          'rgba(75, 192, 192, 0.6)', 'rgba(153, 102, 255, 0.6)', 'rgba(255, 159, 64, 0.6)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }
    ]
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProductosTop = async () => {
      try {
        setLoading(true);
        setError(null);
        // Asegúrate de que este endpoint '/productos/mas-vendidos' está implementado en tu backend
        const response = await axios.get('http://localhost:8080/productos/mas-vendidos');
        const productos = response.data;

        const labels = productos.map(p => p.nombreProducto);
        const data = productos.map(p => p.cantidadVendida);

        setChartData({
          labels: labels,
          datasets: [
            {
              label: 'Cantidad Vendida',
              data: data,
              backgroundColor: [
                'rgba(255, 99, 132, 0.6)', 'rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)',
                'rgba(75, 192, 192, 0.6)', 'rgba(153, 102, 255, 0.6)', 'rgba(255, 159, 64, 0.6)'
              ],
              borderColor: [
                'rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)'
              ],
              borderWidth: 1
            }
          ]
        });
      } catch (err) {
        console.error('Error fetching top products:', err);
        setError('No se pudieron cargar los productos más vendidos.');
      } finally {
        setLoading(false);
      }
    };

    fetchProductosTop();
  }, []);

  const options = {
    responsive: true,
    maintainAspectRatio: !isMini,
    plugins: {
      legend: {
        display: !isMini, // Ocultar leyenda en versión mini
        position: 'top',
      },
      title: {
        display: !isMini, // Ocultar título en versión mini
        text: 'Productos Más Vendidos'
      },
      tooltip: {
        enabled: !isMini, // Deshabilitar tooltips en versión mini
      }
    },
    scales: {
      x: {
        display: !isMini, // Ocultar eje X en versión mini
        title: {
          display: !isMini,
          text: 'Producto'
        },
        ticks: { // Para controlar las etiquetas en mini
          maxTicksLimit: isMini ? 3 : 5,
          font: {
            size: isMini ? 8 : 12
          }
        }
      },
      y: {
        beginAtZero: true,
        display: !isMini, // Ocultar eje Y en versión mini
        title: {
          display: !isMini,
          text: 'Cantidad Vendida'
        },
        ticks: { // Para controlar las etiquetas en mini
          maxTicksLimit: isMini ? 3 : 5,
          font: {
            size: isMini ? 8 : 12
          }
        }
      }
    }
  };

  if (loading) return isMini ? <p>Cargando...</p> : <p>Cargando productos más vendidos...</p>;
  if (error) return isMini ? <p>Error</p> : <p className={styles.errorMessage}>{error}</p>;
  if (chartData.labels.length === 0) return isMini ? <p>Sin datos</p> : <p>No hay datos de productos más vendidos para mostrar.</p>;

  // Puedes cambiar el tipo de gráfico aquí, por ejemplo, a <Doughnut> o <Pie>
  return (
    <div className={isMini ? styles.chartMini : styles.chartContainer}>
      {isMini ? null : <h2>Productos Más Vendidos</h2>}
      <Bar data={chartData} options={options} /> {/* O usa <Doughnut> */}
    </div>
  );
};


export default function Home() {
  // Agrega este estado para las alertas de stock
  const [alertasStock, setAlertasStock] = useState([]);
  const [showAlertaStock, setShowAlertaStock] = useState(false);
  // =============================================
  // ESTADOS
  // =============================================
  const [activeSection, setActiveSection] = useState('Dashboard');
  //const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [expandedChartType, setExpandedChartType] = useState(null); 

  const toggleSidebar = () => {
    setSidebarCollapsed(!sidebarCollapsed);
  };

  const handleEditVentaClick = (venta) => {
  setEditingVenta({...venta});
  setShowEditVentaModal(true);
};
  // AGREGAR ESTAS DOS FUNCIONES
  const handleExpandChart = (chartType) => {
    setExpandedChartType(chartType);
  };

  const handleCloseExpandedChart = () => {
    setExpandedChartType(null);
  };
  // Estados generales
  const [user, setUser] = useState(null);
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
    fecha: "", 
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
  
  // Estados para proveedores
  const [proveedores, setProveedores] = useState([]);
  const [showProveedorModal, setShowProveedorModal] = useState(false);
  const [nuevoProveedor, setNuevoProveedor] = useState({
  nombre: '',
  contacto: '',
  telefono: '',
  email: '',
  direccion: ''
  });
  const [showEditProveedorModal, setShowEditProveedorModal] = useState(false);
  const [editingProveedor, setEditingProveedor] = useState(null);

  // Estados para ventas
  const [ventas, setVentas] = useState([]);
  const [showVentaModal, setShowVentaModal] = useState(false);
  const [nuevaVenta, setNuevaVenta] = useState({
  fecha: new Date().toISOString().split('T')[0], // Fecha actual por defecto
  clienteId: 1, // Valor por defecto, deberías obtener los clientes disponibles
  usuarioId: 1, // Valor por defecto, deberías obtener el usuario logueado
  detalles: []
  });
  const [showEditVentaModal, setShowEditVentaModal] = useState(false);
  const [editingVenta, setEditingVenta] = useState(null);
  const [detallesVenta, setDetallesVenta] = useState([]);
  const [showDetallesModal, setShowDetallesModal] = useState(false);
  const [nuevoDetalle, setNuevoDetalle] = useState({
  productoId: 1,
  cantidad: 1,
  precioUnitario: 0
  });

  // Estados para clientes
  const [clientes, setClientes] = useState([]);
  const [showClienteModal, setShowClienteModal] = useState(false);
  const [nuevoCliente, setNuevoCliente] = useState({
  nombre: '',
  email: '',
  telefono: '',
  direccion: ''
  });
  const [showEditClienteModal, setShowEditClienteModal] = useState(false);
  const [editingCliente, setEditingCliente] = useState(null);


  // Estados para auditoría
  const [auditorias, setAuditorias] = useState([]);
  const [auditoriaFiltro, setAuditoriaFiltro] = useState({
  entidad: '',
  fechaInicio: '',
  fechaFin: ''
  });  

  ///ventas graficas
  
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

  useEffect(() => {
  if (activeSection === 'Proveedores') {
    fetchProveedores();
  }
  }, [activeSection]);

  useEffect(() => {
  if (activeSection === 'Auditoria') {
    fetchAuditorias();
  }
  }, [activeSection]);

  useEffect(() => {
  if (activeSection === 'Clientes') {
    fetchClientes();
  }
  }, [activeSection]);

  useEffect(() => {
  if (activeSection === 'Ventas') {
    fetchVentas();
    fetchClientesDisponibles();
    fetchProductosDisponibles();
  }
}, [activeSection]);
  // =============================================
  // FUNCIONES GENERALES
  // =============================================

  const toggleSection = (section) => {
    setActiveSection(section);
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
// FUNCIONES PARA PROVEEDORES
// =============================================

const fetchProveedores = async () => {
  setLoading(true);
  setError(null);
  try {
    const response = await fetch('http://localhost:8080/proveedores');
    if (!response.ok) throw new Error('Error al cargar proveedores');
    const data = await response.json();
    setProveedores(Array.isArray(data) ? data : [data]);
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  } finally {
    setLoading(false);
  }
};

const handleProveedorSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch('http://localhost:8080/proveedores', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevoProveedor),
    });

    if (!response.ok) throw new Error('Error al crear proveedor');

    setShowProveedorModal(false);
    setNuevoProveedor({
      nombre: '',
      contacto: '',
      telefono: '',
      email: '',
      direccion: ''
    });
    await fetchProveedores();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleDeleteProveedor = async (id) => {
  if (window.confirm('¿Estás seguro de que deseas eliminar este proveedor?')) {
    try {
      const response = await fetch(`http://localhost:8080/proveedores/${id}`, {
        method: 'DELETE',
      });

      if (!response.ok) throw new Error('Error al eliminar proveedor');
      await fetchProveedores();
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
    }
  }
};

const handleEditProveedorClick = (proveedor) => {
  setEditingProveedor(proveedor);
  setShowEditProveedorModal(true);
};

const handleEditProveedorSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/proveedores/${editingProveedor.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editingProveedor),
    });

    if (!response.ok) throw new Error('Error al actualizar proveedor');

    setShowEditProveedorModal(false);
    setEditingProveedor(null);
    await fetchProveedores();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleProveedorInputChange = (e) => {
  const { name, value } = e.target;
  setNuevoProveedor({
    ...nuevoProveedor,
    [name]: value
  });
};

const handleEditProveedorInputChange = (e) => {
  const { name, value } = e.target;
  setEditingProveedor({
    ...editingProveedor,
    [name]: value
  });
};

// =============================================
// FUNCIONES PARA VENTAS
// =============================================

const [clientesDisponibles, setClientesDisponibles] = useState([]);
const [productosDisponibles, setProductosDisponibles] = useState([]);

const fetchClientesDisponibles = async () => {
  try {
    const response = await fetch('http://localhost:8080/clientes');
    const data = await response.json();
    setClientesDisponibles(Array.isArray(data) ? data : [data]);
  } catch (err) {
    console.error('Error fetching clientes:', err);
  }
};

const fetchProductosDisponibles = async () => {
  try {
    const response = await fetch('http://localhost:8080/productos');
    const data = await response.json();
    setProductosDisponibles(Array.isArray(data) ? data : [data]);
  } catch (err) {
    console.error('Error fetching productos:', err);
  }
};

const fetchVentas = async () => {
  setLoading(true);
  setError(null);
  try {
    const response = await fetch('http://localhost:8080/ventas');
    if (!response.ok) throw new Error('Error al cargar ventas');
    const ventasData = await response.json();
    
    // Obtener clientes y usuarios para mapear los nombres
    const clientesResponse = await fetch('http://localhost:8080/clientes');
    const clientesData = await clientesResponse.json();
    const usuariosResponse = await fetch('http://localhost:8080/usuarios');
    const usuariosData = await usuariosResponse.json();
    
    // Mapear las ventas con los nombres correspondientes
    const ventasConNombres = ventasData.map(venta => {
      const cliente = Array.isArray(clientesData) 
        ? clientesData.find(c => c.id === venta.clienteId)
        : clientesData.id === venta.clienteId ? clientesData : null;
      
      const usuario = Array.isArray(usuariosData)
        ? usuariosData.find(u => u.id === venta.usuarioId)
        : usuariosData.id === venta.usuarioId ? usuariosData : null;
      
      return {
        ...venta,
        clienteNombre: cliente ? cliente.nombre : 'Desconocido',
        usuarioNombre: usuario ? usuario.nombre : 'Desconocido'
      };
    });
    
    setVentas(ventasConNombres);
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  } finally {
    setLoading(false);
  }
};

const handleVentaInputChange = (e) => {
  const { name, value } = e.target;
  setNuevaVenta({
    ...nuevaVenta,
    [name]: value
  });
};

const handleEditVentaInputChange = (e) => {
  const { name, value } = e.target;
  setEditingVenta({
    ...editingVenta,
    [name]: value
  });
};

const fetchDetallesVenta = async (ventaId) => {
  try {
    const response = await fetch(`http://localhost:8080/ventas/${ventaId}/detalles`);
    if (!response.ok) throw new Error('Error al cargar detalles');
    const data = await response.json();
    setDetallesVenta(data);
    setShowDetallesModal(true);
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleAddDetalle = () => {
  setNuevaVenta({
    ...nuevaVenta,
    detalles: [...nuevaVenta.detalles, nuevoDetalle]
  });
  setNuevoDetalle({
    productoId: 1,
    cantidad: 1,
    precioUnitario: 0
  });
};

const handleDetalleInputChange = (e) => {
  const { name, value } = e.target;
  
  // Si cambia el producto, actualizar el precio automáticamente
  if (name === 'productoId') {
    const productoSeleccionado = productosDisponibles.find(p => p.id === parseInt(value));
    setNuevoDetalle({
      ...nuevoDetalle,
      productoId: parseInt(value),
      precioUnitario: productoSeleccionado ? productoSeleccionado.precio : 0
    });
  } else {
    setNuevoDetalle({
      ...nuevoDetalle,
      [name]: name === 'cantidad' ? parseInt(value) : parseFloat(value)
    });
  }
};

const generarVentasDePrueba = async () => {
  if (!window.confirm('¿Desea generar ventas de prueba? Esto creará 2 ventas.')) {
    return;
  }

  try {
    setLoading(true);
    
    // ventas de prueba
    const venta1 = {
      fecha: new Date().toISOString().split('T')[0],
      clienteId: 1,
      usuarioId: 1,
      detalles: [
        { productoId: 1, cantidad: 2, precioUnitario: 2000 },
        { productoId: 2, cantidad: 1, precioUnitario: 5000 },
        { productoId: 3, cantidad: 3, precioUnitario: 3500 }
      ]
    };
    const venta2 = {
      fecha: new Date(Date.now() - 86400000).toISOString().split('T')[0], // Ayer
      clienteId: 2,
      usuarioId: 1,
      detalles: [
        { productoId: 2, cantidad: 2, precioUnitario: 1500 },
        { productoId: 4, cantidad: 1, precioUnitario: 3000 },
        { productoId: 1, cantidad: 2, precioUnitario: 5500 },
        { productoId: 1, cantidad: 2, precioUnitario: 3200 }
      ]
    };
    const venta3 = {
      fecha: new Date(Date.now() - 86400000).toISOString().split('T')[0], // Ayer
      clienteId: 2,
      usuarioId: 1,
      detalles: [
        { productoId: 2, cantidad: 2, precioUnitario: 4500 },
        { productoId: 4, cantidad: 1, precioUnitario: 6000 }
      ]
    };
  
    // Crear las ventas
    await Promise.all([
      crearVentaConDetalles(venta1),
      crearVentaConDetalles(venta2),
      crearVentaConDetalles(venta3)
    ]);

    // Actualizar la lista de ventas
    await fetchVentas();
    
    alert('Ventas de prueba creadas exitosamente!');
  } catch (error) {
    console.error('Error al crear ventas de prueba:', error);
    setError('Error al crear ventas de prueba: ' + error.message);
  } finally {
    setLoading(false);
  }
};

const handleVentaSubmit = async (e) => {
  e.preventDefault();
  try {
    // Validar que haya al menos un detalle
    if (nuevaVenta.detalles.length === 0) {
      alert('Debe agregar al menos un detalle a la venta');
      return;
    }

    // Crear la venta principal
    const ventaResponse = await fetch('http://localhost:8080/ventas', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        fecha: nuevaVenta.fecha,
        clienteId: nuevaVenta.clienteId,
        usuarioId: nuevaVenta.usuarioId
      })
    });

    if (!ventaResponse.ok) throw new Error('Error al crear venta');

    const createdVenta = await ventaResponse.json();

    // Crear los detalles de la venta
    for (const detalle of nuevaVenta.detalles) {
      const detalleResponse = await fetch(`http://localhost:8080/ventas/${createdVenta.id}/detalles`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(detalle)
      });

      if (!detalleResponse.ok) throw new Error('Error al crear detalle de venta');
    }

    // Limpiar el formulario y actualizar la lista
    setNuevaVenta({
      fecha: new Date().toISOString().split('T')[0],
      clienteId: 1,
      usuarioId: 1,
      detalles: []
    });
    setNuevoDetalle({
      productoId: 1,
      cantidad: 1,
      precioUnitario: 0
    });
    setShowVentaModal(false);
    await fetchVentas();
    alert('Venta creada exitosamente!');

  } catch (err) {
    console.error('Error al crear venta:', err);
    setError(err.message);
    alert('Error al crear venta: ' + err.message);
  }
};

const handleRemoveDetalle = (indexToRemove) => {
  setNuevaVenta(prev => ({
    ...prev,
    detalles: prev.detalles.filter((_, index) => index !== indexToRemove)
  }));
};

const handleDeleteVenta = async (id) => {
  if (window.confirm('¿Estás seguro de eliminar esta venta y todos sus detalles?')) {
    try {
      // Primero eliminar los detalles
      const detallesResponse = await fetch(`http://localhost:8080/ventas/${id}/detalles`);
      if (detallesResponse.ok) {
        const detalles = await detallesResponse.json();
        for (const detalle of detalles) {
          await fetch(`http://localhost:8080/ventas/${id}/detalles/${detalle.id}`, {
            method: 'DELETE'
          });
        }
      }
      
      // Luego eliminar la venta
      const response = await fetch(`http://localhost:8080/ventas/${id}`, {
        method: 'DELETE'
      });

      if (!response.ok) throw new Error('Error al eliminar venta');
      
      // Actualizar lista
      await fetchVentas();
      alert('Venta eliminada correctamente');
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
      alert('Error al eliminar venta: ' + err.message);
    }
  }
};
const handleEditVentaSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/ventas/${editingVenta.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        fecha: editingVenta.fecha,
        clienteId: editingVenta.clienteId,
        usuarioId: editingVenta.usuarioId
      })
    });

    if (!response.ok) throw new Error('Error al actualizar venta');

    setShowEditVentaModal(false);
    setEditingVenta(null);
    await fetchVentas();
    alert('Venta actualizada correctamente');
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
    alert('Error al actualizar venta: ' + err.message);
  }
};
const crearVentaConDetalles = async (venta) => {
  try {
    // Primero creamos la venta
    const ventaResponse = await fetch('http://localhost:8080/ventas', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        fecha: venta.fecha,
        clienteId: venta.clienteId,
        usuarioId: venta.usuarioId
      })
    });

    if (!ventaResponse.ok) throw new Error('Error al crear venta');

    const createdVenta = await ventaResponse.json();
    
    // Luego agregamos los detalles
    for (const detalle of venta.detalles) {
      const detalleResponse = await fetch(`http://localhost:8080/ventas/${createdVenta.id}/detalles`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(detalle)
      });

      if (!detalleResponse.ok) throw new Error('Error al crear detalle de venta');
    }

    return createdVenta;
  } catch (err) {
    console.error('Error en crearVentaConDetalles:', err);
    throw err;
  }
};
// =============================================
// FUNCIONES PARA CLIENTES
// =============================================

const fetchClientes = async () => {
  setLoading(true);
  setError(null);
  try {
    const response = await fetch('http://localhost:8080/clientes');
    if (!response.ok) throw new Error('Error al cargar clientes');
    const data = await response.json();
    setClientes(Array.isArray(data) ? data : [data]);
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  } finally {
    setLoading(false);
  }
};

const handleClienteSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch('http://localhost:8080/clientes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevoCliente),
    });

    if (!response.ok) throw new Error('Error al crear cliente');

    setShowClienteModal(false);
    setNuevoCliente({
      nombre: '',
      email: '',
      telefono: '',
      direccion: ''
    });
    await fetchClientes();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleDeleteCliente = async (id) => {
  if (window.confirm('¿Estás seguro de que deseas eliminar este cliente?')) {
    try {
      const response = await fetch(`http://localhost:8080/clientes/${id}`, {
        method: 'DELETE',
      });

      if (!response.ok) throw new Error('Error al eliminar cliente');
      await fetchClientes();
    } catch (err) {
      setError(err.message);
      console.error('Error:', err);
    }
  }
};

const handleEditClienteClick = (cliente) => {
  setEditingCliente(cliente);
  setShowEditClienteModal(true);
};

const handleEditClienteSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/clientes/${editingCliente.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editingCliente),
    });

    if (!response.ok) throw new Error('Error al actualizar cliente');

    setShowEditClienteModal(false);
    setEditingCliente(null);
    await fetchClientes();
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  }
};

const handleClienteInputChange = (e) => {
  const { name, value } = e.target;
  setNuevoCliente({
    ...nuevoCliente,
    [name]: value
  });
};

const handleEditClienteInputChange = (e) => {
  const { name, value } = e.target;
  setEditingCliente({
    ...editingCliente,
    [name]: value
  });
};

// =============================================
// FUNCIONES PARA Auditorias
// =============================================
const fetchAuditorias = async () => {
  setLoading(true);
  setError(null);
  try {
    // Construye la URL con los filtros si existen
    let url = 'http://localhost:8080/auditoria';
    const params = new URLSearchParams();
    
    if (auditoriaFiltro.entidad) params.append('entidad', auditoriaFiltro.entidad);
    if (auditoriaFiltro.fechaInicio) params.append('fechaInicio', auditoriaFiltro.fechaInicio);
    if (auditoriaFiltro.fechaFin) params.append('fechaFin', auditoriaFiltro.fechaFin);
    
    if (params.toString()) url += `?${params.toString()}`;
    
    const response = await fetch(url);
    if (!response.ok) throw new Error('Error al cargar auditorías');
    const data = await response.json();
    setAuditorias(Array.isArray(data) ? data : [data]);
  } catch (err) {
    setError(err.message);
    console.error('Error:', err);
  } finally {
    setLoading(false);
  }
};
const handleAuditoriaFiltroChange = (e) => {
  const { name, value } = e.target;
  setAuditoriaFiltro({
    ...auditoriaFiltro,
    [name]: value
  });
};

// Agrega este efecto para cargar las alertas
useEffect(() => {
  if (activeSection === 'Dashboard') {
    fetchAlertasStock();
  }
}, [activeSection]);

// Función para obtener productos con stock bajo
const fetchAlertasStock = async () => {
  try {
    const response = await axios.get('http://localhost:8080/productos/bajo-stock');
    setAlertasStock(response.data);
  } catch (err) {
    console.error('Error fetching alertas de stock:', err);
  }
};

// Componente para la tarjeta de alertas de stock
const AlertaStockCard = ({ isMini = false }) => {
  if (isMini) {
    return (
      <div className={styles.alertBadge}>
        {alertasStock.length > 0 && (
          <span className={styles.alertCount}>{alertasStock.length}</span>
        )}
      </div>
    );
  }
  return (
    <div className={styles.alertTable}>
      <h3>Productos con stock bajo</h3>
      {alertasStock.length === 0 ? (
        <p>No hay productos con stock bajo</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Producto</th>
              <th>Stock</th>
            </tr>
          </thead>
          <tbody>
            {alertasStock.map(producto => (
              <tr key={producto.id}>
                <td>{producto.nombre}</td>
                <td className={styles.lowStock}>{producto.stock} unidades</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
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
     
        <div className={styles.userWelcome}>{!sidebarCollapsed && (<h2>Bienvenido/a {user ? user.nombre : 'Usuario/a'}{user?.rol && <span className={styles.userRole}>({user.rol})</span>}</h2>)}</div>
        <nav className={styles.sidebarNav}>
          <button onClick={() => toggleSection('Dashboard')}className={activeSection === 'Dashboard' ? styles.activeButton : ''}title="Dashboard"><FaTh />{!sidebarCollapsed && <span>Dashboard</span>}</button>
          <button onClick={() => toggleSection('Auditoria')}className={activeSection === 'Auditoria' ? styles.activeButton : ''}title="Auditoria"><FaChartLine />{!sidebarCollapsed && <span>Auditoria</span>}</button>
          <button onClick={() => toggleSection('Usuarios')}className={activeSection === 'Usuarios' ? styles.activeButton : ''}title="Usuarios"><FaUsers />{!sidebarCollapsed && <span>Usuarios</span>}</button>
          <button onClick={() => toggleSection('Productos')}className={activeSection === 'Productos' ? styles.activeButton : ''}title="Productos"><FaBoxes />{!sidebarCollapsed && <span>Productos</span>}</button>
          <button onClick={() => toggleSection('Ventas')}className={activeSection === 'Ventas' ? styles.activeButton : ''}title="Ventas"><FaShoppingCart />{!sidebarCollapsed && <span>Ventas</span>}</button>
          <button onClick={() => toggleSection('Clientes')}className={activeSection === 'Clientes' ? styles.activeButton : ''}title="Clientes"><FaUsers />{!sidebarCollapsed && <span>Clientes</span>}</button>
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
              {/* Tarjeta para Gráfico de Ventas */}
              <div className={styles.statCard} onClick={() => handleExpandChart('ventas')}> {/* <--- AGREGADO onClick */}
                <h3>Gráfico Ventas</h3>
                <p>Últimos 30 días</p>
                <div className={styles.miniChartWrapper}> {/* <--- AGREGADO: Contenedor para el mini gráfico */}
                  <GraficoVentas isMini={true} /> {/* <--- MODIFICADO: Renderiza la versión mini */}
                </div>
              </div>

              {/* Tarjeta para Productos Top */}
              <div className={styles.statCard} onClick={() => handleExpandChart('productosTop')}> {/* <--- AGREGADO onClick */}
                <h3>Productos Top</h3>
                <p>Más vendidos</p>
                <div className={styles.miniChartWrapper}> {/* <--- AGREGADO: Contenedor para el mini gráfico */}
                  <GraficoProductosTop isMini={true} /> {/* <--- AGREGADO: Renderiza la versión mini */}
                </div>
              </div>
              {/* Tarjeta para Alertas de Stock */}
      <div className={styles.statCard} onClick={() => handleExpandChart('alertasStock')}>
        <h3>Alertas de Stock</h3>
        <p>Productos con bajo inventario</p>
        <div className={styles.miniChartWrapper}>
          <AlertaStockCard isMini={true} />
        </div>
      </div>
               {/* Modal/Overlay para el gráfico expandido */}
        {/* AGREGAR ESTE BLOQUE CONDICIONAL PARA EL MODAL */}
        {expandedChartType && (
          <ChartModal onClose={handleCloseExpandedChart}>
            {expandedChartType === 'ventas' && <GraficoVentas isMini={false} />}
            {expandedChartType === 'productosTop' && <GraficoProductosTop isMini={false} />}
            {expandedChartType === 'alertasStock' && <AlertaStockCard isMini={false} />}

          </ChartModal>
        )}

              {/* Otras tarjetas del dashboard se mantienen */}
              <div className={styles.statCard}>
                <h3>Últimas Órdenes</h3>
                <p>Tablas</p>
              </div>
              <div className={styles.statCard}>
                <h3>Alertas Pendientes</h3>
                <p>Tablas</p>
              </div>
            </div>
          </div>
        )}

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
                        <input type="date" name="fecha" value={nuevoProducto.fecha}onChange={handleInputChange}min="2025-12-10" required/>
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
     {activeSection === 'Proveedores' && (
  <div className={styles.proveedoresContainer}>
    <div className={styles.proveedoresHeader}>
      <h1><FaClipboardCheck /> Proveedores</h1>
      <button onClick={() => setShowProveedorModal(true)} className={styles.nuevoProveedorBtn}><FaPlus /> NUEVO PROVEEDOR</button>
    </div>
    <div className={styles.proveedoresToolbar}>
      <div className={styles.showEntries}>
        <span>Mostrar</span>
        <select value={itemsPerPage} onChange={(e) => setItemsPerPage(Number(e.target.value))}>
          <option value={5}>5</option>
          <option value={10}>10</option>
          <option value={20}>20</option>
        </select>
        <span>Entradas</span>
      </div>
      <div className={styles.searchBox}><FaSearch /><input type="text" placeholder="Buscar..." /></div>
    </div>
    <div className={styles.proveedoresTableContainer}>
      <h2>LISTA DE PROVEEDORES</h2>
      {loading ? (
        <p>Cargando proveedores...</p>
      ) : error ? (
        <div className={styles.emptyState}>
          <p>Error al cargar proveedores: {error}</p>
          <button onClick={fetchProveedores} className={styles.nuevoProveedorBtn}>
            Reintentar
          </button>
        </div>
      ) : proveedores.length === 0 ? (
        <div className={styles.emptyState}>
          <p>No hay proveedores registrados</p>
        </div>
      ) : (
        <div className={styles.tableWrapper}>
          <table className={styles.proveedoresTable}>
            <thead>
              <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Contacto</th>
                <th>Teléfono</th>
                <th>Email</th>
                <th>Dirección</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {proveedores.slice(0, itemsPerPage).map((proveedor, index) => (
                <tr key={proveedor.id}>
                  <td>{index + 1}</td>
                  <td>{proveedor.nombre}</td>
                  <td>{proveedor.contacto}</td>
                  <td>{proveedor.telefono}</td>
                  <td>{proveedor.email}</td>
                  <td>{proveedor.direccion}</td>
                  <td>
                    <div className={styles.actions}>
                      <button className={`${styles.actionBtn} ${styles.editBtn}`} title="Editar" onClick={() => handleEditProveedorClick(proveedor)}><FaEdit /></button>
                      <button className={`${styles.actionBtn} ${styles.deleteBtn}`} title="Eliminar" onClick={() => handleDeleteProveedor(proveedor.id)}><FaTrash /></button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>

    {/* Modal Nuevo Proveedor */}
    {showProveedorModal && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Nuevo Proveedor</h2>
          <form onSubmit={handleProveedorSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información del Proveedor</legend>
              <div className={styles.formGroup}>
                <label>Nombre:</label>
                <input type="text" name="nombre" value={nuevoProveedor.nombre} onChange={handleProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Contacto:</label>
                <input type="text" name="contacto" value={nuevoProveedor.contacto} onChange={handleProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Teléfono:</label>
                <input type="text" name="telefono" value={nuevoProveedor.telefono} onChange={handleProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Email:</label>
                <input type="email" name="email" value={nuevoProveedor.email} onChange={handleProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Dirección:</label>
                <textarea name="direccion" value={nuevoProveedor.direccion} onChange={handleProveedorInputChange} required />
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowProveedorModal(false)} className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Proveedor
              </button>
            </div>
          </form>
        </div>
      </div>
    )}

    {/* Modal Editar Proveedor */}
    {showEditProveedorModal && editingProveedor && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Editar Proveedor</h2>
          <form onSubmit={handleEditProveedorSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información del Proveedor</legend>
              <div className={styles.formGroup}>
                <label>Nombre:</label>
                <input type="text" name="nombre" value={editingProveedor.nombre} onChange={handleEditProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Contacto:</label>
                <input type="text" name="contacto" value={editingProveedor.contacto} onChange={handleEditProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Teléfono:</label>
                <input type="text" name="telefono" value={editingProveedor.telefono} onChange={handleEditProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Email:</label>
                <input type="email" name="email" value={editingProveedor.email} onChange={handleEditProveedorInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Dirección:</label>
                <textarea name="direccion" value={editingProveedor.direccion} onChange={handleEditProveedorInputChange} required />
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowEditProveedorModal(false)} className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Cambios
              </button>
            </div>
          </form>
        </div>
      </div>
    )}
  </div>)}

  {activeSection === 'Ventas' && (
  <div className={styles.ventasContainer}>
    <div className={styles.ventasHeader}>
      <h1><FaShoppingCart /> Ventas</h1>
      <button onClick={() => setShowVentaModal(true)} className={styles.nuevoVentaBtn}><FaPlus /> NUEVA VENTA</button>
    </div>
    <div className={styles.ventasToolbar}>
      <div className={styles.showEntries}>
        <span>Mostrar</span>
        <select value={itemsPerPage} onChange={(e) => setItemsPerPage(Number(e.target.value))}>
          <option value={5}>5</option>
          <option value={10}>10</option>
          <option value={20}>20</option>
        </select>
        <span>Entradas</span>
      </div>
      <div className={styles.searchBox}><FaSearch /><input type="text" placeholder="Buscar..." /></div>
    </div>
    <div className={styles.ventasTableContainer}>
      <h2>LISTA DE VENTAS</h2>
      {loading ? (
        <p>Cargando ventas...</p>
      ) : error ? (
        <div className={styles.emptyState}>
          <p>Error al cargar ventas: {error}</p>
          <button onClick={fetchVentas} className={styles.nuevoVentaBtn}>
            Reintentar
          </button>
        </div>
      ) : ventas.length === 0 ? (
        <div className={styles.emptyState}>
          <p>No hay ventas registradas</p>
        </div>
      ) : (
        <div className={styles.tableWrapper}>
          <table className={styles.ventasTable}>
  <thead>
    <tr>
      <th>#</th>
      <th>Fecha</th>
      <th>Cliente</th>
      <th>Usuario</th>
      <th>Total</th>
      <th>Acciones</th>
    </tr>
  </thead>
  <tbody>
    {ventas.slice(0, itemsPerPage).map((venta, index) => (
      <tr key={venta.id}>
        <td>{index + 1}</td>
        <td>{new Date(venta.fecha).toLocaleDateString('es-ES')}</td>
        <td>{venta.clienteNombre}</td>
        <td>{venta.usuarioNombre}</td>
        <td>${venta.total?.toLocaleString('es-ES', {
          minimumFractionDigits: 2, 
          maximumFractionDigits: 2
        }) || '0.00'}</td>
        <td>
          <div className={styles.actions}>
            <button className={`${styles.actionBtn} ${styles.infoBtn}`} 
                    title="Ver detalles" 
                    onClick={() => fetchDetallesVenta(venta.id)}>
              <FaSearch />
            </button>
            <button className={`${styles.actionBtn} ${styles.editBtn}`} 
                    title="Editar" 
                    onClick={() => handleEditVentaClick(venta)}>
              <FaEdit />
            </button>
            <button className={`${styles.actionBtn} ${styles.deleteBtn}`} 
                    title="Eliminar" 
                    onClick={() => handleDeleteVenta(venta.id)}>
              <FaTrash />
            </button>
          </div>
        </td>
      </tr>
    ))}
  </tbody>
</table>
        </div>
      )}
    </div>
{activeSection === 'Ventas' && (
  <div className={styles.ventasContainer}>
    <div className={styles.ventasHeader}>
      <h1><FaShoppingCart /> Ventas</h1>
      <div className={styles.ventasHeaderButtons}>
        <button onClick={() => setShowVentaModal(true)} className={styles.nuevoVentaBtn}><FaPlus /> NUEVA VENTA</button>
        <button onClick={generarVentasDePrueba} className={styles.ventasPruebaBtn}><FaPlus /> GENERAR VENTAS DE PRUEBA</button>
      </div>
    </div>
    {/* ... resto del código ... */}
  </div>
)}

{showVentaModal && (
  <div className={styles.modalOverlay}>
    <div className={styles.modalContent}>
      <h2>Nueva Venta</h2>
      <form onSubmit={handleVentaSubmit} className={styles.modalForm}>
        <fieldset className={styles.formFieldset}>
          <legend className={styles.formLegend}>Información de la Venta</legend>
          <div className={styles.formGroup}>
            <label>Fecha:</label>
            <input type="date" name="fecha" value={nuevaVenta.fecha} onChange={handleVentaInputChange} required />
          </div>
          <div className={styles.formGroup}>
            <label>Cliente:</label>
            <select name="clienteId" value={nuevaVenta.clienteId} onChange={handleVentaInputChange} required>
              <option value="">Seleccione un cliente</option>
              {clientesDisponibles.map(cliente => (
                <option key={cliente.id} value={cliente.id}>
                  {cliente.nombre} (ID: {cliente.id})
                </option>
              ))}
            </select>
          </div>
          <div className={styles.formGroup}>
            <label>Usuario:</label>
            <input type="number" name="usuarioId" value={nuevaVenta.usuarioId} onChange={handleVentaInputChange} min="1" required />
          </div>
        </fieldset>

        <fieldset className={styles.formFieldset}>
          <legend className={styles.formLegend}>Detalles de la Venta</legend>
          <div className={styles.formGroup}>
            <label>Producto:</label>
            <select name="productoId" value={nuevoDetalle.productoId} onChange={handleDetalleInputChange} required>
              <option value="">Seleccione un producto</option>
              {productosDisponibles.map(producto => (
                <option key={producto.id} value={producto.id}>
                  {producto.nombre} - ${producto.precio.toFixed(2)}
                </option>
              ))}
            </select>
          </div>
          <div className={styles.formGroup}>
            <label>Cantidad:</label>
            <input 
              type="number" 
              name="cantidad" 
              value={nuevoDetalle.cantidad} 
              onChange={handleDetalleInputChange} 
              min="1" 
              required 
            />
          </div>
          <div className={styles.formGroup}>
            <label>Precio Unitario:</label>
            <input 
              type="number" 
              name="precioUnitario" 
              value={nuevoDetalle.precioUnitario} 
              onChange={handleDetalleInputChange} 
              step="0.01" 
              min="0" 
              required 
              readOnly // Hacer el campo de solo lectura ya que se autocompleta
            />
          </div>
          <button type="button" onClick={handleAddDetalle} className={styles.addButton}>
            Agregar Detalle
          </button>
          
          {nuevaVenta.detalles.length > 0 && (
            <div className={styles.detallesList}>
              <h4>Detalles agregados:</h4>
              <ul>
                {nuevaVenta.detalles.map((detalle, index) => {
                  const producto = productosDisponibles.find(p => p.id === detalle.productoId);
                  return (
                    <li key={index} className={styles.detalleItem}>
            <div>
              <span>Producto: {producto ? producto.nombre : detalle.productoId}, </span>
              <span>Cantidad: {detalle.cantidad}, </span>
              <span>Precio: ${detalle.precioUnitario.toFixed(2)}, </span>
              <span>Subtotal: ${(detalle.cantidad * detalle.precioUnitario).toFixed(2)}</span>
            </div>
            <button 
              type="button" 
              onClick={() => handleRemoveDetalle(index)}
              className={styles.deleteDetalleBtn}
            >
              <FaTrash /> Eliminar
            </button>
          </li>
        );
      })}
    </ul>
              <div className={styles.totalVenta}>
                <strong>Total: ${
                  nuevaVenta.detalles.reduce((sum, detalle) => sum + (detalle.cantidad * detalle.precioUnitario), 0).toFixed(2)
                }</strong>
              </div>
            </div>
          )}
        </fieldset>

        <div className={styles.modalButtons}>
          <button type="button" onClick={() => setShowVentaModal(false)} className={styles.cancelButton}>
            Cancelar
          </button>
          <button type="submit" className={styles.submitButton} disabled={nuevaVenta.detalles.length === 0}>
  Guardar Venta
</button>
        </div>
      </form>
    </div>
  </div>
)}
    {/* Modal Editar Venta */}
    {showEditVentaModal && editingVenta && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Editar Venta</h2>
          <form onSubmit={handleEditVentaSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información de la Venta</legend>
              <div className={styles.formGroup}>
                <label>Fecha:</label>
                <input type="date" name="fecha" value={editingVenta.fecha} onChange={handleEditVentaInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Cliente ID:</label>
                <input type="number" name="clienteId" value={editingVenta.clienteId} onChange={handleEditVentaInputChange} min="1" required />
              </div>
              <div className={styles.formGroup}>
                <label>Usuario ID:</label>
                <input type="number" name="usuarioId" value={editingVenta.usuarioId} onChange={handleEditVentaInputChange} min="1" required />
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowEditVentaModal(false)} className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Cambios
              </button>
            </div>
          </form>
        </div>
      </div>
    )}

    {/* Modal Detalles de Venta */}
    {showDetallesModal && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Detalles de Venta</h2>
          <div className={styles.detallesContainer}>
            {detallesVenta.length === 0 ? (
              <p>No hay detalles para esta venta</p>
            ) : (
              <table className={styles.detallesTable}>
                <thead>
                  <tr>
                    <th>Producto ID</th>
                    <th>Cantidad</th>
                    <th>Precio Unitario</th>
                    <th>Subtotal</th>
                  </tr>
                </thead>
                <tbody>
                  {detallesVenta.map((detalle, index) => (
                    <tr key={index}>
                      <td>{detalle.productoId}</td>
                      <td>{detalle.cantidad}</td>
                      <td>${detalle.precioUnitario?.toLocaleString('es-ES', {minimumFractionDigits: 2, maximumFractionDigits: 2}) || '0.00'}</td>
                      <td>${(detalle.cantidad * detalle.precioUnitario)?.toLocaleString('es-ES', {minimumFractionDigits: 2, maximumFractionDigits: 2}) || '0.00'}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
          <div className={styles.modalButtons}>
            <button type="button" onClick={() => setShowDetallesModal(false)} className={styles.cancelButton}>
              Cerrar
            </button>
          </div>
        </div>
      </div>
    )}
  </div>
)}
   {activeSection === 'Clientes' && (
  <div className={styles.clientesContainer}>
    <div className={styles.clientesHeader}>
      <h1><FaUsers /> Clientes</h1>
      <button onClick={() => setShowClienteModal(true)} className={styles.nuevoClienteBtn}><FaPlus /> NUEVO CLIENTE</button>
    </div>
    <div className={styles.clientesToolbar}>
      <div className={styles.showEntries}>
        <span>Mostrar</span>
        <select value={itemsPerPage} onChange={(e) => setItemsPerPage(Number(e.target.value))}>
          <option value={5}>5</option>
          <option value={10}>10</option>
          <option value={20}>20</option>
        </select>
        <span>Entradas</span>
      </div>
      <div className={styles.searchBox}><FaSearch /><input type="text" placeholder="Buscar..." /></div>
    </div>
    <div className={styles.clientesTableContainer}>
      <h2>LISTA DE CLIENTES</h2>
      {loading ? (
        <p>Cargando clientes...</p>
      ) : error ? (
        <div className={styles.emptyState}>
          <p>Error al cargar clientes: {error}</p>
          <button onClick={fetchClientes} className={styles.nuevoClienteBtn}>
            Reintentar
          </button>
        </div>
      ) : clientes.length === 0 ? (
        <div className={styles.emptyState}>
          <p>No hay clientes registrados</p>
        </div>
      ) : (
        <div className={styles.tableWrapper}>
          <table className={styles.clientesTable}>
            <thead>
              <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Teléfono</th>
                <th>Dirección</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {clientes.slice(0, itemsPerPage).map((cliente, index) => (
                <tr key={cliente.id}>
                  <td>{index + 1}</td>
                  <td>{cliente.nombre}</td>
                  <td>{cliente.email}</td>
                  <td>{cliente.telefono}</td>
                  <td>{cliente.direccion}</td>
                  <td>
                    <div className={styles.actions}>
                      <button className={`${styles.actionBtn} ${styles.editBtn}`} title="Editar" onClick={() => handleEditClienteClick(cliente)}>
                        <FaEdit />
                      </button>
                      <button className={`${styles.actionBtn} ${styles.deleteBtn}`} title="Eliminar" onClick={() => handleDeleteCliente(cliente.id)}>
                        <FaTrash />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>

    {/* Modal Nuevo Cliente */}
    {showClienteModal && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Nuevo Cliente</h2>
          <form onSubmit={handleClienteSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información del Cliente</legend>
              <div className={styles.formGroup}>
                <label>Nombre:</label>
                <input type="text" name="nombre" value={nuevoCliente.nombre} onChange={handleClienteInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Email:</label>
                <input type="email" name="email" value={nuevoCliente.email} onChange={handleClienteInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Teléfono:</label>
                <input type="text" name="telefono" value={nuevoCliente.telefono} onChange={handleClienteInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Dirección:</label>
                <textarea name="direccion" value={nuevoCliente.direccion} onChange={handleClienteInputChange} required />
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowClienteModal(false)} className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Cliente
              </button>
            </div>
          </form>
        </div>
      </div>
    )}

    {/* Modal Editar Cliente */}
    {showEditClienteModal && editingCliente && (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <h2>Editar Cliente</h2>
          <form onSubmit={handleEditClienteSubmit} className={styles.modalForm}>
            <fieldset className={styles.formFieldset}>
              <legend className={styles.formLegend}>Información del Cliente</legend>
              <div className={styles.formGroup}>
                <label>Nombre:</label>
                <input type="text" name="nombre" value={editingCliente.nombre} onChange={handleEditClienteInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Email:</label>
                <input type="email" name="email" value={editingCliente.email} onChange={handleEditClienteInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Teléfono:</label>
                <input type="text" name="telefono" value={editingCliente.telefono} onChange={handleEditClienteInputChange} required />
              </div>
              <div className={styles.formGroup}>
                <label>Dirección:</label>
                <textarea name="direccion" value={editingCliente.direccion} onChange={handleEditClienteInputChange} required />
              </div>
            </fieldset>

            <div className={styles.modalButtons}>
              <button type="button" onClick={() => setShowEditClienteModal(false)} className={styles.cancelButton}>
                Cancelar
              </button>
              <button type="submit" className={styles.submitButton}>
                Guardar Cambios
              </button>
            </div>
          </form>
        </div>
      </div>
    )}
  </div>
)}
{activeSection === 'Auditoria' && (
  <div className={styles.auditoriaContainer}>
    <div className={styles.auditoriaHeader}>
      <h1><FaChartLine /> Auditoría</h1>
    </div>
    
    {/* Filtros */}
    <div className={styles.auditoriaFiltros}>
      <div className={styles.formGroup}>
        <label>Entidad:</label>
        <select name="entidad" value={auditoriaFiltro.entidad} onChange={handleAuditoriaFiltroChange}>
          <option value="">Todas</option>
          <option value="Producto">Productos</option>
          <option value="Usuario">Usuarios</option>
          <option value="Proveedor">Proveedores</option>
          <option value="Venta">Ventas</option>
          <option value="Cliente">Clientes</option>
        </select>
      </div>
      
      <div className={styles.formGroup}>
        <label>Fecha Desde:</label>
        <input type="date" name="fechaInicio" value={auditoriaFiltro.fechaInicio} onChange={handleAuditoriaFiltroChange} />
      </div>
      
      <div className={styles.formGroup}>
        <label>Fecha Hasta:</label>
        <input type="date" name="fechaFin" value={auditoriaFiltro.fechaFin} onChange={handleAuditoriaFiltroChange} />
      </div>
      
      <button onClick={fetchAuditorias} className={styles.filterButton}>
        <FaSearch /> Filtrar
      </button>
    </div>
    
    <div className={styles.auditoriaTableContainer}>
      <h2>REGISTROS DE AUDITORÍA</h2>
      {loading ? (
        <p>Cargando registros de auditoría...</p>
      ) : error ? (
        <div className={styles.emptyState}>
          <p>Error al cargar auditoría: {error}</p>
          <button onClick={fetchAuditorias} className={styles.nuevoProductoBtn}>
            Reintentar
          </button>
        </div>
      ) : auditorias.length === 0 ? (
        <div className={styles.emptyState}>
          <p>No hay registros de auditoría</p>
        </div>
      ) : (
        <div className={styles.tableWrapper}>
          <table className={styles.auditoriaTable}>
            <thead>
              <tr>
                <th>#</th>
                <th>Fecha</th>
                <th>Acción</th>
                <th>Entidad</th>
                <th>ID Entidad</th>
                <th>Detalle</th>
              </tr>
            </thead>
            <tbody>
              {auditorias.slice(0, itemsPerPage).map((auditoria, index) => (
                <tr key={auditoria.id}>
                  <td>{index + 1}</td>
                  <td>{new Date(auditoria.fecha).toLocaleString('es-ES')}</td>
                  <td>{auditoria.accion}</td>
                  <td>{auditoria.entidadAfectada}</td>
                  <td>{auditoria.idEntidad}</td>
                  <td>{auditoria.detalle}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  </div>
)}
     </main>
  </div>);
}