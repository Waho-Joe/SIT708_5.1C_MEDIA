package com.example.a51c_media;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Insert
    void insertPlaylistItem(Playlist playlistItem);

    @Query("SELECT * FROM playlist_items WHERE username = :username")
    List<Playlist> getPlaylistByUsername(String username);
}
