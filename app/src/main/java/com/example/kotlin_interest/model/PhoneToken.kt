package com.example.kotlin_interest.model

import java.io.Serializable
import javax.inject.Inject

class PhoneToken @Inject constructor(val token: String) : Serializable {

}