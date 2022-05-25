package com.project.guideapp.ui.streetart

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.guideapp.AudioPlayerView
import com.project.guideapp.databinding.FragmentStreetArtBinding


class StreetArtFragment : Fragment() {

    private lateinit var binding: FragmentStreetArtBinding

    private lateinit var mediaPlayer: MediaPlayer

    private val timeUpdater = Runnable {
        updateSeekbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreetArtBinding.inflate(inflater)
        mediaPlayer = MediaPlayer()
        binding.audioPlayer.seekbarAudio.max = 100
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPlayer.setDataSource("https://westkruiskade1.blob.core.windows.net/audios/69fa9d1d-3113-4ec4-843f-ba4c79dc4483")
        mediaPlayer.prepare()
        binding.audioPlayer.ivPlay.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                updateSeekbar()
            }
        }
        binding.audioPlayer.ivMuteUnmute.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.duration / 2)
        }
        mediaPlayer.setOnBufferingUpdateListener { _, position ->
            binding.audioPlayer.seekbarAudio.secondaryProgress = position
        }
    }

    private fun updateSeekbar() {
        binding.audioPlayer.seekbarAudio.progress =
            ((mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration.toFloat()) * 100).toInt()
        Handler(Looper.getMainLooper()).postDelayed(timeUpdater, 1000)
    }

}