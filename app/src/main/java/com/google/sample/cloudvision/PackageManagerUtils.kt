package com.google.sample.cloudvision

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature

import com.google.common.io.BaseEncoding

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Provides utility logic for getting the app's SHA1 signature. Used with restricted API keys.

 */
object PackageManagerUtils {

    /**
     * Gets the SHA1 signature, hex encoded for inclusion with Google Cloud Platform API requests

     * @param packageName Identifies the APK whose signature should be extracted.
     * *
     * @return a lowercase, hex-encoded
     */
    fun getSignature(pm: PackageManager, packageName: String): String? {
        try {
            val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            if (packageInfo == null
                    || packageInfo.signatures == null
                    || packageInfo.signatures.size == 0
                    || packageInfo.signatures[0] == null) {
                return null
            }
            return signatureDigest(packageInfo.signatures[0])
        } catch (e: PackageManager.NameNotFoundException) {
            return null
        }

    }

    private fun signatureDigest(sig: Signature): String? {
        val signature = sig.toByteArray()
        try {
            val md = MessageDigest.getInstance("SHA1")
            val digest = md.digest(signature)
            return BaseEncoding.base16().lowerCase().encode(digest)
        } catch (e: NoSuchAlgorithmException) {
            return null
        }

    }
}