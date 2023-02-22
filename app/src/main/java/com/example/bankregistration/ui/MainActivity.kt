package com.example.bankregistration.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.example.bankregistration.R
import com.example.bankregistration.databinding.ActivityMainBinding
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mBindingMainActivity: ActivityMainBinding
    lateinit var panEditText: AppCompatEditText
    lateinit var panValue: String
    var panIdStatus: Boolean = false
    var dateStatus: Boolean = false
    var monthStatus: Boolean = false
    var yearStatus: Boolean = false

    lateinit var date: String
    lateinit var month: String
    lateinit var year: String

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBindingMainActivity.root)

        setSpannableString()

        panEditText = mBindingMainActivity.panCardEditText

        if (panEditText.text.toString().isEmpty()) {
            mBindingMainActivity.next.isEnabled = false
            mBindingMainActivity.next.background = getDrawable(R.drawable.button_shape_disable)
        }

        panEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                println("Tag 1 :- $s")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("Tag 2 :- $s")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                panValue = s.toString()
                println("Tag 3 :- $s")
                println("Tag 4 :- $start")
                println("Tag 5 :- $before")
                println("Tag 6 :- $count")

                val pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
                val matcher = pattern.matcher(panValue)

                if (s.toString().trim().length != 10) {
                    mBindingMainActivity.next.isEnabled = false
                    mBindingMainActivity.next.background =
                        getDrawable(R.drawable.button_shape_disable)
                    panIdStatus = false
                } else {
                    if (matcher.matches()) {
                        mBindingMainActivity.next.isEnabled = true
                        mBindingMainActivity.next.background = getDrawable(R.drawable.button_shape)
                        panIdStatus = true
                    } else {
                        mBindingMainActivity.next.isEnabled = false
                        mBindingMainActivity.next.background =
                            getDrawable(R.drawable.button_shape_disable)
                        panIdStatus = false
                    }
                }
            }
        })

//      DateEditText
        mBindingMainActivity.dateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    mBindingMainActivity.dateEditText.error = "Enter Date"
                } else {
                    if (s.toString().toInt() < 1 || s.toString().toInt() > 31) {
                        dateStatus = false;
                        date = s.toString()

                        mBindingMainActivity.next.isEnabled = false
                        mBindingMainActivity.next.background =
                            getDrawable(R.drawable.button_shape_disable)
                    } else {
                        dateStatus = true
                        date = s.toString()

                        mBindingMainActivity.next.isEnabled = true
                        mBindingMainActivity.next.background = getDrawable(R.drawable.button_shape)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

//      MonthEditText
        mBindingMainActivity.monthEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isEmpty()) {
                    mBindingMainActivity.monthEditText.error = "Enter Month"
                } else {
                    if (s.toString().toInt() < 1 || s.toString().toInt() > 12) {
                        monthStatus = false
                        month = s.toString()

                        mBindingMainActivity.next.isEnabled = false
                        mBindingMainActivity.next.background =
                            getDrawable(R.drawable.button_shape_disable)
                    } else {
                        monthStatus = true
                        month = s.toString()

                        mBindingMainActivity.next.isEnabled = true
                        mBindingMainActivity.next.background = getDrawable(R.drawable.button_shape)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

//      YearEditText
        mBindingMainActivity.yearsEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val calendar = Calendar.getInstance();
                val yearValue = calendar.get(Calendar.YEAR)

                println("My Calendar Year: - $yearValue")

                if (s.toString().isEmpty()) {
                    mBindingMainActivity.yearsEditText.error = "Enter Year"
                } else {
                    if (s.toString().length == 4) {
                        if (s.toString().toInt() < 1900 || s.toString().toInt() > yearValue) {
                            mBindingMainActivity.next.isEnabled = false
                            mBindingMainActivity.next.background =
                                getDrawable(R.drawable.button_shape_disable)

//                            LeapYear
                            if (s.toString().toInt() % 4 == 0 || s.toString()
                                    .toInt() % 400 == 0 && (s.toString().toInt() % 100 != 0)
                            ) {
                                year = s.toString()
                                yearStatus = true
                            } else {
                                yearStatus = false
                                year = s.toString()
                            }

                        } else {
                            mBindingMainActivity.next.isEnabled = true
                            mBindingMainActivity.next.background =
                                getDrawable(R.drawable.button_shape)
                        }
                    } else {
                        yearStatus = false
                        year = s.toString()

                        mBindingMainActivity.next.isEnabled = false
                        mBindingMainActivity.next.background =
                            getDrawable(R.drawable.button_shape_disable)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        mBindingMainActivity.next.setOnClickListener(this)
        mBindingMainActivity.doNotHavePan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.next -> {

                if (mBindingMainActivity.dateEditText.text.toString().isEmpty()) {
                    mBindingMainActivity.dateEditText.error = "Enter Date"
                } else if (mBindingMainActivity.monthEditText.text.toString().isEmpty()) {
                    mBindingMainActivity.monthEditText.error = "Enter Month"
                } else if (mBindingMainActivity.yearsEditText.text.toString().isEmpty()) {
                    mBindingMainActivity.yearsEditText.error = "Enter Year"
                } else {
                    Toast.makeText(this, "Details submitted successfully", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            R.id.doNotHavePan -> {
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setSpannableString() {
        val spannable =
            SpannableString(getString(R.string.providing_pan_amp_date_of_birth_helps_us_find_and_fetch_your_kyc_from_a_center_registry_by_the_government_of_india))
        spannable.setSpan(
            ForegroundColorSpan(getColor(R.color.violet)), 117, // start
            125, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        mBindingMainActivity.descrption.text = spannable
    }
}