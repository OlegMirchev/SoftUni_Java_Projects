package com.example.spotifyplaylistapp.service;

import com.example.spotifyplaylistapp.model.dto.AddSongDto;
import com.example.spotifyplaylistapp.model.dto.SongDto;
import com.example.spotifyplaylistapp.model.entities.Song;
import com.example.spotifyplaylistapp.model.entities.Style;
import com.example.spotifyplaylistapp.model.entities.User;
import com.example.spotifyplaylistapp.repository.SongRepository;
import com.example.spotifyplaylistapp.repository.StyleRepository;
import com.example.spotifyplaylistapp.repository.UserRepository;
import com.example.spotifyplaylistapp.util.Enum.StyleType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {

    private SongRepository songRepository;
    private StyleRepository styleRepository;
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Autowired
    public SongService(SongRepository songRepository, StyleRepository styleRepository, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.styleRepository = styleRepository;
        this.userRepository = userRepository;

        this.modelMapper = new ModelMapper();
    }

    public void add(AddSongDto addSongDto) {
        Optional<Style> style = this.styleRepository.findByName(StyleType.valueOf(addSongDto.getStyle()));

        Song song = this.modelMapper.map(addSongDto, Song.class);

        song.setStyle(style.get());

        this.songRepository.save(song);
    }


    public List<SongDto> selectAllSongsWithStylePop(StyleType style) {
        return this.songRepository.findByStyleName(style).stream().map(s -> this.modelMapper.map(s, SongDto.class))
                .collect(Collectors.toList());
    }

    public void addSongWithId(long id, long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Song> songOp = this.songRepository.findById(id);

        user.get().getPlayList().add(songOp.get());

        this.userRepository.save(user.get());
    }

    public void removeAllSongsAtPlayListUserWithId(long userId) {
        Optional<User> user = this.userRepository.findById(userId);

        user.get().getPlayList().clear();

        this.userRepository.save(user.get());
    }
}
