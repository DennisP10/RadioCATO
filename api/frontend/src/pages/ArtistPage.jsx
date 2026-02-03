import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export default function ArtistPage() {
  const { id } = useParams(); // Obtiene el ID de la URL
  const [artist, setArtist] = useState(null);
  const [albums, setAlbums] = useState([]);

  useEffect(() => {
    // 1. Cargar Perfil
    fetch(`http://http://172.20.10.30:8080/api/v1/artists/${id}`)
      .then(res => res.json())
      .then(data => setArtist(data));

    // 2. Cargar Discografía
    fetch(`http://172.20.10.30:8080/api/v1/artists/${id}/albums`)
      .then(res => res.json())
      .then(data => setAlbums(data));
  }, [id]);

  if (!artist) return <div className="loading">Cargando artista...</div>;

  return (
    <div className="page-container">
      {/* Cabecera del Artista */}
      <div className="artist-header">
        <h1>{artist.name}</h1>
        <p className="bio">{artist.bio}</p>
        <div className="genres">
            {artist.genres && Array.from(artist.genres).map(g => <span key={g} className="tag">{g}</span>)}
        </div>
      </div>

      {/* Grid de Álbumes */}
      <h2>Discografía</h2>
      <div className="albums-grid">
        {albums.map(album => (
          <div key={album.key.albumId} className="album-card">
            <img src={album.coverUrl} alt={album.albumName} />
            <h3>{album.albumName}</h3>
            <p>{album.key.releaseDate}</p>
          </div>
        ))}
      </div>
    </div>
  );
}