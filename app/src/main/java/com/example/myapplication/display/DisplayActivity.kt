package com.example.myapplication.display

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_display.*
import java.util.*
import java.util.concurrent.TimeUnit


class   DisplayActivity : AppCompatActivity(), View.OnClickListener, Runnable {

    var images = intArrayOf(
//            R.drawable.shop1, R.drawable.walmart,
            R.drawable.chanel, R.drawable.shop,
            R.drawable.ic_add,R.drawable.ic_add_circle,
            R.drawable.ic_more_horiz
    )

    var mediaPlayer: MediaPlayer? = null
    var wasPlaying: Boolean = false
    lateinit var seekHandler: Handler
    var sTime =0
    var eTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        seekHandler = Handler()
        val viewPagerAdapter = ViewPagerAdapter(this, images)
        Log.e("Display Activity", tabLayoutImage.tabCount.toString())
        vpImage.adapter = viewPagerAdapter

        tabLayoutImage.setupWithViewPager(vpImage, true)
//        tabLayoutImage.setSelectedTabIndicator(R.drawable.selector_image)

        vpImage.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutImage))

        var currentPage:Int=vpImage.currentItem

        val handler = Handler()
        val Update = Runnable {
            if (currentPage === images.size ) {
                currentPage = 0
            }
            vpImage.setCurrentItem(currentPage++, true)
        }

        var timer = Timer() // This will create a new Thread

        timer.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, 500, 3000)

//        vpImage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                if (position == images.size - 1) {
//                    currentPage = 0
//                }
//                vpImage.setCurrentItem(currentPage++, true)
//            }
//
//            override fun onPageSelected(position: Int) {}
//            override fun onPageScrollStateChanged(state: Int) {}
//        })

        tabLayoutImage.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                vpImage.currentItem = tabLayoutImage.selectedTabPosition
                tabLayoutImage.setSelectedTabIndicator(R.drawable.slider_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tabLayoutImage.setSelectedTabIndicator(R.drawable.slider_unselected)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

//        seekBarMusic.setEnabled(false)
//        seekBarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//
//            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
//                if (mediaPlayer != null && p2) {
////                    mediaPlayer!!.seekTo(progress * 1000);
//                }
//
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
////                mediaPlayer!!.seekTo(sTime)
//            }
//
//        })

    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            Log.e("If", "Checking")
            when (p0.id) {
                R.id.btnCheckOut -> {
                    Log.e("Check out", "Checking")
                    finish()
                }
                R.id.ivPlayPause -> {
                    Log.e("Play onclick", "Checking")
                    playSong()
                }
            }
        }
    }

    public fun playSong() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            Log.e("2", "Pause Checking")
            clearMediaPlayer()
            seekBarMusic.setProgress(0)
            wasPlaying = true
            ivPlayPause.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_play_circle_filled))
            tvMusicStart.setText("0:0")
//            mediaPlayer!!.seekTo(sTime)
        }

        if (!wasPlaying) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }
            ivPlayPause.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_pause_circle_filled))

            val descriptor: AssetFileDescriptor = assets.openFd("ringtone.mp3")
            mediaPlayer!!.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()
//            mediaPlayer= MediaPlayer.create(this, R.raw.ringtone)
//            mediaPlayer!!.start()

            mediaPlayer!!.prepare()
            mediaPlayer!!.setVolume(50f, 50f)
            mediaPlayer!!.setLooping(false)
            mediaPlayer!!.start()

            sTime= mediaPlayer?.currentPosition!!
            eTime = mediaPlayer?.duration!!
            tvMusicStart.setText(java.lang.String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()))))
            tvMusicEnd.setText(java.lang.String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(eTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(eTime.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime.toLong()))))
            seekBarMusic.max=(eTime/1000)

            seekBarMusic.setProgress(sTime / 1000)
            Log.e(sTime.toString(), "Checking")
            seekHandler = Handler()
            seekHandler.postDelayed(UpdateSongTime, 1000)
        }
        wasPlaying = false
    }

    override fun onDestroy() {
        super.onDestroy()
        clearMediaPlayer()
    }

    public fun clearMediaPlayer() {
        if (mediaPlayer!=null){
            if (mediaPlayer?.isPlaying!!){
                mediaPlayer?.stop()
            }
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private val UpdateSongTime: Runnable = object : Runnable {
        override fun run() {
            if (mediaPlayer!=null){
                sTime= mediaPlayer!!.getCurrentPosition()
                seekBarMusic.progress= sTime/1000
                tvMusicStart.setText(java.lang.String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()))))
            }
            seekHandler.postDelayed(this, 1000)
        }
    }

    override fun run() {
        var currentPosition = mediaPlayer!!.currentPosition
        val total = mediaPlayer!!.duration


        while (mediaPlayer != null && mediaPlayer!!.isPlaying && currentPosition < total) {
            currentPosition = try {
                Thread.sleep(1000)
                mediaPlayer!!.currentPosition
            } catch (e: InterruptedException) {
                return
            } catch (e: Exception) {
                return
            }
            seekBarMusic.setProgress(currentPosition)
        }
    }
}
