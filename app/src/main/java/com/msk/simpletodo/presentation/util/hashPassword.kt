package com.msk.simpletodo.presentation.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val SECRET_KEY = "ABCDEFGH12345678"

fun String.encryptECB(): String {
    val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)
    val cipherText = cipher.doFinal(this.toByteArray())
    val encodedByte = Base64.encode(cipherText, Base64.DEFAULT)
    return String(encodedByte)
}

fun String.decryptECB(): String {
    val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
    var decodeByte: ByteArray = Base64.decode(this, Base64.DEFAULT)
    val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    cipher.init(Cipher.DECRYPT_MODE, keySpec)
    val output = cipher.doFinal(decodeByte)
    return String(output)
}