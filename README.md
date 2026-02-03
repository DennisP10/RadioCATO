RadioCATO - Plataforma de Streaming Musical Distribuida
RadioCATO es un sistema de streaming de audio diseñado bajo una arquitectura de sistemas distribuidos. Su objetivo principal es ofrecer alta disponibilidad, tolerancia a fallos y consistencia eventual en la gestión de catálogos musicales, listas de reproducción y estadísticas de usuario.

El sistema se compone de tres capas principales: una base de datos NoSQL distribuida (Apache Cassandra), una API RESTful resiliente (Spring Boot) y una interfaz de usuario reactiva (React/Vite).

Arquitectura del Sistema
El proyecto implementa patrones de diseño orientados a la resiliencia:

Persistencia: Apache Cassandra configurado en clúster. Utiliza estrategias de replicación y niveles de consistencia (ONE o QUORUM) para asegurar que el servicio continúe operando incluso ante la caída de nodos.

Backend: Spring Boot (Java) con controladores asíncronos y manejo de excepciones para evitar bloqueos en cascada cuando la infraestructura subyacente presenta latencia.

Frontend: Single Page Application (SPA) construida con React, optimizada para ofrecer una experiencia de usuario fluida con búsquedas no bloqueantes y navegación inmediata ("Fire and Forget").

Requisitos Previos
Antes de desplegar el sistema, asegúrese de contar con lo siguiente:

Java Development Kit (JDK): Versión 17 o superior.

Node.js: Versión 18 o superior y npm.

Apache Cassandra: Un clúster activo o una instancia local.

Sistema Operativo: Ubuntu Server (recomendado) o cualquier distribución Linux compatible.

Instalación y Configuración
1. Base de Datos (Cassandra)
El sistema requiere un Keyspace llamado music_app y un esquema de tablas específico para manejar usuarios, canciones, playlists y estadísticas. Asegúrese de ejecutar los scripts CQL proporcionados en la documentación de base de datos para crear las tablas:

users_by_email (Autenticación)

playlists_by_user (Biblioteca personal)

songs_by_playlist (Contenido de listas)

song_stats (Contadores globales de reproducción)

user_history (Historial de escucha)

user_search (Auditoría de búsquedas)

2. Backend (Spring Boot API)
El backend es el núcleo lógico que conecta los archivos físicos con los metadatos en Cassandra.

Configuración de Conectividad (Alta Disponibilidad): Edite el archivo src/main/resources/application.properties.

Para garantizar la máxima resiliencia del sistema, en la propiedad spring.cassandra.contact-points se han listado explícitamente todas las direcciones IP de los nodos disponibles en el clúster. Esta configuración permite que el driver de la aplicación conozca la topología completa de la red desde el inicio. Si uno o varios nodos fallan, la aplicación conmutará automáticamente (failover) a las IPs restantes sin interrumpir el servicio.

Properties
# Listado exhaustivo de nodos para tolerancia a fallos (West y East side)
spring.cassandra.contact-points=172.20.10.5,172.20.10.6,172.20.10.7,172.20.10.20,172.20.10.21,172.20.10.22

spring.cassandra.keyspace-name=music_app
spring.cassandra.local-datacenter=east-side

# Nivel de consistencia ONE para priorizar disponibilidad sobre consistencia estricta
spring.cassandra.request.consistency=ONE
Sincronización de Medios: El sistema espera encontrar los archivos de audio .mp3 en la ruta absoluta /music del servidor. Asegúrese de que esta carpeta exista y tenga permisos de lectura.

Compilación y Ejecución: Navegue al directorio de la API y genere el artefacto ejecutable.

Bash
./mvnw clean package
java -jar target/music-api-0.0.1-SNAPSHOT.jar
3. Frontend (Cliente Web)
La interfaz de usuario consume la API REST y gestiona la reproducción de audio.

Instalación de Dependencias:

Bash
cd client
npm install
Configuración del Endpoint: Verifique en App.jsx que las constantes API_URL y MUSIC_SERVER_URL apunten a la dirección IP correcta de su servidor Backend.

Construcción para Producción: No se recomienda usar el modo desarrollo en servidores productivos. Genere los archivos estáticos optimizados:

Bash
npm run build
Despliegue: Sirva el contenido de la carpeta dist generada utilizando un servidor estático como serve o Nginx.

Bash
npx serve -s dist -l 5173
Funcionalidades Principales
Resiliencia y Alta Disponibilidad
La aplicación está configurada para tolerar fallos parciales en la base de datos. Al definir la lista completa de nodos en la configuración, el sistema elimina puntos únicos de fallo en la conexión, permitiendo que las operaciones continúen mientras exista al menos un nodo operativo.

Sincronización Automática
El endpoint /api/v1/music/sync escanea recursivamente el directorio /music del servidor, extrae los metadatos de los archivos MP3 y actualiza el catálogo global en Cassandra sin intervención manual.

Sistema de Búsqueda Híbrido
La barra de búsqueda implementa un patrón asíncrono. Registra la consulta del usuario en la base de datos con fines de auditoría (sin bloquear la interfaz) y simultáneamente filtra el catálogo local en el cliente por título y artista.

Gestión de Identidad y Playlists
El sistema resuelve la identidad del usuario para mostrar listas de reproducción personalizadas. Utiliza tablas compuestas en Cassandra para mapear eficientemente las relaciones entre usuarios y sus colecciones musicales privadas.
