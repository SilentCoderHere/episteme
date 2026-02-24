package com.aryan.reader.epub

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

fun parseXMLFile(inputSteam: InputStream): Document? =
    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSteam)

fun parseXMLFile(byteArray: ByteArray): Document? = parseXMLFile(byteArray.inputStream())

fun String.asFileName(): String = this.replace("/", "_")

fun Document.selectFirstTag(tag: String): Node? = getElementsByTagName(tag).item(0)
fun Node.selectFirstChildTag(tag: String) = childElements.find { it.tagName == tag }
fun Node.selectChildTag(tag: String) = childElements.filter { it.tagName == tag }
fun Node.getAttributeValue(attribute: String): String? =
    attributes?.getNamedItem(attribute)?.textContent

val NodeList.elements get() = (0..length).asSequence().mapNotNull { item(it) as? Element }
val Node.childElements get() = childNodes.elements

