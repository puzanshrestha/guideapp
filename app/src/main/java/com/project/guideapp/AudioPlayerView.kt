package com.project.guideapp


import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class AudioPlayerView : FrameLayout {

    private lateinit var ivPlayPause: ImageView
    private lateinit var tvElapsedTime: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var tvTotalTime: TextView

    private lateinit var mediaPlayer: MediaPlayer
    private var isPrepared: Boolean = false

    private val timeUpdater = Runnable {
        updateSeekbar()
    }


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }


    private fun initView() {
        val view = inflate(context, R.layout.layout_audio_player, this)
        ivPlayPause = view.findViewById(R.id.iv_play_pause)
        tvElapsedTime = view.findViewById(R.id.tv_elapsed_time)
        seekBar = view.findViewById(R.id.seekbar_audio)
        tvTotalTime = view.findViewById(R.id.tv_total_time)
        mediaPlayer = MediaPlayer()

        seekBar.max = 100
        ivPlayPause.setOnClickListener {
            playPause()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initWithURL(url: String) {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnBufferingUpdateListener { _, progress ->
            seekBar.secondaryProgress = progress
        }
        isPrepared = false
        mediaPlayer.setOnPreparedListener { isPrepared = true }
        seekBar.setOnTouchListener { _, _ ->
            if (isPrepared) {
                mediaPlayer.seekTo((seekBar.progress.toFloat() / 100f * mediaPlayer.duration.toFloat()).toInt())
            }
            false
        }

    }

    private fun stop() {
        mediaPlayer.stop()
        handler.removeCallbacks(timeUpdater)
    }

    private fun playPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            handler.removeCallbacks(timeUpdater)
            ivPlayPause.setImageResource(R.drawable.ic_play)
        } else {
            if (isPrepared) {
                mediaPlayer.start()
                updateSeekbar()
                ivPlayPause.setImageResource(R.drawable.ic_pause)
            } else {
                Toast.makeText(context, "Loading please wait ..", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSeekbar() {
        seekBar.progress =
            ((mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration.toFloat()) * 100).toInt()
        tvElapsedTime.text = getFormattedTime(mediaPlayer.currentPosition.toLong())

        tvTotalTime.text = getFormattedTime(mediaPlayer.duration.toLong())

        handler.postDelayed(timeUpdater, 1000)
    }

    private fun getFormattedTime(timeInMilliseconds: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds),
            TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds))
        )
    }

    override fun onDetachedFromWindow() {
        stop()
        super.onDetachedFromWindow()
    }

}