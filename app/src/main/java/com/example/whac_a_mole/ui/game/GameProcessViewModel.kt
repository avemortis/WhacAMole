package com.example.whac_a_mole.ui.game

import android.os.CountDownTimer
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whac_a_mole.model.Hole
import kotlin.random.Random

class GameProcessViewModel : ViewModel() {
    var gameRunning = false

    private val _score = MutableLiveData<Int>().apply { value = 0 }
    val score: LiveData<Int> = _score
    private val _time = MutableLiveData<Int>().apply { value = 30 }
    val time: LiveData<Int> = _time
    private val _activeHole = MutableLiveData<Int>().apply { 0 }
    val activeHole : LiveData<Int> = _activeHole

    val holes : MutableList<Hole> = mutableListOf()



    fun startGame(roundTime : Long, intervalBetweenShowing : Long){
        if (!gameRunning){
            gameRunning = true
            object : CountDownTimer(roundTime, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    _time.value = _time.value?.minus(1)
                }

                override fun onFinish() {
                }

            }.start()

            object : CountDownTimer(roundTime, intervalBetweenShowing){
                override fun onTick(millisUntilFinished: Long) {
                    var random = Random.nextInt(0, holes.size)
                    _activeHole.value = random
                }

                override fun onFinish() {
                }

            }.start()
        }
    }

    fun activateHole(count : Int){
        holes[count].activate()
    }

    fun check(count : Int){
        if (holes[count].check()) addPoint()
    }

    fun addPoint () {
        _score.value = _score.value?.plus(1)
    }
}