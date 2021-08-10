package com.example.whac_a_mole.model

import android.os.CountDownTimer
import android.util.Log

class Hole {
    private val TAG = "Hole"

    private val activeTime : Long = 500
    private val blockTime : Long = 600

    var active = false
    var wasPushed = false
    var blocked = false

    val blockTimer: CountDownTimer = object : CountDownTimer(blockTime, 1) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            blocked = false
        }
    }

    val activeTimer: CountDownTimer = object : CountDownTimer(activeTime, 1) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            active = false
        }
    }

    fun check() : Boolean {
        return if (active && !wasPushed && !blocked){
            wasPushed = true
            true
        } else {
            block()
            false
        }
    }

    fun activate() {
        active = true
        wasPushed = false
        activeTimer.start()
/*        object : CountDownTimer(activeTime, 1){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                active = false
            }

        }.start()*/
    }

    fun block() {
        blocked = true
        blockTimer.start()
/*        object : CountDownTimer(blockTime, 1){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                blocked = false
            }
        }.start()*/
    }

}