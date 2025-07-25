package com.brianmoler.hexaroll.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(
    private val context: Context,
    private val onShake: () -> Unit
) : SensorEventListener {
    
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    
    private var lastUpdate: Long = 0
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f
    
    companion object {
        private const val SHAKE_THRESHOLD = 800
        private const val MIN_TIME_BETWEEN_SHAKES = 1000L // 1 second
    }
    
    fun start() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    
    fun stop() {
        sensorManager.unregisterListener(this)
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return
        
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastUpdate < MIN_TIME_BETWEEN_SHAKES) return
        
        val diffTime = currentTime - lastUpdate
        lastUpdate = currentTime
        
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        
        val speed = sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY) + (z - lastZ) * (z - lastZ)) / diffTime * 10000
        
        if (speed > SHAKE_THRESHOLD) {
            onShake()
        }
        
        lastX = x
        lastY = y
        lastZ = z
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for shake detection
    }
} 