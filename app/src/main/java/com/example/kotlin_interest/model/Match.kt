package com.example.kotlin_interest.model

import java.io.Serializable
import javax.inject.Inject

class Match (val id: Long, val userId: Long, val likedId: Long) : Serializable {
}