"use client";

import React, { useState } from 'react';
import styles from '../../styles/login.module.css';
import { useRouter } from 'next/navigation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faEnvelope, faLock, faEye } from '@fortawesome/free-solid-svg-icons';

const SignupPage = () => {
    const [formData, setFormData] = useState({
        nombre: '',
        email: '',
        contraseña: '',
        confirmarContraseña: ''
    });
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const router = useRouter();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');

        // Validaciones básicas
        if (formData.contraseña !== formData.confirmarContraseña) {
            setError('Las contraseñas no coinciden');
            setIsLoading(false);
            return;
        }

        try {
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/usuarios`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    nombre: formData.nombre,
                    email: formData.email,
                    contraseña: formData.contraseña,
                    rol: 'USUARIO' // Rol por defecto para nuevos registros
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Error en el registro');
            }

            // Registro exitoso, redirigir a login
            router.push('/login?registro=exitoso');
        } catch (err) {
            setError(err.message || 'Ocurrió un error al registrarse');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className={styles.loginContainer}>
            <div className={styles.loginForm}>
                <h1>Crear Cuenta</h1>
                {error && <p className={styles.error}>{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className={styles.formGroup}>
                        <label htmlFor="nombre">Nombre Completo</label>
                        <input
                            type="text"
                            id="nombre"
                            name="nombre"
                            value={formData.nombre}
                            onChange={handleChange}
                            placeholder="Ingresa tu nombre completo"
                            required
                        />
                        <FontAwesomeIcon icon={faUser} className={styles.icon} />
                    </div>
                    
                    <div className={styles.formGroup}>
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            placeholder="Ingresa tu email"
                            required
                        />
                        <FontAwesomeIcon icon={faEnvelope} className={styles.icon} />
                    </div>
                    
                    <div className={styles.formGroup}>
                        <label htmlFor="contraseña">Contraseña</label>
                        <input
                            type="password"
                            id="contraseña"
                            name="contraseña"
                            value={formData.contraseña}
                            onChange={handleChange}
                            placeholder="Crea una contraseña"
                            required
                            minLength="6"
                        />
                        <FontAwesomeIcon icon={faLock} className={styles.icon} />
                    </div>
                    
                    <div className={styles.formGroup}>
                        <label htmlFor="confirmarContraseña">Confirmar Contraseña</label>
                        <input
                            type="password"
                            id="confirmarContraseña"
                            name="confirmarContraseña"
                            value={formData.confirmarContraseña}
                            onChange={handleChange}
                            placeholder="Confirma tu contraseña"
                            required
                            minLength="6"
                        />
                        <FontAwesomeIcon icon={faEye} className={styles.icon} />
                    </div>
                    
                    <button
                        type="submit"
                        className={isLoading ? styles.loading : ''}
                        disabled={isLoading}
                    >
                        {isLoading ? '' : 'Registrarse'}
                    </button>
                </form>
                
                <p className={styles['signup-link']}>
                    ¿Ya tienes una cuenta? <a href="/login">Iniciar sesión</a>
                </p>
            </div>
        </div>
    );
};

export default SignupPage;