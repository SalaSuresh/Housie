package com.example.myapplication.ticketgenerator

import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTicketGeneratorBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Random


class TicketGeneratorActivity : AppCompatActivity(), OnClickListener {

    private var line1 = IntArray(9)
    private var line2 = IntArray(9)
    private var line3 = IntArray(9)
    private var lines = listOf(line1, line2, line3)
    private var isValidTicketBlocks = false

    private lateinit var ticketLayout: LinearLayout

    private var count: Int = 0
    private var countLine1: Int = 0
    private var countLine2: Int = 0
    private var countLine3: Int = 0
    private lateinit var binding: ActivityTicketGeneratorBinding

    private lateinit var musicRaise: MediaPlayer
    private lateinit var musicFull: MediaPlayer

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicRaise = MediaPlayer.create(this, R.raw.music_raise)
        musicFull = MediaPlayer.create(this, R.raw.music_full)

        binding.buttonGenerateTicket.setOnClickListener {
            generateBlocks()
        }

        ticketLayout = findViewById(R.id.ticketLayout)

        binding.animationSuccess.setAnimation("success_animation.json")

        testFunction()

        generateBlocks()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitGameDialog()
            }
        })
    }

    private fun testFunction() {
        binding.textTicketId.text = "${generateRandomNumber()}"
        val size = 512 //pixels
        val qrCodeContent = binding.textTicketId.text.toString()
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
        binding.imageQRCode.setImageBitmap(bitmap)
    }

    private fun generateRandomNumber(): Int {
        val random = Random()
        // Generates a number between 10000 (inclusive) and 99999 (inclusive)
        return 10000 + random.nextInt(90000)
    }

    override fun onResume() {
        super.onResume()
        val bannerAdRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(bannerAdRequest)
        loadFullScreenAd()
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
            mInterstitialAd!!.show(this@TicketGeneratorActivity)
        } catch (e: Exception) {
            Log.d("ads", "Ad Exception: " + e.localizedMessage)
        }

    }

    private fun showExitGameDialog() {
        val exitGameDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        exitGameDialogBuilder.setMessage(R.string.do_you_want_to_exit_from_game)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.cancel()
                finish()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> //  Action for 'NO' Button
                dialog.cancel()
            }
        val exitGameDialog: AlertDialog = exitGameDialogBuilder.create()
        exitGameDialog.setTitle(R.string.alert)
        exitGameDialog.show()
    }

    private fun generateBlocks() {
        Log.d("test", "generateBlocks() called")
        for (a in lines.indices) {
            for (i in lines[a].indices) {
                if (i <= 4) {
                    lines[a][i] = 1
                } else {
                    lines[a][i] = 0
                }
            }
        }
        shuffleAndValidateBlocks()
    }

    private fun shuffleAndValidateBlocks() {
        for (a in lines.indices) {
            lines[a].shuffle()
        }

        for (a in lines.indices) {
            for (i in lines[a].indices) {
                Log.d("test", "Value: ${lines[a][i]}")
                Log.d("test", "Index: $i Value: ${lines[a][i]}")
            }
            Log.d("test", "---------------------")
        }

        for (a in 0..8) {
            if (lines[0][a] == 0 && lines[1][a] == 0 && lines[2][a] == 0) {
                Log.d("test", "INVALID TICKET")
                isValidTicketBlocks = false
                break
            } else {
                isValidTicketBlocks = true
            }
        }

        if (!isValidTicketBlocks) {
            shuffleAndValidateBlocks()
        } else {
            Log.d("test", " **** VALID TICKET **** ")
            generateTicketNumbers()
        }
    }

    private fun generateTicketNumbers() {
        Log.d("test", " ******* GENERATE TICKET NUMBERS ******* ")
        for (a in lines.indices) {
            for (i in lines[a].indices) {
                Log.d("test", "Value: ${lines[a][i]}")
                Log.d("test", "Index: $i Value: ${lines[a][i]}")
            }
            Log.d("test", "---------------------")
        }
        for (a in 0..8) {
            if ((lines[0][a] == 0 && lines[1][a] == 0 && lines[2][a] == 1) ||
                (lines[0][a] == 0 && lines[1][a] == 1 && lines[2][a] == 0) ||
                (lines[0][a] == 1 && lines[1][a] == 0 && lines[2][a] == 0)
            ) {
                val value = getOneRandomNumber(a)
                for (b in 0..2) {
                    if (lines[b][a] == 1) {
                        lines[b][a] = value
                    }
                }

            } else if ((lines[0][a] == 0 && lines[1][a] == 1 && lines[2][a] == 1) ||
                (lines[0][a] == 1 && lines[1][a] == 0 && lines[2][a] == 1) ||
                (lines[0][a] == 1 && lines[1][a] == 1 && lines[2][a] == 0)
            ) {
                val values = getTwoRandomNumbers(a)
                var valuesCounter = 0
                for (b in 0..2) {
                    if (lines[b][a] == 1) {
                        lines[b][a] = values[valuesCounter]
                        valuesCounter++
                    }
                }
            } else if ((lines[0][a] == 1 && lines[1][a] == 1 && lines[2][a] == 1)) {
                val values = getThreeRandomNumbers(a)
                var valuesCounter = 0
                for (b in 0..2) {
                    if (lines[b][a] == 1) {
                        lines[b][a] = values[valuesCounter]
                        valuesCounter++
                    }
                }
            }
        }

        Log.d("test", "FINAL TICKET")
        for (a in lines.indices) {
            for (i in lines[a].indices) {
                Log.d("test", "Value: ${lines[a][i]}")
                Log.d("test", "Index: $i Value: ${lines[a][i]}")
            }
            Log.d("test", "---------------------")
        }

        showTicket()
    }

    private fun showTicket() {
        Log.d("test", "showTicket() called")
        val rows = ArrayList<LinearLayout>()
        for (a in 0..2) {
            val rowNumbers = ArrayList<View>()
            for (b in 0..8) {
                rowNumbers.add(createButton(lines[a][b].toString(), "" + a + "_" + b))
            }
            rows.add(createRow(rowNumbers))
        }
        ticketLayout.removeAllViews()
        ticketLayout.addView(getKeyboardLayout(rows))
    }

    private fun getKeyboardLayout(rows: List<LinearLayout>): LinearLayout {
        val column = LinearLayout(this)
        column.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        column.orientation = VERTICAL
        column.gravity = Gravity.CENTER

        for (linearLayout in rows) {
            column.addView(linearLayout)
        }
        return column
    }

    private fun createRow(buttons: List<View>): LinearLayout {
        val row = LinearLayout(this)
        row.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.orientation = HORIZONTAL
        row.gravity = Gravity.CENTER

        for (button in buttons) {
            row.addView(button)
        }
        return row
    }

    private fun createButton(text: String, tag: String): Button {
        val button = Button(this)
        button.isAllCaps = false
        button.textSize = 28.0f
        button.setTextColor(Color.BLACK)
        button.tag = tag
        button.setBackgroundColor(
            if (text == "0") {
                Color.LTGRAY
            } else {
                Color.GRAY
            }
        )
        button.text = if (text == "0") {
            ""
        } else {
            text
        }
        if (text != "0") {
            button.setOnClickListener(this)
        }

        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        val tileSize = getDPI(70 , metrics);

        val params = LinearLayout.LayoutParams(tileSize, tileSize)
        params.setMargins(3, 3, 3, 3)
        button.layoutParams = params
        return button
    }

    private fun getOneRandomNumber(colIndex: Int): Int {
        val rand = Random()
        return when (colIndex) {
            0 -> {
                val min = 1
                val max = 9
                rand.nextInt(max - min + 1) + min
            }

            8 -> {
                val min = colIndex * 10
                val max = min + 10
                rand.nextInt(max - min + 1) + min
            }

            else -> {
                val min = colIndex * 10
                val max = min + 9
                rand.nextInt(max - min + 1) + min
            }
        }
    }

    private fun getTwoRandomNumbers(colIndex: Int): IntArray {
        val rand = Random()
        val twoRandomNumbers = IntArray(2)
        when (colIndex) {
            0 -> {
                val min1 = 1
                val max1 = 5
                twoRandomNumbers[0] = rand.nextInt(max1 - min1 + 1) + min1
                val min2 = 6
                val max2 = 9
                twoRandomNumbers[1] = rand.nextInt(max2 - min2 + 1) + min2
            }

            8 -> {
                val min1 = colIndex * 10
                val max1 = min1 + 5
                twoRandomNumbers[0] = rand.nextInt(max1 - min1 + 1) + min1
                val min2 = max1 + 1
                val max2 = min2 + 4
                twoRandomNumbers[1] = rand.nextInt(max2 - min2 + 1) + min2
            }

            else -> {
                val min1 = colIndex * 10
                val max1 = min1 + 5
                twoRandomNumbers[0] = rand.nextInt(max1 - min1 + 1) + min1
                val min2 = max1 + 1
                val max2 = min2 + 3
                twoRandomNumbers[1] = rand.nextInt(max2 - min2 + 1) + min2
            }
        }
        return twoRandomNumbers
    }

    private fun getThreeRandomNumbers(colIndex: Int): IntArray {
        val rand = Random()
        val threeRandomNumbers = IntArray(3)
        when (colIndex) {
            0 -> {
                val min1 = 1
                val max1 = 3
                threeRandomNumbers[0] = rand.nextInt(max1 - min1 + 1) + min1
                val min2 = 4
                val max2 = 6
                threeRandomNumbers[1] = rand.nextInt(max2 - min2 + 1) + min2
                val min3 = 7
                val max3 = 9
                threeRandomNumbers[2] = rand.nextInt(max3 - min3 + 1) + min3
            }

            8 -> {
                val min1 = colIndex * 10
                val max1 = min1 + 3
                threeRandomNumbers[0] = rand.nextInt(max1 - min1 + 1) + min1
                val min2 = max1 + 1
                val max2 = min2 + 2
                threeRandomNumbers[1] = rand.nextInt(max2 - min2 + 1) + min2
                val min3 = max2 + 1
                val max3 = min3 + 3
                threeRandomNumbers[2] = rand.nextInt(max3 - min3 + 1) + min3
            }

            else -> {
                val min1 = colIndex * 10
                val max1 = min1 + 3
                threeRandomNumbers[0] = rand.nextInt(max1 - min1 + 1) + min1
                val min2 = max1 + 1
                val max2 = min2 + 2
                threeRandomNumbers[1] = rand.nextInt(max2 - min2 + 1) + min2
                val min3 = max2 + 1
                val max3 = min3 + 2
                threeRandomNumbers[2] = rand.nextInt(max3 - min3 + 1) + min3
            }
        }
        return threeRandomNumbers
    }

    override fun onClick(view: View?) {
        view!!.setBackgroundColor(Color.RED)
        count++
        if (count == 5) {
            binding.chip5Numbers.chipBackgroundColor = getColorStateList(R.color.purple_500)
            binding.animationSuccess.playAnimation()
            musicRaise.start()
        } else if (count == 15) {
            binding.chipFull.chipBackgroundColor = getColorStateList(R.color.purple_500)
            binding.animationSuccess.playAnimation()
            musicFull.start()
            showFullScreenAd()
        }

        if (view.tag.toString().startsWith("0")) {
            countLine1++
            if (countLine1 == 5) {
                binding.chipLine1.chipBackgroundColor = getColorStateList(R.color.purple_500)
                binding.animationSuccess.playAnimation()
                musicRaise.start()
            }
        } else if (view.tag.toString().startsWith("1")) {
            countLine2++
            if (countLine2 == 5) {
                binding.chipLine2.chipBackgroundColor = getColorStateList(R.color.purple_500)
                binding.animationSuccess.playAnimation()
                musicRaise.start()
            }
        } else if (view.tag.toString().startsWith("2")) {
            countLine3++
            if (countLine3 == 5) {
                binding.chipLine3.chipBackgroundColor = getColorStateList(R.color.purple_500)
                binding.animationSuccess.playAnimation()
                musicRaise.start()
            }
        }
    }

    private fun getDPI(size: Int, metrics: DisplayMetrics): Int {
        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT
    }
}