package com.example.myapplication

import com.google.firebase.firestore.PropertyName
import com.google.j2objc.annotations.Property

data class User(
    var uid:String="", var email:String="",var profileurl:String="",var filename:String="",var nickname:String="")
