package com.example.practice

data class Node<T>(var value: T, var next: Node<T>? = null) {
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}

class LinkedList<T> {
    var head: Node<T>? = null
    var tail: Node<T>? = null
    private var size = 0

    fun isEmpty(): Boolean {
        return size == 0
    }

    override fun toString(): String {
        return if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }
    }

    fun push(value: T): LinkedList<T> {
        head = Node(value = value, next = head)

        if (isEmpty()) {
            tail = head
        }
        size++
        return this
    }

    fun append(value: T): LinkedList<T> {
        val newNode = Node(value = value)
        if (isEmpty()) {
            push(value)
            return this
        }
        tail?.next = newNode
        tail = tail?.next
        size++
        return this
    }

    fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0

        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }

        return currentNode
    }

    fun insertAt(value: T, index: Int): Node<T> {
        val afterNode = nodeAt(index)
        if (tail == afterNode) {
            append(value)
            return tail!!
        }

        val newNode = Node(value = value, next = afterNode?.next)
        afterNode?.next = newNode
        size++
        return newNode
    }

    fun removeValue(value: T): T? {
        var currentNode = head
        var removalNode: Node<T>? = null

        if (currentNode?.value == value) {
            removalNode = head
            head = currentNode.next
            size--
            return removalNode?.value
        }

        while (currentNode != null) {
            if (currentNode.next?.value == value) {
                removalNode = currentNode.next
                currentNode.next = removalNode?.next
                if (removalNode?.next == null) {
                    tail = currentNode
                }
                size--
                break
            } else {
                currentNode = currentNode.next
            }
        }

        return removalNode?.value
    }

    fun removeAt(index: Int): T? {
        if (index == 0) {
            val value = head?.value
            head = head?.next
            size--
            if (isEmpty())
                tail = null
            return value
        }

        val removalNode = nodeAt(index)
        val nodeBefore = nodeAt(index - 1)
        nodeBefore?.next = removalNode?.next
        if (removalNode == tail) {
            tail = nodeBefore
        }
        size--
        return removalNode?.value
    }

}