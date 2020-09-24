package com.example.chattingapp

import java.io.Serializable

class ChatData: Serializable {
    private var msg: String = ""
    private var nickname: String = ""

    fun getMsg(): String {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun getNickname(): String {
        return nickname
    }

    fun setNickname(nickname: String) {
        this.nickname = nickname
    }
}