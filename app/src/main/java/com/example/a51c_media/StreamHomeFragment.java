package com.example.a51c_media;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StreamHomeFragment extends Fragment {

    private EditText editTextYoutubeURL;
    private Button btnPlay, btnAddToList, btnMyPlaylist;
    private String currentUsername = "";
    private AppDatabase database;

    public StreamHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream_home, container, false);

        editTextYoutubeURL = view.findViewById(R.id.editTextYoutubeURL);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnAddToList = view.findViewById(R.id.btnAddToList);
        btnMyPlaylist = view.findViewById(R.id.btnMyPlaylist);
        database = AppDatabase.getInstance(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            currentUsername = bundle.getString("currentUsername", "");
        }

        btnPlay.setOnClickListener(v -> {
            String url = editTextYoutubeURL.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle playerBundle  = new Bundle();
            playerBundle.putString("youtubeUrl", url);
            playerBundle.putString("currentUsername", currentUsername);

            Navigation.findNavController(view)
                    .navigate(R.id.action_streamHomeFragment_to_playerFragment, playerBundle);
        });

        btnAddToList.setOnClickListener(v -> {
            String url = editTextYoutubeURL.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentUsername.isEmpty()) {
                Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            Playlist playlistItem = new Playlist(currentUsername, url);
            database.playlistDao().insertPlaylistItem(playlistItem);

            Toast.makeText(requireContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
        });

        btnAddToList.setOnClickListener(v -> {
            String url = editTextYoutubeURL.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter YouTube URL", Toast.LENGTH_SHORT).show();
            }

            if (currentUsername.isEmpty()) {
                Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            Playlist playlist = new Playlist(currentUsername, url);
            database.playlistDao().insertPlaylistItem(playlist);

            Toast.makeText(requireContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
        });

        btnMyPlaylist.setOnClickListener(v -> {
            Bundle playlistBundle = new Bundle();
            playlistBundle.putString("currentUsername", currentUsername);

            Navigation.findNavController(view)
                    .navigate(R.id.action_streamHomeFragment_to_playlistFragment, playlistBundle);
        });

        return view;
    }
}