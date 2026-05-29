package com.example.practice

import org.junit.Assert
import org.junit.Test

class LinkedListTest {

    @Test
    fun `check if item is inserted in linked list`() {
        val linkedList = LinkedList<Int>()
        linkedList.push(1)
        val x = linkedList.nodeAt(0)
        Assert.assertEquals(1, x?.value)
    }

    @Test
    fun `check if item is removed from linked list`() {
        val list = LinkedList<Int>()
        list.append(1).append(2).append(3).append(4)
        println(list)

        val removedValue = list.removeAt(2)
        println(list)
        Assert.assertEquals(3, removedValue)
    }

    @Test
    fun `removing index greater than list length should return null`() {
        val list = LinkedList<Int>()
        list.append(1).append(2).append(3)

        val removedValue = list.removeAt(3)
        Assert.assertEquals(null, removedValue)
    }

    @Test
    fun `removing existing value should remove it from the list`() {
        val list = LinkedList<Int>()
        list.append(1).append(2).append(3).append(4)
        println(list)

        val removedValue = list.removeValue(4)
        println(list)
        println("head -> ${list.head?.value}")
        println("tail -> ${list.tail?.value}")
        Assert.assertEquals(4, removedValue)
    }

    @Test
    fun `removing non-existent value should return null`() {
        val list = LinkedList<Int>()
        list.append(1).append(2).append(3).append(4)
        println(list)

        val removedValue = list.removeValue(5)
        println(list)
        println("head -> ${list.head?.value}")
        println("tail -> ${list.tail?.value}")
        Assert.assertEquals(null, removedValue)
    }
}