import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export default function PlayListPage() {
  const { id } = useParams();
  const [playlist, setPlaylist] = useState(null);
  const [songs, setSongs] = useState([]);

  useEffect(() => {
    // 1. Info Playlist
    fetch(`http://172.20.10.30:8080/api/v1/playlists/${id}`)
      .then(res => res.json())
      .then(data => setPlaylist(data));

    // 2. Canciones
    fetch(`http://172.20.10.30:8080/api/v1/playlists/${id}/songs`)
      .then(res => res.json())
      .then(data => setSongs(data));
  }, [id]);

  if (!playlist) return <div className="loading">Cargando playlist...</div>;

  return (
    <div className="page-container">
      <div className="playlist-header">
        <h1>{playlist.name}</h1>
        <p>{playlist.description}</p>
      </div>

      <table className="songs-table">
        <thead>
          <tr>
            <th>#</th>
            <th>Título</th>
            <th>Artista</th>
            <th>Duración</th>
          </tr>
        </thead>
        <tbody>
          {songs.map((song, index) => (
            <tr key={song.key.songId}>
              <td>{index + 1}</td>
              <td>{song.title}</td>
              <td>{song.artistName}</td>
              <td>{song.duration}s</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}