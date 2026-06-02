package com.example.a51c_media;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {

    private ListView playlistListView;
    private Button btnBackToStream;
    private AppDatabase database;
    private String currentUsername = "";
    private List<Playlist> playlistItems;
    private ArrayList<String> playlistUrls;

    public PlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        playlistListView = view.findViewById(R.id.playlistListView);
        btnBackToStream = view.findViewById(R.id.btnBackToStream);

        database = AppDatabase.getInstance(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            currentUsername = bundle.getString("currentUsername", "");
        }

        if (currentUsername.isEmpty()) {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            return view;
        }

        playlistItems = database.playlistDao().getPlaylistByUsername(currentUsername);

        playlistUrls = new ArrayList<>();

        for (Playlist item : playlistItems) {
            playlistUrls.add(item.getVideoUrl());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                playlistUrls
        );

        playlistListView.setAdapter(adapter);

        playlistListView.setOnItemClickListener((parent, itemView, position, id) -> {
            Playlist selectedItem = playlistItems.get(position);

            Bundle playerBundle = new Bundle();
            playerBundle.putString("youtubeUrl", selectedItem.getVideoUrl());
            playerBundle.putString("currentUsername", currentUsername);

            Navigation.findNavController(view)
                    .navigate(R.id.action_playlistFragment_to_playerFragment, playerBundle);
        });

        btnBackToStream.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }
}