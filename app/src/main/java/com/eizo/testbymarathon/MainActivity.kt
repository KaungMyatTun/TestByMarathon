package com.eizo.testbymarathon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    lateinit var display: TextView
    lateinit var btn_solution1: Button
    lateinit var btn_solution2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.solution)
        btn_solution1 = findViewById(R.id.solution1)
        btn_solution2 = findViewById(R.id.solution2)

        val example1: String = "Example 1"
        val inputExample1: String = "https://google.com?a=1&b=2&a=2"
        val resultExample1: String? = stripUrl(inputExample1)
        val toShowExample: String =
            "$example1\nInput::   $inputExample1\nResult::   $resultExample1"

        var arr1: Array<String> = arrayOf("b")
        val example2: String = "Example 2"
        val inputExample2: String = "https://google.com?a=1&b=2&a=2,[\"b\"]"
        val resultExample2: String? = stripUrl("https://google.com?a=1&b=2&a=2", arr1)
        val toShowExample2: String =
            "$example2\nInput::   $inputExample2\nResult::   $resultExample2"

        val example3: String = "Example 3"
        var arr2: Array<String> = arrayOf("b")
        var secondArg: String = ""
        for (value in arr2[0]) {
            secondArg = value.toString()
        }
        val inputExample3: String = "https://google.com,[\"$secondArg\"]"
        val resultExample3: String? = stripUrl("https://google.com", arr2)
        val toShowExample3: String =
            "$example3\nInput::   $inputExample3\nResult::   $resultExample3"

        btn_solution1.setOnClickListener(View.OnClickListener {
            display.text =
                toShowExample + "\n\n\n\n\n" + toShowExample2 + "\n\n\n\n\n" + toShowExample3
        })

        val list3 = ArrayList<String>()

        var arbitray1 = ArrayList<ArrayList<ArrayList<String>>>()
        arbitray1 = arrayListOf(
            arrayListOf(arrayListOf("1", "five", "2wenty", "thr33"))
        )

        // second example
        var arbitray2 = ArrayList<ArrayList<ArrayList<String>>>()
        arbitray2 = arrayListOf(
            arrayListOf(arrayListOf("1X2", "t3n"), arrayListOf("1024", "5", "64"))
        )

        // third example
        var arbitray3 = ArrayList<ArrayList<ArrayList<String>>>()
        arbitray3 = arrayListOf(
            arrayListOf(arrayListOf("0", "0x2", "z3r1"), arrayListOf("1", "55a46")),
            arrayListOf(
                arrayListOf("1", "2", "4"),
                arrayListOf("0x5fp-2", "nine", "9"),
                arrayListOf("4", "4", "4")
            ),
            arrayListOf(arrayListOf("03")),
            arrayListOf(arrayListOf())
        )

        btn_solution2.setOnClickListener(View.OnClickListener {
            display.text = "Result of Example 1 >>>> " + sum(arbitray1) + "\n\n\n\n" + "Result of Example 2 >>>> " + sum(arbitray2) + "\n\n\n\n" + "Result of Example 3 >>>> " + sum(arbitray3)
        })
    }


    private fun stripUrl(originalUrl: String, vararg arr: Array<String>?): String? {
        var lastValueOfDuplicateIndex: Int
        var combineParameter: String = ""
        var combineSign: String = ""
        var stripUrl: String = ""

        val urlParts = originalUrl.split("?")
        if (urlParts.size >= 2) {
            // last part after '?'
            val staff = urlParts[1]
            val pairs = staff.split("&")

            val firstValue = arrayListOf<String>()
            val secondValue = arrayListOf<String>()

            for (content in pairs) {
                val spl = content.split("=")
                firstValue.add(spl[0])
                secondValue.add(spl[1])
            }

            if (arr.isNotEmpty()) {
                for (value in arr[0]!!) {
                    for (index: Int in 0..firstValue.size - 1) {
                        if (value == firstValue[index]) {
                            firstValue[index] = "-1"
                        }
                    }
                }
            }

            for (i: Int in 0 until (firstValue.size)) {
                lastValueOfDuplicateIndex = secondValue[i].toInt()
                for (j: Int in i + 1 until firstValue.size) {
                    if (firstValue[i] == firstValue[j]) {
                        firstValue[j] = "-1"
                        lastValueOfDuplicateIndex = secondValue[j].toInt();
                    }
                }
                combineSign = if (i == 0) {
                    ""
                } else
                    "&"

                if (firstValue[i] !== "-1")
                    combineParameter += combineSign + firstValue[i] + "=" + lastValueOfDuplicateIndex.toString()
                stripUrl = urlParts[0] + "?" + combineParameter
            }
            return stripUrl
        } else {
            return originalUrl
        }
    }

    private fun sum(arr: ArrayList<ArrayList<ArrayList<String>>>) :String {
        var resultArr: ArrayList<String>? = arrayListOf()
        for (first: Int in 0 until arr.size) {
            for (second: Int in 0 until arr[first].size) {
                for (third: Int in 0 until arr[first][second].size) {
//                    Log.e("input string", arr[first][second][third])
                    if (find(arr[first][second][third])?.size != 0) {
                        Log.e("result list size", find(arr[first][second][third])?.size.toString())
                        for (value in find(arr[first][second][third])!!) {
                            if (value.contains("-")) {
                                var spl = value.split("-")
                                for (index: Int in spl.indices) {
                                    if (index == 0 && spl[index] != "") {
                                        resultArr?.add(spl[index])
                                    } else if (index != 0 && spl[index] != "") {
                                        resultArr?.add("-" + spl[index])
                                    }
                                }
                            } else {
                                resultArr?.add(value)
                            }
                        }
                    }
                }
            }
        }
        var sumResult: Int = 0
        for (value in resultArr!!) {
            sumResult += value.toInt()
            Log.e("result list", sumResult.toString())
        }
        return sumResult.toString()
    }

    private fun find(inputString: String): ArrayList<String>? {
        var temp: String = ""
        var numericList: ArrayList<String> = arrayListOf()
        for (i: Int in inputString.indices) {
//            Log.e("each value", inputString[i].toString())
            if (isDigit(inputString[i].toString()) || inputString[i].toString() == "-") {
                temp += inputString[i].toString()
//                Log.e("testing", inputString[i] + temp)
            } else if (!isDigit(inputString[i].toString()) && temp != "") {
                numericList.add(temp)
//                Log.e("temp value", temp)
                temp = ""
            }
        }
        if (temp != "") {
            numericList.add(temp)
        }

//        Log.e("numeric list size", numericList.size.toString())
        return numericList
    }

    private fun isDigit(str: String): Boolean {
        var _isDigit: Boolean = true
        try {
            var e = str.toDouble()
        } catch (e: NumberFormatException) {
            _isDigit = false
        }
        return _isDigit
    }
}