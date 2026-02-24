package com.aryan.reader.paginatedreader

object Woff2Converter {

    init {
        System.loadLibrary("native-lib")
    }

    /**
     * Converts a WOFF2 font file into a TTF font file.
     *
     * @param woff2Data The raw byte array of the WOFF2 file.
     * @return A byte array of the converted TTF file, or null if conversion fails.
     */
    external fun convertWoff2ToTtf(woff2Data: ByteArray): ByteArray?
}