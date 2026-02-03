import { useState, useEffect, useRef } from 'react';
import { BrowserRouter, Routes, Route, Link, Navigate, useParams, useNavigate } from 'react-router-dom';
import './App.css';

// --- CONFIGURACIÓN DE RED ---
const API_URL = "http://172.20.10.30:8080"; 
const MUSIC_SERVER_URL = "http://172.20.10.30"; 

const cleanFileName = (fileName) => {
  if (!fileName) return "Canción Desconocida";
  return fileName
    .replace(/\.mp3$/i, '')
    .replace(/^\d+[\s-_]*/, '')
    .replace(/_/g, ' ')
    .replace(/-/g, ' - ')
    .replace(/\b\w/g, l => l.toUpperCase());
};

// --- LOGIN ---
function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => { 
    e.preventDefault(); 
    setError(null);
    try {
      const response = await fetch(`${API_URL}/api/v1/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      if (response.ok) {
        onLogin(await response.json()); 
      } else {
        setError("Credenciales inválidas.");
      }
    } catch (err) { setError("Error de conexión con el servidor."); }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <img src="/logo.png" alt="RadioCATO Logo" className="login-brand-logo" />
        <h1 className="brand-title">RadioCATO</h1>
        <form onSubmit={handleSubmit}>
          <input type="email" placeholder="Correo" value={email} onChange={(e) => setEmail(e.target.value)} required />
          <input type="password" placeholder="Contraseña" value={password} onChange={(e) => setPassword(e.target.value)} required />
          {error && <p className="error-text">{error}</p>}
          <button type="submit" className="login-btn">ENTRAR</button>
        </form>
      </div>
    </div>
  );
}

// --- PLAYER ---
function PlayerBar({ currentSong, isPlaying, onTogglePlay, audioRef }) {
  const [progress, setProgress] = useState(0);
  
  useEffect(() => {
    const audio = audioRef.current;
    const update = () => setProgress((audio.currentTime / audio.duration) * 100 || 0);
    audio.addEventListener('timeupdate', update);
    return () => audio.removeEventListener('timeupdate', update);
  }, [audioRef]);

  if (!currentSong) return null;

  return (
    <div className="player-bar">
      <div className="player-info">
        <h4>{currentSong.title || cleanFileName(currentSong.artistName)}</h4>
        <small>{cleanFileName(currentSong.artistName)}</small>
      </div>
      <div className="player-center">
        <button onClick={onTogglePlay} className="main-play-btn">{isPlaying ? "⏸" : "▶"}</button>
        <input type="range" className="progress-slider" value={progress} readOnly />
      </div>
      <div className="player-right"></div>
    </div>
  );
}

// --- SIDEBAR (Q7 & Q12) ---
function Sidebar({ user, onLogout }) {
  const [playlists, setPlaylists] = useState([]);
  const [query, setQuery] = useState('');
  const navigate = useNavigate();

  // 1. Cargar Playlists del Usuario (Q7)
  useEffect(() => {
    if(user?.userId) {
      fetch(`${API_URL}/api/v1/users/${user.userId}/playlists`)
        .then(res => res.ok ? res.json() : [])
        .then(data => setPlaylists(data))
        .catch(() => setPlaylists([]));
    }
  }, [user]);

  // 2. Búsqueda Resiliente (Q12)
  const handleSearch = (e) => {
    if (e.key === 'Enter' && query.trim()) {
      const q = query.trim();
      // Guardar historial (Fire & Forget)
      fetch(`${API_URL}/api/v1/users/${user.userId}/search`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ query: q })
      }).catch(console.warn);
      
      // Navegar
      navigate(`/catalog?q=${encodeURIComponent(q)}`);
      setQuery('');
    }
  };

  const handleSyncMusic = async () => {
    try {
      const res = await fetch(`${API_URL}/api/v1/music/sync`, { method: 'POST' });
      if (res.ok) { alert("Sincronización completa."); window.location.reload(); }
    } catch { alert("Error al sincronizar."); }
  };

  return (
    <nav className="sidebar">
      <div className="logo-section">
        <img src="/logo.png" alt="RadioCATO" className="sidebar-logo-img" />
        <h2 className="logo-text">RadioCATO</h2>
      </div>
      
      <div className="search-container">
        <input 
          type="text" className="search-input" placeholder="🔍 Buscar..." 
          value={query} onChange={e=>setQuery(e.target.value)} onKeyDown={handleSearch} 
        />
      </div>

      <ul className="nav-links">
        <li><Link to="/">🏠 Inicio</Link></li>
        <li><Link to="/profile">👤 Mi Perfil</Link></li>
      </ul>

      <div className="sidebar-separator">MIS PLAYLISTS</div>
      <ul className="nav-links" style={{flex: 1, overflowY: 'auto', minHeight: '100px'}}>
        {/* Playlists dinámicas del usuario */}
        {playlists.map(pl => (
          <li key={pl.key.playlistId}>
            <Link to={`/playlist/${pl.key.playlistId}`}>🎵 {pl.key.playlistName}</Link>
          </li>
        ))}
        {/* Playlist Global fija */}
        <li><Link to="/playlist/550e8400-e29b-41d4-a716-446655440000">📚 Biblioteca Global</Link></li>
      </ul>

      <div className="sidebar-separator">EXPLORAR</div>
      <ul className="nav-links">
        <li><Link to="/catalog">🌐 Catálogo Total</Link></li>
      </ul>

      <div style={{marginTop: 'auto', display: 'flex', flexDirection: 'column', gap: '10px'}}>
        <button className="btn-secondary" onClick={handleSyncMusic}>🔄 Sync Archivos</button>
        <button className="logout-btn" onClick={onLogout}>Salir</button>
      </div>
    </nav>
  );
}

// --- CATALOGO MEJORADO ---
function TotalCatalog({ onPlaySong }) {
  const [songs, setSongs] = useState([]);
  const [params] = useParams(); 
  const query = new URLSearchParams(window.location.search).get('q') || '';

  useEffect(() => {
    fetch(`${API_URL}/api/v1/playlists/550e8400-e29b-41d4-a716-446655440000/songs`)
      .then(res => res.json())
      .then(setSongs)
      .catch(() => setSongs([]));
  }, []);

  // FILTRO: Busca en Artista O en Título
  const filtered = songs.filter(s => {
    if (!query) return true;
    const q = query.toLowerCase();
    const artist = (s.artistName || "").toLowerCase();
    const title = (s.title || cleanFileName(s.artistName)).toLowerCase();
    return artist.includes(q) || title.includes(q);
  });

  return (
    <div className="page-container">
      <h2 className="section-title">{query ? `Resultados: "${query}"` : "Catálogo Total"}</h2>
      <div className="songs-list">
        {filtered.length === 0 && <p style={{color:'#666'}}>No se encontraron canciones.</p>}
        {filtered.map(s => (
          <div key={s.key?.songId} className="song-item" onClick={() => onPlaySong(s)}>
            <div className="song-details">
              <h4>{cleanFileName(s.artistName)}</h4>
              <small>Catálogo</small>
            </div>
            <button className="list-play-btn">▶</button>
          </div>
        ))}
      </div>
    </div>
  );
}

// --- PÁGINAS (Profile, Playlist, Home) ---
function ProfilePage({ user }) {
  const [history, setHistory] = useState([]);
  useEffect(() => {
    fetch(`${API_URL}/api/v1/users/${user.userId}/history`)
      .then(r=>r.json()).then(setHistory).catch(console.warn);
  }, [user]);

  return (
    <div className="page-container">
      <div className="profile-header">
        <div className="profile-avatar">{user.firstName?.charAt(0)}</div>
        <div>
          <h1 style={{margin:0}}>{user.firstName} {user.lastName}</h1>
          <p style={{color:'#888'}}>{user.email}</p>
        </div>
      </div>
      <h3 style={{color:'white', marginTop:'30px', borderBottom:'1px solid #333', paddingBottom:'10px'}}>Historial Reciente</h3>
      <div className="songs-list">
        {history.map((h, i) => (
          <div key={i} className="song-item">
             <div className="song-details">
                <h4 style={{color:'#aaa'}}>ID Canción: {h.songId}</h4>
                <small>{new Date(h.key.timestamp).toLocaleString()}</small>
             </div>
          </div>
        ))}
      </div>
    </div>
  );
}

function PlayListPage({ onPlaySong }) {
  const { id } = useParams();
  const [songs, setSongs] = useState([]);
  
  useEffect(() => {
    fetch(`${API_URL}/api/v1/playlists/${id}/songs`)
      .then(r=>r.json()).then(setSongs).catch(()=>setSongs([]));
  }, [id]);

  return (
    <div className="page-container">
      <h2 className="section-title">Playlist</h2>
      <div className="songs-list">
        {songs.length === 0 && <p style={{color:'#666'}}>Lista vacía o no encontrada.</p>}
        {songs.map(s => (
          <div key={s.key?.songId} className="song-item" onClick={()=>onPlaySong(s)}>
            <div className="song-details">
              <h4>{cleanFileName(s.artistName)}</h4>
              <small>Agregado recientemente</small>
            </div>
            <button className="list-play-btn">▶</button>
          </div>
        ))}
      </div>
    </div>
  );
}

function Home({ onPlaySong }) {
  const [popular, setPopular] = useState([]);
  useEffect(() => {
    fetch(`${API_URL}/api/v1/home/popular`).then(r=>r.json()).then(setPopular).catch(()=>[]);
  }, []);
  
  return (
    <div className="page-container">
      <h2 className="section-title">🔥 Tendencias Globales</h2>
      <div className="cards-grid">
        {popular.map(s => (
          <div key={s.key?.songId} className="music-card" onClick={()=>onPlaySong(s)}>
            <div className="card-image-placeholder" style={{display:'flex', alignItems:'center', justifyContent:'center', fontSize:'2rem'}}>📈</div>
            <h4>{cleanFileName(s.artistName)}</h4>
            <p className="play-count-text">▶ {s.playCount} plays</p>
          </div>
        ))}
      </div>
    </div>
  );
}

// --- APP ---
export default function App() {
  const [user, setUser] = useState(null);
  const [currentSong, setCurrentSong] = useState(null);
  const [currentList, setCurrentList] = useState([]);
  const [isPlaying, setIsPlaying] = useState(false);
  const audioRef = useRef(new Audio());

  // Manejo de fin de canción (Autoplay)
  useEffect(() => {
    const audio = audioRef.current;
    const handleEnd = () => setIsPlaying(false);
    audio.addEventListener('ended', handleEnd);
    return () => audio.removeEventListener('ended', handleEnd);
  }, []);

  const playSong = (song) => {
    const id = song.key?.songId || song.songId;
    setCurrentSong(song);
    setIsPlaying(true);
    
    // Registrar Play y Historial (Fire & Forget)
    fetch(`${API_URL}/api/v1/songs/${id}/play`, {method:'POST'}).catch(()=>{});
    if(user) fetch(`${API_URL}/api/v1/users/${user.userId}/history`, {
      method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify({songId:id})
    }).catch(()=>{});

    const url = (song.artistName?.toLowerCase().endsWith('.mp3')) 
      ? `${MUSIC_SERVER_URL}/music/${encodeURIComponent(song.artistName)}`
      : song.fileUrl;
    
    audioRef.current.src = url;
    audioRef.current.play().catch(console.warn);
  };

  const togglePlay = () => {
    if(isPlaying) audioRef.current.pause();
    else audioRef.current.play();
    setIsPlaying(!isPlaying);
  };

  if (!user) return <Login onLogin={setUser} />;

  return (
    <BrowserRouter>
      <div className="app-layout">
        {/* Renderizamos Sidebar como componente */}
        <Sidebar user={user} onLogout={()=>setUser(null)} />
        
        <main className="content">
          <header className="top-bar">Hola, <b>{user.firstName}</b></header>
          <div className="scrollable-area">
            <Routes>
              <Route path="/" element={<Home onPlaySong={playSong} />} />
              <Route path="/playlist/:id" element={<PlayListPage onPlaySong={playSong} />} />
              <Route path="/catalog" element={<TotalCatalog onPlaySong={playSong} />} />
              <Route path="/profile" element={<ProfilePage user={user} />} />
              <Route path="*" element={<Navigate to="/" />} />
            </Routes>
          </div>
        </main>
        
        <PlayerBar currentSong={currentSong} isPlaying={isPlaying} onTogglePlay={togglePlay} audioRef={audioRef} />
      </div>
    </BrowserRouter>
  );
}