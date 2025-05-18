const Sidebar = ({ sidebarCollapsed, toggleSidebar, activeSection, toggleSection, userName, handleLogout }) => (
  <aside className={styles.sidebar}>
    <div className={styles.sidebarHeader}>
      <div className={styles.logo}>{!sidebarCollapsed && <h1>Kompta</h1>}</div>
      <button onClick={toggleSidebar} className={styles.menuToggle}>
        <FaBars />
      </button>
    </div>

    <div className={styles.userWelcome}>
      {!sidebarCollapsed && <h2>Bienvenido/a {userName}</h2>}
    </div>

    <nav className={styles.sidebarNav}>
      {['Dashboard', 'Auditoria', 'Usuarios', 'Productos', 'Compras', 'Proveedores'].map((section) => (
        <button
          key={section}
          onClick={() => toggleSection(section)}
          className={activeSection === section ? styles.activeButton : ''}
          title={section}
        >
          {section === 'Dashboard' && <FaTh />}
          {section === 'Auditoria' && <FaChartLine />}
          {section === 'Usuarios' && <FaUsers />}
          {section === 'Productos' && <FaBoxes />}
          {section === 'Compras' && <FaShoppingCart />}
          {section === 'Proveedores' && <FaClipboardCheck />}
          {!sidebarCollapsed && <span>{section}</span>}
        </button>
      ))}
    </nav>

    <div className={styles.sidebarFooter}>
      <button onClick={handleLogout} className={styles.logoutButton} title="Cerrar sesión">
        <FaSignOutAlt />
        {!sidebarCollapsed && <span>Cerrar sesión</span>}
      </button>
    </div>
  </aside>
);