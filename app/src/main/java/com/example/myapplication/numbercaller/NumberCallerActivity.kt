package com.example.myapplication.numbercaller

import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNumberCallerBinding
import com.example.myapplication.utils.SharedPreferenceUtils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.Arrays
import java.util.Locale
import java.util.Random

class NumberCallerActivity : AppCompatActivity(), TextToSpeech.OnInitListener,
    MediaPlayer.OnCompletionListener {
    private lateinit var textToSpeech: TextToSpeech
    private var numbers = IntArray(90)
    private var shuffledNumbers = IntArray(90)
    private val numberTextViews = ArrayList<View>()
    private var counter = 0
    private lateinit var gridLayout: GridLayout
    private lateinit var binding: ActivityNumberCallerBinding
    private var isMuted: Boolean = false
    private lateinit var musicShuffle: MediaPlayer
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberCallerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val testDeviceIds = Arrays.asList("BAECF24679C6C394318EACC12ED9BD78")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        binding.buttonCall.setOnClickListener {
            callNumber()
        }
        musicShuffle = MediaPlayer.create(this, R.raw.music_shuffle)
        musicShuffle.setOnCompletionListener(this)

        textToSpeech()
        generateNumbers()
        showNumberLayout()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitCallerDialog()
            }
        })
    }

    private fun showExitCallerDialog() {
        val exitCallerDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        exitCallerDialogBuilder.setMessage(R.string.do_you_want_to_exit_from_caller)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.cancel()
                finish()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> //  Action for 'NO' Button
                dialog.cancel()
            }
        val exitCallerDialog: AlertDialog = exitCallerDialogBuilder.create()
        exitCallerDialog.setTitle(R.string.alert)
        exitCallerDialog.show()
    }

    override fun onResume() {
        super.onResume()
        isMuted = SharedPreferenceUtils.getIsMuted(this@NumberCallerActivity)
        val bannerAdRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(bannerAdRequest)
        loadFullScreenAd()
    }

    private fun generateNumbers() {
        for (i in 0..89) {
            numbers[i] = i + 1
        }
        shuffledNumbers = numbers

        val randomNumber = Random().nextInt(90 - 1 + 1) + 1
        for (i in 0..randomNumber)
            shuffledNumbers.shuffle()
    }

    private fun callNumber() {
        if (counter < 90) {
            binding.textCalledNumber.text = shuffledNumbers[counter].toString()
            updateNumberLayout(shuffledNumbers[counter] - 1)
            if (!isMuted)
                textToSpeech.speak(
                    "" + shuffledNumbers[counter],
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    ""
                )
            counter++
        } else {
            binding.buttonCall.text = getString(R.string.game_over)
        }
    }

    private fun getNumberTextViewsList(): ArrayList<View> {
        for (i in 1..90) {
            val textNumber = TextView(this)
            textNumber.textSize = 22.0f
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 10, 10, 10)
            textNumber.layoutParams = params
            textNumber.text = if (i.toString().length == 1) {
                "0$i"
            } else {
                "$i"
            }
            numberTextViews.add(textNumber)
        }
        return numberTextViews
    }

    private fun showNumberLayout() {
        getNumberTextViewsList()
        gridLayout = GridLayout(this)
        gridLayout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        gridLayout.columnCount = 10
        gridLayout.rowCount = 9

        for (i in 0 until numberTextViews.size) {
            val doubleLayoutParams = GridLayout.LayoutParams()
            doubleLayoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1)
            doubleLayoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1)
            gridLayout.addView(numberTextViews[i])
        }

        binding.layoutNumbers.addView(gridLayout)
    }

    private fun updateNumberLayout(index: Int) {
        binding.layoutNumbers.removeAllViews()
        gridLayout.removeAllViews()

        val calledNumberText: TextView = numberTextViews[index] as TextView
        calledNumberText.setTextColor(Color.RED)
        calledNumberText.setTypeface(calledNumberText.typeface, Typeface.BOLD)
        numberTextViews[index] = calledNumberText

        gridLayout = GridLayout(this)
        gridLayout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        gridLayout.columnCount = 10
        gridLayout.rowCount = 9
        for (i in 0 until numberTextViews.size) {
            val doubleLayoutParams = GridLayout.LayoutParams()
            doubleLayoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1)
            doubleLayoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1)
            gridLayout.addView(numberTextViews[i])
        }
        binding.layoutNumbers.addView(gridLayout)
    }

    private fun textToSpeech() {
        textToSpeech = TextToSpeech(this, this)
        textToSpeech.language = Locale.UK
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("test", "The Language not supported!")
            } else {
                binding.buttonCall.isEnabled = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.number_caller_menu, menu)
        menu?.getItem(2)?.icon = AppCompatResources.getDrawable(
            this@NumberCallerActivity,
            if (isMuted) {
                R.drawable.baseline_voice_over_off_24
            } else {
                R.drawable.baseline_record_voice_over_24
            }
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuShuffle -> {
            musicShuffle.start()
            true
        }

        R.id.menuRestart -> {
            showRestartDialog()
            true
        }

        R.id.menuAudio -> {
            SharedPreferenceUtils.setIsMuted(this@NumberCallerActivity, !isMuted)
            isMuted = SharedPreferenceUtils.getIsMuted(this@NumberCallerActivity)
            item.icon = AppCompatResources.getDrawable(
                this@NumberCallerActivity,
                if (isMuted) {
                    R.drawable.baseline_voice_over_off_24
                } else {
                    R.drawable.baseline_record_voice_over_24
                }
            )
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showRestartDialog() {
        val restartDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        restartDialogBuilder.setMessage(R.string.do_you_want_to_restart)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.cancel()
                restartNumberCaller()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> //  Action for 'NO' Button
                dialog.cancel()
            }
        val restartDialog: AlertDialog = restartDialogBuilder.create()
        restartDialog.setTitle(R.string.alert)
        restartDialog.show()
    }

    private fun restartNumberCaller() {
        generateNumbers()
        gridLayout.removeAllViews()
        binding.layoutNumbers.removeAllViews()
        counter = 0
        numberTextViews.clear()
        binding.textCalledNumber.text = getString(R.string.smile)
        showNumberLayout()
        binding.buttonCall.text = getString(R.string.call_number)

        showFullScreenAd()
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    private fun loadFullScreenAd() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-6485501165399913/6394444307",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("ads", adError.toString().toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("ads", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showFullScreenAd() {
        try {
            mInterstitialAd!!.show(this@NumberCallerActivity)
        } catch (e: Exception) {
            Log.d("ads", "Ad Exception: " + e.localizedMessage)
        }

    }

}