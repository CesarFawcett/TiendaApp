"use client";

import React, { useState } from 'react';
import styles from '../../styles/login.module.css';
import { useRouter, useSearchParams } from 'next/navigation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; 
import { faUser, faEye } from '@fortawesome/free-solid-svg-icons';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const router = useRouter();
    const searchParams = useSearchParams();
    const registroExitoso = searchParams.get('registro') === 'exitoso';

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');

        try {
            if (!email || !password) {
                throw new Error('Por favor ingresa email y contraseña');
            }
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Error en la autenticación');
            }

            const data = await response.json();
            localStorage.setItem('authToken', data.token);
            localStorage.setItem('user', JSON.stringify(data.user)); // Guardar datos del usuario si los retorna el backend
            router.push('/');
        } catch (err) {
            setError(err.message || 'Ocurrió un error al iniciar sesión');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className={styles.loginContainer}>
            <div className={styles.loginForm}>
                <h1>Iniciar Sesión</h1>
                
                {registroExitoso && (
                    <div className={styles.success}>
                        ¡Registro exitoso! Por favor inicia sesión con tus credenciales.
                    </div>
                )}
                
                {error && <div className={styles.error}>{error}</div>}
                
                <p className={styles['signup-link']}>
                    ¿Aún no tienes una cuenta? <a href="/signup">Crear nueva cuenta</a>
                </p>
                
                <form onSubmit={handleSubmit}>
                    <div className={styles.formGroup}>
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Ingresa tu email"
                            required
                        />
                        <FontAwesomeIcon icon={faUser} className={styles.icon} />
                    </div>
                    
                    <div className={styles.formGroup}>
                        <label htmlFor="password">Contraseña</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Ingresa tu contraseña"
                            required
                        />
                        <FontAwesomeIcon icon={faEye} className={styles.icon} />
                    </div>
                    
                    <button
                        type="submit"
                        className={isLoading ? styles.loading : ''}
                        disabled={isLoading}
                    >
                        {isLoading ? '' : 'Iniciar sesión'}
                    </button>
                </form>
                
                <div className={styles['forgot-password']}>
                    <a href="/recuperar-contrasena">¿Olvidaste tu contraseña?</a>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;