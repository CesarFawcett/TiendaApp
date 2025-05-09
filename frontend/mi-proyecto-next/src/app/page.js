"use client";
import { useState } from 'react';
import styles from '../styles/page.module.css';

export default function Home() {
  const [activeSection, setActiveSection] = useState(null);
  const [formData, setFormData] = useState({
    nombre: '',
    email: '',
    telefono: '',
    empresa: '',
    mensaje: ''
  });

  const toggleSection = (section) => {
    setActiveSection(activeSection === section ? null : section);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Lógica para enviar el formulario
    console.log('Formulario enviado:', formData);
  };

  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <div className={styles.headerContent}>
          <h1>Tienda App solucion para tiendas</h1>
          <p>Innovación y tecnología para tu negocio</p>
        </div>
        <nav className={styles.nav}>
          <button 
            onClick={() => toggleSection('servicios')} 
            className={activeSection === 'servicios' ? styles.activeButton : ''}
          >
            Proveedores
          </button>
          <button 
            onClick={() => toggleSection('contacto')}
            className={activeSection === 'contacto' ? styles.activeButton : ''}
          >
            Productos
          </button>
          <button 
            onClick={() => toggleSection('contacto')}
            className={activeSection === 'contacto' ? styles.activeButton : ''}
          >
            Ventas
          </button>
          <button 
            onClick={() => toggleSection('contacto')}
            className={activeSection === 'contacto' ? styles.activeButton : ''}
          >
            Auditoria
          </button>
          <a href="/login" className={styles.loginLink}>Área de Clientes</a>
        </nav>
      </header>

      <main className={styles.main}>
        {/* Sección de Servicios */}
        {activeSection === 'servicios' && (
          <div className={styles.section}>
            <h2>Nuestros Servicios Profesionales</h2>
            <div className={styles.grid}>
              {[
                { id: 1, name: "Desarrollo Web", icon: "💻", desc: "Sitios web a medida con las últimas tecnologías" },
                { id: 2, name: "Diseño UX/UI", icon: "🎨", desc: "Experiencias de usuario intuitivas y atractivas" },
                { id: 3, name: "Marketing Digital", icon: "📈", desc: "Estrategias para aumentar tu presencia online" },
                { id: 4, name: "Consultoría IT", icon: "🔍", desc: "Soluciones tecnológicas para tu negocio" },
                { id: 5, name: "Cloud Solutions", icon: "☁️", desc: "Infraestructura escalable en la nube" },
                { id: 6, name: "Soporte 24/7", icon: "🛠️", desc: "Asistencia técnica permanente" }
              ].map((service) => (
                <div key={service.id} className={styles.card}>
                  <span className={styles.serviceIcon}>{service.icon}</span>
                  <h3>{service.name}</h3>
                  <p>{service.desc}</p>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Sección de Contacto */}
        {activeSection === 'contacto' && (
          <div className={styles.section}>
            <h2>Contáctenos</h2>
            <form className={styles.form} onSubmit={handleSubmit}>
              <div className={styles.formRow}>
                <div className={styles.formGroup}>
                  <label htmlFor="nombre">Nombre Completo</label>
                  <input 
                    type="text" 
                    id="nombre" 
                    name="nombre" 
                    value={formData.nombre}
                    onChange={handleInputChange}
                    required 
                  />
                </div>
                <div className={styles.formGroup}>
                  <label htmlFor="email">Correo Electrónico</label>
                  <input 
                    type="email" 
                    id="email" 
                    name="email" 
                    value={formData.email}
                    onChange={handleInputChange}
                    required 
                  />
                </div>
              </div>
              
              <div className={styles.formRow}>
                <div className={styles.formGroup}>
                  <label htmlFor="telefono">Teléfono</label>
                  <input 
                    type="tel" 
                    id="telefono" 
                    name="telefono" 
                    value={formData.telefono}
                    onChange={handleInputChange}
                  />
                </div>
                <div className={styles.formGroup}>
                  <label htmlFor="empresa">Empresa</label>
                  <input 
                    type="text" 
                    id="empresa" 
                    name="empresa" 
                    value={formData.empresa}
                    onChange={handleInputChange}
                  />
                </div>
              </div>
              
              <div className={styles.formGroup}>
                <label htmlFor="mensaje">Mensaje</label>
                <textarea 
                  id="mensaje" 
                  name="mensaje" 
                  rows="4"
                  value={formData.mensaje}
                  onChange={handleInputChange}
                  required
                ></textarea>
              </div>
              
              <button type="submit" className={styles.submitButton}>Enviar Mensaje</button>
            </form>
          </div>
        )}

        {/* Mensaje cuando no hay sección activa */}
        {!activeSection && (
          <div className={styles.hero}>
            <h2>Soluciones Tecnológicas para tu Empresa</h2>
            <p>Descubre cómo podemos ayudarte a transformar tu negocio digitalmente</p>
            <button 
              onClick={() => toggleSection('servicios')} 
              className={styles.ctaButton}
            >
              Conoce nuestros servicios
            </button>
          </div>
        )}
      </main>

      <footer className={styles.footer}>
        <div className={styles.footerContent}>
          <p>© {new Date().getFullYear()} Empresa de Soluciones Digitales. Todos los derechos reservados.</p>
          <div className={styles.footerLinks}>
            <a href="#">Política de Privacidad</a>
            <a href="#">Términos de Servicio</a>
            <a href="#">Contacto</a>
          </div>
        </div>
      </footer>
    </div>
  );
}