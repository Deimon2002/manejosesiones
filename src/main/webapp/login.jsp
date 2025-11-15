<%--
  Created by IntelliJ IDEA.
  User: Thede
  Date: 12/11/2025
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login de Usuario</title>

    <!-- Bootstrap CSS 5.3.0 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .login-container {
            animation: fadeInUp 0.6s ease-out;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            max-width: 450px;
            width: 100%;
        }

        .login-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 40px 30px;
            text-align: center;
            color: white;
        }

        .login-header i {
            font-size: 4rem;
            margin-bottom: 15px;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
        }

        .login-body {
            padding: 40px 35px;
        }

        .form-floating {
            margin-bottom: 20px;
        }

        .form-control {
            border-radius: 10px;
            border: 2px solid #e0e0e0;
            padding: 12px 15px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.25rem rgba(102, 126, 234, 0.25);
        }

        .btn-login {
            width: 100%;
            padding: 14px;
            border-radius: 10px;
            font-weight: 600;
            font-size: 1.1rem;
            text-transform: uppercase;
            letter-spacing: 1px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .btn-login:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
        }

        .btn-login:active {
            transform: translateY(-1px);
        }

        .input-group-text {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px 0 0 10px;
        }

        .form-control.with-icon {
            border-radius: 0 10px 10px 0;
        }

        .login-footer {
            text-align: center;
            padding: 20px;
            background: #f8f9fa;
            border-top: 1px solid #e0e0e0;
        }

        .login-footer a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .login-footer a:hover {
            color: #764ba2;
            text-decoration: underline;
        }

        .alert-custom {
            border-radius: 10px;
            border: none;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6">
            <div class="login-container">
                <div class="login-card">
                    <!-- Header -->
                    <div class="login-header">
                        <i class="bi bi-person-circle"></i>
                        <h2 class="mb-0 fw-bold">Iniciar Sesión</h2>
                        <p class="mb-0 mt-2 opacity-75">Ingresa tus credenciales para continuar</p>
                    </div>

                    <!-- Body -->
                    <div class="login-body">
                        <!-- Mensaje de error (opcional) -->
                        <% String error = request.getParameter("error"); %>
                        <% if (error != null) { %>
                        <div class="alert alert-danger alert-custom d-flex align-items-center" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <div>
                                <strong>Error:</strong> Usuario o contraseña incorrectos.
                            </div>
                        </div>
                        <% } %>

                        <!-- Formulario -->
                        <form action="${pageContext.request.contextPath}/login.html" method="post">
                            <!-- Campo Usuario -->
                            <div class="input-group mb-3">
                                    <span class="input-group-text">
                                        <i class="bi bi-person-fill"></i>
                                    </span>
                                <input type="text"
                                       class="form-control form-control-lg with-icon"
                                       id="user"
                                       name="user"
                                       placeholder="Nombre de usuario"
                                       required
                                       autofocus>
                            </div>

                            <!-- Campo Contraseña -->
                            <div class="input-group mb-3">
                                    <span class="input-group-text">
                                        <i class="bi bi-lock-fill"></i>
                                    </span>
                                <input type="password"
                                       class="form-control form-control-lg with-icon"
                                       id="password"
                                       name="password"
                                       placeholder="Contraseña"
                                       required>
                            </div>

                            <!-- Checkbox Recordar -->
                            <div class="form-check mb-3">
                                <input class="form-check-input"
                                       type="checkbox"
                                       id="remember"
                                       name="remember">
                                <label class="form-check-label" for="remember">
                                    Recordar mis credenciales
                                </label>
                            </div>

                            <!-- Botón Submit -->
                            <button type="submit" class="btn btn-login">
                                <i class="bi bi-box-arrow-in-right me-2"></i>
                                Ingresar
                            </button>
                        </form>
                    </div>


                </div>

                <!-- Link de regreso -->
                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/index.html"
                       class="btn btn-outline-light btn-lg">
                        <i class="bi bi-arrow-left-circle"></i> Volver al Inicio
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Animación al enviar el formulario
    document.querySelector('form').addEventListener('submit', function(e) {
        const btn = this.querySelector('.btn-login');
        btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Ingresando...';
        btn.disabled = true;
    });

    // Validación en tiempo real
    const inputs = document.querySelectorAll('input[required]');
    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            }
        });
    });
</script>
</body>
</html>