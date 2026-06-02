package com.example.a51c_media;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_items")
public class Playlist {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String videoUrl;

    public Playlist(String username, String videoUrl) {
        this.username = username;
        this.videoUrl = videoUrl;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setId(int id) {
        this.id = id;
    }
}
