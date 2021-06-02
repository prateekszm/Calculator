package com.example.personalcalculator

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var tvWorking: TextView
    // private val displayOperation by lazy ( LazyThreadSafetyMode.NONE ) {findViewById<TextView>(R.id.tvWorkings)}

    //variables to check the backbone
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.splashScreenTheme)
        setContentView(R.layout.activity_main)

        //to hide action bar by code
        supportActionBar?.hide()
        //to hide status bar and simply change status bar color to Activity theme
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.design_default_color_on_secondary)
        //window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN


        //for all calculation process shown
        tvResult = findViewById(R.id.tvResult)
        tvWorking = findViewById(R.id.tvWorkings)

        //buttons
        val btnZero: Button = findViewById(R.id.btnZero)
        val btnOne: Button = findViewById(R.id.btnOne)
        val btnTwo: Button = findViewById(R.id.btnTwo)
        val btnThree: Button = findViewById(R.id.btnThree)
        val btnFour: Button = findViewById(R.id.btnFour)
        val btnFive: Button = findViewById(R.id.btnFive)
        val btnSix: Button = findViewById(R.id.btnSix)
        val btnSeven: Button = findViewById(R.id.btnSeven)
        val btnEight: Button = findViewById(R.id.btnEight)
        val btnNine: Button = findViewById(R.id.btnNine)
        val btnDot: Button = findViewById(R.id.btnDot)

        //Operations Buttons
        val btnEquals = findViewById<Button>(R.id.btnEqual)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        //val btnModulus = findViewById<Button>(R.id.btnModulus)


        val listener = View.OnClickListener { v ->
            val b = v as Button
            tvWorking.append(b.text)
            lastNumeric = true

        }
        btnZero.setOnClickListener(listener)
        btnOne.setOnClickListener(listener)
        btnTwo.setOnClickListener(listener)
        btnThree.setOnClickListener(listener)
        btnFour.setOnClickListener(listener)
        btnFive.setOnClickListener(listener)
        btnSix.setOnClickListener(listener)
        btnSeven.setOnClickListener(listener)
        btnEight.setOnClickListener(listener)
        btnNine.setOnClickListener(listener)
        //btnDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            if (lastNumeric && !isOperatorAdded(tvWorking.text.toString())) {
                tvWorking.append(op)
                lastNumeric = false
                lastDot = false
            }

        }
        btnDivide.setOnClickListener(opListener)
        btnMinus.setOnClickListener(opListener)
        btnPlus.setOnClickListener(opListener)
        btnMultiply.setOnClickListener(opListener)
        //btnModulus.setOnClickListener(opListener)


        val btnClear: Button = findViewById(R.id.btnClear)
        btnClear.setOnClickListener {
            tvWorking.text = ""
            tvResult.text = ""
            lastDot = false
            lastNumeric = false
        }

        //For the Dot using LastNumeric and lastDot Boolean values
        btnDot.setOnClickListener {
            if (lastNumeric && !lastDot) {
                tvWorking.append(".")
                lastNumeric = false
                lastDot = true
            }
        }

        btnEquals.setOnClickListener {
            var operation: String = tvWorking.toString()
            if(lastNumeric){
                val result: String = isEqual()
                tvResult.text = result
            }

        }


    }


    fun isEqual():String{
        var resultDone: String? =null
        if (lastNumeric) {
            var prefix=""
            var result: String = tvWorking.text.toString()

            try {
                if (result.startsWith("-")){
                    prefix= "-"
                    result =result.substring(1)
                }
                when {
                    result.contains("-") -> {
                        val splitValue = result.split("-")
                        var operand1 = splitValue[0]
                        val operand2 = splitValue[1]
                        if (prefix.isNotEmpty()){
                            operand1=prefix+operand1

                        }
                        //tvResult.text = (operand1.toDouble() - operand2.toDouble()).toString()
                        resultDone=reduceDotAndZero((operand1.toDouble() -   operand2.toDouble()).toString())

                    }
                    result.contains("+") -> {
                        val splitValue = result.split("+")
                        var operand1 = splitValue[0]
                        val operand2 = splitValue[1]
                        if (prefix.isNotEmpty()){
                            operand1=prefix+operand1

                        }
                        //tvResult.text = (operand1.toDouble() - operand2.toDouble()).toString()
                        resultDone=reduceDotAndZero((operand1.toDouble() + operand2.toDouble()).toString())

                    }
                    result.contains("÷") -> {
                        val splitValue = result.split("÷")
                        var operand1 = splitValue[0]
                        val operand2 = splitValue[1]
                        if (prefix.isNotEmpty()){
                            operand1=prefix+operand1

                        }
                        //tvResult.text = (operand1.toDouble() - operand2.toDouble()).toString()
                        resultDone=reduceDotAndZero((operand1.toDouble() / operand2.toDouble()).toString())

                    }
                    result.contains("×") -> {
                        val splitValue = result.split("×")
                        var operand1 = splitValue[0]
                        val operand2 = splitValue[1]
                        if (prefix.isNotEmpty()){
                            operand1=prefix+operand1

                        }
                        //tvResult.text = (operand1.toDouble() - operand2.toDouble()).toString()
                        resultDone=reduceDotAndZero((operand1.toDouble() * operand2.toDouble()).toString())

                    }
                }

            } catch (e: ArithmeticException) {
                Toast.makeText(
                    this,
                    "Some Error! \n Hold On We Are Not Going To Fix Please Download Other Calculator",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return resultDone!!

    }

    private fun reduceDotAndZero(result:String):String{
        var noZero:String=result
        if (result.contains(".0")){
             noZero=result.substring(0,result.length-2)
        }
        return noZero

    }
            //-------------------------------Button onClick Xml---------------------//
//    fun isEqual(view: View){
//        if (lastNumeric) {
//            var result: String = tvWorking.text.toString()
//
//            try {
//                if (result.contains("-")) {
//                    val splitValue = result.split("-")
//                    val operand1 = splitValue[0]
//                    val operand2 = splitValue[1]
//                    tvResult.text = (operand1.toDouble() - operand2.toDouble()).toString()
//
//                }
//
//            } catch (e: ArithmeticException) {
//                Toast.makeText(
//                    this,
//                    "Some Error! \n Hold On We Are Not Going To Fix Please Download Other Calculator",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//
//    }

         //----------------------ButtonClick XML END----------------------------//

    private fun isOperatorAdded(value: String): Boolean {

        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("+") ||
                    value.contains("×") ||
                    value.contains("÷")
        }
    }


}



















