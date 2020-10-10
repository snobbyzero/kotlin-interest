package com.example.kotlin_interest.model

import java.io.Serializable
import javax.inject.Inject

class Interest (val id: Long, val name: String, val categoryId: Long) : Serializable {
}
