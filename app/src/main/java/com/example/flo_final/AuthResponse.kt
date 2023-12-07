package com.example.flo_final

import io.reactivex.internal.operators.single.SingleDoOnSuccess

class AuthResponse(val isSuccess: Boolean, val code:Int, val message:String)