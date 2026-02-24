package com.aryan.reader.data

/**
 * Agnostic representation of a purchase to decouple MainViewModel from Billing Library.
 */
data class PurchaseEntity(
    val orderId: String?,
    val products: List<String>,
    val purchaseToken: String,
    val purchaseTime: Long,
    val isAcknowledged: Boolean,
    val isAutoRenewing: Boolean
)

/**
 * Agnostic representation of product details.
 */
data class ProductDetailsEntity(
    val productId: String,
    val name: String,
    val description: String,
    val formattedPrice: String,
    val currencyCode: String,
    val priceAmountMicros: Long
)